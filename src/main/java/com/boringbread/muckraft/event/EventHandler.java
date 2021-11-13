package com.boringbread.muckraft.event;

import com.boringbread.muckraft.MuckReflections;
import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.WorldProviderS1;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class EventHandler
{
    //non init events here
    private static boolean createdSpawn;
    private static World overWorld;

    //fix this - supposed to spawn you in S1
    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event) throws InvocationTargetException, IllegalAccessException, IOException
    {
        if (event.getWorld().provider.getDimension() == 0)
        {
            createdSpawn = false;
            event.setCanceled(true);
            overWorld = event.getWorld();
            overWorld.provider.setDimension(Config.stageOneID);
        }

        if (event.getWorld().provider.getDimension() == Config.stageOneID && !createdSpawn)
        {
            createdSpawn = true;
            World stageOne = event.getWorld();
            MuckReflections.CREATE_SPAWN_POSITION.setAccessible(true);
            MuckReflections.CREATE_SPAWN_POSITION.invoke(stageOne, event.getSettings());
            overWorld.setSpawnPoint(stageOne.getSpawnPoint());
        }
    }
}
