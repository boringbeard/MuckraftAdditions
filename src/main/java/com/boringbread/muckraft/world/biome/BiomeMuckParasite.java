package com.boringbread.muckraft.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public abstract class BiomeMuckParasite extends Biome
{
    public BiomeMuckParasite(BiomeProperties properties)
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

    public abstract void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal);
}
