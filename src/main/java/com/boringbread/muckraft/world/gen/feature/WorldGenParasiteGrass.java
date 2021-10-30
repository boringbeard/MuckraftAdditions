package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenParasiteGrass extends WorldGenerator implements IWorldGenMuck
{
    private static final IBlockState INFECTED = SRPBlocks.ParasiteBush.getDefaultState();
    private static final IBlockState ARC = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.TOOH);
    private static final IBlockState GRASS = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.EYE);
    private static final IBlockState FLOWER = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.TENDRIL);
    private static final IBlockState SPINE = INFECTED.withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.POP);
    private static final Biome.FlowerEntry[] GRASS_LIST = {new Biome.FlowerEntry(INFECTED, 2), new Biome.FlowerEntry(ARC, 3), new Biome.FlowerEntry(FLOWER, 3), new Biome.FlowerEntry(SPINE, 3), new Biome.FlowerEntry(GRASS, 22)};

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        IBlockState blockState = ((Biome.FlowerEntry) WeightedRandom.getRandomItem(rand, Arrays.asList(GRASS_LIST))).state;
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.DOWN, SRPBlocks.ParasiteStain.getDefaultState());

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
}
