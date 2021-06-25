package com.boringbread.muckraft.common;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class MuckraftWorldGen {
            public static final DimensionType STAGE_ONE = DimensionType.register("stageone", "_stageone", 69, MuckraftWorldProvider.class, true);

            public static void preInit()
            {
                DimensionManager.registerDimension(69, STAGE_ONE);
            }
}
