package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.awt.*;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenBoneStalactites extends WorldGenerator
{
    private static final IBlockState BONE = Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32);

        if (!validSpots.isEmpty())
        {
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));

            genLargeStalactite(worldIn, rand, placementPos);
        }
        return true;
    }

    private void genLargeStalactite(World worldIn, Random rand, BlockPos basePos)
    {
        List<BlockPos> spikeFootprint = new LinkedList<>();
        int length = (int) MathHelper.clamp((rand.nextGaussian() + 12), 8, 20);
        int radius = MathHelper.clamp(length / 6 + (int) rand.nextGaussian(), 1, 4);

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
            int lengthForGen = (int) (length - (distance * 6 + rand.nextGaussian()));
            for (int i = 0; i < lengthForGen; i++)
            {
                if (worldIn.isAirBlock(pos)) worldIn.setBlockState(pos, BONE);
                pos = pos.down();
            }
        }
    }

    private List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth)
    {
        List<BlockPos> positions = new LinkedList<>();

        for (int i = 0; i < layerDepth; i++)
        {
            BlockPos newPos = pos.up();

            for (int j = 0; j < 8; j++)
            {
                EnumFacing direction = scanSurroundingBlocks(newPos, worldIn);
                if (direction == null)
                {
                    if (worldIn.isAirBlock(newPos.down()) && !worldIn.isAirBlock(newPos)) positions.add(newPos.down());
                    else break;
                }
                else newPos = newPos.offset(direction.getOpposite());
            }

            pos = pos.up();
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
