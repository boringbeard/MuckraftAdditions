package com.boringbread.muckraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class TileEntityIncinerator extends TileEntity
{
    private ItemStack item = ItemStack.EMPTY;
    private FluidTank tank = new FluidTank(5_000);

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("sacrifice")) item = new ItemStack(compound.getCompoundTag("sacrifice"));
        if (compound.hasKey("fluid")) tank.readFromNBT(compound.getCompoundTag("fluid"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("sacrifice", item.writeToNBT(new NBTTagCompound()));
        compound.setTag("fluid", tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return super.hasCapability(capability, facing);
    }

    public void setItem(ItemStack item)
    {
        this.item = item;
    }

    public ItemStack getItem()
    {
        return item;
    }

    public FluidTank getTank()
    {
        return tank;
    }
}
