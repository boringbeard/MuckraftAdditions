package com.boringbread.muckraft.world;

import com.boringbread.muckraft.block.BlockMuckPortal;
import com.boringbread.muckraft.block.BlockPortalS1;
import com.boringbread.muckraft.block.BlockPortalS1Slab;
import com.boringbread.muckraft.init.MuckBlocks;
import com.boringbread.muckraft.util.DimBlockPos;
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
    //figures out how to teleport entities to different dimensions
    //TO DO: fix destination cache and setting the muck portal down at the new location
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
            //tries to find acceptable location
            BlockPos pos = new BlockPos(entity);
            BlockPos newPos = findAcceptableLocation(1024, pos, world);
            IBlockState state = world.getBlockState(newPos);

            //makes portal on other side if no portal there already
            if(state != portal.withProperty(BlockMuckPortal.ACTIVATED, true))
            {
                ((BlockMuckPortal) portal.getBlock()).makePortal(newPos, world, portal);
                DESTINATION_CACHE.add(new DimBlockPos(newPos, world.provider.getDimension()));
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

        BlockPos existingPortalPos = findExistingPortal(range, pos, world); //first check if a portal exists within range

        if(existingPortalPos != null) return existingPortalPos;

        for (int r = 0; r <= range; r += 16)
        {
            boolean isAcceptableLocation = false;
            BlockPos newLocation = null;

            if (r == 0)
            {
                //only check once if range is 0 (it's at the origin point)
                int y1 = getGoodHeight(new BlockPos(x, y, z), world);
                isAcceptableLocation = y1 != -1;
                newLocation = new BlockPos(x, y1, z);
            }
            else
            {
                //checks 16 times in concentric circles around origin point for suitable locations
                //TO DO: cos and sin are kind of inefficient, can replace with square or something instead of being fancy with the circle
                int checksPerRing = 16;

                for (int i = 0; i <= checksPerRing; i++)
                {
                    //math. basically finds the location on a circle of 16 equally spaced out locations
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
        //checks cache for portals within range
        //does not actually work well
        //Probably move this into separate class for a cache
        DimBlockPos[] destinationCache = DESTINATION_CACHE.toArray(new DimBlockPos[0]); //there's no way this is the best way to do it but at least for now it solves the co-modification problem

        //iterates through each portal location in the cache. Theres probably a way more efficient algorithm for this - TO DO: replace with more efficient algorithm.
        for(DimBlockPos portalLocation : destinationCache)
        {
            IBlockState state = world.getBlockState(portalLocation.getPos());

            if(pos.distanceSq(portalLocation.getPos()) < range * range && world.provider.getDimension() == portalLocation.getDimID())
            {
                if (state.getBlock().equals(portal.getBlock()) && state == state.withProperty(BlockPortalS1.ACTIVATED, true))
                {
                    return portalLocation.getPos();
                }

                DESTINATION_CACHE.remove(portalLocation);
            }
        }

        return null;
    }

    private int getGoodHeight(BlockPos pos, World world)
    {
        //checks for a 3x3 flat area with sun at every height, returns the height where its found
        //TO DO: make it be able to check for other conditions based on parameters
        if (!world.isRemote)
        {
            int x = pos.getX();
            int z = pos.getZ();
            int y1 = -1;
            //checks from y 63 to y 129
            for (int y = 63; y < 130; y++)
            {
                //counts each block that satisfies a. has sunlight b. is 1 block above ground
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
                    y1 = y; // if all 9 squares in the 3x3 area has sunlight and are 1 block above ground set return value to y
                    break;
                }
            }
            return y1; // return y1, the return value. Will be -1 if it found no suitable height
        }

        return -1; //only happens if world is clientside
    }
}
