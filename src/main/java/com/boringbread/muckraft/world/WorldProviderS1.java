package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckraftWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS1;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class WorldProviderS1 extends WorldProvider
{
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderS1(world.getWorldInfo());
    }

    @Override
    public @NotNull DimensionType getDimensionType()
    {
        return MuckraftWorldGen.STAGE_ONE;
    }

    @Override
    public @NotNull IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorOverworld(this.world, this.world.getSeed() * 3, false, this.world.getWorldInfo().getGeneratorOptions());
    }
}
