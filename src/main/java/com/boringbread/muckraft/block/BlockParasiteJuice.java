package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.init.MuckBlocks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BlockParasiteJuice extends BlockFluidClassic
{
    public static final String NAME = "parasite_juice";
    public BlockParasiteJuice(Fluid parasiteJuice)
    {
        super(parasiteJuice, Material.WATER);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setRegistryName(NAME);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + NAME);
    }
}