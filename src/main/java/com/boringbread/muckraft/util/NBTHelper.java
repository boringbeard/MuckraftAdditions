package com.boringbread.muckraft.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
    public static NBTTagCompound getTagCompoundSafe(ItemStack stack)
    {
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tagCompound == null)
        {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }

        return tagCompound;
    }
}
