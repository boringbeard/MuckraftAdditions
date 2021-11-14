package com.boringbread.muckraft.block;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.init.MuckBlocks;
import com.boringbread.muckraft.util.DimBlockPos;
import com.boringbread.muckraft.world.MuckTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class BlockMuckPortal extends Block
{
    //Class to contain methods and other common code for all portal blocks (mostly just teleportation)
    //TO DO: get rid of unnecessary stuff in here/get rid of the class as a whole.
    //TO DO: transfer multiblock checker into a separate more general thing to check for multiblocks
    //could maybe replace this with an interface full of default methods
    private final int stage;
    private final int cooldown;
    public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

    public BlockMuckPortal(Material blockMaterialIn, int stage, int cooldown)
    {
        super(blockMaterialIn);
        this.stage = stage; //used to figure out which stages to transfer between - get rid of this later
        this.cooldown = cooldown; //time it takes to transfer - also get rid of this
    }

    protected void teleportPlayer(Entity entityIn, World worldIn, BlockPos pos)
    {
        //method to teleport player
        IBlockState state = worldIn.getBlockState(pos);

        //figures out which dimension to transport to based on current dimension and stage of portal
        //if current dimension = the stage of the portal, transport to next stage, otherwise transport to stage of portal
        if (worldIn.provider.getDimension() == Config.dimensionIDs[stage])
        {
            entityIn.changeDimension(Config.dimensionIDs[stage + 1], new MuckTeleporter(state));
        }
        else
        {
            entityIn.changeDimension(Config.dimensionIDs[stage], new MuckTeleporter(state));
        }
    }

    public abstract void makePortal(BlockPos pos, World world, IBlockState portal);

    @Override
    protected @NotNull BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED);
    }

    public int getStage()
    {
        return stage;
    }

    protected abstract PortalStatus getPortalStatus(BlockPos pos, World worldIn);

    //this is to check if multiblock is complete - transfer this into a separate more general thing to check for multiblocks
    protected enum PortalStatus
    {
        COMPLETE_X, COMPLETE_Z, ACTIVE_COMPLETE_X, ACTIVE_COMPLETE_Z, INCOMPLETE
    }
}
