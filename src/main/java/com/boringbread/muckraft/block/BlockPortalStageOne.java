package com.boringbread.muckraft.block;

import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
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
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 1);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MODID + "_" + NAME);
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        if(!worldIn.isRemote)
        {
            entityIn.motionY = 0.0D;

            if(entityIn.timeUntilPortal > 100) entityIn.timeUntilPortal = 100;

            if(entityIn.timeUntilPortal == 0)
            {
                if (worldIn.provider.getDimension() != 69)
                {
                    entityIn.changeDimension(69, new MuckTeleporter());
                }
                else
                {
                    entityIn.changeDimension(0, new MuckTeleporter());
                }

                entityIn.timeUntilPortal = 100;
            }
            else
            {
                entityIn.timeUntilPortal -= 1;
            }
        }
    }
}
