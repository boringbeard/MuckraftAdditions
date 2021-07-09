package com.boringbread.muckraft.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;

public class BiomeProviderS2 extends BiomeProvider {
    private ChunkGeneratorSettings settings;
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private final BiomeCache biomeCache;
    private final List<Biome> biomesToSpawnIn;
    public static List<Biome> allowedBiomes = Lists.newArrayList(Biomes.FOREST, Biomes.PLAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.FOREST_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS);

    protected BiomeProviderS2()
    {
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = Lists.newArrayList(allowedBiomes);
    }

    private BiomeProviderS2(long seed, WorldType worldTypeIn, String options)
    {
        this();

        if (worldTypeIn == WorldType.CUSTOMIZED && !options.isEmpty())
        {
            this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(options).build();
        }

        GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, this.settings);
        this.genBiomes = agenlayer[0];
        this.biomeIndexLayer = agenlayer[1];
    }

    public BiomeProviderS2(WorldInfo info)
    {
        this(info.getSeed(), info.getTerrainType(), info.getGeneratorOptions());
    }
}
