package com.boringbread.muckraft.world;

import com.boringbread.muckraft.init.MuckWorldGen;
import com.boringbread.muckraft.world.biome.BiomeProviderS4;
import com.boringbread.muckraft.world.gen.ChunkGeneratorS4;
import com.dhanantry.scapeandrunparasites.util.SRPConfig;
import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderS4 extends WorldProvider
{
    //world provider for parasite dimension
    @Override
    public void init()
    {
        this.biomeProvider = new BiomeProviderS4(world.getWorldInfo());
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.5F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
    {
        //dark green fog
        //TO DO: find a way to make fog closer
        return new Vec3d(0.03921568627D, 0.34117647058D, 0.16470588235D);
    }

    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorS4(this.world, this.world.getSeed());
    }

    @Override
    public DimensionType getDimensionType()
    {
        return MuckWorldGen.STAGE_FOUR;
    }
}
