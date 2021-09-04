package com.boringbread.muckraft.event;

import com.boringbread.muckraft.config.Config;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class OreHandler
{
    private static final int[] S1_COUNTS = {24, 15, 1, 2, 0, 0};
    private static final int[] S1_SIZES = {26, 7, 6, 4, 0, 0};
    private static final int[] S2_COUNTS = {20, 20, 5, 8, 1, 1};
    private static final int[] S2_SIZES = {17, 9, 10, 7, 6, 7};
    private static final int[] S3_COUNTS = {22, 25, 3, 10, 2, 2};
    private static final int[] S3_SIZES = {24, 12, 8, 10, 12, 6};
    public static WorldGenerator gravelGen = new WorldGenSand(Blocks.GRAVEL, 6);
    public static WorldGenerator dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), 33);
    public static WorldGenerator gravelOreGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 33);
    public static WorldGenerator graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), 33);
    public static WorldGenerator dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 33);
    public static WorldGenerator andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), 33);
    public static WorldGenerator coalGen;
    public static WorldGenerator ironGen;
    public static WorldGenerator goldGen;
    public static WorldGenerator redstoneGen;
    public static WorldGenerator diamondGen;
    public static WorldGenerator lapisGen;
    private static BlockPos chunkPos;

    @SubscribeEvent
    public static void onGenerateOres(GenerateMinable event)
    {
        World world = event.getWorld();
        Random random = event.getRand();
        chunkPos = event.getPos();
        int dimID = event.getWorld().provider.getDimension();

        if (dimID == Config.stageOneID)
        {
            event.setResult(Event.Result.DENY);
            if (event.getType() == GenerateMinable.EventType.COAL) generateS1Ores(world, random);
        }

        if (dimID == Config.stageTwoID)
        {
            event.setResult(Event.Result.DENY);
            if (event.getType() == GenerateMinable.EventType.COAL) generateS2Ores(world, random);
        }

        if (dimID == Config.stageThreeID)
        {
            event.setResult(Event.Result.DENY);
            if (event.getType() == GenerateMinable.EventType.COAL) generateS3Ores(world, random);
        }
    }

    private static void generateS1Ores(World worldIn, Random random)
    {
        generateVanillaOres(worldIn, random, S1_COUNTS, S1_SIZES);
    }

    private static void generateS2Ores(World worldIn, Random random)
    {
        generateVanillaOres(worldIn, random, S2_COUNTS, S2_SIZES);
    }

    private static void generateS3Ores(World worldIn, Random random)
    {
        generateVanillaOres(worldIn, random, S3_COUNTS, S3_SIZES);
    }

    private static void generateVanillaOres(World worldIn, Random random, int[] counts, int[] sizes)
    {
        genStandardOre1(worldIn, random, 10, dirtGen, 0, 256);
        genStandardOre1(worldIn, random, 10, gravelOreGen, 0, 256);
        genStandardOre1(worldIn, random, 10, dioriteGen, 0, 80);
        genStandardOre1(worldIn, random, 10, graniteGen, 0, 80);
        genStandardOre1(worldIn, random, 10, andesiteGen, 0, 80);

        coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), sizes[0]);
        ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), sizes[1]);
        goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), sizes[2]);
        redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), sizes[3]);
        diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), sizes[4]);
        lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), sizes[5]);

        genStandardOre1(worldIn, random, counts[0], coalGen, 0, 128);
        genStandardOre1(worldIn, random, counts[1], ironGen, 0, 64);
        genStandardOre1(worldIn, random, counts[2], goldGen, 0, 32);
        genStandardOre1(worldIn, random, counts[3], redstoneGen, 0, 16);
        genStandardOre1(worldIn, random, counts[4], diamondGen, 0, 16);
        genStandardOre2(worldIn, random, counts[5], lapisGen, 16, 16);
    }

    private static void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }

    private static void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread)
    {
        for (int i = 0; i < blockCount; ++i)
        {
            BlockPos blockpos = chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
}
