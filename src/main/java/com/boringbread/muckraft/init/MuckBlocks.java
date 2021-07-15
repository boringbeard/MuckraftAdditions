package com.boringbread.muckraft.init;

import com.boringbread.muckraft.block.BlockPortalStageOne;
import com.boringbread.muckraft.block.BlockPortalStageOneSlab;
import com.boringbread.muckraft.block.BlockPortalStageTwo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MuckBlocks
{
    public static final BlockPortalStageOne PORTAL_STAGE_ONE = new BlockPortalStageOne();
    public static final BlockPortalStageOneSlab PORTAL_STAGE_ONE_SLAB = new BlockPortalStageOneSlab();
    public static final BlockPortalStageTwo PORTAL_STAGE_TWO = new BlockPortalStageTwo();

    public static final Item ITEM_PORTAL_STAGE_ONE = createItemBlock(PORTAL_STAGE_ONE, 1);
    public static final Item ITEM_PORTAL_STAGE_ONE_SLAB = createItemBlock(PORTAL_STAGE_ONE_SLAB, 64);
    public static final Item ITEM_PORTAL_STAGE_TWO = createItemBlock(PORTAL_STAGE_TWO, 1);

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        MuckItems.initModel(ITEM_PORTAL_STAGE_ONE, "portal_stage_one");
        MuckItems.initModel(ITEM_PORTAL_STAGE_ONE_SLAB, "portal_stage_one_slab");
        MuckItems.initModel(ITEM_PORTAL_STAGE_TWO, "portal_stage_two");
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(PORTAL_STAGE_ONE);
        event.getRegistry().register(PORTAL_STAGE_ONE_SLAB);
        event.getRegistry().register(PORTAL_STAGE_TWO);
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(ITEM_PORTAL_STAGE_ONE);
        event.getRegistry().register(ITEM_PORTAL_STAGE_ONE_SLAB);
        event.getRegistry().register(ITEM_PORTAL_STAGE_TWO);
    }

    public static Item createItemBlock(Block block, int stackSize)
    {
        Item itemBlock = new ItemBlock(block);
        assert block.getRegistryName() != null;
        itemBlock.setRegistryName(block.getRegistryName());
        itemBlock.setMaxStackSize(stackSize);
        return itemBlock;
    }
}
