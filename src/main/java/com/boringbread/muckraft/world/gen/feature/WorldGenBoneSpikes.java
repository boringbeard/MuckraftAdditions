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
    private static final IBlockState BONE = Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
    private final EnumFacing direction;

    public WorldGenBoneSpikes(EnumFacing direction)
    {
        this.direction = direction;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, direction);

        if (!validSpots.isEmpty())
        {
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));

            genLargeStalactite(worldIn, rand, placementPos, rand.nextInt(16) == 0 ? 36 : 16, direction.getOpposite());
        }
        return true;
    }

    private void genLargeStalactite(World worldIn, Random rand, BlockPos basePos, int size, EnumFacing direction)
    {
        List<BlockPos> spikeFootprint = new LinkedList<>();
        int length = (int) MathHelper.clamp((rand.nextGaussian() + size), 0, 50);
        int radius = MathHelper.clamp(length / 8 + (int) rand.nextGaussian(), 1, 20);

        for (int y = -radius; y <= radius; y++)
        {
            int x1 = (int) Math.sqrt(radius * radius - y * y);
            for (int x = -x1; x <= x1; x++)
            {
                spikeFootprint.add(new BlockPos(basePos.getX() + x, basePos.getY(), basePos.getZ() + y));
            }
        }

        for (BlockPos pos: spikeFootprint)
        {
            double distance = basePos.getDistance(pos.getX(), pos.getY(), pos.getZ());
            int lengthForGen = (int) (length - (distance * 8 + rand.nextGaussian() * length / 16));
            for (int i = 0; i < lengthForGen; i++)
            {
                if (worldIn.isAirBlock(pos)) worldIn.setBlockState(pos, BONE);
                pos = pos.offset(direction);
            }
        }
    }

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
