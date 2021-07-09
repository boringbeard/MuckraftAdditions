package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckraftWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS1;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderS1 extends WorldProvider
{
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderS1(world.getWorldInfo());
    }

    @Override
    public DimensionType getDimensionType()
    {
        return MuckraftWorldGen.STAGE_ONE;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorS1(this.world, this.world.getSeed(), this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getWorldInfo().getGeneratorOptions());
    }
}
