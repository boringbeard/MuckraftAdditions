package com.boringbread.muckraft.world.biome;

import com.dhanantry.scapeandrunparasites.entity.monster.adapted.*;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityOronco;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityTerla;
import com.dhanantry.scapeandrunparasites.entity.monster.primitive.EntityCanra;
import com.dhanantry.scapeandrunparasites.entity.monster.pure.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public abstract class BiomeMuckParasite extends Biome
{
    //pretty much for default muck parasite biome - adds default spawn list
    public BiomeMuckParasite(BiomeProperties properties)
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        spawnableMonsterList.add(new SpawnListEntry(EntityOronco.class, 8, 1, 1));
        spawnableMonsterList.add(new SpawnListEntry(EntityTerla.class, 8, 1, 1));
        spawnableMonsterList.add(new SpawnListEntry(EntityOmboo.class, 16, 1, 2));
        spawnableMonsterList.add(new SpawnListEntry(EntityAlafha.class, 16, 1, 2));
        spawnableMonsterList.add(new SpawnListEntry(EntityEsor.class, 16, 1, 2));
        spawnableMonsterList.add(new SpawnListEntry(EntityFlog.class, 16, 1, 2));
        spawnableMonsterList.add(new SpawnListEntry(EntityGanro.class, 16, 1, 2));
        spawnableMonsterList.add(new SpawnListEntry(EntityBanoAdapted.class, 32, 2, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityCanraAdapted.class, 32, 2, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityEmanaAdapted.class, 32, 2, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityHullAdapted.class, 32, 2, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityNoglaAdapted.class, 32, 2, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityRanracAdapted.class, 32, 2, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityShycoAdapted.class, 32, 2, 4));
    }

    //method to generate single surface terrain block because terrain is 3D here, so the other one doesn't work
    public abstract void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal);
}
