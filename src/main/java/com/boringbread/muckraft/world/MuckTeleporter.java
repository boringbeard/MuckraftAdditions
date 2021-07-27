package com.boringbread.muckraft.world;

import com.boringbread.muckraft.block.BlockMuckPortal;
import com.boringbread.muckraft.block.BlockPortalStageOne;
import com.boringbread.muckraft.block.BlockPortalStageOneSlab;
import com.boringbread.muckraft.init.MuckBlocks;
import com.boringbread.muckraft.util.DimBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MuckTeleporter implements ITeleporter
{
    public static final List<DimBlockPos> DESTINATION_CACHE = new ArrayList<>();
    private final IBlockState portal;

    public MuckTeleporter(IBlockState portal)
    {
        this.portal = portal;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
        if(!world.isRemote)
        {
            BlockPos pos = new BlockPos(entity);
            BlockPos newPos = findAcceptableLocation(1024, pos, world);
            IBlockState state = world.getBlockState(newPos);

            if(state != portal.withProperty(BlockMuckPortal.ACTIVATED, true))
            {
                makePortal(newPos, world, this.portal);
            }

            entity.setLocationAndAngles(newPos.getX() + 0.5, newPos.getY() + 1, newPos.getZ() + 0.5, yaw, 0.0F);
            entity.motionX = 0;
            entity.motionY = 0;
            entity.motionZ = 0;
        }
    }

    private BlockPos findAcceptableLocation(int range, BlockPos pos, World world)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        BlockPos existingPortalPos = findExistingPortal(range, pos, world);

        if(existingPortalPos != null) return existingPortalPos;

        for (int r = 0; r <= range; r += 16)
        {
            boolean isAcceptableLocation = false;
            BlockPos newLocation = null;

            if (r == 0)
            {
                int y1 = getGoodHeight(new BlockPos(x, y, z), world);
                isAcceptableLocation = y1 != -1;
                newLocation = new BlockPos(x, y1, z);
            }
            else
            {
                int checksPerRing = 16;

                for (int i = 0; i <= checksPerRing; i++)
                {
                    double tau = 2 * Math.PI;
                    int changeX = (int) Math.round(r * Math.cos(i * tau / checksPerRing));
                    int changeZ = (int) Math.round(r * Math.sin(i * tau / checksPerRing));
                    int x1 = x + changeX;
                    int z1 = z + changeZ;
                    int y1 = getGoodHeight(new BlockPos(x1, y, z1), world);
                    isAcceptableLocation = y1 != -1;
                    newLocation = new BlockPos(x1, y1, z1);
                }
            }

            if (isAcceptableLocation)
            {
                return newLocation;
            }
        }
        return pos;
    }

    @Nullable
    private BlockPos findExistingPortal(int range, BlockPos pos, World world)
    {
        DimBlockPos[] destinationCache = DESTINATION_CACHE.toArray(new DimBlockPos[0]); //there's no way this is the best way to do it but at least for now it solves the co-modification problem

        for(DimBlockPos portalLocation : destinationCache)
        {
            IBlockState state = world.getBlockState(portalLocation.getPos());

            if(pos.distanceSq(portalLocation.getPos()) < range * range && world.provider.getDimension() == portalLocation.getDimID())
            {
                if (state.getBlock().equals(portal.getBlock()) && state == state.withProperty(BlockPortalStageOne.ACTIVATED, true))
                {
                    return portalLocation.getPos();
                }

                DESTINATION_CACHE.remove(portalLocation);
            }
        }

        return null;
    }

    private void makePortal(BlockPos pos, World world, IBlockState portal)
    {
        switch (((BlockMuckPortal) portal.getBlock()).getStage())
        {
            case 0:
                IBlockState activePortalSlab = MuckBlocks.PORTAL_STAGE_ONE_SLAB.getDefaultState().withProperty(BlockPortalStageOneSlab.ACTIVATED, true);
                world.setBlockState(pos.north(), activePortalSlab.withProperty(BlockPortalStageOneSlab.FACING, EnumFacing.NORTH));
                world.setBlockState(pos.south(), activePortalSlab.withProperty(BlockPortalStageOneSlab.FACING, EnumFacing.SOUTH));
                world.setBlockState(pos, portal.withProperty(BlockMuckPortal.ACTIVATED, true));
                break;
            case 1:
                world.setBlockState(pos, portal.withProperty(BlockMuckPortal.ACTIVATED, true));
        }

        DESTINATION_CACHE.add(new DimBlockPos(pos, world.provider.getDimension()));
    }

    private int getGoodHeight(BlockPos pos, World world)
    {
        if (!world.isRemote)
        {
            int x = pos.getX();
            int z = pos.getZ();
            int y1 = -1;

            for (int y = 63; y < 130; y++)
            {
                int counter = 0;

                for (int x1 = x - 1; x1 < x + 2; x1++)
                {
                    for (int z1 = z - 1; z1 < z + 2; z1++)
                    {
                        BlockPos checkPos = new BlockPos(x1, y, z1);
                        IBlockState blockState = world.getBlockState(checkPos.down());

                        if (world.getLight(checkPos) == 15 && blockState.isOpaqueCube()) counter++;
                    }
                }

                if (counter == 9)
                {
                    y1 = y;
                    break;
                }
            }
            return y1;
        }

        return -1;
    }
}
