package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.init.MuckBlocks;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.potion.SRPEffectBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
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

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if(entityIn instanceof EntityLivingBase && !worldIn.isRemote)
        {
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 1));
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 60, 0));
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 0));
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 120, 0));
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(SRPPotions.COTH_E, 100, 1));
        }
    }
}
