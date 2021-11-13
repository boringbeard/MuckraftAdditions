package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS2;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class WorldProviderS2 extends WorldProvider
{
    //world provider for S2
    //TO DO: give this world a gimmick and make generation more unique
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderS2(world.getWorldInfo());
    }

    @Override
    public @NotNull DimensionType getDimensionType()
    {
        return MuckWorldGen.STAGE_TWO;
    }

    @Override
    public @NotNull IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorOverworld(this.world, this.world.getSeed() * 7, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getWorldInfo().getGeneratorOptions());
    }
}
