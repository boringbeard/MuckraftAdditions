package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import net.minecraft.world.biome.Biome;

public class BiomeInfected extends Biome
{
    public BiomeInfected()
    {
        super(new BiomeProperties("infected"));
        setRegistryName(Muckraft.MOD_ID, "infected");
    }
}
