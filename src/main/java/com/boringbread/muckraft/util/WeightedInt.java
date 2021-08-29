package com.boringbread.muckraft.util;

import net.minecraft.util.WeightedRandom;

public class WeightedInt extends WeightedRandom.Item
{
    public int value;

    public WeightedInt(int itemWeightIn, int value)
    {
        super(itemWeightIn);
        this.value = value;
    }
}
