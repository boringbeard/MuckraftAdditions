package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteCanister;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class WorldGenParasiteSacs extends WorldGenerator implements IWorldGenMuck
{
    //TO DO: get rid of the old stuff from stacked biomes
    private static final IBlockState SAC = SRPBlocks.ParasiteCanister.getDefaultState().withProperty(BlockParasiteCanister.VARIANT, BlockParasiteCanister.EnumType.SAC);

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        //get vertical surfaces (ceilings)
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.UP, SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FLESH));

        if (!validSpots.isEmpty())
        {
            //set down parasite sac at one of the random surfaces
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));
            worldIn.setBlockState(placementPos, SAC);
        }
        return true;
    }
}