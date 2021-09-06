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
    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth, EnumFacing direction, boolean isBlacklist, List<IBlockState> states, boolean acceptAny)
    {
        List<BlockPos> positions = new LinkedList<>();

        for (int i = 0; i < layerDepth; i++)
        {
            if ((acceptAny || isValidSurface(worldIn, pos.offset(direction), isBlacklist, states)) && worldIn.isAirBlock(pos)) positions.add(pos);
            pos = pos.offset(direction);
        }

        return positions;
    }

    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth, EnumFacing direction, boolean acceptAny)
    {
        return getValidSurfaces(worldIn, pos, layerDepth, direction, false, new ArrayList<>(), acceptAny);
    }

    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth, EnumFacing direction, boolean isBlacklist, List<IBlockState> states)
    {
        return getValidSurfaces(worldIn, pos, layerDepth, direction, isBlacklist, states, false);
    }

    default List<BlockPos> getValidSurfaces(World worldIn, BlockPos pos, int layerDepth, EnumFacing direction, IBlockState state)
    {
        List <IBlockState> states = new LinkedList<>();
        states.add(state);
        return getValidSurfaces(worldIn, pos, layerDepth, direction, false, states);
    }


    default boolean isValidSurface(World worldIn, BlockPos pos, boolean isBlacklist, List<IBlockState> states)
    {
        boolean containsState = states.contains(worldIn.getBlockState(pos));
        return isBlacklist ? !containsState : containsState;
    }
}
