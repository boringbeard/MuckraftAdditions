package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeBone extends BiomeMuckParasite
{
    private static final IBlockState BONE = Blocks.BONE_BLOCK.getDefaultState();

    public BiomeBone()
    {
        super(new BiomeProperties("bone"));
        setRegistryName(Muckraft.MOD_ID, "bone");
    }

    @Override
    public void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal)
    {
        if (noiseVal > -0.2 && noiseVal < 0.2)
        {
            chunkPrimerIn.setBlockState(x, y, z, BONE);
        }
    }
}
