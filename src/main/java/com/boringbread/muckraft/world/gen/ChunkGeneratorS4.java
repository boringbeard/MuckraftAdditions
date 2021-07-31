package com.boringbread.muckraft.world.gen;

import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
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
    private static final IBlockState DEFAULT_BLOCK = Blocks.STONE.getDefaultState();
    private final Random rand;
    private final World world;
    private NoiseGeneratorOctaves perlinNoise;
    private double[] mainStructureX;
    private double[] mainStructureY;
    private double[] mainStructureZ;

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
        mainStructureX = perlinNoise.generateNoiseOctaves(mainStructureX, x * 16, 0, z * 16, 1, 256, 16, 0, 0.25, 0.25);
        mainStructureY = perlinNoise.generateNoiseOctaves(mainStructureY, x * 16, 0, z * 16, 16, 1, 16, 0.25, 0, 0.25);
        mainStructureZ = perlinNoise.generateNoiseOctaves(mainStructureZ, x * 16, 0, z * 16, 16, 256, 1, 0.25, 0.25, 0);

        for (int x1 = 0; x1 < 16; x1++)
        {
            for (int z1 = 0; z1 < 16; z1++)
            {
                for (int y = 0; y < 256; y++)
                {
                    double d = mainStructureX[z1 * 256 + y] + mainStructureY[x1 * 16 + z1] + mainStructureZ[x1 * 256 + y];
                    if (d > 0) primer.setBlockState(x1, y, z1, DEFAULT_BLOCK);  //noise generator fills in order of y, z, x
                }
            }
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
