package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.List;
import java.util.Random;

public class WorldGenInfestedTrees extends WorldGenAbstractTree implements IWorldGenMuck
{
    //TO DO: get rid of the old stuff from stacked biomes
    private static final IBlockState TRUNK = SRPBlocks.InfestedTrunk.getDefaultState();
    private static final IBlockState VEINS = SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FEELER);

    public WorldGenInfestedTrees()
    {
        super(false);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        //find random ground surfaces
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.DOWN, SRPBlocks.ParasiteStain.getDefaultState());

        if (!validSpots.isEmpty())
        {
            //get one of the surfaces found
            position = validSpots.get(rand.nextInt(validSpots.size()));

            //kind of creates root type things
            for (EnumFacing direction: EnumFacing.HORIZONTALS) //repeats in each horizontal direction; north, east, south, west
            {
                //go 1 block out in the current direction and place a block
                worldIn.setBlockState(position.offset(direction), VEINS);
                //place a block 2 blocks out in the current direction and 1 block down
                worldIn.setBlockState(position.offset(direction, 2).down(), VEINS);
                //place a block 3 blocks out in the current direction and 1 block down
                worldIn.setBlockState(position.offset(direction, 3).down(), VEINS);
                //End shape looks like below (Xs are blocks)
                // X
                //  XX
            }

            //generate part of the trunk; straight up for about 4 blocks (give or take based on random gaussian distribution)
            for (int i = 0; i < rand.nextGaussian() + 4; i++)
            {
                worldIn.setBlockState(position, TRUNK);
                position = position.up();
            }
            //generate branches at this point up the trunk
            generateBranches(worldIn, rand, position, rand.nextDouble() * 2);

            //shift the trunk position by a random x and z value between -1 and 1
            int x = (int) MathHelper.clamp(rand.nextGaussian(), -1, 1);
            int z = (int) MathHelper.clamp(rand.nextGaussian(), -1, 1);
            position = position.add(x, 0, z);

            //do the next part of the trunk from the position we just found on top of the last half of the trunk and make it about 3 blocks high
            for (int i = 0; i < rand.nextGaussian() + 3; i++)
            {
                worldIn.setBlockState(position, TRUNK);
                position = position.up();
            }
            //generate more branches on top
            generateBranches(worldIn, rand, position, rand.nextDouble() * 2);
        }
        return true;
    }

    private void generateBranches(World worldIn, Random rand, BlockPos position, double slope)
    {
        //generates branches in 4 directions given a slope that the branches will follow in the x and z axes
        //the ints x y and z represent shifts from the inputted position where we will be placing branch blocks
        int x = 0;
        int z = 0;

        for (int i = 0; i < rand.nextGaussian() + 2; i++)
        {
            //increments either z or x while randomly incrementing the other depending on slope
            if(slope >= 1)
            {
                x += rand.nextDouble() / -(slope - 2) < 1 ? 1 : 0;
                z++;
            }
            else
            {
                x++;
                z += rand.nextDouble() < slope ? 1 : 0;
            }
            //will shift in the y axis once the length of the branch exceeds 2
            int y = i == 2 ? -1 : 0;

            //sets blocks with offsets in each of the 4 directions
            //different directions are done by turning the offsets in either x, z, or both negative
            worldIn.setBlockState(position.add(x, y, z), TRUNK);
            worldIn.setBlockState(position.add(z, y, -x), TRUNK);
            worldIn.setBlockState(position.add(-x, y, -z), TRUNK);
            worldIn.setBlockState(position.add(-z, y, x), TRUNK);
        }
    }

    @Override
    protected void setDirtAt(World worldIn, BlockPos pos)
    {
        if (worldIn.getBlockState(pos).getBlock() != SRPBlocks.ParasiteStain)
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, SRPBlocks.ParasiteStain.getDefaultState());
        }
    }
}
