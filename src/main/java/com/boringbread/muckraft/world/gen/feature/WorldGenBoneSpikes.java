package com.boringbread.muckraft.world.gen.feature;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenBoneSpikes extends WorldGenerator
{
    //TO DO: get rid of the old stuff from stacked biomes
    private static final IBlockState BONE = Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
    private final EnumFacing direction;

    public WorldGenBoneSpikes(EnumFacing direction)
    {
        this.direction = direction;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        //find random surfaces in the given direction
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, direction);

        if (!validSpots.isEmpty())
        {
            //get random surface from list that we found earlier
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));

            //generate a large stalactite with length either being 16 or having a 1/16 chance of being 36 instead (in the end the length is slightly varied using a gaussian in genLargeStalactite)
            genLargeSpike(worldIn, rand, placementPos, rand.nextInt(16) == 0 ? 36 : 16, direction.getOpposite());
        }
        return true;
    }

    private void genLargeSpike(World worldIn, Random rand, BlockPos basePos, int size, EnumFacing direction)
    {
        //starts of by creating a circular "footprint" of the spike, and then creating a vertical column in each block in the footprint
        //the length of the column is longer the closer it is to the center of the footprint, thereby creating a cone looking shape

        //first create a list that will contain blockpositions in the spike footprint
        List<BlockPos> spikeFootprint = new LinkedList<>();
        //randomize length of spike and radius of footprint. Radius of the footprint will be about 1/8 of the length
        int length = (int) MathHelper.clamp((rand.nextGaussian() + size), 0, 50);
        int radius = MathHelper.clamp(length / 8 + (int) rand.nextGaussian(), 1, 20);

        //next part finds all blocks within the radius of the footprint from the base position (basepos will end up being in the center) Theres probably a more efficient way to do this
        //TO DO: maybe make this a separate method for future use or something
        //TO DO: we only really have to solve for an eighth of the circle because these are blocks, so do that
        for (int y = -radius; y <= radius; y++)
        {
            //for each y value, it finds out the corresponding x value that would be on the perimeter of the circle
            //it does this by finding the x value that would make the distance from center equal to the radius
            //the math here is just the pythagorean theorem: radius^2 = x^2 + y^2
            //we're trying to solve for our x value so we just rewrite it to x = sqrt(radius^2 - y^2)
            int x1 = (int) Math.sqrt(radius * radius - y * y);

            //once we have the x value on the outside of the circle, we just add each position in a line to the other side of the circle (which will just be -x) to our list of positions
            //because we do this for each y value from top to bottom, we end up with a completed circle
            for (int x = -x1; x <= x1; x++)
            {
                spikeFootprint.add(new BlockPos(basePos.getX() + x, basePos.getY(), basePos.getZ() + y));
            }
        }
        //we now have a list of all blocks in a circle around our base position, which we will call our "footprint"

        //for each block position in the footprint, create a vertical column
        for (BlockPos pos: spikeFootprint)
        {
            //distance is the distance from the center of the footprint our point is
            double distance = basePos.getDistance(pos.getX(), pos.getY(), pos.getZ());

            //the length of the column will be increase in inverse to the distance to the center
            //the way its done here is just we subtract distance * 8 + a bit of random variation (deviates about 1/16th of the total length)
            //from the overall length to get the length of the single column
            int lengthForGen = (int) (length - (distance * 8 + rand.nextGaussian() * length / 16));

            //fill in the column unless it hits bedrock
            for (int i = 0; i < lengthForGen; i++)
            {
                if (worldIn.getBlockState(pos) != Blocks.BEDROCK.getDefaultState()) worldIn.setBlockState(pos, BONE);
                pos = pos.offset(direction);
            }
        }
    }

    //has its own surface finding method that makes sure its not just on the edge of a surface
    //TO DO: get rid of this and put it somewhere else or integrate it into the normal surface finding code
    private List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth, EnumFacing direction)
    {
        List<BlockPos> positions = new LinkedList<>();

        for (int i = 0; i < layerDepth; i++)
        {
            BlockPos newPos = pos.offset(direction);

            for (int j = 0; j < 8; j++)
            {
                EnumFacing direction1 = scanSurroundingBlocks(newPos, worldIn);
                if (direction1 == null)
                {
                    if (worldIn.isAirBlock(newPos.offset(direction.getOpposite())) && !worldIn.isAirBlock(newPos)) positions.add(newPos.offset(direction.getOpposite()));
                    else break;
                }
                else newPos = newPos.offset(direction1.getOpposite());
            }

            pos = pos.offset(direction);
        }

        return positions;
    }

    //private method for the surface finder above; makes sure that there are a few blocks surrounding the block we found so that the spike isnt dangling off an edge
    private EnumFacing scanSurroundingBlocks(BlockPos pos, World worldIn)
    {
        BlockPos scannedBlock = pos;
        EnumFacing direction = EnumFacing.NORTH;
        for (int i = 0; i < 4; i++)
        {
            if (worldIn.isAirBlock(pos.offset(direction)) || worldIn.isAirBlock(pos.offset(direction, 2))) return direction;
            direction = direction.rotateY();
        }
        return null;
    }
}
