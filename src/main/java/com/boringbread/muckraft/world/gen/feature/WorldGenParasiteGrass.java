package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockInfestedBush;
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
    //TO DO: get rid of the old stuff from stacked biomes
    //Make this reusable for other grass lists
    //blocks to use for grass
    private static final IBlockState INFECTED = SRPBlocks.InfestedBush.getDefaultState();
    private static final IBlockState ARC = INFECTED.withProperty(BlockInfestedBush.VARIANT, BlockInfestedBush.EnumType.ARC);
    private static final IBlockState GRASS = INFECTED.withProperty(BlockInfestedBush.VARIANT, BlockInfestedBush.EnumType.GRASS1);
    private static final IBlockState FLOWER = INFECTED.withProperty(BlockInfestedBush.VARIANT, BlockInfestedBush.EnumType.FLOWER1);
    private static final IBlockState SPINE = INFECTED.withProperty(BlockInfestedBush.VARIANT, BlockInfestedBush.EnumType.SPINE);
    //list of weighted random entries that represent each of our grass blocks. The higher the weight the more often it will be selected by weighted random
    private static final Biome.FlowerEntry[] GRASS_LIST = {new Biome.FlowerEntry(INFECTED, 2), new Biome.FlowerEntry(ARC, 3), new Biome.FlowerEntry(FLOWER, 3), new Biome.FlowerEntry(SPINE, 3), new Biome.FlowerEntry(GRASS, 22)};

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        //TO DO: get rid of the position randomization code inside the worldgen classes and just keep it in here
        //randomize position in chunk
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        //get weighted random entry from our list of potential grasses
        IBlockState blockState = ((Biome.FlowerEntry) WeightedRandom.getRandomItem(rand, Arrays.asList(GRASS_LIST))).state;
        //find valid ground surfaces
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.DOWN, SRPBlocks.ParasiteStain.getDefaultState());

        if (!validSpots.isEmpty())
        {
            //get one of the random surfaces we founnd
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));

            //continually stacks the grass to a certain height
            //height = 1 unless its the SPINE type of grass, in which case height is randomized between 1 and 8 using gaussian distribution
            for (int i = 0; i < (blockState == SPINE ? (int) MathHelper.clamp(rand.nextGaussian() + 3, 1, 8) : 1); i++)
            {
                if (worldIn.isAirBlock(placementPos)) worldIn.setBlockState(placementPos, blockState); //only sets block if the position to set is air
                else break; //if it hits a block then itll stop stacking the grass
                placementPos = placementPos.up(); //move the next position to place down a block one up
            }
        }

        return true;
    }
}
