package net.day.chopped.blocks.crops;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class ChoppedCropBlock extends CropBlock {
    private final int maxCropHeight;
    private final ItemLike seedItem;

    public ChoppedCropBlock(int height, ItemLike seedItem, Properties properties) {
        super(properties);
        this.maxCropHeight = height;
        this.seedItem = seedItem;
    }

    public int getMaxHeight(int height) {
        return maxCropHeight;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return seedItem;
    }
}
