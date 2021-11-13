package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.world.gen.feature.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeFleshDecorator extends BiomeDecorator
{
    public WorldGenerator grass = new WorldGenFleshGrass();
    public WorldGenerator sacs = new WorldGenParasiteSacs();
    public WorldGenerator stalactites = new WorldGenBoneSpikes(EnumFacing.UP);
    public WorldGenerator stalagmites = new WorldGenBoneSpikes(EnumFacing.DOWN);

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        //each of these just generates decorations and randomizes the position within the chunk
        for (int i = 0; i < 32; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.grass.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        for (int i = 0; i < 16; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.sacs.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        if (random.nextGaussian() > 1)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.stalactites.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        if (random.nextGaussian() > 1)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.stalagmites.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }
    }
}
