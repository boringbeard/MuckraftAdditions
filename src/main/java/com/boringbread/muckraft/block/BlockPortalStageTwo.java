package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.client.gui.GuiHandler;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.tileentity.TileEntityPortalStageTwo;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BlockPortalStageTwo extends BlockMuckPortal implements ITileEntityProvider
{
    public static final String NAME = "portal_stage_two";

    public BlockPortalStageTwo()
    {
        super(Material.ROCK, 1, 100);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 2);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + NAME);
        setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, true));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return true;

        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityPortalStageTwo)
        {
            System.out.println(GuiHandler.getID());
            playerIn.openGui(Muckraft.instance, GuiHandler.getID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPortalStageTwo();
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED);
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer)
    {
        return this.getDefaultState();
    }

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(ACTIVATED, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ACTIVATED) ? 1 : 0;
    }

    @Override
    public void onFallenUpon(World worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn, float fallDistance)
    {
        if(!worldIn.isRemote)
        {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
            entityIn.timeUntilPortal = 300;
        }
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        double x = entityIn.posX;
        double y = entityIn.posY;
        double z = entityIn.posZ;
        BlockPos pos = new BlockPos(x, y - 1, z);
        boolean isActivated = worldIn.getBlockState(pos).getValue(ACTIVATED);

        if(isActivated)
        {
            if(!worldIn.isRemote) teleportPlayer(entityIn, worldIn);
        }
        else if(!worldIn.isRemote) entityIn.timeUntilPortal = 300;

        entityIn.motionY = 0.0D;
    }

    @Override
    protected PortalStatus getPortalStatus(BlockPos pos, World worldIn) {
        return PortalStatus.ACTIVE_COMPLETE_X;
    }
}
