package com.boringbread.muckraft.world.biome;

import com.dhanantry.scapeandrunparasites.init.SRPBiomes;
import com.dhanantry.scapeandrunparasites.world.biome.BiomeInfestedPlain;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;

public class BiomeProviderS4 extends BiomeProviderSingle
{
    public BiomeProviderS4()
    {
        super(SRPBiomes.biomeInfested);
    }
}
