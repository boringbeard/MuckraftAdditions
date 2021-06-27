package com.boringbread.muckraft.common;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class MuckraftWorldType extends WorldType {

    public MuckraftWorldType()
    {
        super(MuckraftWorldGen.STAGE_ONE.name());

        // DEBUG
        System.out.println("Constructing");
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        return new MuckraftChunkGenerator(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
    }

    @Override
    public int getMinimumSpawnHeight(World world)
    {
        return world.getSeaLevel() + 1;
    }

    @Override
    public double getHorizon(World world)
    {
        return 63.0D;
    }

    @Override
    public double voidFadeMagnitude()
    {
        return 0.03125D;
    }

    @Override
    public void onGUICreateWorldPress() { }

    @Override
    public boolean isCustomizable()
    {
        return false;
    }
}
