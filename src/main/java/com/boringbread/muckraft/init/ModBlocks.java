package com.boringbread.muckraft.init;

import com.boringbread.muckraft.block.BlockPortalStageOne;
import com.boringbread.muckraft.block.BlockPortalStageOneSlab;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    public static final BlockPortalStageOne PORTAL_STAGE_ONE = new BlockPortalStageOne();
    public static final BlockPortalStageOneSlab PORTAL_STAGE_ONE_SLAB = new BlockPortalStageOneSlab();

    public static final Item ITEM_PORTAL_STAGE_ONE = createItemBlock(PORTAL_STAGE_ONE, 1);
    public static final Item ITEM_PORTAL_STAGE_ONE_SLAB = createItemBlock(PORTAL_STAGE_ONE_SLAB, 64);

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        ModItems.initModel(ITEM_PORTAL_STAGE_ONE, "portal_stage_one");
        ModItems.initModel(ITEM_PORTAL_STAGE_ONE_SLAB, "portal_stage_one_slab");
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(PORTAL_STAGE_ONE);
        event.getRegistry().register(PORTAL_STAGE_ONE_SLAB);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(ITEM_PORTAL_STAGE_ONE);
        event.getRegistry().register(ITEM_PORTAL_STAGE_ONE_SLAB);
    }

    public static Item createItemBlock(Block block, int stackSize)
    {
        Item itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        itemBlock.setMaxStackSize(stackSize);
        return itemBlock;
    }
}
