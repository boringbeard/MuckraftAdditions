package com.boringbread.muckraft.block;

import com.boringbread.muckraft.Muckraft;
import com.boringbread.muckraft.client.gui.GuiHandler;
import com.boringbread.muckraft.creativetab.MuckraftCreativeTab;
import com.boringbread.muckraft.tileentity.TileEntityPortalS2;
import com.boringbread.muckraft.util.DimBlockPos;
import com.boringbread.muckraft.world.MuckTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class BlockPortalS2 extends BlockMuckPortal implements ITileEntityProvider
{
    //portal to transport between S2 and S3
    //TO DO: implement energy storage + varying speed based on energy input + make multiblock
    //Idea: no more GUI, everything is done in the actual minecraft world; pedestal-like sacrifice receivers, some sort
    //of energy index reader, and visual indicators for everything
    public static final String NAME = "portal_stage_two";
    public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final int TELEPORT_COST_FE = 50_000;

    public BlockPortalS2()
    {
        super(Material.ROCK, 1, 100);
        setCreativeTab(MuckraftCreativeTab.muckraftCreativeTab);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 2);
        setRegistryName(NAME);
        setResistance(9);
        setUnlocalizedName(Muckraft.MOD_ID + "_" + NAME);
        setDefaultState(this.blockState.getBaseState().withProperty(ACTIVATED, false).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        //opens GUI on right click
        if (worldIn.isRemote) return true;

        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityPortalS2)
        {
            playerIn.openGui(Muckraft.instance, GuiHandler.getID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void neighborChanged(@NotNull IBlockState state, World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos)
    {
        if (!worldIn.isRemote) worldIn.scheduleUpdate(pos, this, 0);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @org.jetbrains.annotations.Nullable EnumFacing side)
    {
        //only connects to redstone on the redstone side
        return side.getOpposite() == state.getValue(FACING);
    }

    @Override
    public void updateTick(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand)
    {
        //activates if sacrifice is accepted and is redstone powered
        EnumFacing facing = (EnumFacing) state.getValue(FACING);
        if (worldIn.getTileEntity(pos) instanceof TileEntityPortalS2)
        {
            TileEntityPortalS2 tileentity = (TileEntityPortalS2) worldIn.getTileEntity(pos);

            if (!worldIn.isRemote && worldIn.isBlockPowered(pos) && tileentity.readyForActivation())
            {
                tileentity.getEnergyStorage().extractEnergy(tileentity.getEnergyStorage().getMaxEnergyStored(), false);
                worldIn.setBlockState(pos, state.withProperty(ACTIVATED, true));
                MuckTeleporter.DESTINATION_CACHE.add(new DimBlockPos(pos, worldIn.provider.getDimension())); //adds portal to a cache once it gets activated
            }
        }
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPortalS2();
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ACTIVATED, FACING);
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta)
    {
        //TO DO: fix these ones too
        //converts int into booleans in the worst way possible
        IBlockState blockState = this.getDefaultState();

        boolean activated = (meta + 1) > 4;
        int facingInt;
        EnumFacing facing;

        if (activated) facingInt = (meta + 1) - 4;
        else facingInt = meta + 1;

        switch (facingInt)
        {
            case 2:
                facing = EnumFacing.EAST;
                break;
            case 3:
                facing = EnumFacing.SOUTH;
                break;
            case 4:
                facing = EnumFacing.WEST;
                break;
            default:
                facing = EnumFacing.NORTH;
        }

        return getDefaultState().withProperty(ACTIVATED, activated).withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        //same thing as in S1 and above
        int activated = state.getValue(ACTIVATED) ? 2 : 1;
        int direction;
        switch (state.getValue(FACING))
        {
            case EAST:
                direction = 2;
                break;
            case SOUTH:
                direction = 3;
                break;
            case WEST:
                direction = 4;
                break;
            default:
                direction = 1;
        }
        return activated * direction - 1;
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        if (!worldIn.isRemote)
        {
            double x = entityIn.posX;
            double y = entityIn.posY;
            double z = entityIn.posZ;
            BlockPos pos = new BlockPos(x, y - 1, z);
            boolean isActivated = worldIn.getBlockState(pos).getValue(ACTIVATED);
            TileEntityPortalS2 tileEntity = (TileEntityPortalS2) worldIn.getTileEntity(pos);

            //checks tile entity to see if the stored UUID is null. If so set it to the entities UUID to make this entity the one to teleport
            //look in TileEntityPortalS2 for more info
            if (tileEntity.getToTeleport() == null) tileEntity.setToTeleport(entityIn.getUniqueID());

            //start timer if entity to teleport matches this entity, the portal is activated, and there's enough energy
            if (isActivated && tileEntity.getToTeleport().equals(entityIn.getUniqueID()) && tileEntity.getEnergyStorage().getEnergyStored() >= TELEPORT_COST_FE)
            {
                if (tileEntity.getTimer() > 0) //decrements timer value if timer is greater than 0
                {
                    tileEntity.setTimer(tileEntity.getTimer() - 1);
                }
                else //else reset timer and to teleport slot and teleport player to correct dimension
                {
                    tileEntity.setTimer(300);
                    tileEntity.setToTeleport(null);
                    tileEntity.getEnergyStorage().extractEnergy(TELEPORT_COST_FE, false);
                    teleportPlayer(entityIn, worldIn, pos);
                }
            }

            entityIn.motionY = 0.0D; //stop player falling once it lands on this block
        }
    }

    @Override
    protected PortalStatus getPortalStatus(BlockPos pos, World worldIn)
    {
        return PortalStatus.ACTIVE_COMPLETE_X; //stupid thing get rid of this later. Byproduct of multiblock checker for S1 and bad code organization.
    }

    @Override
    public void makePortal(BlockPos pos, World world, IBlockState portal)
    {
        world.setBlockState(pos, portal.withProperty(BlockMuckPortal.ACTIVATED, true));
    }
}
