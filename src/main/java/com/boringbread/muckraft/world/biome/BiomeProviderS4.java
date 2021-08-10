package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.gen.layer.GenLayerParasite;
import com.boringbread.muckraft.world.gen.layer.GenLayerParasiteStacker;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.storage.WorldInfo;

import java.util.Random;

public class BiomeProviderS4 extends BiomeProvider
{
    private GenLayer[] genBiomeLayers;
    private GenLayer genBiomes;
    private Random rand;

    public BiomeProviderS4(WorldInfo worldInfo)
    {
        super(worldInfo);
        genBiomeLayers = new GenLayer[8];
        this.rand = new Random(worldInfo.getSeed());

        for (int i = 0; i < genBiomeLayers.length; i++)
        {
            GenLayer layer = new GenLayerParasite(rand.nextLong());
            layer = GenLayerFuzzyZoom.magnify(1000, layer, 6);
            layer = new GenLayerVoronoiZoom(10, layer);
            layer.initWorldGenSeed(worldInfo.getSeed());

            genBiomeLayers[i] = layer;
        }

        genBiomes = new GenLayerParasiteStacker(worldInfo.getSeed(), genBiomeLayers);
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * height * 256)
        {
            biomes = new Biome[width * height * 256];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        for (int i = 0; i < width * height * 256; ++i)
        {
            biomes[i] = Biome.getBiome(aint[i], MuckWorldGen.BIOME_FLESH);
        }

        return biomes;
    }
}
