package com.boringbread.muckraft;

import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

public class MuckReflections
{
    public static final Method CREATE_SPAWN_POSITION = ObfuscationReflectionHelper.findMethod(WorldServer.class, "func_73052_b", void.class, WorldSettings.class);
}
