package com.boringbread.muckraft.network;

import com.boringbread.muckraft.tileentity.TileEntityPortalS2;
import io.netty.buffer.ByteBuf;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemStackHandler;

public class MessageConfirmPortal implements IMessage
{
    public MessageConfirmPortal(){}

    private BlockPos pos;

    public MessageConfirmPortal(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
    }

    public static class MessageHandler implements IMessageHandler<MessageConfirmPortal, IMessage>
    {
        @Override
        public IMessage onMessage(MessageConfirmPortal message, MessageContext ctx)
        {
            World world = ctx.getServerHandler().player.world;
            BlockPos pos = message.pos;
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityPortalS2)
            {
                ItemStackHandler portalInventory = ((TileEntityPortalS2) tileEntity).getItemStackHandler();
                ((TileEntityPortalS2) tileEntity).setSacrificeAccepted(true);
                world.scheduleUpdate(message.pos, world.getBlockState(pos).getBlock(), 0);
                for (int i = 0; i < portalInventory.getSlots(); i++)
                {
                    portalInventory.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
            return null;
        }
    }
}
