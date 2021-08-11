package com.boringbread.muckraft.world.biome;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

import java.util.List;

public class BiomeCache3D
{
    private final BiomeProvider provider;
    private long lastCleanupTime;
    private final Long2ObjectMap<BiomeCache3D.Block> cacheMap = new Long2ObjectOpenHashMap<BiomeCache3D.Block>(4096);
    private final List<BiomeCache3D.Block> cache = Lists.<BiomeCache3D.Block>newArrayList();

    public BiomeCache3D(BiomeProvider provider)
    {
        this.provider = provider;
    }

    public BiomeCache3D.Block getEntry(int x, int z)
    {
        x = x >> 4;
        z = z >> 4;
        long i = (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
        BiomeCache3D.Block biomecache$block = (BiomeCache3D.Block)this.cacheMap.get(i);

        if (biomecache$block == null)
        {
            biomecache$block = new BiomeCache3D.Block(x, z);
            this.cacheMap.put(i, biomecache$block);
            this.cache.add(biomecache$block);
        }

        biomecache$block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
        return biomecache$block;
    }

    public Biome getBiome(int x, int y, int z, Biome defaultValue)
    {
        Biome biome = this.getEntry(x, z).getBiome(x, y, z);
        return biome == null ? defaultValue : biome;
    }

    public void cleanupCache()
    {
        long i = MinecraftServer.getCurrentTimeMillis();
        long j = i - this.lastCleanupTime;

        if (j > 7500L || j < 0L)
        {
            this.lastCleanupTime = i;

            for (int k = 0; k < this.cache.size(); ++k)
            {
                BiomeCache3D.Block biomecache$block = this.cache.get(k);
                long l = i - biomecache$block.lastAccessTime;

                if (l > 30000L || l < 0L)
                {
                    this.cache.remove(k--);
                    long i1 = (long)biomecache$block.x & 4294967295L | ((long)biomecache$block.z & 4294967295L) << 32;
                    this.cacheMap.remove(i1);
                }
            }
        }
    }

    public Biome[] getCachedBiomes(int x, int z)
    {
        return this.getEntry(x, z).biomes;
    }

    public class Block
    {
        public Biome[] biomes = new Biome[256 * 256];
        public int x;
        public int z;
        public long lastAccessTime;

        public Block(int x, int z)
        {
            this.x = x;
            this.z = z;
            BiomeCache3D.this.provider.getBiomes(this.biomes, x << 4, z << 4, 16, 16, false);
        }

        public Biome getBiome(int x, int y, int z)
        {
            x = x & 15;
            z = z & 15;
            return this.biomes[(x * 16 + z) * 256 + y];
        }
    }
}
