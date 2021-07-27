package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS4;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;

public class WorldProviderS4 extends WorldProviderHell
{
    @Override
    public void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderS4();
    }

    @Override
    public DimensionType getDimensionType()
    {
        return MuckWorldGen.STAGE_FOUR;
    }
}
