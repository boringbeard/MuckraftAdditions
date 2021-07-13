package com.boringbread.muckraft.creativetab;

import com.boringbread.muckraft.init.MuckItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class MuckraftCreativeTab extends CreativeTabs
{
    public static MuckraftCreativeTab muckraftCreativeTab;

    public MuckraftCreativeTab(String label) { super(label); }

    @SideOnly(Side.CLIENT)
    @Override
    public @NotNull ItemStack getTabIconItem() { return new ItemStack(MuckItems.MUCK_CHEESE); }

    public static void preInitCommon() { muckraftCreativeTab = new MuckraftCreativeTab("muckraft_creative_tab"); }
}