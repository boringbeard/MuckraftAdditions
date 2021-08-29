package com.boringbread.muckraft.client.gui;

import com.boringbread.muckraft.inventory.ContainerPortalS2;
import com.boringbread.muckraft.tileentity.TileEntityPortalS2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler
{
    private static final int ID = 69;
    public static int getID() {return ID;}

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID != GuiHandler.ID) {
            System.err.println("Invalid ID: expected " + GuiHandler.ID + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityPortalS2) {
            TileEntityPortalS2 tileEntityPortalS2 = (TileEntityPortalS2) tileEntity;
            return new ContainerPortalS2(player.inventory, tileEntityPortalS2);
        }
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID != GuiHandler.ID) {
            System.err.println("Invalid ID: expected " + GuiHandler.ID + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityPortalS2) {
            TileEntityPortalS2 tileEntityPortalS2 = (TileEntityPortalS2) tileEntity;
            return new GuiPortalStageTwo(player.inventory, tileEntityPortalS2);
        }
        return null;
    }
}
