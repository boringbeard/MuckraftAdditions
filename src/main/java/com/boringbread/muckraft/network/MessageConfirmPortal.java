package com.boringbread.muckraft.network;

import com.boringbread.muckraft.block.BlockPortalStageTwo;
import com.boringbread.muckraft.tileentity.TileEntityPortalStageTwo;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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
            TileEntity tileEntity = ctx.getServerHandler().player.world.getTileEntity(message.pos);
            if (tileEntity instanceof TileEntityPortalStageTwo)
            {
                ItemStackHandler portalInventory = ((TileEntityPortalStageTwo) tileEntity).getItemStackHandler();
                ((TileEntityPortalStageTwo) tileEntity).setSacrificeAccepted(true);
                for (int i = 0; i < portalInventory.getSlots(); i++)
                {
                    portalInventory.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
            return null;
        }
    }
}
