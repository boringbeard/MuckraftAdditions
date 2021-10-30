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
    public static final IBlockState HANGING_VINE = SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FEELER);

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        position = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.UP, SRPBlocks.ParasiteStain.getDefaultState());

        if (!validSpots.isEmpty())
        {
            BlockPos placementPos = validSpots.get(rand.nextInt(validSpots.size()));
            int length = (int) MathHelper.clamp(rand.nextGaussian() * 4 + 12, 1, 30);

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