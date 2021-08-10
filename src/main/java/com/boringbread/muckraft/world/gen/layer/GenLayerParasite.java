package com.boringbread.muckraft.world.gen.layer;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.util.WeightedInt;
import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import scala.Int;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GenLayerParasite extends GenLayer
{
    public GenLayerParasite(long l)
    {
        super(l);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] ints = new int[areaWidth * areaHeight];

        for (int x = 0; x < areaWidth; x++)
        {
            for (int z = 0; z < areaHeight; z++)
            {
                int biomeForGeneration;
                initChunkSeed(areaX + x, areaY + z);

                WeightedInt[] weightedInts = {
                        new WeightedInt(5, Biome.getIdForBiome(MuckWorldGen.INFECTED)),
                        new WeightedInt(3, Biome.getIdForBiome(MuckWorldGen.BIOME_FLESH)),
                        new WeightedInt(1, Biome.getIdForBiome(MuckWorldGen.BONE))
                };

                int totalWeight = WeightedRandom.getTotalWeight(Arrays.asList(weightedInts));
                int biomeId = WeightedRandom.getRandomItem(Arrays.asList(weightedInts), nextInt(totalWeight)).value;

                ints[x * areaHeight + z] = biomeId;
            }
        }

        return ints;
    }
}
