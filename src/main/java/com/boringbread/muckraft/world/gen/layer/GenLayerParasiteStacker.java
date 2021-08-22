package com.boringbread.muckraft.world.gen.layer;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.layer.GenLayer;

import java.util.Arrays;
import java.util.Random;

public class GenLayerParasiteStacker extends GenLayer
{
    private GenLayer[] layers;

    public GenLayerParasiteStacker(long p_i2125_1_, GenLayer[] layers)
    {
        super(p_i2125_1_);
        this.layers = layers;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] ints = new int[areaWidth * areaHeight * 8];

        for (int y = 0; y < 8; y++)
        {
            int[] layerInts = layers[y].getInts(areaX, areaY, areaWidth, areaHeight);

            for (int x = 0; x < areaWidth; x++)
            {
                for (int z = 0; z < areaHeight; z++)
                {
                    ints[(x * areaHeight + z) * 8 + y] = layerInts[x * areaHeight + z];
                }
            }
        }

        return ints;
    }
}
