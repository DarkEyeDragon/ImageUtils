package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.imageuploaders.CustomUploader;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import com.darkeyedragon.imageutils.client.utils.ImageResource;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GuiLocalScreenshots extends GuiScreen implements IGuiConfirmAction{

    private File screenshotDirectory = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
    private java.util.List<ImageResource> screenshots = new ArrayList<>();
    private GuiLocalScreenshots.List list;
    private final GuiScreen parentScreen;
    private boolean isSelected = false;
    private GuiButton deleteButton;
    private GuiButton uploadButton;
    private GuiButton refreshButton;
    private GuiButton cancelButton;
    private BufferedImage finalImage;
    private ResourceLocation resource;
    private ImageResource imageResource;
    private boolean completedLoading;
    private boolean deleteImage;
    private boolean deleteImageConfirm;
    private int imageIndex;

    public GuiLocalScreenshots(GuiScreen parentScreen){
        this.parentScreen = parentScreen;
    }



    @Override
    public void initGui(){
        super.initGui();
        this.buttonList.clear();
        uploadButton = new GuiButton(3, this.width / 2 - 180, this.height - 40, 80, 20, "Upload");
        deleteButton = new GuiButton(4, this.width / 2 - 90, this.height - 40, 80, 20, "Delete");
        refreshButton = new GuiButton(8, this.width / 2, this.height - 40, 80, 20, "Refresh");
        cancelButton = new GuiButton(0, this.width / 2 +90, this.height - 40, 80, 20, I18n.format("gui.cancel"));
        deleteButton.enabled = false;
        uploadButton.enabled = false;
        this.buttonList.add(deleteButton);
        this.buttonList.add(uploadButton);
        this.buttonList.add(refreshButton);
        this.buttonList.add(cancelButton);
        if(!completedLoading)
            ImageUtilsMain.fixedThreadPool.submit(this::loadScreenshots);
        this.list = new GuiLocalScreenshots.List(this.mc);
        this.list.registerScrollButtons(7, 8);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        BufferedImage img = finalImage;
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(completedLoading){
            if(isSelected){
                this.drawCenteredString(this.fontRenderer, "Screenshots ("+(imageIndex+1)+"/"+screenshots.size()+")", this.width / 2, 16, 16777215);
                deleteButton.enabled = true;
                uploadButton.enabled = true;
                int scaledHeight = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();

                int imgWidth = (int) Math.round(img.getWidth() * (scaledHeight * 0.00105));
                int imgHeight = (int) Math.round(img.getHeight() * (scaledHeight * 0.00105));
                int imgOffsetX = (imgWidth + (width / 15));
                int imgOffsetY = height / 2 - (imgHeight / 2 + height / 8);
                String name = "Name: " + imageResource.getName();
                String dimensions = "Dimensions: " + imageResource.getImage().getWidth() + "x" + imageResource.getImage().getHeight();
                String location = "Location: " + imageResource.getPath();
                mc.fontRenderer.drawString(name, width - imgOffsetX, imgOffsetY + imgHeight + 10, 0xffffff);
                mc.fontRenderer.drawString(dimensions, width - imgOffsetX, imgOffsetY + imgHeight + 20, 0xffffff);
                mc.fontRenderer.drawString(location, width - imgOffsetX, imgOffsetY + imgHeight + 30, 0xffffff);
                Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
                drawModalRectWithCustomSizedTexture(width - imgOffsetX, imgOffsetY, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
                if(deleteImage){
                    GuiConfirmAction guiConfirmDelete = new GuiConfirmAction(this);
                    mc.displayGuiScreen(guiConfirmDelete);
                    deleteImage = false;
                }
            }else{
                this.drawCenteredString(this.fontRenderer, "Screenshots", this.width / 2, 16, 16777215);
            }
        }else{
            this.drawCenteredString(this.fontRenderer, "Screenshots", this.width / 2, 16, 16777215);
            drawCenteredString(mc.fontRenderer,"loading your screenshots...", (int)(Math.round(width/1.4)), (int)(Math.round(height/2.3)), 0xffffff);
        }
    }
    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button == cancelButton){
            this.mc.displayGuiScreen(this.parentScreen);
        }
        else if(button == refreshButton){
            completedLoading = false;
            screenshots.clear();
            ImageUtilsMain.fixedThreadPool.submit(this::loadScreenshots);
        }
        else if(button == uploadButton){

            //TODO make upload screen
            uploadButton.displayString = "Uploading...";
            uploadButton.enabled = false;
            if(imageResource.getImage() != null){
                if(ModConfig.customServer){
                    ImgurUploader.uploadImage(imageResource.getImage());
                }else{
                    CustomUploader.uploadImage(imageResource.getImage());
                }
            }else{
                uploadButton.displayString = "Unable to upload screenshot!";
            }
            //mc.displayGuiScreen(new GuiProgressbar(this));
        }
        else if(button == deleteButton){

            deleteImage = true;
            if(deleteImageConfirm){

                deleteImageConfirm = false;
            }
        }
    }

    private void loadScreenshots(){
        File[] files = screenshotDirectory.listFiles();
        if(files == null) return;
        for(File file : files){
             String fileName = file.getName();
            try{
                BufferedImage image = ImageIO.read(file);
                addScreenshot(new ImageResource(fileName, image, false, file.getPath()));
            }catch(IOException e){
                addScreenshot(new ImageResource(fileName, null, false, file.getPath()));
                e.printStackTrace();
            }
        }
        completedLoading();
    }
    private synchronized void addScreenshot(ImageResource imageResource){
        mc.addScheduledTask(()->{
            screenshots.add(imageResource);
        });
    }
    private synchronized void completedLoading(){
        mc.addScheduledTask(()->{
            completedLoading = true;
        });
    }

    public void deleteScreenshots(){
        java.util.List<ImageResource> toDelete = new ArrayList<>();
        screenshots.forEach((screenshot) -> {
            if(screenshot.isSelected()){
                File file = new File(screenshot.getPath());
                if(file.delete()){
                    ImageUtilsMain.logger.info("Removed image " + file.getName() + " successfully!");
                    toDelete.add(screenshot);
                }else{
                    ImageUtilsMain.logger.warn("Could not remove image " + file.getName() + "!");
                }
            }
        });
        screenshots.removeAll(toDelete);
    }

    @Override
    public void confirm(){
        deleteScreenshots();
    }

    class List extends GuiSlot
    {

        List(Minecraft mcIn)
        {
            super(mcIn, GuiLocalScreenshots.this.width, GuiLocalScreenshots.this.height, 32, GuiLocalScreenshots.this.height - 65 + 4, 20);

        }
        @Override
        public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks){
            super.drawScreen(mouseXIn, mouseYIn, partialTicks);
        }

        /**
         * Sets the left and right bounds of the slot. Param is the left bound, right is calculated as left + width.
         */
        @Override
        public void setSlotXBoundsFromLeft(int leftIn)
        {
            this.left = 0;
            this.right = 0;
        }
        @Override
        protected int getScrollBarX()
        {
            return (int)Math.round(this.width / 2.5);
        }
        protected int getSize()
        {
            return GuiLocalScreenshots.this.screenshots.size();
        }

        /**
         * The element in the slot that was clicked, boolean for whether it was double clicked or not
         */
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {
            screenshots.forEach((item)-> item.setSelected(false));
            imageResource = screenshots.get(slotIndex);
            boolean toggle = !imageResource.isSelected();
            imageResource.setSelected(toggle);
            GuiLocalScreenshots.this.isSelected = true;
            finalImage = ImageUtil.resize(imageResource.getImage(), 800, 800);
            resource = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(imageResource.getName(), new DynamicTexture(finalImage));
            imageIndex = slotIndex;
        }

        @Override
        protected boolean isSelected(int slotIndex){
            ImageResource selected = screenshots.get(slotIndex);
            return selected != null && screenshots.get(slotIndex).isSelected();
        }


        /**
         * Return the height of the content being scrolled
         */
        protected int getContentHeight()
        {
            return this.getSize() * 20;
        }

        protected void drawBackground()
        {
            GuiLocalScreenshots.this.drawDefaultBackground();
        }

        protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks)
        {
            GuiLocalScreenshots.this.drawCenteredString(GuiLocalScreenshots.this.fontRenderer, (screenshots.get(slotIndex).getName()), this.width / 5, yPos + 1, 16777215);
        }
        public void handleMouseInput()
        {
            if (this.isMouseYWithinSlotBounds(this.mouseY))
            {
                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom)
                {
                    //int i = (this.width - this.getListWidth()) / 5;
                    //int j = (this.width + this.getListWidth()) / 5;
                    int i = this.left + (this.width / 5) - (this.getListWidth() / 3);
                    int j = this.left + (this.width / 5) + (this.getListWidth() / 3);
                    int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                    int l = k / this.slotHeight;

                    if (l < this.getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0)
                    {
                        this.elementClicked(l, false, this.mouseX, this.mouseY);
                        this.selectedElement = l;
                    }
                    else if (this.mouseX >= i && this.mouseX <= j && k < 0)
                    {
                        this.clickedHeader(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
                    }
                }

                if (Mouse.isButtonDown(0) && this.getEnabled())
                {
                    if (this.initialClickY == -1)
                    {
                        boolean flag1 = true;

                        if (this.mouseY >= this.top && this.mouseY <= this.bottom)
                        {
                            int j2 = (this.width - this.getListWidth()) / 4;
                            int k2 = (this.width + this.getListWidth()) / 4;
                            int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                            int i1 = l2 / this.slotHeight;

                            if (i1 < this.getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0)
                            {
                                boolean flag = i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                                this.elementClicked(i1, flag, this.mouseX, this.mouseY);
                                this.selectedElement = i1;
                                this.lastClicked = Minecraft.getSystemTime();
                            }
                            else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0)
                            {
                                this.clickedHeader(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
                                flag1 = false;
                            }

                            int i3 = this.getScrollBarX();
                            int j1 = i3 + 6;

                            if (this.mouseX >= i3 && this.mouseX <= j1)
                            {
                                this.scrollMultiplier = -1.0F;
                                int k1 = this.getMaxScroll();

                                if (k1 < 1)
                                {
                                    k1 = 1;
                                }

                                int l1 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
                                l1 = MathHelper.clamp(l1, 32, this.bottom - this.top - 8);
                                this.scrollMultiplier /= (float)(this.bottom - this.top - l1) / (float)k1;
                            }
                            else
                            {
                                this.scrollMultiplier = 1.0F;
                            }

                            if (flag1)
                            {
                                this.initialClickY = this.mouseY;
                            }
                            else
                            {
                                this.initialClickY = -2;
                            }
                        }
                        else
                        {
                            this.initialClickY = -2;
                        }
                    }
                    else if (this.initialClickY >= 0)
                    {
                        this.amountScrolled -= (float)(this.mouseY - this.initialClickY) * this.scrollMultiplier;
                        this.initialClickY = this.mouseY;
                    }
                }
                else
                {
                    this.initialClickY = -1;
                }

                int i2 = Mouse.getEventDWheel();

                if (i2 != 0)
                {
                    if (i2 > 0)
                    {
                        i2 = -1;
                    }
                    else if (i2 < 0)
                    {
                        i2 = 1;
                    }

                    this.amountScrolled += (float)(i2 * this.slotHeight*2);
                }
            }
        }
        protected void drawSelectionBox(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks){
            int i = this.getSize();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();

            for(int j = 0; j < i; ++j){
                int k = insideTop + j * this.slotHeight + this.headerPadding;
                int l = this.slotHeight-4;

                if(k > this.bottom || k + l < this.top){
                    this.updateItemPos(j, insideLeft, k, partialTicks);
                }

                if(this.showSelectionBox && this.isSelected(j)){
                    int i1 = this.left + (this.width / 5) - (this.getListWidth() / 3);
                    int j1 = this.left + (this.width / 5) + (this.getListWidth() / 3);
                    GlStateManager.color(1.0F, 0F, 1.0F, 0F);
                    GlStateManager.disableTexture2D();
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    bufferbuilder.pos((double) i1, (double) (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double) j1, (double) (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double) j1, (double) (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double) i1, (double) (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                    bufferbuilder.pos((double) (i1 + 1), (double) (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                    bufferbuilder.pos((double) (j1 - 1), (double) (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                    bufferbuilder.pos((double) (j1 - 1), (double) (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                    bufferbuilder.pos((double) (i1 + 1), (double) (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }
                this.drawSlot(j, insideLeft, k, l, mouseXIn, mouseYIn, partialTicks);
            }
        }
    }
}
