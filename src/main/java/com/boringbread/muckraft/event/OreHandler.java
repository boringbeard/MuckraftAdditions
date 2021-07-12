package com.boringbread.muckraft.event;

import com.boringbread.muckraft.config.Config;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class OreHandler
{
    private static final GenerateMinable.EventType[] oreBlacklistS1 = new GenerateMinable.EventType[]{
            GenerateMinable.EventType.DIAMOND, GenerateMinable.EventType.EMERALD, GenerateMinable.EventType.GOLD,
            GenerateMinable.EventType.LAPIS
    };

    @SubscribeEvent
    public static void onGenerateOres(GenerateMinable event)
    {
        if (event.getWorld().provider.getDimension() == Config.stageOneID) {
            if (Arrays.asList(oreBlacklistS1).contains(event.getType())) event.setResult(Event.Result.DENY);
        }
    }
}
