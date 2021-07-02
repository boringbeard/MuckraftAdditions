package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BlockPortalStageOneSlab extends BlockSlab
{
    public static final String NAME = "portal_stage_one_slab";
    public static final String UNLOCALIZED_NAME = Muckraft.MODID + "_" + NAME;
    public static final PropertyBool ACTIVATED = PropertyBool.create("activated");
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockPortalStageOneSlab()
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
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing direction;
        if(facing != EnumFacing.UP && facing != EnumFacing.DOWN)
        {
            direction = facing;
        }
        else
        {
            if(hitX > hitZ)
            {
                direction = hitX + hitZ > 1 ? EnumFacing.WEST : EnumFacing.SOUTH;
            }
            else
            {
                direction = hitX + hitZ > 1 ? EnumFacing.NORTH : EnumFacing.EAST;
            }
        }

        IBlockState iblockstate = getDefaultState().withProperty(FACING, direction);

        if (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D))
        {
            return iblockstate;
        }

        return iblockstate.withProperty(HALF, EnumBlockHalf.TOP);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED, FACING, HALF);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int[] digitValues = {8, 4, 1};
        int totalValue = meta;
        List<Integer> digits = new LinkedList<>();

        for (int i: digitValues)
        {
            int digit = 0;

            while(totalValue > i)
            {
                totalValue -= i;
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
        boolean activated = state.getValue(ACTIVATED);
        EnumFacing direction = state.getValue(FACING);
        EnumBlockHalf half = state.getValue(HALF);

        int i = activated ? 1 : 0;
        int j = half == EnumBlockHalf.TOP ? 1 : 0;
        int k;

        switch (direction)
        {
            case EAST:
                k = 1;
                break;
            case SOUTH:
                k = 2;
                break;
            case WEST:
                k = 3;
                break;
            default: k = 0;
        }

        return i * 8 + j * 4 + k;
    }

    @Override
    public String getUnlocalizedName(int meta) {
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
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }
}
