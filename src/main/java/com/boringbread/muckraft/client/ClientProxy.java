package com.boringbread.muckraft.client;

import com.boringbread.muckraft.client.renderer.tileentity.TileEntityIncineratorRenderer;
import com.boringbread.muckraft.init.CommonProxy;
import com.boringbread.muckraft.init.MuckBlocks;
import com.boringbread.muckraft.init.MuckItems;
import com.boringbread.muckraft.tileentity.TileEntityIncinerator;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy
{
    public static final TileEntityIncineratorRenderer INCINERATOR_RENDERER = new TileEntityIncineratorRenderer();
    //place for clientside events
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIncinerator.class, INCINERATOR_RENDERER);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        MuckBlocks.initModels();
        MuckItems.initModels();
    }
}
