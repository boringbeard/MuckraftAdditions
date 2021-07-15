package com.boringbread.muckraft.event;

import com.boringbread.muckraft.config.Config;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event)
    {
        event.getWorld().provider.setDimension(Config.stageOneID);
    }
}
