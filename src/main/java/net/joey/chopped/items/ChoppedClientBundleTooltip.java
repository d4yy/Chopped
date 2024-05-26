package net.joey.chopped.items;

import net.joey.chopped.items.ChoppedBundleContents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.math.Fraction;

@OnlyIn(Dist.CLIENT)
public class ChoppedClientBundleTooltip implements ClientTooltipComponent {
    private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("container/bundle/background");
    private static final int MARGIN_Y = 4;
    private static final int BORDER_WIDTH = 1;
    private static final int SLOT_SIZE_X = 18;
    private static final int SLOT_SIZE_Y = 20;
    private final ChoppedBundleContents contents;

    public ChoppedClientBundleTooltip(ChoppedBundleContents pContents) {
        this.contents = pContents;
    }

    @Override
    public int getHeight() {
        return this.backgroundHeight() + 4;
    }

    @Override
    public int getWidth(Font pFont) {
        return this.backgroundWidth();
    }

    private int backgroundWidth() {
        return this.gridSizeX() * 18 + 2;
    }

    private int backgroundHeight() {
        return this.gridSizeY() * 20 + 2;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        int i = this.gridSizeX();
        int j = this.gridSizeY();
        pGuiGraphics.blitSprite(BACKGROUND_SPRITE, pX, pY, this.backgroundWidth(), this.backgroundHeight());
        boolean flag = this.contents.weight().compareTo(Fraction.ONE) >= 0;
        int k = 0;

        for (int l = 0; l < j; l++) {
            for (int i1 = 0; i1 < i; i1++) {
                int j1 = pX + i1 * 18 + 1;
                int k1 = pY + l * 20 + 1;
                this.renderSlot(j1, k1, k++, flag, pGuiGraphics, pFont);
            }
        }
    }

    private void renderSlot(int pX, int pY, int pItemIndex, boolean pIsBundleFull, GuiGraphics pGuiGraphics, Font pFont) {
        if (pItemIndex >= this.contents.size()) {
            this.blit(pGuiGraphics, pX, pY, pIsBundleFull ? ChoppedClientBundleTooltip.Texture.BLOCKED_SLOT : ChoppedClientBundleTooltip.Texture.SLOT);
        } else {
            ItemStack itemstack = this.contents.getItemUnsafe(pItemIndex);
            this.blit(pGuiGraphics, pX, pY, ChoppedClientBundleTooltip.Texture.SLOT);
            pGuiGraphics.renderItem(itemstack, pX + 1, pY + 1, pItemIndex);
            pGuiGraphics.renderItemDecorations(pFont, itemstack, pX + 1, pY + 1);
            if (pItemIndex == 0) {
                AbstractContainerScreen.renderSlotHighlight(pGuiGraphics, pX + 1, pY + 1, 0);
            }
        }
    }

    private void blit(GuiGraphics pGuiGraphics, int pX, int pY, ChoppedClientBundleTooltip.Texture pTexture) {
        pGuiGraphics.blitSprite(pTexture.sprite, pX, pY, 0, pTexture.w, pTexture.h);
    }

    private int gridSizeX() {
        return Math.max(2, (int)Math.ceil(Math.sqrt((double)this.contents.size() + 1.0)));
    }

    private int gridSizeY() {
        return (int)Math.ceil(((double)this.contents.size() + 1.0) / (double)this.gridSizeX());
    }

    @OnlyIn(Dist.CLIENT)
    static enum Texture {
        BLOCKED_SLOT(new ResourceLocation("container/bundle/blocked_slot"), 18, 20),
        SLOT(new ResourceLocation("container/bundle/slot"), 18, 20);

        public final ResourceLocation sprite;
        public final int w;
        public final int h;

        private Texture(ResourceLocation pSprite, int pW, int pH) {
            this.sprite = pSprite;
            this.w = pW;
            this.h = pH;
        }
    }
}
