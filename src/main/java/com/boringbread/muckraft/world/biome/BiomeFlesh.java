package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.EntityInhooM;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.EntityInhooS;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.EntityLesh;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHellDecorator;

public class BiomeFlesh extends Biome
{
    public BiomeFlesh()
    {
        super(new BiomeProperties("Flesh").setRainDisabled().setTemperature(1.2F).setWaterColor(2660695));
        setRegistryName(Muckraft.MOD_ID, "flesh");
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityInhooS.class, 1, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityInhooM.class, 1, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityLesh.class, 1, 4, 4));
        this.decorator = new BiomeHellDecorator();
    }
}
