package com.boringbread.muckraft.block;

import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.world.MuckTeleporter;
import com.boringbread.muckraft.Muckraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortalStageOne extends Block {
    public static final String NAME = "portal_stage_one";
    public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

    public BlockPortalStageOne() {
        super(Material.ROCK);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 1);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MODID + "_" + NAME);
        setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn) {
        double x = entityIn.posX;
        double y = entityIn.posY;
        double z = entityIn.posZ;
        boolean isActivated = worldIn.getBlockState(new BlockPos(x, y - 1, z)).getValue(ACTIVATED);
        System.out.println(isActivated);

        if (!worldIn.isRemote && isActivated)
        {
            entityIn.motionY = 0.0D;

            if (entityIn.timeUntilPortal > 100) entityIn.timeUntilPortal = 100;

            if (entityIn.timeUntilPortal == 0) {
                if (worldIn.provider.getDimension() != 69) {
                    entityIn.changeDimension(69, new MuckTeleporter());
                }
                else
                {
                    entityIn.changeDimension(0, new MuckTeleporter());
                }

                entityIn.timeUntilPortal = 100;
            } else {
                entityIn.timeUntilPortal -= 1;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(playerIn.getHeldItem(hand).getItem() instanceof ItemBook && !worldIn.isRemote && !state.getValue(ACTIVATED))
        {
            worldIn.setBlockState(pos, state.withProperty(ACTIVATED, true));
            playerIn.getHeldItem(hand).shrink(1);
        }

        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (state.getValue(ACTIVATED))
        {
            setLightLevel(14);
            return 14;
        }
        setLightLevel(0);
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(ACTIVATED, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ACTIVATED) ? 1 : 0;
    }
}
