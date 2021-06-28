package com.boringbread.muckraft.common.block;

import com.boringbread.muckraft.common.Muckraft;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortalStageOne extends BlockBreakable
{
    public static final String NAME = "portal_stage_one";

    public BlockPortalStageOne()
    {
        super(Material.ROCK, false);
        this.setRegistryName(NAME);
        this.setUnlocalizedName(Muckraft.MODID + "_" + NAME);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
        {
            entityIn.changeDimension(69);
        }
    }
}
