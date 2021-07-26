package com.boringbread.muckraft.item;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.item.ItemSword;

public class ItemParasiteSword extends ItemSword {
    public static final String SWORD_NAME = "parasite_sword";
    public ItemParasiteSword() {
        super(ToolMaterial.DIAMOND);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setRegistryName(SWORD_NAME);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + SWORD_NAME);
        setHasSubtypes(false);
    }
}
