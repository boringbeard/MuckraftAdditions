package com.boringbread.muckraft.common;

import com.boringbread.muckraft.common.item.ItemMuckCheese;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public abstract class CommonProxy {
    public void preInit() {
        //MuckraftWorldGen.preInitCommon();
        MuckraftCreativeTab.preInitCommon();
        ItemMuckCheese.preInitCommon();
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

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(ItemMuckCheese.INSTANCE);
    }
}
