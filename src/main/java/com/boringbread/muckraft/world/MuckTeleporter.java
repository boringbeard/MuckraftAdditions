package com.boringbread.muckraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
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

        BlockPos pos = new BlockPos(x, y, z);
        BlockPos newPos = findAcceptableLocation(300, pos, world);

        entity.setLocationAndAngles(newPos.getX(), newPos.getY(), newPos.getZ(), yaw, 0.0F);
        entity.motionX = 0;
        entity.motionY = 0;
        entity.motionZ = 0;
    }

    @Nullable
    private BlockPos findAcceptableLocation(int range, BlockPos pos, World world)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for(int r = 0; r <= range; r += range/6)
        {
            int checksPerRing = 8;

            for(int i = 0; i <= checksPerRing; i++)
            {
                double tau = 2 * Math.PI;
                int changeX = (int) Math.round(r * Math.cos(i * tau / checksPerRing));
                int changeZ = (int) Math.round(r * Math.sin(i * tau / checksPerRing));
                int x1 = x + changeX;
                int z1 = z + changeZ;
                int y1 = getGoodHeight(new BlockPos(x1, y, z1), world);

                System.out.println(changeX + ", " + changeZ);

                boolean isAcceptableLocation = y1 != -1;

                if (isAcceptableLocation) return new BlockPos(x1, y1, z1);
            }
        }
        return null;
    }

    private int getGoodHeight(BlockPos pos, World world)
    {
        int x = pos.getX();
        int z = pos.getZ();
        int y1 = -1;

        for(int y = 63; y < 130; y++)
        {
            int counter = 0;

            for(int x1 = x - 1; x1 < x + 2; x1++)
            {
                for(int z1 = z - 1; z1 < z + 2; z1++)
                {
                    BlockPos checkPos = new BlockPos(x1, y, z1);
                    IBlockState blockState = world.getBlockState(checkPos.add(0,-1, 0));

                    if (world.getLight(checkPos) == 15 && blockState.isOpaqueCube()) counter++;
                }
            }

            if(counter == 9)
            {
               y1 = y;
               break;
            }
        }

        return y1;
    }
}
