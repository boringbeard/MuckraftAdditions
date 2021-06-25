package com.boringbread.muckraft.common;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class MuckraftWorldProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return MuckraftWorldGen.STAGE_ONE;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new MuckraftChunkGenerator(this.world, this.world.getSeed(), this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getWorldInfo().getGeneratorOptions());
    }
}
