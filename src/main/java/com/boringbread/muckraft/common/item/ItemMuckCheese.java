package com.boringbread.muckraft.common.item;

import com.boringbread.muckraft.common.Muckraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import static com.boringbread.muckraft.common.MuckraftCreativeTab.muckraftCreativeTab;

public class ItemMuckCheese extends ItemFood
{
    public static final ItemMuckCheese INSTANCE = new ItemMuckCheese();

    public static final String NAME = "muck_cheese";

    public ItemMuckCheese()
    {
        super(20, 20, false);
        setCreativeTab(muckraftCreativeTab);
        setRegistryName(NAME);
        setUnlocalizedName(Muckraft.MODID + "_" + NAME);
        setHasSubtypes(false);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) { return EnumRarity.EPIC; }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
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

    public static void preInitCommon()
    {

    }

    public static void preInitClient()
    {
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("muckraft:muck_cheese", "inventory");
        ModelLoader.setCustomModelResourceLocation(INSTANCE, 0, itemModelResourceLocation);
    }
}
