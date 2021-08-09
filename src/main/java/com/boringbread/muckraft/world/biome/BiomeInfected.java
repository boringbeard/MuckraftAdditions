package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import com.dhanantry.scapeandrunparasites.block.BlockInfestedStain;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeInfected extends BiomeMuckParasite
{
    private static final IBlockState INFESTED_DIRT = SRPBlocks.InfestedStain.getDefaultState();

    public BiomeInfected()
    {
        super(new BiomeProperties("infected"));
        setRegistryName(Muckraft.MOD_ID, "infected");
    }

    @Override
    public void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal)
    {
        if (noiseVal > -0.2 && noiseVal < 0.2)
        {
            chunkPrimerIn.setBlockState(x, y, z, INFESTED_DIRT);
        }
    }
}
