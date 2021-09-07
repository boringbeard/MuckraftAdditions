package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.EntityInhooM;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.EntityInhooS;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import com.dhanantry.scapeandrunparasites.world.gen.feature.WorldGenParasiteTree;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeFlesh extends BiomeMuckParasite
{
    private static final IBlockState FLESH = SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FLESH);
    private static final WorldGenAbstractTree PARASITE_TREE = new WorldGenParasiteTree(false);

    public BiomeFlesh()
    {
        super(new BiomeProperties("flesh").setRainDisabled().setTemperature(1.2F).setWaterColor(2660695));
        setRegistryName(Muckraft.MOD_ID, "flesh");
        this.decorator = new BiomeFleshDecorator();
    }

    @Override
    public void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal)
    {
        if (noiseVal > -0.2 && noiseVal < 0.2)
        {
            int blockDecider = rand.nextInt(20);
            IBlockState toSet = FLESH;

            if (blockDecider < 2) toSet = Blocks.NETHERRACK.getDefaultState();
            else if (blockDecider == 2) toSet = Blocks.NETHER_WART_BLOCK.getDefaultState();

            chunkPrimerIn.setBlockState(x, y, z, toSet);
        }
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return PARASITE_TREE;
    }
}
