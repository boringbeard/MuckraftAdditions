package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.init.MuckBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BlockPortalS1Slab extends BlockSlab
{
    //required part of portalS1 multiblock
    public static final String NAME = "portal_stage_one_slab";
    public static final String UNLOCALIZED_NAME = Muckraft.MOD_ID + "_" + NAME;
    public static final PropertyBool ACTIVATED = PropertyBool.create("activated");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockPortalS1Slab()
    {
        super(Material.ROCK);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 1);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(UNLOCALIZED_NAME);
        setDefaultState(this.blockState.getBaseState()
                .withProperty(ACTIVATED, false)
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(HALF, EnumBlockHalf.BOTTOM));
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
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer)
    {
            return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                    .withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED, FACING, HALF);
    }

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta)
    {
        //TO DO: use bitwise operators to make this shorter and more concise
        //Converts boolean values into integer in the worst way possible by manually having each binary place value
        int[] digitValues = {8, 4, 1};
        int totalValue = meta;
        List<Integer> digits = new LinkedList<>();

        for (int digitValue: digitValues)
        {
            int digit = 0;

            while(totalValue > digitValue)
            {
                totalValue -= digitValue;
                digit++;
            }

            digits.add(digit);
        }

        boolean activated = digits.get(0) == 1;
        EnumBlockHalf half = digits.get(1) == 1 ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM;
        EnumFacing facing;

        switch(digits.get(2))
        {
            case 1:
                facing = EnumFacing.EAST;
                break;
            case 2:
                facing = EnumFacing.SOUTH;
                break;
            case 3:
                facing = EnumFacing.WEST;
                break;
            default:
                facing = EnumFacing.NORTH;
        }

        return getDefaultState()
                .withProperty(ACTIVATED, activated)
                .withProperty(HALF, half)
                .withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        //converts booleans into int in the worst way possible - use bitwise operators to make better
        boolean activated = state.getValue(ACTIVATED);
        EnumFacing direction = state.getValue(FACING);
        EnumBlockHalf half = state.getValue(HALF);

        int firstDigit = activated ? 1 : 0;
        int secondDigit = half == EnumBlockHalf.TOP ? 1 : 0;
        int thirdDigit;

        switch (direction)
        {
            case EAST:
                thirdDigit = 1;
                break;
            case SOUTH:
                thirdDigit = 2;
                break;
            case WEST:
                thirdDigit = 3;
                break;
            default: thirdDigit = 0;
        }

        return firstDigit * 8 + secondDigit * 4 + thirdDigit;
    }

    @Override
    public void neighborChanged(@NotNull IBlockState state, World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos)
    {
        if(!worldIn.isRemote) worldIn.scheduleUpdate(pos, this, 0);
    }

    @Override
    public void updateTick(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand)
    {
        //turns off portal if multiblock is broken - again, transfer to separate class
        if(!connectedToActivePortal(worldIn, pos, state) && !worldIn.isRemote)
        {
            worldIn.setBlockState(pos, state.withProperty(ACTIVATED, false));
        }
    }

    @Override
    public @NotNull String getUnlocalizedName(int meta) {
        return UNLOCALIZED_NAME;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(@NotNull ItemStack stack) {
        return null;
    }

    private boolean connectedToActivePortal(World worldIn, BlockPos pos, IBlockState state)
    {
        //Multiblock checker - transfer into a more general version in a separate class or something
        BlockPos checkPos;

        switch(state.getValue(FACING))
        {
            case EAST:
                checkPos = pos.west();
                break;
            case SOUTH:
                checkPos = pos.north();
                break;
            case WEST:
                checkPos = pos.east();
                break;
            default:
                checkPos = pos.south();
        }

        return worldIn.getBlockState(checkPos) == MuckBlocks.PORTAL_STAGE_ONE.getDefaultState().withProperty(BlockPortalS1.ACTIVATED, true);
    }
}
