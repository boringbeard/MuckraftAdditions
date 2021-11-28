package com.boringbread.muckraft.tileentity;

import com.boringbread.muckraft.block.BlockIncinerator;
import com.boringbread.muckraft.block.BlockPortalS2;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class TileEntityIncinerator extends TileEntity implements ITickable
{
    private ItemStackHandler itemStackHandler = new ItemStackHandler();
    private FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME);

    @Override
    public void update()
    {

    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("item")) itemStackHandler.deserializeNBT(compound.getCompoundTag("item"));
        if (compound.hasKey("fluid")) tank.readFromNBT(compound.getCompoundTag("fluid"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("item", itemStackHandler.serializeNBT());
        compound.setTag("fluid", tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if ((capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                && facing != world.getBlockState(pos).getValue(BlockIncinerator.FACING))
                ||
                (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                && facing == world.getBlockState(pos).getValue(BlockIncinerator.FACING)))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (facing != world.getBlockState(pos).getValue(BlockPortalS2.FACING)))
        {
            return (T) itemStackHandler;
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && (facing == world.getBlockState(pos).getValue(BlockPortalS2.FACING)))
        {
            return (T) tank;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasFastRenderer()
    {
        return true;
    }

    public ItemStackHandler getItemHandler()
    {
        return itemStackHandler;
    }

    public FluidTank getTank()
    {
        return tank;
    }
}
