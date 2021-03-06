package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS1;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class WorldProviderS1 extends WorldProvider
{
    //world provider for S1
    //TO DO: give this world a gimmick and make generation more unique
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderS1(world.getWorldInfo());
    }

    @Override
    public @NotNull DimensionType getDimensionType()
    {
        return MuckWorldGen.STAGE_ONE;
    }

    @Override
    public @NotNull IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorOverworld(this.world, this.world.getSeed() * 3, false, this.world.getWorldInfo().getGeneratorOptions());
    }
}
