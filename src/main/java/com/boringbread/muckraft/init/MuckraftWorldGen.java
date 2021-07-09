package com.boringbread.muckraft.init;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.world.WorldProviderS1;
import com.boringbread.muckraft.world.WorldProviderS2;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class MuckraftWorldGen {
            public static final DimensionType STAGE_ONE = DimensionType.register("stageone", "_stageone", Config.stageOneID, WorldProviderS1.class, true);
            public static final DimensionType STAGE_TWO = DimensionType.register("stagetwo", "_stagetwo", Config.stageTwoID, WorldProviderS2.class, true);

            public static void preInitCommon()
            {
                DimensionManager.registerDimension(Config.stageOneID, STAGE_ONE);
                DimensionManager.registerDimension(Config.stageTwoID, STAGE_TWO);
            }
}
