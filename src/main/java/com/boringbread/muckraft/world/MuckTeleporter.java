package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
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

        BlockPos pos = new BlockPos(x, y, z);
        BlockPos newPos = findAcceptableLocation(200, pos, world);
        world.setBlockState(newPos, ModBlocks.PORTAL_STAGE_ONE.getDefaultState());

        entity.setLocationAndAngles(newPos.getX()+ 0.5, newPos.getY() + 1, newPos.getZ() + 0.5, yaw, 0.0F);
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

        for(int r = 0; r <= range; r += range/10)
        {
            if(r == 0)
            {
                int y1 = getGoodHeight(new BlockPos(x, y, z), world);
                boolean isAcceptableLocation = y1 != -1;

                if (isAcceptableLocation) return new BlockPos(x, y1, z);
            }

            int checksPerRing = 16;

            for(int i = 0; i <= checksPerRing; i++)
            {
                double tau = 2 * Math.PI;
                int changeX = (int) Math.round(r * Math.cos(i * tau / checksPerRing));
                int changeZ = (int) Math.round(r * Math.sin(i * tau / checksPerRing));
                int x1 = x + changeX;
                int z1 = z + changeZ;
                int y1 = getGoodHeight(new BlockPos(x1, y, z1), world);
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
                    IBlockState blockState = world.getBlockState(checkPos.down());

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
