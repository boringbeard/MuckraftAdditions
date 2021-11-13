package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.world.gen.feature.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeInfectedDecorator extends BiomeDecorator
{
    public WorldGenerator veins = new WorldGenParasiteGrass();
    public WorldGenerator hangingVines = new WorldGenParasiteVines();
    public WorldGenerator thickVines = new WorldGenThickVines();
    public WorldGenerator trees = new WorldGenInfestedTrees();
    public WorldGenerator stalactites = new WorldGenBoneSpikes(EnumFacing.UP);
    public WorldGenerator stalagmites = new WorldGenBoneSpikes(EnumFacing.DOWN);

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        //Each of these loops just generate a certain amount of decorations in a chunk. they also randomize the position within the chunk of each one
        //generate 32 vines
        for (int i = 0; i < 32; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.hangingVines.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        //generate 4 thick vines
        for (int i = 0; i < 4; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.thickVines.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        //generate 64 grass
        for (int i = 0; i < 64; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.veins.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        //generate trees sometimes
        if (random.nextGaussian() > 0.5)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.trees.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        //generates upward spikes
        if (random.nextGaussian() > 1)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.stalactites.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        //generate downward spikes
        if (random.nextGaussian() > 1)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.stalagmites.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }
    }
}
