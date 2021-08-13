package com.boringbread.muckraft.world.biome;

import com.dhanantry.scapeandrunparasites.entity.monster.primitive.EntityCanra;
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
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCanra.class, 8, 2, 4));
    }

    public abstract void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal);
}
