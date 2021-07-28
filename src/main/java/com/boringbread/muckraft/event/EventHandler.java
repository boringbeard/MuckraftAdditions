package com.boringbread.muckraft.event;

import com.boringbread.muckraft.config.Config;
import com.boringbread.muckraft.item.ItemPlantSword;
import com.boringbread.muckraft.util.NBTHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class EventHandler
{
    private static float newFOV;

    @SubscribeEvent
    public static void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event)
    {
        event.getWorld().provider.setDimension(Config.stageOneID);
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onUpdateFov(FOVUpdateEvent event)
    {
        EntityPlayer player = event.getEntity();
        Item activeItem = player.getActiveItemStack().getItem();

        if(!(activeItem instanceof ItemPlantSword))
        {
            newFOV = 0.0F;
            return;
        }

        final float totalChargeUpZoom = 0.15F;
        final float zoomIncrement = totalChargeUpZoom / ItemPlantSword.CHARGE_UP_TICKS;
        int ticksInUse = activeItem.getMaxItemUseDuration(player.getActiveItemStack()) - player.getItemInUseCount();

        if(ticksInUse < ItemPlantSword.CHARGE_UP_TICKS) newFOV -= zoomIncrement;

        event.setNewfov(event.getFov() + newFOV);
    }
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack =  entity.getHeldItemMainhand();
        Item heldItem = stack.getItem();

        if(!(heldItem instanceof ItemPlantSword) || entity.world.isRemote) return;

        NBTHelper.getTagCompoundSafe(stack).setInteger("timer", NBTHelper.getTagCompoundSafe(stack).getInteger("timer") + 1);

        if(NBTHelper.getTagCompoundSafe(stack).getInteger("timer") >= ItemPlantSword.CHARGE_UP_TICKS)
        {
            NBTHelper.getTagCompoundSafe(stack).removeTag("timer");
        }
    }
}
