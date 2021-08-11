package com.boringbread.muckraft.world.gen.layer;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.layer.GenLayer;

import java.util.Arrays;
import java.util.Random;

public class GenLayerParasiteStacker extends GenLayer
{
    private GenLayer[] layers;
    private NoiseGeneratorOctaves perlin;
    private double[] noise;

    public GenLayerParasiteStacker(long p_i2125_1_, GenLayer[] layers)
    {
        super(p_i2125_1_);
        this.layers = layers;
        perlin = new NoiseGeneratorOctaves(new Random(p_i2125_1_), 2);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] ints = new int[areaWidth * areaHeight * 256];
        noise = new double[areaWidth * areaHeight];
        Arrays.fill(noise, 0);

        for (int y = 0; y < 8; y++)
        {
            int[] layerInts = layers[y].getInts(areaX, areaY, areaWidth, areaHeight);
            double[] previousNoise = noise.clone();
            noise = perlin.generateNoiseOctaves(noise, areaX, areaY, areaWidth, areaHeight, 0.125, 0.125, 0);

            for (int x = 0; x < areaWidth; x++)
            {
                for (int z = 0; z < areaHeight; z++)
                {
                    int previousHeight = (int) MathHelper.clamp(previousNoise[x * areaHeight + z] * 5, -8, 8);
                    int currentHeight = y == 7 ? 32 : (int) MathHelper.clamp(noise[x * areaHeight + z] * 5, -8, 8) + 32;
                    for (int y1 = previousHeight; y1 < currentHeight; y1++)
                    {
                        ints[(x * areaHeight + z) * 256 + y * 32 + y1] = layerInts[x * areaHeight + z];
                    }
                }
            }
        }

        return ints;
    }
}
