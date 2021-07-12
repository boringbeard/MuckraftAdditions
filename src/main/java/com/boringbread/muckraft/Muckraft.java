package com.boringbread.muckraft;

import com.boringbread.muckraft.init.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Muckraft.MODID, name = Muckraft.NAME, version = Muckraft.VERSION, acceptedMinecraftVersions = Muckraft.MCVERSION, useMetadata = true)
public class Muckraft
{
    public static final String MODID = "muckraft";
    public static final String NAME = "Muckraft Additions";
    public static final String VERSION = "0.1.0";
    public static final String MCVERSION = "[1.12.2]";

    public static Logger logger;

    @SidedProxy(clientSide = "com.boringbread.muckraft.client.ClientProxy", serverSide = "com.boringbread.muckraft.init.DedicatedServerProxy")

    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
