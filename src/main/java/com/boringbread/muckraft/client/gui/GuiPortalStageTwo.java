package com.boringbread.muckraft.client.gui;

import com.boringbread.muckraft.inventory.ContainerPortalS2;
import com.boringbread.muckraft.network.MessageConfirmPortal;
import com.boringbread.muckraft.network.MuckPacketHandler;
import com.boringbread.muckraft.tileentity.TileEntityPortalS2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiPortalStageTwo extends GuiContainer
{
    //TO DO: add energy indicator or get rid of this entirely and make it in-world
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("muckraft:textures/gui/container/gui_portal_stage_two.png");
    private ConfirmButton confirmButton;
    private TileEntityPortalS2 portalStageTwo;

    public GuiPortalStageTwo(InventoryPlayer playerInventory, TileEntityPortalS2 tilePortalStageTwo)
    {
        super(new ContainerPortalS2(playerInventory, tilePortalStageTwo));
        this.portalStageTwo = tilePortalStageTwo;
        this.xSize = 174;
        this.ySize = 152;
    }

    @Override
    public void initGui()
    {
        //sets up all the buttons and stuff
        super.initGui();
        this.confirmButton = new ConfirmButton(0, this.guiLeft + 144, this.guiTop + 24); //new confirm button left 144 and down 24 from top left corner (i think)
        this.buttonList.add(this.confirmButton); //add confirm button to list of buttons
        this.confirmButton.enabled = false; //means it can't be pressed until slots filled
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void updateScreen()
    {
        //checks if slots are empty - if all sacrifice slots filled unlock confirm button
        //TO DO: make this more efficient
        super.updateScreen();
        int i = 0;
        for (int j = 0; j < portalStageTwo.getItemStackHandler().getSlots(); j++)
        {
            if (portalStageTwo.getItemStackHandler().getStackInSlot(j).isEmpty())
            {
                confirmButton.enabled = false;
                i++;
            }
            if (i == 0) confirmButton.enabled = true;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        for (GuiButton guibutton : this.buttonList)
        {
            if (guibutton.isMouseOver())
            {
                //highlights button if hovered over
                guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
                break;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            //sends message to server if button clicked to tell it that the sacrifice was confirmed
            MuckPacketHandler.INSTANCE.sendToServer(new MessageConfirmPortal(portalStageTwo.getPos()));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @SideOnly(Side.CLIENT)
    static class Button extends GuiButton
    {
        //class for a button. Pretty much copied from vanilla Beacon code
        private final ResourceLocation iconTexture;
        private final int iconX;
        private final int iconY;
        private boolean selected;

        protected Button(int buttonId, int x, int y, ResourceLocation iconTextureIn, int iconXIn, int iconYIn)
        {
            super(buttonId, x, y, 22, 22, "");
            this.iconTexture = iconTextureIn;
            this.iconX = iconXIn;
            this.iconY = iconYIn;
        }

        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                mc.getTextureManager().bindTexture(GUI_TEXTURES);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int j = 0;

                if (!this.enabled)
                {
                    j += this.width * 2;
                }
                else if (this.selected)
                {
                    j += this.width * 1;
                }
                else if (this.hovered)
                {
                    j += this.width * 3;
                }

                this.drawTexturedModalRect(this.x, this.y, j, 152, this.width, this.height);

                if (!GUI_TEXTURES.equals(this.iconTexture))
                {
                    mc.getTextureManager().bindTexture(this.iconTexture);
                }

                this.drawTexturedModalRect(this.x + 2, this.y + 2, this.iconX, this.iconY, 18, 18);
            }
        }

        public boolean isSelected()
        {
            return this.selected;
        }

        public void setSelected(boolean selectedIn)
        {
            this.selected = selectedIn;
        }
    }

    @SideOnly(Side.CLIENT)
    class ConfirmButton extends Button
    {
        public ConfirmButton(int buttonId, int x, int y)
        {
            super(buttonId, x, y, GUI_TEXTURES, 90, 153);
        }

        public void drawButtonForegroundLayer(int mouseX, int mouseY)
        {
            drawHoveringText(I18n.format("gui.done"), mouseX, mouseY);
        }
    }
}