package com.boringbread.muckraft.inventory;

import com.boringbread.muckraft.tileentity.TileEntityPortalStageTwo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPortalStageTwo extends Container
{
    private final TileEntityPortalStageTwo tilePortalStageTwo;
    private final SacrificeSlot[] sacrificeSlots;

    public ContainerPortalStageTwo(IInventory playerInventory, TileEntityPortalStageTwo tilePortalStageTwo)
    {
        this.tilePortalStageTwo = tilePortalStageTwo;
        this.sacrificeSlots = new SacrificeSlot[5];
        int inventoryColumns = 9;
        int inventoryRows = 3;

        for (int row = 0; row < inventoryRows; ++row)
        {
            for (int column = 0; column < inventoryColumns; ++column)
            {
                this.addSlotToContainer(new Slot(playerInventory, row * 9 + column + 9, 7 + column * 18, 70 + row * 18));
            }
        }

        for (int column1 = 0; column1 < inventoryColumns; ++column1)
        {
            this.addSlotToContainer(new Slot(playerInventory, column1, 7 + column1 * 18, 128));
        }

        IItemHandler itemHandler = this.tilePortalStageTwo.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        for (int sacrificeSlotNumber = 0; sacrificeSlotNumber < 5; sacrificeSlotNumber++)
        {
            sacrificeSlots[sacrificeSlotNumber] = new SacrificeSlot(itemHandler, sacrificeSlotNumber, 10 + sacrificeSlotNumber * 27, 27);
            this.addSlotToContainer(sacrificeSlots[sacrificeSlotNumber]);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!playerIn.world.isRemote)
        {
            for (SacrificeSlot sacrificeSlot: sacrificeSlots)
            {
                ItemStack itemstack = sacrificeSlot.decrStackSize(sacrificeSlot.getSlotStackLimit());

                if (!itemstack.isEmpty())
                {
                    playerIn.dropItem(itemstack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return tilePortalStageTwo.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0)
            {
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (this.mergeItemStack(itemstack1, 0, 1, false))
            {
                return ItemStack.EMPTY;
            }
            else if (index >= 1 && index < 28)
            {
                if (!this.mergeItemStack(itemstack1, 28, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 28 && index < 37)
            {
                if (!this.mergeItemStack(itemstack1, 1, 28, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 1, 37, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    class SacrificeSlot extends SlotItemHandler
    {
        public SacrificeSlot(IItemHandler inventoryIn, int index, int xIn, int yIn)
        {
            super(inventoryIn, index, xIn, yIn);
        }

        public boolean isItemValid(ItemStack stack)
        {
            return true;
        }

        public int getSlotStackLimit()
        {
            return 1;
        }
    }

}
