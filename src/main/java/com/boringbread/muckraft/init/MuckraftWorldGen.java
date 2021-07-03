package com.boringbread.muckraft.init;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.world.MuckTeleporter;
import com.boringbread.muckraft.world.MuckraftWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class MuckraftWorldGen {
            public static final DimensionType STAGE_ONE = DimensionType.register("stageone", "_stageone", Config.stageOneID, MuckraftWorldProvider.class, true);

            public static void preInitCommon()
            {
                DimensionManager.registerDimension(Config.stageOneID, STAGE_ONE);
            }
}
