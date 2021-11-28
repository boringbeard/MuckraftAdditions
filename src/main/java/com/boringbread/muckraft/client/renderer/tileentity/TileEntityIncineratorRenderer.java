package com.boringbread.muckraft.client.renderer.tileentity;

import com.boringbread.muckraft.tileentity.TileEntityIncinerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.client.model.pipeline.LightUtil;
import scala.collection.parallel.ParIterableLike;

import javax.vecmath.Matrix4f;

public class TileEntityIncineratorRenderer extends FastTESR<TileEntityIncinerator>
{
    @Override
    public void renderTileEntityFast(TileEntityIncinerator te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer)
    {
        ItemStack itemstack = te.getItemHandler().getStackInSlot(0);
        BlockPos pos = te.getPos();

        if (!itemstack.isEmpty())
        {
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(itemstack, te.getWorld(), (EntityLivingBase) null);
            Matrix4f matrix4f = model.handlePerspective(ItemCameraTransforms.TransformType.GROUND).getRight();
            System.out.println(matrix4f);
            ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

            buffer.setTranslation(x - pos.getX(), y - pos.getY(), z - pos.getZ());

            for (EnumFacing facing :
                    EnumFacing.values())
            {
                for (BakedQuad quad :
                        model.getQuads(null, facing, 0))
                {
                    int k = itemColors.colorMultiplier(itemstack, quad.getTintIndex());
                    if (EntityRenderer.anaglyphEnable)
                    {
                        k = TextureUtil.anaglyphColor(k);
                    }
                    k = k | -16777216;

                    buffer.addVertexData(quad.getVertexData());
                    buffer.lightmap(0, 240);
                    buffer.putPosition(pos.getX(), pos.getY() + 1.5D, pos.getZ());
                    ForgeHooksClient.putQuadColor(buffer, quad, k);
                }
            }

            for (BakedQuad quad : model.getQuads(null, null, 0))
            {
                int k = itemColors.colorMultiplier(itemstack, quad.getTintIndex());
                if (EntityRenderer.anaglyphEnable)
                {
                    k = TextureUtil.anaglyphColor(k);
                }
                k = k | -16777216;
                buffer.addVertexData(quad.getVertexData());
                buffer.lightmap(0, 240);
                buffer.putPosition(pos.getX(), pos.getY() + 1.5D, pos.getZ());
                ForgeHooksClient.putQuadColor(buffer, quad, k);
            }
        }
    }

    private BakedQuad reformatItemToBlockQuad(BakedQuad quad)
    {
        int[] vertexData = quad.getVertexData();
        int[] newVertexData = new int[28];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                newVertexData[i * 7 + j] = vertexData[i * 7 + j];
            }

            newVertexData[i * 7 + 6] = 0;
        }

        return new BakedQuad(newVertexData, quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), DefaultVertexFormats.BLOCK);
    }
}
