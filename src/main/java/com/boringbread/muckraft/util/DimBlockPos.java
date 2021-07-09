package com.boringbread.muckraft.util;

import net.minecraft.util.math.BlockPos;

public class DimBlockPos {
    private BlockPos pos;
    private int DimID;

    public DimBlockPos(BlockPos pos, int DimID)
    {
        this.pos = pos;
        this.DimID = DimID;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getDimID() {
        return DimID;
    }
}
