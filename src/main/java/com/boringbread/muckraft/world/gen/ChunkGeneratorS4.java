package com.boringbread.muckraft.world.gen;

import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ChunkGeneratorS4 implements IChunkGenerator
{
    private static final IBlockState DEFAULT_BLOCK = SRPBlocks.ParasiteRubble.getDefaultState();
    private final Random rand;
    private final World world;
    private NoiseGeneratorOctaves perlinNoise;
    private double[] mainStructure;

    public ChunkGeneratorS4(World worldIn, boolean p_i45637_2_, long seed)
    {
        this.rand = new Random(seed);
        this.world = worldIn;
        this.perlinNoise = new NoiseGeneratorOctaves(rand, 4);
    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        ChunkPrimer primer = new ChunkPrimer();
        mainStructure = perlinNoise.generateNoiseOctaves(mainStructure, x * 16, 0, z * 16, 16, 256, 16, 1.0, 1.0, 1.0);

        for (int i = 0; i < mainStructure.length; i++)
        {
            int x1 = i / 4096;
            int z1 = i % 4096 / 256;
            int y1 = i % 256;
            if (mainStructure[i] > 0.5) primer.setBlockState(x1, y1, z1, DEFAULT_BLOCK);  //noise generator fills in order of y, z, x
        }

        return new Chunk(world, primer, x, z);
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return null;
    }

    @Override
    public void populate(int x, int z)
    {

    }

    //FOCUS ABOVE FOR NOW, STRUCTURES CAN COME LATER

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
