package com.boringbread.muckraft.world.biome;

import com.boringbread.muckraft.Muckraft;
import com.dhanantry.scapeandrunparasites.block.BlockInfestedStain;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.entity.monster.adapted.*;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityAncientPod;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityOronco;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityTerla;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.*;
import com.dhanantry.scapeandrunparasites.entity.monster.primitive.*;
import com.dhanantry.scapeandrunparasites.entity.monster.pure.*;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import com.dhanantry.scapeandrunparasites.world.biome.BiomeParasiteDecorator;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeInfected extends BiomeMuckParasite
{
    private static final IBlockState INFESTED_DIRT = SRPBlocks.InfestedStain.getDefaultState();
    private static final IBlockState PARASITE_STAIN_DIRT = SRPBlocks.ParasiteStain.getDefaultState();

    public BiomeInfected()
    {
        super(new BiomeProperties("infected"));
        setRegistryName(Muckraft.MOD_ID, "infected");
        this.decorator = new BiomeInfectedDecorator();
    }

    @Override
    public void genTerrainBlock(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int y, int z, double noiseVal)
    {
        //sets surface blocks to parasite stain dirt with a 1/5 chance of being infested dirt instead
        if (noiseVal > -0.2 && noiseVal < 0.2)
        {
            chunkPrimerIn.setBlockState(x, y, z, rand.nextInt(5) == 1 ? INFESTED_DIRT.withProperty(BlockInfestedStain.STAGE, (int) MathHelper.clamp(Math.abs(rand.nextGaussian()) * 2, 0, 4)) : PARASITE_STAIN_DIRT);
        }
    }
}
