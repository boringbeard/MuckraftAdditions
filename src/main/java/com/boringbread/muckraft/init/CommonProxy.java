package com.boringbread.muckraft.init;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.client.gui.GuiHandler;
import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.event.OreHandler;
import com.boringbread.muckraft.network.MuckPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.preInitCommon(event);
        MinecraftForge.ORE_GEN_BUS.register(OreHandler.class);
        MuckraftCreativeTab.preInitCommon();
        MuckPacketHandler.preInitCommon();
    }

    public void init()
    {
        MuckWorldGen.initCommon();
        NetworkRegistry.INSTANCE.registerGuiHandler(Muckraft.instance, new GuiHandler());
    }

    public void postInit()
    {
        Config.postInitCommon();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        MuckBlocks.registerBlocks(event);
        MuckBlocks.registerTileEntities();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        MuckItems.registerItems(event);
        MuckBlocks.registerItemBlocks(event);
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event)
    {
        MuckWorldGen.registerBiomes(event);
    }
}
