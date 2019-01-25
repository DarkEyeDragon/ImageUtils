package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.gui.util.ImagePreview;
import com.darkeyedragon.imageutils.client.gui.util.Margin;
import com.darkeyedragon.imageutils.client.imageuploaders.CustomUploader;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import com.darkeyedragon.imageutils.client.utils.ImageResource;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Mouse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GuiLocalScreenshots extends GuiScreen {

    private final GuiScreen parentScreen;
    private File screenshotDirectory = new File(Minecraft.getMinecraft().gameDir, "screenshots");
    private java.util.List<ImagePreview> previewList = new ArrayList<>();
    private java.util.Map<String, ResourceLocation> resources = new HashMap<>();
    private boolean isSelected = false;
    private GuiButton deleteButton;
    private GuiButton uploadButton;
    private GuiButton refreshButton;
    private GuiButton optionsButton;
    private GuiButton cancelButton;
    private ImagePreview imagePreview;
    private boolean completedLoading = false;
    private boolean deleteImage;
    private boolean deleteImageConfirm;
    private int imageIndex;
    private boolean bindingComplete = false;
    private int amountOfScreenshots;
    private int loadedScreenshots;

    private Margin margin;
    private Progressbar progressbar;

    private final short IMAGES_PER_ROW = 4;
    private final short ROWS = 3;

    public GuiLocalScreenshots(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    ImagePreview getImagePreview() {
        return imagePreview;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        uploadButton = new GuiButton(1, this.width / 2 - 72 - 4 - 35 + 2, this.height - 52, 144, 20, I18n.format("imageutil.button.upload"));
        optionsButton = new GuiButton(4, this.width / 2 + 35 + 4, this.height - 52, 70, 20, I18n.format("imageutil.button.options"));
        deleteButton = new GuiButton(2, this.width / 2 - 70 - 35 - 4, this.height - 28, 70, 20, I18n.format("imageutil.button.delete"));
        refreshButton = new GuiButton(3, this.width / 2 - 35, this.height - 28, 70, 20, I18n.format("imageutil.button.refresh"));
        cancelButton = new GuiButton(0, this.width / 2 + 35 + 4, this.height - 28, 70, 20, I18n.format(I18n.format("imageutil.button.cancel")));
        deleteButton.enabled = false;
        uploadButton.enabled = false;
        optionsButton.enabled = false;
        this.buttonList.add(deleteButton);
        this.buttonList.add(uploadButton);
        this.buttonList.add(refreshButton);
        this.buttonList.add(cancelButton);
        this.buttonList.add(optionsButton);
        margin = new Margin(20);
        ImageUtilsMain.fixedThreadPool.submit(this::loadScreenshots);
        progressbar = new Progressbar(width / 2 - 100, height / 2 - 10, 200, 20);
        progressbar.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        int imgOffsetY = 0;
        int amount = 0;

        if (completedLoading) {
            loadedScreenshots = 0;
            amountOfScreenshots = 0;
            if (previewList.size() != resources.size()) {
                for (ImagePreview imagePreview : previewList) {
                    if (!resources.containsKey(imagePreview.getImageResource().getName()))
                        resources.put(imagePreview.getImageResource().getName(), Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(imagePreview.getImageResource().getName(), new DynamicTexture(imagePreview.getImageResource().getImage())));
                }
            }
            String imgName;
            for (ImagePreview imagePreview : previewList) {
                imgName = imagePreview.getImageResource().getName();
                margin = imagePreview.getMargin();
                Minecraft.getMinecraft().getTextureManager().bindTexture(resources.get(imgName));
                int x = margin.getLeft() + (imagePreview.getWidth() + 40) * amount + margin.getRight();
                int y = imgOffsetY + margin.getTop();
                drawModalRectWithCustomSizedTexture(x, y, 0, 0, imagePreview.getWidth(), imagePreview.getHeight(), imagePreview.getWidth(), imagePreview.getHeight());
                drawCenteredString(mc.fontRenderer, imgName, x + fontRenderer.getStringWidth(imgName) / 2, y + imagePreview.getHeight() + 10, 0xffffff);
                amount++;
            }
        } else {
            if (amountOfScreenshots == 0) {
                return;
            }
            progressbar.setMaxProgress(amountOfScreenshots);
            progressbar.setProgress(loadedScreenshots);
            progressbar.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        this.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == cancelButton) {
            this.mc.displayGuiScreen(this.parentScreen);
        } else if (button == refreshButton) {
            completedLoading = false;
            previewList.clear();
            ImageUtilsMain.fixedThreadPool.submit(this::loadScreenshots);
        } else if (button == uploadButton) {

            //TODO make upload screen
            uploadButton.displayString = "Uploading...";
            uploadButton.enabled = false;
            if (imagePreview.getImageResource().getImage() != null) {
                if (ModConfig.customServer) {
                    ImgurUploader.uploadImage(imagePreview.getImageResource().getImage());
                } else {
                    CustomUploader.uploadImage(imagePreview.getImageResource().getImage());
                }
            } else {
                uploadButton.displayString = "Unable to upload screenshot!";
            }
            //mc.displayGuiScreen(new GuiProgressbar(this));
        } else if (button == deleteButton) {

            GuiConfirmAction guiConfirmDelete = new GuiConfirmAction((result, id) ->
            {
                if (result) {
                    deleteScreenshots();
                }
                mc.displayGuiScreen(this);
            }, "Confirm", new TextComponentTranslation("imageutil.gui.delete_screenshot").getUnformattedComponentText(), new TextComponentTranslation("imageutil.gui.delete_screenshot_line2").getUnformattedComponentText(), 0, this) {
                @Override
                public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                    this.parent.drawScreen(-1, -1, partialTicks);
                    super.drawScreen(mouseX, mouseY, partialTicks);
                }
            };
            mc.displayGuiScreen(guiConfirmDelete);
        } else if (button == optionsButton) {
            mc.displayGuiScreen(new GuiScreenshotOptions((result, id) -> {
                mc.displayGuiScreen(this);
            }, "Screenshot Settings", "Save", "Cancel", 0, this) {
                @Override
                public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                    parent.drawScreen(-1, -1, partialTicks);
                    super.drawScreen(mouseX, mouseY, partialTicks);
                }
            });
        }
    }

    private void loadScreenshots() {
        File[] files = screenshotDirectory.listFiles();
        if (files == null || files.length == 0) {
            completedLoading();
            return;
        }
        setAmountOfScreenshots(files.length);
        for (File file : files) {
            String fileName = file.getName();
            try {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    ImageResource imageResource = new ImageResource(fileName, image, false, file.getPath());
                    margin = new Margin(20);
                    ImagePreview imagePreview = new ImagePreview(imageResource, 144, 96);
                    imagePreview.setMargin(margin);
                    addPreview(imagePreview);
                }
            } catch (IOException e) {
                ImageUtilsMain.logger.error(e);
            }
        }
        completedLoading();
    }

    private synchronized void addPreview(ImagePreview imagePreview) {
        mc.addScheduledTask(() -> {
            previewList.add(imagePreview);
            loadedScreenshots++;
        });
    }
    private synchronized void addPreview(ImagePreview[] imagePreviews) {
        mc.addScheduledTask(() -> {
            for (ImagePreview preview : imagePreviews) {
                previewList.add(preview);
                loadedScreenshots++;
            }
        });
    }

    synchronized void clearScreenshotsList() {
        mc.addScheduledTask(() -> previewList.clear());
    }

    private synchronized void completedLoading() {
        mc.addScheduledTask(() -> {
            completedLoading = true;
        });
    }

    private synchronized void setAmountOfScreenshots(int amount) {
        mc.addScheduledTask(() -> {
            amountOfScreenshots = amount;
        });
    }

    private void deleteScreenshots() {
        java.util.List<ImagePreview> toDelete = new ArrayList<>();
        previewList.forEach((preview) -> {
            if (preview.getImageResource().isSelected()) {
                File file = new File(preview.getImageResource().getPath());
                if (file.delete()) {
                    ImageUtilsMain.logger.info("Removed image " + file.getName() + " successfully!");
                    toDelete.add(preview);
                } else {
                    ImageUtilsMain.logger.warn("Could not remove image " + file.getName() + "!");
                }
            }
        });
        if (previewList.indexOf(imagePreview) + toDelete.size() < previewList.size()) {
            int index = previewList.indexOf(imagePreview) + toDelete.size();
            imagePreview = previewList.get(index);
            //list.elementClicked(index, false, list.getScrollBarX(), list.getSlotHeight() * index);
        } else {
            if (previewList.size() == 1) {
                imagePreview = null;
            } else {
                imagePreview = previewList.get(previewList.size() - 2);
                //list.elementClicked(previewList.size() - 2, false, list.getScrollBarX(), list.getSlotHeight());
            }
        }
        previewList.removeAll(toDelete);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        previewList.clear();
        resources.forEach((key, value) -> Minecraft.getMinecraft().getTextureManager().deleteTexture(value));
        resources.clear();
    }

    class List extends GuiSlot {

        List(Minecraft mcIn) {
            super(mcIn, GuiLocalScreenshots.this.width, GuiLocalScreenshots.this.height, 32, GuiLocalScreenshots.this.height - 65 + 4, 20);

        }

        @Override
        public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
            super.drawScreen(mouseXIn, mouseYIn, partialTicks);
        }

        /**
         * Sets the left and right bounds of the slot. Param is the left bound, right is calculated as left + width.
         */
        @Override
        public void setSlotXBoundsFromLeft(int leftIn) {
            this.left = 0;
            this.right = 0;
        }

        @Override
        protected int getScrollBarX() {
            return (int) Math.round(this.width / 2.5);
        }

        protected int getSize() {
            return GuiLocalScreenshots.this.previewList.size();
        }

        /**
         * The element in the slot that was clicked, boolean for whether it was double clicked or not
         */
        public void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            previewList.forEach((item) -> item.getImageResource().setSelected(false));
            imagePreview = previewList.get(slotIndex);
            boolean toggle = !imagePreview.getImageResource().isSelected();
            imagePreview.getImageResource().setSelected(toggle);
            GuiLocalScreenshots.this.isSelected = true;
            imageIndex = slotIndex;
        }

        @Override
        protected boolean isSelected(int slotIndex) {
            ImagePreview selected = previewList.get(slotIndex);
            return selected != null && previewList.get(slotIndex).getImageResource().isSelected();
        }


        /**
         * Return the height of the content being scrolled
         */
        protected int getContentHeight() {
            return this.getSize() * 20;
        }

        protected void drawBackground() {
            GuiLocalScreenshots.this.drawDefaultBackground();
        }

        protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
            GuiLocalScreenshots.this.drawString(GuiLocalScreenshots.this.fontRenderer, (previewList.get(slotIndex).getImageResource().getName()), this.width / 5 - (this.getListWidth() / 3) + 5, yPos + 1, 16777215);
        }

        public void handleMouseInput() {
            if (this.isMouseYWithinSlotBounds(this.mouseY)) {
                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
                    //int i = (this.width - this.getListWidth()) / 5;
                    //int j = (this.width + this.getListWidth()) / 5;
                    int i = this.left + (this.width / 5) - (this.getListWidth() / 3);
                    int j = this.left + (this.width / 5) + (this.getListWidth() / 3);
                    int k = this.mouseY - this.top - this.headerPadding + (int) this.amountScrolled - 4;
                    int l = k / this.slotHeight;

                    if (l < this.getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
                        this.elementClicked(l, false, this.mouseX, this.mouseY);
                        this.selectedElement = l;
                    } else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
                        this.clickedHeader(this.mouseX - i, this.mouseY - this.top + (int) this.amountScrolled - 4);
                    }
                }

                if (Mouse.isButtonDown(0) && this.getEnabled()) {
                    if (this.initialClickY == -1) {
                        boolean flag1 = true;

                        if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                            int j2 = (this.width - this.getListWidth()) / 4;
                            int k2 = (this.width + this.getListWidth()) / 4;
                            int l2 = this.mouseY - this.top - this.headerPadding + (int) this.amountScrolled - 4;
                            int i1 = l2 / this.slotHeight;

                            if (i1 < this.getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
                                boolean flag = i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                                this.elementClicked(i1, flag, this.mouseX, this.mouseY);
                                this.selectedElement = i1;
                                this.lastClicked = Minecraft.getSystemTime();
                            } else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
                                this.clickedHeader(this.mouseX - j2, this.mouseY - this.top + (int) this.amountScrolled - 4);
                                flag1 = false;
                            }

                            int i3 = this.getScrollBarX();
                            int j1 = i3 + 6;

                            if (this.mouseX >= i3 && this.mouseX <= j1) {
                                this.scrollMultiplier = -1.0F;
                                int k1 = this.getMaxScroll();

                                if (k1 < 1) {
                                    k1 = 1;
                                }

                                int l1 = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top)) / (float) this.getContentHeight());
                                l1 = MathHelper.clamp(l1, 32, this.bottom - this.top - 8);
                                this.scrollMultiplier /= (float) (this.bottom - this.top - l1) / (float) k1;
                            } else {
                                this.scrollMultiplier = 1.0F;
                            }

                            if (flag1) {
                                this.initialClickY = this.mouseY;
                            } else {
                                this.initialClickY = -2;
                            }
                        } else {
                            this.initialClickY = -2;
                        }
                    } else if (this.initialClickY >= 0) {
                        this.amountScrolled -= (float) (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                        this.initialClickY = this.mouseY;
                    }
                } else {
                    this.initialClickY = -1;
                }

                int i2 = Mouse.getEventDWheel();

                if (i2 != 0) {
                    if (i2 > 0) {
                        i2 = -1;
                    } else if (i2 < 0) {
                        i2 = 1;
                    }

                    this.amountScrolled += (float) (i2 * this.slotHeight * 2);
                }
            }
        }

        protected void drawSelectionBox(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks) {
            int i = this.getSize();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();

            for (int j = 0; j < i; ++j) {
                int k = insideTop + j * this.slotHeight + this.headerPadding;
                int l = this.slotHeight - 4;

                if (k > this.bottom || k + l < this.top) {
                    this.updateItemPos(j, insideLeft, k, partialTicks);
                }

                if (this.showSelectionBox && this.isSelected(j)) {
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
