package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BlockPortalS3 extends BlockMuckPortal
{
    public static final String NAME = "portal_stage_four";

    public BlockPortalS3()
    {
        super(Material.GLASS, 3, 100);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 2);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + NAME);
        setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
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
    public void onLanded(World worldIn, Entity entityIn)
    {
        double x = entityIn.posX;
        double y = entityIn.posY;
        double z = entityIn.posZ;
        BlockPos pos = new BlockPos(x, y - 1, z);
        boolean isActivated = worldIn.getBlockState(pos).getValue(ACTIVATED);

        if (isActivated)
        {
            if (!worldIn.isRemote) teleportPlayer(entityIn, worldIn, pos);
        }
        else if (!worldIn.isRemote) entityIn.timeUntilPortal = 300;

        entityIn.motionY = 0.0D;
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return getDefaultState().withProperty(ACTIVATED, true);
    }

    @Override
    protected PortalStatus getPortalStatus(BlockPos pos, World worldIn)
    {
        return PortalStatus.ACTIVE_COMPLETE_X;
    }
}
