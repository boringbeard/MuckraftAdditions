package com.boringbread.muckraft.init;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.preInitCommon(event);
        MuckraftCreativeTab.preInitCommon();
        MuckraftWorldGen.preInitCommon();
    }

    public void init()
    {

    }

    public void postInit()
    {
        Config.postInitCommon();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        ModBlocks.registerBlocks(event);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        ModItems.registerItems(event);
        ModBlocks.registerItemBlocks(event);
    }
}
