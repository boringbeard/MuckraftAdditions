package com.boringbread.muckraft.init;

import com.boringbread.muckraft.block.*;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.tileentity.TileEntityPortalS2;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MuckBlocks
{
    public static final Fluid FLUID_PARASITE_JUICE = new Fluid("parasiteJuice", new ResourceLocation("muckraft:"), new ResourceLocation("muckraft:"), 2752289);
    public static final BlockPortalS1 PORTAL_STAGE_ONE = new BlockPortalS1();
    public static final BlockPortalS1Slab PORTAL_STAGE_ONE_SLAB = new BlockPortalS1Slab();
    public static final BlockPortalS2 PORTAL_STAGE_TWO = new BlockPortalS2();
    public static final BlockPortalS3 PORTAL_STAGE_THREE = new BlockPortalS3();
    public static final Item ITEM_PORTAL_STAGE_ONE = createItemBlock(PORTAL_STAGE_ONE, 1);
    public static final Item ITEM_PORTAL_STAGE_ONE_SLAB = createItemBlock(PORTAL_STAGE_ONE_SLAB, 64);
    public static final Item ITEM_PORTAL_STAGE_TWO = createItemBlock(PORTAL_STAGE_TWO, 1);
    public static final Item ITEM_PORTAL_STAGE_THREE = createItemBlock(PORTAL_STAGE_THREE, 1);
    public static BlockParasiteJuice parasiteJuice;

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        MuckItems.initModel(ITEM_PORTAL_STAGE_ONE, "portal_stage_one");
        MuckItems.initModel(ITEM_PORTAL_STAGE_ONE_SLAB, "portal_stage_one_slab");
        MuckItems.initModel(ITEM_PORTAL_STAGE_TWO, "portal_stage_two");
    }

    private static void registerFluids()
    {
        FluidRegistry.addBucketForFluid(FLUID_PARASITE_JUICE);
        parasiteJuice = new BlockParasiteJuice(FLUID_PARASITE_JUICE);
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        registerFluids();
        event.getRegistry().register(PORTAL_STAGE_ONE);
        event.getRegistry().register(PORTAL_STAGE_ONE_SLAB);
        event.getRegistry().register(PORTAL_STAGE_TWO);
        event.getRegistry().register(PORTAL_STAGE_THREE);
        event.getRegistry().register(parasiteJuice);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityPortalS2.class, new ResourceLocation("muckraft:portal_stage_two_tile_entity"));
    }

    public static void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(ITEM_PORTAL_STAGE_ONE);
        event.getRegistry().register(ITEM_PORTAL_STAGE_ONE_SLAB);
        event.getRegistry().register(ITEM_PORTAL_STAGE_TWO);
        event.getRegistry().register(ITEM_PORTAL_STAGE_THREE);
        event.getRegistry().register(createItemBlock(parasiteJuice, 1));
    }

    private static Item createItemBlock(Block block, int stackSize)
    {
        Item itemBlock = new ItemBlock(block);
        assert block.getRegistryName() != null;
        itemBlock.setRegistryName(block.getRegistryName());
        itemBlock.setMaxStackSize(stackSize);
        return itemBlock;
    }
}
