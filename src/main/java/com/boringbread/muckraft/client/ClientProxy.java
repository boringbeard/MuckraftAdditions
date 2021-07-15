package com.boringbread.muckraft.client;

import com.boringbread.muckraft.init.CommonProxy;
import com.boringbread.muckraft.init.MuckBlocks;
import com.boringbread.muckraft.init.MuckItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        MuckBlocks.initModels();
        MuckItems.initModels();
    }
}
