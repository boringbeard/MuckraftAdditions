package com.boringbread.muckraft.world.biome;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.world.gen.feature.WorldGenParasiteBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeInfectedDecorator extends BiomeDecorator
{
    public WorldGenerator veins = new WorldGenParasiteBush(false, BlockParasiteBush.EnumType.GRASS1, 3);

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        super.decorate(worldIn, random, biome, pos);
    }

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        for (int i = 0; i < 32; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.veins.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }
    }
}
