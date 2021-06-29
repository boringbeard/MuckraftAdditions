package com.boringbread.muckraft.common.init;

import com.boringbread.muckraft.common.MuckraftCreativeTab;
import com.boringbread.muckraft.common.block.BlockPortalStageOne;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    public static final BlockPortalStageOne PORTAL_STAGE_ONE = new BlockPortalStageOne();

    public static final Item ITEM_PORTAL_STAGE_ONE = createItemBlock(PORTAL_STAGE_ONE);

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        ModItems.initModel(ITEM_PORTAL_STAGE_ONE, "portal_stage_one");
    }

    public static Item createItemBlock(Block block)
    {
        Item itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        itemBlock.setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        return itemBlock;
    }
}
