package com.boringbread.muckraft.common.block;

import com.boringbread.muckraft.common.Muckraft;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.MaterialPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

import static com.boringbread.muckraft.common.MuckraftCreativeTab.muckraftCreativeTab;

public class BlockPortalStageOne extends BlockBreakable
{
    public static final BlockPortalStageOne INSTANCE = new BlockPortalStageOne();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE);

    public static final String NAME = "portal_stage_one";

    public BlockPortalStageOne()
    {
        super(MaterialPortal.PORTAL, false);
        this.setRegistryName(NAME);
        this.setUnlocalizedName(Muckraft.MODID + ":" + NAME);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        System.out.println("joe");
        if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
        {
            System.out.println("mama");
            entityIn.changeDimension(69);
        }
    }

    public static void preInitCommon()
    {
        ITEM_BLOCK.setRegistryName(Objects.requireNonNull(INSTANCE.getRegistryName())).setUnlocalizedName(INSTANCE.getUnlocalizedName()).setCreativeTab(muckraftCreativeTab);
    }

    public static void preInitClient()
    {

    }
}
