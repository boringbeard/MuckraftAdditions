package com.boringbread.muckraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class MuckTeleporter implements ITeleporter {
    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        entity.setLocationAndAngles(0, 100, 0, yaw, 0.0F);
        entity.motionX = 0;
        entity.motionY = 0;
        entity.motionZ = 0;
    }
}
