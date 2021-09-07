package com.boringbread.muckraft.world.gen.feature;

import com.dhanantry.scapeandrunparasites.block.BlockParasiteStain;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.List;
import java.util.Random;

public class WorldGenInfestedTrees extends WorldGenAbstractTree implements IWorldGenMuck
{
    private static final IBlockState TRUNK = SRPBlocks.InfestedTrunk.getDefaultState();
    private static final IBlockState VEINS = SRPBlocks.ParasiteStain.getDefaultState().withProperty(BlockParasiteStain.VARIANT, BlockParasiteStain.EnumType.FEELER);

    public WorldGenInfestedTrees()
    {
        super(false);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        List<BlockPos> validSpots = getValidSurfaces(worldIn, position, 32, EnumFacing.DOWN, SRPBlocks.ParasiteStain.getDefaultState());

        if (!validSpots.isEmpty())
        {
            position = validSpots.get(rand.nextInt(validSpots.size()));

            for (EnumFacing direction: EnumFacing.HORIZONTALS)
            {
                worldIn.setBlockState(position.offset(direction), VEINS);
                worldIn.setBlockState(position.offset(direction, 2).down(), VEINS);
                worldIn.setBlockState(position.offset(direction, 3).down(), VEINS);
            }

            for (int i = 0; i < rand.nextGaussian() + 4; i++)
            {
                worldIn.setBlockState(position, TRUNK);
                position = position.up();
            }
            generateBranches(worldIn, rand, position, rand.nextDouble() * 2);

            int x = (int) MathHelper.clamp(rand.nextGaussian(), -1, 1);
            int z = (int) MathHelper.clamp(rand.nextGaussian(), -1, 1);
            position = position.add(x, 0, z);

            for (int i = 0; i < rand.nextGaussian() + 3; i++)
            {
                worldIn.setBlockState(position, TRUNK);
                position = position.up();
            }
            generateBranches(worldIn, rand, position, rand.nextDouble() * 2);
        }
        return true;
    }

    private void generateBranches(World worldIn, Random rand, BlockPos position, double slope)
    {
        int x = 0;
        int z = 0;

        for (int i = 0; i < rand.nextGaussian() + 2; i++)
        {
            if(slope >= 1)
            {
                x += rand.nextDouble() / -(slope - 2) < 1 ? 1 : 0;
                z++;
            }
            else
            {
                x++;
                z += rand.nextDouble() < slope ? 1 : 0;
            }
            int y = i == 2 ? -1 : 0;

            worldIn.setBlockState(position.add(x, y, z), TRUNK);
            worldIn.setBlockState(position.add(z, y, -x), TRUNK);
            worldIn.setBlockState(position.add(-x, y, -z), TRUNK);
            worldIn.setBlockState(position.add(-z, y, x), TRUNK);
        }
    }

    @Override
    protected void setDirtAt(World worldIn, BlockPos pos)
    {
        if (worldIn.getBlockState(pos).getBlock() != SRPBlocks.ParasiteStain)
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, SRPBlocks.ParasiteStain.getDefaultState());
        }
    }
}
