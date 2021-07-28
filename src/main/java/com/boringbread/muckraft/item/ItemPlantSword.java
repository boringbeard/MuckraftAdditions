package com.boringbread.muckraft.item;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemPlantSword extends ItemSword
{
    public static final int CHARGE_UP_TICKS = 15;
    public static final String NAME = "plant_sword";

    public ItemPlantSword() {
        super(ToolMaterial.DIAMOND);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setRegistryName(NAME);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + NAME);
        this.addPropertyOverride(new ResourceLocation("muckraft:charge"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack itemStack, @org.jetbrains.annotations.Nullable World world, @org.jetbrains.annotations.Nullable EntityLivingBase entityLivingBase) {
                return 0;
            }
        });

    }
    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 100; }

    public static int getChargeUpTicks()
    {
        return CHARGE_UP_TICKS;
    }
}
