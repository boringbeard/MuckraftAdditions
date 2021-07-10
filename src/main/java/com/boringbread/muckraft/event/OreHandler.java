package com.boringbread.muckraft.event;

import com.boringbread.muckraft.config.Config;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class OreHandler
{
    private static final GenerateMinable.EventType[] oreWhitelistS1 = new GenerateMinable.EventType[]{
            GenerateMinable.EventType.COAL, GenerateMinable.EventType.IRON, GenerateMinable.EventType.ANDESITE,
            GenerateMinable.EventType.DIORITE, GenerateMinable.EventType.DIRT, GenerateMinable.EventType.GRANITE,
            GenerateMinable.EventType.GRAVEL, GenerateMinable.EventType.REDSTONE, GenerateMinable.EventType.SILVERFISH,
            GenerateMinable.EventType.CUSTOM
    };

    @SubscribeEvent
    public static void onGenerateOres(GenerateMinable event)
    {
        if (event.getWorld().provider.getDimension() == Config.stageOneID) {
            if (!Arrays.asList(oreWhitelistS1).contains(event.getType())) event.setResult(Event.Result.DENY);
        }
    }
}
