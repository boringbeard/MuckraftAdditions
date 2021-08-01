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
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();
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
        mainStructure = perlinNoise.generateNoiseOctaves(mainStructure, x * 4, 0, z * 4, 5, 17, 5, 0.25, 0.25, 0.25);

        for (int x1 = 0; x1 < 4; x1++)
        {
            for (int z1 = 0; z1 < 4; z1++)
            {
                for (int y = 0; y < 16; y++)
                {
                    double d = mainStructure[(x1 * 4 + z1) * 16 + y];
                    double d1 = mainStructure[(x1 * 4 + z1) * 16 + y];

                    for (int x2 = 0; x2 < 4; x2++)
                    {
                        for (int z2 = 0; z2 < 4; z2++)
                        {
                            for (int y1 = 0; y1 < 16; y1++)
                            {
                                IBlockState toFill = DEFAULT_BLOCK;
                                if (d > 0) toFill = AIR;
                                int x3 = x2 + 4 * x1;
                                int y2 = y1 + 16 * y;
                                int z3 = z2 + 4 * z1;

                                //if (y2 > 254 - this.rand.nextInt(5) || y < 1 + this.rand.nextInt(5)) toFill = BEDROCK;
                                primer.setBlockState(x3, y2, z3, toFill);  //noise generator fills in order of y, z, x
                            }
                        }
                    }
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
