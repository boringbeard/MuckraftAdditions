package com.boringbread.muckraft.world.gen;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.biome.BiomeFlesh;
import com.boringbread.muckraft.world.biome.BiomeMuckParasite;
import com.boringbread.muckraft.world.biome.BiomeProviderS4;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteRubble;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import com.dhanantry.scapeandrunparasites.util.SRPConfig;
import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
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
    //Chunk generator for parasite dimension
    //TO DO: replace old biome stacking stuff
    private static final IBlockState DEFAULT_BLOCK = SRPBlocks.ParasiteRubble.getDefaultState().withProperty(BlockParasiteRubble.VARIANT, BlockParasiteRubble.EnumType.FLESH); //default will be compressed flesh
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();
    private static final IBlockState DEAD_BLOOD = SRPBlocks.DeadBlood.getDefaultState();
    private static final IBlockState INFESTED_BLOCK = SRPBlocks.InfestedStain.getDefaultState();
    private final WorldGenerator infestedBlockGenerator = new WorldGenMinable(INFESTED_BLOCK, 33, BlockMatcher.forBlock(DEFAULT_BLOCK.getBlock()));
    private final WorldGenerator airPocketGenerator = new WorldGenMinable(AIR, 33, BlockMatcher.forBlock(DEFAULT_BLOCK.getBlock()));
    private final Random rand;
    private final World world;
    private final NoiseGeneratorOctaves perlinNoise;
    private final NoiseGeneratorOctaves perlinNoise1;
    private Biome[] biomesForGeneration;
    private double[] mainStructure;
    private double[] largeCaverns;
    private double[] mainStructureInterpolated;
    private double[] infectionPatches;
    private double[] infectionPatchesY;
    private double[] noise;

    public ChunkGeneratorS4(World worldIn, long seed)
    {
        this.rand = new Random(seed);
        this.world = worldIn;
        this.perlinNoise = new NoiseGeneratorOctaves(rand, 2);
        this.perlinNoise1 = new NoiseGeneratorOctaves(rand, 8);
        this.mainStructureInterpolated = new double[16 * 16 * 256];
    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn)
    {
        noise = new double[16 * 16];
        Arrays.fill(noise, 0);
        //goes through each block in the chunk and replaces surface blocks; theres probably a more efficient way to do this
        for (int x1 = 0; x1 < 16; ++x1)
        {
            for (int z1 = 0; z1 < 16; ++z1)
            {
                BiomeMuckParasite biome = (BiomeMuckParasite) biomesIn[z1 + x1 * 16];
                for (int y = 0; y < 256; y++)
                {
                    biome.genTerrainBlock(this.world, this.rand, primer, x1, y, z1, this.mainStructureInterpolated[(z1 + x1 * 16) * 256 + y]);
                }
            }
        }
    }

    public void genMainStructure(ChunkPrimer primer, int x, int z)
    {
        //starts off with low res 3D perlin noise and then interpolates for efficiency
        int xSize = 2;
        int ySize = 32;
        int zSize = 2;

        mainStructure = perlinNoise.generateNoiseOctaves(mainStructure, x * xSize + 1, 10, z * zSize + 1, xSize + 1, ySize + 1, zSize + 1, 1, 2, 1);
        largeCaverns = perlinNoise.generateNoiseOctaves(largeCaverns, x * xSize + 1, 0, z * zSize + 1, xSize + 1, ySize + 1, zSize + 1, 0.0625, 0.125, 0.0625);
        infectionPatches = perlinNoise1.generateNoiseOctaves(infectionPatches, x * 16, 0, z * 16, 16, 1, 16, 2, 0, 2);
        infectionPatchesY = perlinNoise1.generateNoiseOctaves(infectionPatchesY, x * 16, 0, z * 16, 16, 128, 1, 2, 2, 0);

        for (int i = 0; i < mainStructure.length; i++)
        {
            mainStructure[i] = largeCaverns[i] > 1.8 ? largeCaverns[i] : mainStructure[i]; //clear out areas that are in the large caverns areas
            if (largeCaverns[i] > 1.6 && largeCaverns[i] <= 1.8) mainStructure[i] += largeCaverns[i]; // areas around large caverns areas kind of ease in
        }

        for (int x1 = 0; x1 < xSize; x1++)
        {
            for (int z1 = 0; z1 < zSize; z1++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    //interpolation
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
                        //interpolation
                        double origin1 = origin;
                        double offY1 = offY;
                        double originIncZ = (offZ - origin) * zSize / 16.0;
                        double offYIncZ = (offZY - offY) * zSize / 16.0;

                        for (int z2 = 0; z2 < 16 / zSize; z2++)
                        {
                            //interpolation
                            double origin2 = origin1;
                            double originIncY = (offY1 - origin1) * ySize / 256.0;

                            for (int y1 = 0; y1 < 256 / ySize; y1++)
                            {
                                double origin3 = origin2;
                                IBlockState toFill = DEFAULT_BLOCK; //default fill the block with the default block (compressed flesh)
                                int x3 = x2 + 16 / xSize * x1;
                                int y2 = y1 + 256 / ySize * y;
                                int z3 = z2 + 16 / zSize * z1;

                                // make top and bottom a bit more solid/less air pockets
                                mainStructureInterpolated[(x3 * 16 + z3) * 256 + y2] = origin3;
                                origin3 -= ((MathHelper.clamp(Math.abs(128.0 - y2), 80, 128) - 80) / 48);

                                //fills slots that have a perlin value over 0.2 with air if over y 63, otherwise dead blood fluid
                                if (origin3 > 0.2)
                                {
                                    if (y2 > 63) toFill = AIR;
                                    else toFill = DEAD_BLOOD;
                                }

                                //generate bedrock floor and ceiling
                                if (y2 > 254 - this.rand.nextInt(5) || y2 < 1 + this.rand.nextInt(5)) toFill = BEDROCK;

                                primer.setBlockState(x3, y2, z3, toFill);

                                //interpolation
                                origin2 += originIncY;
                            }
                            //interpolation
                            origin1 += originIncZ;
                            offY1 += offYIncZ;
                        }
                        //interpolation
                        origin += originIncX;
                        offZ += plusZIncX;
                        offY += plusYIncX;
                        offZY += plusZYIncX;
                    }
                }
            }
        }

    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        int xSize = 2;
        int ySize = 32;
        int zSize = 2;
        ChunkPrimer primer = new ChunkPrimer();
        genMainStructure(primer, x, z);

        //get biomes from biome provider
        biomesForGeneration = world.getBiomeProvider().getBiomesForGeneration(biomesForGeneration, x * 16, z * 16, 16, 16);
        replaceBiomeBlocks(x, z, primer, biomesForGeneration);

        Chunk chunk = new Chunk(world, primer, x, z);

        byte[] abyte = chunk.getBiomeArray();

        //set biomes
        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(biomesForGeneration[i]);
        }

        chunk.resetRelightChecks();
        return chunk;
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
        SRPWorldData data = SRPWorldData.get(world);
        if (data.getEvolutionPhase() != 8) data.setEvolutionPhase((byte) 8, true, world); //always set parasite evolution phase to 8 (maximum)
        int x1 = x * 16;
        int z1 = z * 16;
        BlockPos blockpos = new BlockPos(x1, 0, z1);

        for (int y = 0; y < 8; y++)
        {
            //Add biome decorations
            BlockPos pos = blockpos.add(16, y * 32, 16);
            Biome biome = world.getBiome(pos);
            biome.decorate(world, rand, blockpos.up(y * 32));
        }

        for (int i = 0; i < 16; i++)
        {
            //generates a few random pockets of infested blocks
            int i1 = this.rand.nextInt(16);
            int j = this.rand.nextInt(256);
            int k = this.rand.nextInt(16);
            infestedBlockGenerator.generate(world, rand, blockpos.add(i1, j, k));
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
