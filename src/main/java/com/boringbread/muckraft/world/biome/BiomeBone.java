package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import net.minecraft.world.biome.Biome;

public class BiomeBone extends Biome
{
    public BiomeBone()
    {
        super(new BiomeProperties("bone"));
        setRegistryName(Muckraft.MOD_ID, "bone");
    }
}
