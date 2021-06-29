package com.boringbread.muckraft.block;

import com.boringbread.muckraft.world.MuckTeleporter;
import com.boringbread.muckraft.Muckraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockPortalStageOne extends Block
{
    public static final String NAME = "portal_stage_one";

    public BlockPortalStageOne()
    {
        super(Material.ROCK);
        this.setRegistryName(NAME);
        this.setHarvestLevel("pickaxe", 1);
        this.setUnlocalizedName(Muckraft.MODID + "_" + NAME);
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        if(!worldIn.isRemote)
        {
            entityIn.motionY = 0.0D;

            if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
            {
                entityIn.changeDimension(69, new MuckTeleporter());
            }
        }
    }
}
