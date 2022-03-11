package net.day.chopped.blocks;

import net.day.chopped.blocks.crops.CropSectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ChoppedBlockStateProperties extends BlockStateProperties {
    public static final EnumProperty<CropSectionProperty> CROP_SECTION = EnumProperty.create("section", CropSectionProperty.class);
}
