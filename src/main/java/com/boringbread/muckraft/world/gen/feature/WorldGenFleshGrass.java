package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteCanister;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteRubble;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
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

import java.util.List;
import java.util.Random;

public class WorldGenFleshGrass extends WorldGenerator implements IWorldGenMuck
{
    private static final IBlockState VEINS = SRPBlocks.ParasiteBush.getDefaultState().withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.TENDRIL);
    private static final IBlockState SPINE = SRPBlocks.ParasiteBush.getDefaultState().withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.BINE);
    private static final IBlockState MOUTH = SRPBlocks.ParasiteMouth.getDefaultState();
    private static final IBlockState LUMP = SRPBlocks.ParasiteCanister.getDefaultState().withProperty(BlockParasiteCanister.VARIANT, BlockParasiteCanister.EnumType.LUMP);
    private static final Biome.FlowerEntry[] GRASS_LIST = {new Biome.FlowerEntry(VEINS, 8), new Biome.FlowerEntry(SPINE, 8), new Biome.FlowerEntry(MOUTH, 4), new Biome.FlowerEntry(MOUTH, 2)};

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));

        IBlockState blockState = ((Biome.FlowerEntry) WeightedRandom.getRandomItem(rand, Arrays.asList(GRASS_LIST))).state;
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.DOWN, SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FLESH));

        if (!validSpots.isEmpty())
        {
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));
            worldIn.setBlockState(placementPos, blockState);
        }

        return true;
    }
}
