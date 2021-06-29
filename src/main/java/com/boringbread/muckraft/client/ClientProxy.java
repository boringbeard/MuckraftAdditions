package com.boringbread.muckraft.client;

import com.boringbread.muckraft.common.CommonProxy;
import com.boringbread.muckraft.common.init.ModBlocks;
import com.boringbread.muckraft.common.init.ModItems;
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
