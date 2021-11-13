package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.gen.layer.GenLayerParasite;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.storage.WorldInfo;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BiomeProviderS4 extends BiomeProvider
{
    private GenLayer genBiomes;
    private Random rand;

    public BiomeProviderS4(WorldInfo worldInfo)
    {
        super(worldInfo);
        rand = new Random(worldInfo.getSeed());

        GenLayer layer = new GenLayerParasite(rand.nextLong());
        //magnifies and zooms biomes a ton in order to make the biomes larger than 1 block each
        layer = GenLayerFuzzyZoom.magnify(1000, layer, 6);
        layer = new GenLayerVoronoiZoom(10, layer);
        layer.initWorldGenSeed(worldInfo.getSeed());

        genBiomes = layer;
    }


    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        //fills in biomes array with generated biome array from the genlayer
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        for (int i = 0; i < width * height; ++i)
        {
            biomes[i] = Biome.getBiome(aint[i], MuckWorldGen.BIOME_FLESH);
        }

        return biomes;
    }
}
