package com.boringbread.muckraft.world.chunk;

import net.minecraft.init.Biomes;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Chunk3DBiomes extends Chunk
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final World world;

    public Chunk3DBiomes(World worldIn, ChunkPrimer primer, int x, int z)
    {
        super(worldIn, primer, x, z);
        this.blockBiomeArray = new byte[256 * 8];
        this.world = worldIn;
        Arrays.fill(this.blockBiomeArray, (byte) - 1);
    }

    @Override
    public Biome getBiome(BlockPos pos, BiomeProvider provider)
    {
        int i = pos.getX() & 15;
        int j = pos.getZ() & 15;
        int y = pos.getY() / 32;
        int k = this.blockBiomeArray[(j << 4 | i) << 3 | y] & 255;

        if (k == 255)
        {
            Biome biome = world.isRemote ? Biomes.PLAINS : provider.getBiome(pos, Biomes.PLAINS);
            k = Biome.getIdForBiome(biome);
            this.blockBiomeArray[(j << 4 | i) << 3 | y] = (byte)(k & 255);
        }

        Biome biome1 = Biome.getBiome(k);
        return biome1 == null ? Biomes.PLAINS : biome1;
    }
}
