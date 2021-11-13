package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IWorldGenMuck
{
    //some common methods for muckraft worldgen stuff
    //TO DO get rid of acceptAny (doesnt do anything)
    //direction is the direction it searches in
    //search depth is how far the thing searches for a surface
    //isBlacklist is just whether or not the inputted list is a blacklist
    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int searchDepth, EnumFacing direction, boolean isBlacklist, List<IBlockState> states, boolean acceptAny)
    {
        //finds surfaces given a direction and a block/blocks to look for on the surface
        List<BlockPos> positions = new LinkedList<>();

        for (int i = 0; i < searchDepth; i++)
        {
            //checks if theres a surface, if so add it to the list.
            if ((acceptAny || isValidSurface(worldIn, pos.offset(direction), isBlacklist, states)) && worldIn.isAirBlock(pos)) positions.add(pos);
            //continue moving in the direction
            pos = pos.offset(direction);
        }

        return positions; //return list of positions it found
    }

    //Next few are just for default values and stuff
    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int searchDepth, EnumFacing direction, boolean acceptAny)
    {
        return getValidSurfaces(worldIn, pos, searchDepth, direction, false, new ArrayList<>(), acceptAny);
    }

    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int searchDepth, EnumFacing direction, boolean isBlacklist, List<IBlockState> states)
    {
        return getValidSurfaces(worldIn, pos, searchDepth, direction, isBlacklist, states, false);
    }

    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int searchDepth, EnumFacing direction, IBlockState state)
    {
        List <IBlockState> states = new LinkedList<>();
        states.add(state);
        return getValidSurfaces(worldIn, pos, searchDepth, direction, false, states);
    }

    default boolean isValidSurface(World worldIn, BlockPos pos, boolean isBlacklist, List<IBlockState> states)
    {
        //surface in this case just means that its an air block and the next block is the type of block that makes up the surface you're trying to find
        //returns true if the block at pos is a surface given the above definition
        boolean containsState = states.contains(worldIn.getBlockState(pos));
        return isBlacklist ? !containsState : containsState;
    }
}
