package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckraftWorldGen;
import com.boringbread.muckraft.world.biome.StageOneBiomeProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class MuckraftWorldProvider extends WorldProvider
{
    @Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new StageOneBiomeProvider();
    }

    @Override
    public DimensionType getDimensionType() {
        return MuckraftWorldGen.STAGE_ONE;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new MuckraftChunkGenerator(this.world, this.world.getSeed(), this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getWorldInfo().getGeneratorOptions());
    }
}
