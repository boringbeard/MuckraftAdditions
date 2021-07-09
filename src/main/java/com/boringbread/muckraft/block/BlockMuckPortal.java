package com.boringbread.muckraft.block;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.world.MuckTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockMuckPortal extends Block
{
    private final int stage;
    private final int cooldown;
    public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

    public BlockMuckPortal(Material blockMaterialIn, int stage, int cooldown) {
        super(blockMaterialIn);
        this.stage = stage;
        this.cooldown = cooldown;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if(!worldIn.isRemote)
        {
            PortalStatus status = getPortalStatus(pos, worldIn);
            if (status != PortalStatus.ACTIVE_COMPLETE_X && status != PortalStatus.ACTIVE_COMPLETE_Z) {
                worldIn.setBlockState(pos, state.withProperty(ACTIVATED, false));
            }
        }
    }

    protected void teleportPlayer(Entity entityIn, World worldIn)
    {
        if (entityIn.timeUntilPortal > cooldown + 1) entityIn.timeUntilPortal = cooldown + 1;

        if (entityIn.timeUntilPortal < 0) entityIn.timeUntilPortal = -1;

        if (entityIn.timeUntilPortal == 1) {
            System.out.println(Config.dimensionIDs[stage]);
            if (worldIn.provider.getDimension() != Config.dimensionIDs[stage]) {
                entityIn.changeDimension(Config.dimensionIDs[stage + 1], new MuckTeleporter(stage));
            } else {
                entityIn.changeDimension(Config.dimensionIDs[stage], new MuckTeleporter(stage));
            }

            entityIn.timeUntilPortal -= 1;
        }

        entityIn.timeUntilPortal -= 1;
    }

    protected abstract PortalStatus getPortalStatus(BlockPos pos, World worldIn);

    protected enum PortalStatus
    {
        COMPLETE_X, COMPLETE_Z, ACTIVE_COMPLETE_X, ACTIVE_COMPLETE_Z, INCOMPLETE
    }
}
