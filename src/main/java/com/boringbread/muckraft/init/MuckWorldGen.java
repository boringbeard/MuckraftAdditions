package com.boringbread.muckraft.init;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.world.WorldProviderS1;
import com.boringbread.muckraft.world.WorldProviderS2;
import com.boringbread.muckraft.world.WorldProviderS4;
import com.boringbread.muckraft.world.biome.BiomeBone;
import com.boringbread.muckraft.world.biome.BiomeFlesh;
import com.boringbread.muckraft.world.biome.BiomeInfected;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;

public class MuckWorldGen
{
        public static final DimensionType STAGE_ONE = DimensionType.register("STAGE_ONE", "_stageone", Config.stageOneID, WorldProviderS1.class, true);
        public static final DimensionType STAGE_TWO = DimensionType.register("STAGE_TWO", "_stagetwo", Config.stageTwoID, WorldProviderS2.class, true);
        public static final DimensionType STAGE_FOUR = DimensionType.register("STAGE_FOUR", "_stagefour", Config.stageFourID, WorldProviderS4.class, true);

        public static final BiomeFlesh BIOME_FLESH = new BiomeFlesh();
        public static final Biome BONE = new BiomeBone();
        public static final Biome INFECTED = new BiomeInfected();

        public static void initCommon()
        {
            DimensionManager.registerDimension(Config.stageOneID, STAGE_ONE);
            DimensionManager.registerDimension(Config.stageTwoID, STAGE_TWO);
            DimensionManager.registerDimension(Config.stageFourID, STAGE_FOUR);
        }

        public static void registerBiomes(RegistryEvent.Register<Biome> event)
        {
            event.getRegistry().register(BIOME_FLESH);
            event.getRegistry().register(BONE);
            event.getRegistry().register(INFECTED);
        }
}
