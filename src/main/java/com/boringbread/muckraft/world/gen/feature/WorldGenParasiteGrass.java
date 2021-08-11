package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteBush;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenParasiteGrass extends WorldGenerator
{
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 8; i++)
        {
            BlockPos pos = position.add(rand.nextInt(8) - rand.nextInt(8), 32, rand.nextInt(8) - rand.nextInt(8));
            IBlockState blockState = SRPBlocks.ParasiteBush.getDefaultState().withProperty(BlockParasiteBush.VARIANT, BlockParasiteBush.EnumType.ARC);

            while (!(worldIn.isAirBlock(pos) && worldIn.getBlockState(pos.down()) == SRPBlocks.ParasiteStain.getDefaultState()) && pos.getY() > position.getY())
            {
                pos = pos.down();
            }

            if (pos.getY() > position.getY()) worldIn.setBlockState(pos, blockState);
        }
        return true;
    }
}
