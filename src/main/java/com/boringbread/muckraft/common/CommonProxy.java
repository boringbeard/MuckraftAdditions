package com.boringbread.muckraft.common;

import com.boringbread.muckraft.common.block.BlockPortalStageOne;
import com.boringbread.muckraft.common.block.ModBlocks;
import com.boringbread.muckraft.common.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit() {
        MuckraftCreativeTab.preInitCommon();
        MuckraftWorldGen.preInitCommon();
    }

    public void init()
    {

    }

    public void postInit()
    {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(ModBlocks.PORTAL_STAGE_ONE);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        System.out.println(" 1 ");
        event.getRegistry().register(ModItems.MUCK_CHEESE);
        event.getRegistry().register(ModBlocks.ITEM_PORTAL_STAGE_ONE);
    }
}
