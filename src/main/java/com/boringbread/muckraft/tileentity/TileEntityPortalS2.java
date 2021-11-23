package com.boringbread.muckraft.tileentity;

import com.boringbread.muckraft.block.BlockPortalS2;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class TileEntityPortalS2 extends TileEntity implements SimpleComponent
{
    private static final int CAPACITY = 1_000_000;
    private final ItemStack[] heldItems = new ItemStack[5]; //items held in sacrifice slots
    private boolean sacrificeAccepted; //whether or not the item sacrifice has been made
    private UUID toTeleport = null; //entity queued up for transport
    private int timer = 300; //used to time the amount of time the player is on the portal
    //handles items and storage
    private ItemStackHandler itemStackHandler = new ItemStackHandler(heldItems.length)
    {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack)
        {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            TileEntityPortalS2.this.markDirty();
        }
    };
    private EnergyStorage energyStorage = new EnergyStorage(CAPACITY, 1_000); //handles energy storage

    public TileEntityPortalS2() {
        Arrays.fill(heldItems, ItemStack.EMPTY);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if (compound.hasKey("energy")) energyStorage = new EnergyStorage(CAPACITY, 1_000, 1_000, compound.getInteger("energy"));
        if (compound.hasKey("timer")) timer = compound.getInteger("timer");
        if (compound.hasKey("UUID")) toTeleport = compound.getUniqueId("UUID");
        else toTeleport = null;
        if (compound.hasKey("sacrificeAccepted")) sacrificeAccepted = compound.getBoolean("sacrificeAccepted");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setInteger("energy", energyStorage.getEnergyStored());
        compound.setInteger("timer", timer);
        if (toTeleport != null)
        {
            compound.setUniqueId("UUID", toTeleport);
        }
        else
        {
            compound.removeTag("UUID");
        }
        compound.setBoolean("sacrificeAccepted", sacrificeAccepted);
        return compound;
    }

    public boolean isUsableByPlayer(EntityPlayer player)
    {
        //only can use if its within 8 blocks
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 8.0D;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                || (capability == CapabilityEnergy.ENERGY && facing == world.getBlockState(pos).getValue(BlockPortalS2.FACING).getOpposite()))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        if (capability == CapabilityEnergy.ENERGY && (facing == world.getBlockState(pos).getValue(BlockPortalS2.FACING).getOpposite()))
        {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }

        return super.getCapability(capability, facing);
    }

    public boolean readyForActivation()
    {
        return sacrificeAccepted && energyStorage.getEnergyStored() >= CAPACITY;
    }

    //getter and setter methods
    public EnergyStorage getEnergyStorage()
    {
        return energyStorage;
    }

    public ItemStackHandler getItemStackHandler()
    {
        return itemStackHandler;
    }

    public boolean isSacrificeAccepted()
    {
        return sacrificeAccepted;
    }

    public void setSacrificeAccepted(boolean sacrificeAccepted)
    {
        this.sacrificeAccepted = sacrificeAccepted;
    }

    public void setTimer(int timer)
    {
        this.timer = timer;
    }

    public int getTimer()
    {
        return timer;
    }

    public void setToTeleport(UUID uuid)
    {
        this.toTeleport = uuid;
    }

    public UUID getToTeleport()
    {
        return toTeleport;
    }

    @Override
    public String getComponentName()
    {
        return "portal_stage_two";
    }

    @Callback
    public Object[] greet(Context context, Arguments args) {
        return new Object[]{String.format("Hello, %s!", args.checkString(0))};
    }
}
