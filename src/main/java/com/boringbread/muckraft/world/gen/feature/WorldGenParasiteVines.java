package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockInfestedBush;
import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import scala.actors.threadpool.Arrays;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenParasiteVines extends WorldGenerator implements IWorldGenMuck
{
    //TO DO: get rid of the old stuff from stacked biomes
    private static final IBlockState HANGING_VINE = SRPBlocks.InfestedBush.getDefaultState().withProperty(BlockInfestedBush.VARIANT, BlockInfestedBush.EnumType.VINE);

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        //TO DO: get rid of the position randomization code inside the worldgen classes and just keep it in here
        //randomize block position within chunk
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        //find vertical surfaces given x and y coordinates
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.UP, SRPBlocks.ParasiteStain.getDefaultState());

        if (!validSpots.isEmpty())
        {
            //choose random one of the surfaces we found earlier
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));

            //repeat setting vine block down for random length between 1 and 8
            for (int i = 0; i < (int) MathHelper.clamp(rand.nextGaussian() + 3, 1, 8); i++)
            {
                if (worldIn.isAirBlock(placementPos)) worldIn.setBlockState(placementPos, HANGING_VINE); //only set if air block
                else break; //stop if it hits the ground or another block
                placementPos = placementPos.down(); //next position to place block will be one down
            }
        }
        return true;
    }
}
