package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.world.gen.feature.WorldGenFleshTrees;
import com.boringbread.muckraft.world.gen.feature.WorldGenParasiteGrass;
import com.boringbread.muckraft.world.gen.feature.WorldGenParasiteVines;
import com.boringbread.muckraft.world.gen.feature.WorldGenThickVines;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.world.gen.feature.WorldGenParasiteBush;
import com.dhanantry.scapeandrunparasites.world.gen.feature.WorldGenParasiteTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeInfectedDecorator extends BiomeDecorator
{
    public WorldGenerator veins = new WorldGenParasiteGrass();
    public WorldGenerator hangingVines = new WorldGenParasiteVines();
    public WorldGenerator thickVines = new WorldGenThickVines();
    public WorldGenerator trees = new WorldGenFleshTrees();

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        for (int i = 0; i < 32; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.hangingVines.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        for (int i = 0; i < 4; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.thickVines.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        for (int i = 0; i < 64; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.veins.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }

        for (int i = 0; i < 1; i++)
        {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.trees.generate(worldIn, random, this.chunkPos.add(j, 0, k));
        }
    }
}
