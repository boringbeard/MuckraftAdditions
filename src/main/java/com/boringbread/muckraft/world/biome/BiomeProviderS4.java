package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.gen.layer.GenLayerParasite;
import com.boringbread.muckraft.world.gen.layer.GenLayerParasiteStacker;
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
    private final BiomeCache3D biomeCache;
    private GenLayer[] genBiomeLayers;
    private GenLayer genBiomes;
    private Random rand;

    public BiomeProviderS4(WorldInfo worldInfo)
    {
        super(worldInfo);
        genBiomeLayers = new GenLayer[8];
        biomeCache = new BiomeCache3D(this);
        rand = new Random(worldInfo.getSeed());

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
    public Biome getBiome(BlockPos pos, Biome defaultBiome)
    {
        defaultBiome = MuckWorldGen.INFECTED;
        return this.biomeCache.getBiome(pos.getX(), pos.getY(), pos.getZ(), defaultBiome);
    }


    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * height * 8)
        {
            biomes = new Biome[width * height * 8];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        for (int i = 0; i < width * height * 8; ++i)
        {
            biomes[i] = Biome.getBiome(aint[i], MuckWorldGen.BIOME_FLESH);
        }

        return biomes;
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length * 8)
        {
            listToReuse = new Biome[width * length * 8];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] abiome = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length * 8);
            return listToReuse;
        }
        else
        {
            int[] aint = this.genBiomes.getInts(x, z, width, length);

            for (int i = 0; i < width * length * 8; ++i)
            {
                listToReuse[i] = Biome.getBiome(aint[i], MuckWorldGen.INFECTED);
            }

            return listToReuse;
        }
    }

    @Override
    public void cleanupCache()
    {
        this.biomeCache.cleanupCache();
    }
}
