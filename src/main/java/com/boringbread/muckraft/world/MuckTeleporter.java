package com.boringbread.muckraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;

public class MuckTeleporter implements ITeleporter {
    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        int x = (int)Math.floor(entity.posX);
        int y = (int)Math.floor(entity.posY);
        int z = (int)Math.floor(entity.posZ);

        //for (int changeY = -1; changeY < 2; changeY++)
        //{
        //    world.setBlockState(new BlockPos(x, y + changeY, z), (changeY == -1) ? ModBlocks.PORTAL_STAGE_ONE.getDefaultState() : Blocks.AIR.getDefaultState());
        //}
        System.out.println(world.isChunkGeneratedAt(x, z));
        BlockPos pos = new BlockPos(x, y, z);
        BlockPos top = world.getHeight(pos);
        entity.setLocationAndAngles(pos.getX(), top.getY(), pos.getZ(), yaw, 0.0F);

        entity.motionX = 0;
        entity.motionY = 0;
        entity.motionZ = 0;
    }

    @Nullable
    private BlockPos findAcceptableLocation(int range, BlockPos pos, Entity entity)
    {
        return null;
    }

    private boolean isAcceptableLocation(BlockPos pos, World world)
    {
        return false;
    }
}
