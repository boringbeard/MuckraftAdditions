package com.boringbread.muckraft.init;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.item.ItemMuckCheese;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MuckItems
{
    public static final ItemMuckCheese MUCK_CHEESE = new ItemMuckCheese();

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        initModel(MUCK_CHEESE, ItemMuckCheese.NAME);
    }

    @SideOnly(Side.CLIENT)
    public static void initModel(Item item, String name)
    {
        ModelResourceLocation location = new ModelResourceLocation(Muckraft.MOD_ID + ":" + name, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
    }

    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(MuckItems.MUCK_CHEESE);
    }
}
