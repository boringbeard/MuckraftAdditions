package com.boringbread.muckraft.block;

import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.init.MuckBlocks;
import com.boringbread.muckraft.util.DimBlockPos;
import com.boringbread.muckraft.world.MuckTeleporter;
import com.boringbread.muckraft.Muckraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BlockPortalStageOne extends BlockMuckPortal
{
    public static final String NAME = "portal_stage_one";

    public BlockPortalStageOne()
    {
        super(Material.ROCK, 0, 100);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 1);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MODID + "_" + NAME);
        setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
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
            for (int i = 0; i < 3; i++)
            {
                worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.25 + Math.random()/2, pos.getY() + 1.0F, pos.getZ() + 0.25 + Math.random()/2, 0, 1, 0);
            }

            if(!worldIn.isRemote) teleportPlayer(entityIn, worldIn);
        }
        else if(!worldIn.isRemote) entityIn.timeUntilPortal = 300;

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
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        PropertyBool activated = BlockPortalStageOneSlab.ACTIVATED;
        PortalStatus status = getPortalStatus(pos, worldIn);
        boolean complete = status != PortalStatus.INCOMPLETE;

        if(playerIn.getHeldItem(hand).getItem() instanceof ItemBook && !state.getValue(ACTIVATED) && complete)
        {
            BlockPos pos1 = status == PortalStatus.COMPLETE_X ? pos.east() : pos.north();
            BlockPos pos2 = status == PortalStatus.COMPLETE_X ? pos.west() : pos.south();
            worldIn.setBlockState(pos, state.withProperty(ACTIVATED, true));
            worldIn.setBlockState(pos1, worldIn.getBlockState(pos1).withProperty(activated, true));
            worldIn.setBlockState(pos2, worldIn.getBlockState(pos2).withProperty(activated, true));
            MuckTeleporter.DESTINATION_CACHE.add(new DimBlockPos(pos, worldIn.provider.getDimension()));
            playerIn.getHeldItem(hand).shrink(1);
        }

        return true;
    }

    @Override
    public void neighborChanged(@NotNull IBlockState state, World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos)
    {
        if(!worldIn.isRemote) worldIn.scheduleUpdate(pos, this, 0);
    }

    @Override
    public int getLightValue(IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos)
    {
        if(state.getValue(ACTIVATED))
        {
            setLightLevel(14);
            return 14;
        }
        setLightLevel(0);
        return 0;
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED);
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
    protected PortalStatus getPortalStatus(BlockPos pos, World worldIn)
    {
        IBlockState portalSlab = MuckBlocks.PORTAL_STAGE_ONE_SLAB.getDefaultState();
        IBlockState portalSlabActive = portalSlab.withProperty(BlockPortalStageOneSlab.ACTIVATED, true);
        IBlockState blockEast = worldIn.getBlockState(pos.east());
        IBlockState blockWest = worldIn.getBlockState(pos.west());
        IBlockState blockNorth = worldIn.getBlockState(pos.north());
        IBlockState blockSouth = worldIn.getBlockState(pos.south());
        PropertyDirection direction = BlockPortalStageOneSlab.FACING;

        boolean eastSlab = blockEast == portalSlab.withProperty(direction, EnumFacing.EAST);
        boolean westSlab = blockWest == portalSlab.withProperty(direction, EnumFacing.WEST);
        boolean northSlab = blockNorth == portalSlab.withProperty(direction, EnumFacing.NORTH);
        boolean southSlab = blockSouth == portalSlab.withProperty(direction, EnumFacing.SOUTH);
        boolean eastSlabActive = blockEast == portalSlabActive.withProperty(direction, EnumFacing.EAST);
        boolean westSlabActive = blockWest == portalSlabActive.withProperty(direction, EnumFacing.WEST);
        boolean southSlabActive = blockNorth == portalSlabActive.withProperty(direction, EnumFacing.NORTH);
        boolean northSlabActive = blockSouth == portalSlabActive.withProperty(direction, EnumFacing.SOUTH);

        if(eastSlabActive && westSlabActive) return PortalStatus.ACTIVE_COMPLETE_X;
        if(northSlabActive && southSlabActive) return PortalStatus.ACTIVE_COMPLETE_Z;
        if(eastSlab && westSlab) return PortalStatus.COMPLETE_X;
        if(northSlab && southSlab) return PortalStatus.COMPLETE_Z;
        return PortalStatus.INCOMPLETE;
    }
}
