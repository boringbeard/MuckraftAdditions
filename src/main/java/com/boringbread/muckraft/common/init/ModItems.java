package com.boringbread.muckraft.common.init;

import com.boringbread.muckraft.common.Muckraft;
import com.boringbread.muckraft.common.item.ItemMuckCheese;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static final ItemMuckCheese MUCK_CHEESE = new ItemMuckCheese();

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        initModel(MUCK_CHEESE, "muck_cheese");
    }

    @SideOnly(Side.CLIENT)
    public static void initModel(Item item, String name)
    {
        ModelResourceLocation location = new ModelResourceLocation(Muckraft.MODID + ":" + name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
    }
}
