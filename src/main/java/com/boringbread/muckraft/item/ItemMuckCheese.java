package com.boringbread.muckraft.item;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemMuckCheese extends ItemFood
{
    public static final String NAME = "muck_cheese";

    public ItemMuckCheese()
    {
        super(20, 20, false);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setRegistryName(NAME);
        setUnlocalizedName(Muckraft.MODID + "_" + NAME);
        setHasSubtypes(false);
    }

    @Override
    public @NotNull EnumRarity getRarity(@NotNull ItemStack stack) { return EnumRarity.EPIC; }

    @Override
    protected void onFoodEaten(@NotNull ItemStack stack, World worldIn, @NotNull EntityPlayer player)
    {
        if(!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1200, 2));
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 12000, 3));
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 12000, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 4800, 4));
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2400, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 2400, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2400, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2400, 0));
        }
    }
}
