package com.boringbread.muckraft.common;

import com.boringbread.muckraft.common.block.BlockPortalStageOne;
import com.boringbread.muckraft.common.item.ItemMuckCheese;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public abstract class CommonProxy {
    public void preInit() {
        MuckraftCreativeTab.preInitCommon();
        BlockPortalStageOne.preInitCommon();
        ItemMuckCheese.preInitCommon();
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
        event.getRegistry().register(BlockPortalStageOne.INSTANCE);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(ItemMuckCheese.INSTANCE);
        event.getRegistry().register(BlockPortalStageOne.ITEM_BLOCK);
    }
}
