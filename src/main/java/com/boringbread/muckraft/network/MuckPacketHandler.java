package com.boringbread.muckraft.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MuckPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("muckraft");
    private static int id = 0;

    public static void preInitCommon()
    {
        INSTANCE.registerMessage(MessageConfirmPortal.MessageHandler.class, MessageConfirmPortal.class, id++, Side.SERVER);
    }
}
