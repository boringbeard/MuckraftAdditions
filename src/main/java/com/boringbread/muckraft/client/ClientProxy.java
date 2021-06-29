package com.boringbread.muckraft.client;

import com.boringbread.muckraft.init.CommonProxy;
import com.boringbread.muckraft.init.ModBlocks;
import com.boringbread.muckraft.init.ModItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit()
    {
        super.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
        ModItems.initModels();
    }
}
