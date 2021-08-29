package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenParasiteGrass extends WorldGenerator
{
    private static final IBlockState INFECTED = SRPBlocks.ParasiteBush.getDefaultState();
    private static final IBlockState ARC = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.ARC);
    private static final IBlockState GRASS = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.GRASS1);
    private static final IBlockState FLOWER = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.FLOWER1);
    private static final IBlockState SPINE = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.SPINE);
    private static final BlockStateEntry[] GRASS_LIST = {new BlockStateEntry(2, INFECTED), new BlockStateEntry(3, ARC), new BlockStateEntry(3, FLOWER), new BlockStateEntry(3, SPINE), new BlockStateEntry(22, GRASS)};

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        IBlockState blockState = ((BlockStateEntry) WeightedRandom.getRandomItem(rand, Arrays.asList(GRASS_LIST))).state;
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32);

        if (!validSpots.isEmpty())
        {
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));

            for (int i = 0; i < (blockState == SPINE ? (int) MathHelper.clamp(rand.nextGaussian() + 3, 1, 8) : 1); i++)
            {
                if (worldIn.isAirBlock(placementPos)) worldIn.setBlockState(placementPos, blockState);
                else break;
                placementPos = placementPos.up();
            }
        }

        return true;
    }

    private List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth)
    {
        List<BlockPos> positions = new LinkedList<>();

        for (int i = 0; i < layerDepth; i++)
        {
            if (worldIn.isAirBlock(pos) && worldIn.getBlockState(pos.down()) == SRPBlocks.ParasiteStain.getDefaultState()) positions.add(pos);
            pos = pos.down();
        }

        return positions;
    }

    public static class BlockStateEntry extends WeightedRandom.Item
    {
        public final IBlockState state;

        public BlockStateEntry(int itemWeightIn, IBlockState state)
        {
            super(itemWeightIn);
            this.state = state;
        }
    }
}
