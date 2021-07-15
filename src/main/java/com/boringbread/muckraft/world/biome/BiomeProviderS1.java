package com.boringbread.muckraft.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BiomeProviderS1 extends BiomeProvider
{
    private ChunkGeneratorSettings settings;
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private final BiomeCache biomeCache;
    private final List<Biome> biomesToSpawnIn;
    private final BiomeReplacer[] biomeReplacers = new BiomeReplacer[]
            {
                    new BiomeReplacer(Biomes.FOREST_HILLS, Biomes.FOREST),
                    new BiomeReplacer(Biomes.BIRCH_FOREST_HILLS, Biomes.BIRCH_FOREST),
                    new BiomeReplacer(Biomes.COLD_TAIGA_HILLS, Biomes.COLD_TAIGA),
                    new BiomeReplacer(Biomes.DESERT_HILLS, Biomes.DESERT),
                    new BiomeReplacer(Biomes.ICE_MOUNTAINS, Biomes.ICE_PLAINS),
                    new BiomeReplacer(Biomes.JUNGLE, Biomes.PLAINS),
                    new BiomeReplacer(Biomes.JUNGLE_EDGE, Biomes.PLAINS),
                    new BiomeReplacer(Biomes.JUNGLE_HILLS, Biomes.PLAINS),
                    new BiomeReplacer(Biomes.REDWOOD_TAIGA, Biomes.TAIGA),
                    new BiomeReplacer(Biomes.REDWOOD_TAIGA_HILLS, Biomes.TAIGA),
                    new BiomeReplacer(Biomes.MUSHROOM_ISLAND, Biomes.PLAINS),
                    new BiomeReplacer(Biomes.MUSHROOM_ISLAND_SHORE, Biomes.BEACH),
                    new BiomeReplacer(Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA),
                    new BiomeReplacer(Biomes.MUTATED_SAVANNA_ROCK, Biomes.SAVANNA),
                    new BiomeReplacer(Biomes.MUTATED_SAVANNA, Biomes.SAVANNA)
            };

    protected BiomeProviderS1()
    {
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = Lists.newArrayList(allowedBiomes);
    }

    private BiomeProviderS1(long seed, WorldType worldTypeIn)
    {
        this();
        GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, this.settings);
        this.genBiomes = agenlayer[0];
        this.biomeIndexLayer = agenlayer[1];
    }

    public BiomeProviderS1(WorldInfo info)
    {
        this(info.getSeed() * 3, info.getTerrainType());
    }

    public List<Biome> getBiomesToSpawnIn()
    {
        return this.biomesToSpawnIn;
    }

    public Biome getBiome(BlockPos pos)
    {
        return this.getBiome(pos, (Biome)null);
    }

    public Biome getBiome(BlockPos pos, Biome defaultBiome)
    {
        return this.biomeCache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
    }

    public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_)
    {
        return p_76939_1_;
    }

    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        try
        {
            for (int i = 0; i < width * height; ++i)
            {
                biomes[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
            }

            for (int i = 0; i < biomes.length; i++)
            {
                if(biomes[i] instanceof BiomeJungle) biomes[i] = Biomes.MUTATED_SAVANNA;
                if(biomes[i] instanceof BiomeHills) biomes[i] = Biomes.FOREST;
                if(biomes[i] instanceof BiomeMesa) biomes[i] = Biomes.DESERT;
                for (BiomeReplacer biomeReplacer: biomeReplacers) {
                    if(biomeReplacer.toBeReplaced == biomes[i]) biomes[i] = biomeReplacer.replacement;
                }
            }

            return biomes;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
            crashreportcategory.addCrashSection("x", Integer.valueOf(x));
            crashreportcategory.addCrashSection("z", Integer.valueOf(z));
            crashreportcategory.addCrashSection("w", Integer.valueOf(width));
            crashreportcategory.addCrashSection("h", Integer.valueOf(height));
            throw new ReportedException(crashreport);
        }
    }

    public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth)
    {
        return this.getBiomes(oldBiomeList, x, z, width, depth, true);
    }

    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length)
        {
            listToReuse = new Biome[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] abiome = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length);
            return listToReuse;
        }
        else
        {
            int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);

            for (int i = 0; i < width * length; ++i)
            {
                listToReuse[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
            }

            return listToReuse;
        }
    }

    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed)
    {
        IntCache.resetIntCache();
        int i = x - radius >> 2;
        int j = z - radius >> 2;
        int k = x + radius >> 2;
        int l = z + radius >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] aint = this.genBiomes.getInts(i, j, i1, j1);

        try
        {
            for (int k1 = 0; k1 < i1 * j1; ++k1)
            {
                Biome biome = Biome.getBiome(aint[k1]);

                if (!allowed.contains(biome))
                {
                    return false;
                }
            }

            return true;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
            crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
            crashreportcategory.addCrashSection("x", Integer.valueOf(x));
            crashreportcategory.addCrashSection("z", Integer.valueOf(z));
            crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
            crashreportcategory.addCrashSection("allowed", allowed);
            throw new ReportedException(crashreport);
        }
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random)
    {
        IntCache.resetIntCache();
        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] aint = this.genBiomes.getInts(i, j, i1, j1);
        BlockPos blockpos = null;
        int k1 = 0;

        for (int l1 = 0; l1 < i1 * j1; ++l1)
        {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            Biome biome = Biome.getBiome(aint[l1]);

            if (biomes.contains(biome) && (blockpos == null || random.nextInt(k1 + 1) == 0))
            {
                blockpos = new BlockPos(i2, 0, j2);
                ++k1;
            }
        }

        return blockpos;
    }

    private static class BiomeReplacer
    {
        private final Biome toBeReplaced;
        private final Biome replacement;

        private BiomeReplacer(Biome toBeReplaced, Biome replacement)
        {
            this.toBeReplaced = toBeReplaced;
            this.replacement = replacement;
        }
    }
}

