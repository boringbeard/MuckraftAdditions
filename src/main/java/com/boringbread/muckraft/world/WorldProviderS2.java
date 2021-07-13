package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckraftWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS2;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class WorldProviderS2 extends WorldProvider
{
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderS2(world.getWorldInfo());
    }

    @Override
    public @NotNull DimensionType getDimensionType()
    {
        return MuckraftWorldGen.STAGE_TWO;
    }

    @Override
    public @NotNull IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorOverworld(this.world, this.world.getSeed() * 7, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getWorldInfo().getGeneratorOptions());
    }
}