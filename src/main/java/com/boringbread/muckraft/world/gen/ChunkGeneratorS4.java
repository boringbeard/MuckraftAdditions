package com.boringbread.muckraft.world.gen;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteRubble;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBiomes;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import com.dhanantry.scapeandrunparasites.init.SRPEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorS4 implements IChunkGenerator
{
    private static final IBlockState DEFAULT_BLOCK = SRPBlocks.ParasiteRubble.getDefaultState().withProperty(BlockParasiteRubble.VARIANT, BlockParasiteRubble.EnumType.FLESH);
    private static final IBlockState FLESH = SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FLESH);
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();
    private static final IBlockState DEAD_BLOOD = SRPBlocks.DeadBlood.getDefaultState();
    private static final IBlockState INFESTED_BLOCK = SRPBlocks.InfestedStain.getDefaultState();
    private static final IBlockState PARASITE_STAIN = SRPBlocks.ParasiteStain.getDefaultState();
    private final WorldGenerator infestedBlockGenerator = new WorldGenMinable(INFESTED_BLOCK, 33, BlockMatcher.forBlock(DEFAULT_BLOCK.getBlock()));
    private final WorldGenerator airPocketGenerator = new WorldGenMinable(AIR, 33, BlockMatcher.forBlock(DEFAULT_BLOCK.getBlock()));
    private final Random rand;
    private final World world;
    private final NoiseGeneratorOctaves perlinNoise;
    private final NoiseGeneratorOctaves perlinNoise1;
    private double[] mainStructure;
    private double[] hugeCaverns;
    private double[] infectionPatches;
    private double[] infectionPatchesY;

    public ChunkGeneratorS4(World worldIn, long seed)
    {
        this.rand = new Random(seed);
        this.world = worldIn;
        this.perlinNoise = new NoiseGeneratorOctaves(rand, 2);
        this.perlinNoise1 = new NoiseGeneratorOctaves(rand, 8);
    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        int xSize = 2;
        int ySize = 32;
        int zSize = 2;
        ChunkPrimer primer = new ChunkPrimer();
        mainStructure = perlinNoise.generateNoiseOctaves(mainStructure, x * xSize + 1, 10, z * zSize + 1, xSize + 1, ySize + 1, zSize + 1, 1, 2, 1);
        hugeCaverns = perlinNoise.generateNoiseOctaves(hugeCaverns, x * xSize + 1, 0, z * zSize + 1, xSize + 1, ySize + 1, zSize + 1, 0.0625, 0.125, 0.0625);
        infectionPatches = perlinNoise1.generateNoiseOctaves(infectionPatches, x * 16, 0, z * 16, 16, 1, 16, 1, 0, 1);
        infectionPatchesY = perlinNoise1.generateNoiseOctaves(infectionPatchesY, x * 16, 0, z * 16, 16, 128, 1, 1, 1, 0);

        for (int i = 0; i < mainStructure.length; i++)
        {
            mainStructure[i] = hugeCaverns[i] > 2 ? hugeCaverns[i] : mainStructure[i];
        }

        for (int x1 = 0; x1 < xSize; x1++)
        {
            for (int z1 = 0; z1 < zSize; z1++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    double origin = mainStructure[(x1 * (xSize + 1) + z1) * (ySize + 1) + y];
                    double offZ = mainStructure[(x1 * (xSize + 1) + (z1 + 1)) * (ySize + 1) + y];
                    double offY = mainStructure[(x1 * (xSize + 1) + z1) * (ySize + 1) + y + 1];
                    double offZY = mainStructure[(x1 * (xSize + 1) + (z1 + 1)) * (ySize + 1) + y + 1];
                    double originIncX = (mainStructure[((x1 + 1) * (xSize + 1) + z1) * (ySize + 1) + y] - origin) * xSize / 16.0;
                    double plusZIncX = (mainStructure[((x1 + 1) * (xSize + 1) + (z1 + 1)) * (ySize + 1) + y] - offZ) * xSize / 16.0;
                    double plusYIncX = (mainStructure[((x1 + 1) * (xSize + 1) + z1) * (ySize + 1) + y + 1] - offY) * xSize / 16.0;
                    double plusZYIncX = (mainStructure[((x1 + 1) * (xSize + 1) + (z1 + 1)) * (ySize + 1) + y + 1] - offZY) * xSize / 16.0;

                    for (int x2 = 0; x2 < 16 / xSize; x2++)
                    {
                        double origin1 = origin;
                        double offY1 = offY;
                        double originIncZ = (offZ - origin) * zSize / 16.0;
                        double offYIncZ = (offZY - offY) * zSize / 16.0;

                        for (int z2 = 0; z2 < 16 / zSize; z2++)
                        {
                            double origin2 = origin1;
                            double originIncY = (offY1 - origin1) * ySize / 256.0;
                            for (int y1 = 0; y1 < 256 / ySize; y1++)
                            {
                                double origin3 = origin2;
                                IBlockState toFill = DEFAULT_BLOCK;
                                int x3 = x2 + 16 / xSize * x1;
                                int y2 = y1 + 256 / ySize * y;
                                int z3 = z2 + 16 / zSize * z1;
                                origin3 -= ((MathHelper.clamp(Math.abs(128.0 - y2), 80, 128) - 80) / 48) * 3;

                                if (origin3 > -0.2)
                                {
                                    if (infectionPatches[x3 * 16 + z3] > -0.5 && infectionPatchesY[x3 * 128 + y2 / 2] > 0)
                                    {
                                        if (rand.nextInt(8) == 7) toFill = INFESTED_BLOCK;
                                        else toFill = PARASITE_STAIN;
                                    }
                                    else toFill = FLESH;
                                }
                                if (origin3 > 0.2)
                                {
                                    if (y2 > 63) toFill = AIR;
                                    else toFill = DEAD_BLOOD;
                                }

                                if (y2 > 254 - this.rand.nextInt(5) || y2 < 1 + this.rand.nextInt(5)) toFill = BEDROCK;
                                primer.setBlockState(x3, y2, z3, toFill);
                                origin2 += originIncY;
                            }

                            origin1 += originIncZ;
                            offY1 += offYIncZ;
                        }
                        origin += originIncX;
                        offZ += plusZIncX;
                        offY += plusYIncX;
                        offZY += plusZYIncX;
                    }
                }
            }
        }

        return new Chunk(world, primer, x, z);
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Override
    public void populate(int x, int z)
    {
        int x1 = x * 16;
        int z1 = z * 16;
        BlockPos blockpos = new BlockPos(x1, 0, z1);

        for (int i = 0; i < 16; i++)
        {
            infestedBlockGenerator.generate(world, rand, blockpos.add(this.rand.nextInt(16), this.rand.nextInt(246) + 10, this.rand.nextInt(16)));
        }
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
