package com.boringbread.muckraft.item;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.item.ItemSword;

public class ItemParasiteSword extends ItemSword
{
    //sword probably made out of parasites or from parasite dimension. currently just reskinned diamond sword
    //add cool mechanics and stuff
    public static final String NAME = "parasite_sword";

    public ItemParasiteSword()
    {
        super(ToolMaterial.DIAMOND);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setRegistryName(NAME);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + NAME);
        setHasSubtypes(false);
    }
}