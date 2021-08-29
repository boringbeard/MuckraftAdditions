package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.world.gen.feature.WorldGenBoneStalactites;
import com.boringbread.muckraft.world.gen.feature.WorldGenParasiteVines;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeBoneDecorator extends BiomeDecorator
{
    public WorldGenerator stalactites = new WorldGenBoneStalactites();

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        for (int i = 0; i < 4; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.stalactites.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }
    }
}
