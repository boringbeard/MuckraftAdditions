package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class WorldGenThickVines extends WorldGenerator implements IWorldGenMuck
{
    //TO DO: get rid of the old stuff from stacked biomes
    public static final IBlockState HANGING_VINE = SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FEELER);

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        //search for ceilings that the vines can grow on
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.UP, SRPBlocks.ParasiteStain.getDefaultState());

        if (!validSpots.isEmpty())
        {
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size())); //choose random surface from list earlier
            int length = (int) MathHelper.clamp(rand.nextGaussian() * 4 + 12, 1, 30); //determine random length (gaussian distribution)

            //start from surface point we chose and go down length times or until it hits another block, each time randomly shifting a bit to the left or right
            for (int i = 0; i < length; i++)
            {
                if (worldIn.isAirBlock(placementPos)) worldIn.setBlockState(placementPos, HANGING_VINE);
                else break;
                int x = (int) MathHelper.clamp(rand.nextGaussian(), -1, 1);
                int z = (int) MathHelper.clamp(rand.nextGaussian(), -1, 1);
                placementPos = placementPos.add(x, -1, z);
            }
        }
        return true;
    }
}
