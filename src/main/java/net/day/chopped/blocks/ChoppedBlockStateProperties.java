package net.day.chopped.blocks;

import net.day.chopped.blocks.crops.CropSectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ChoppedBlockStateProperties extends BlockStateProperties {
    public static final BooleanProperty REFLECTIVE = BooleanProperty.create("reflective");

    public static final EnumProperty<CropSectionProperty> CROP_SECTION = EnumProperty.create("section", CropSectionProperty.class);

    public static final IntegerProperty BLOCK_LIGHT_ABSORPTION = IntegerProperty.create("light_absorption", 0, 15);
    public static final IntegerProperty BLOCK_LIGHT_EMISSION = IntegerProperty.create("light_emission", 0, 15);
}
