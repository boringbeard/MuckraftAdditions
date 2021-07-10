package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortalStageTwo extends BlockMuckPortal {
    public static final String NAME = "portal_stage_two";

    public BlockPortalStageTwo() {
        super(Material.ROCK, 1, 0);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 1);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MODID + "_" + NAME);
        setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false));
    }

    @Override
    protected PortalStatus getPortalStatus(BlockPos pos, World worldIn) {
        return PortalStatus.ACTIVE_COMPLETE_X;
    }
}
