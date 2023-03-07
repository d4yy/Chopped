package net.day.chopped.blocks;

import net.day.chopped.blocks.crops.CropSectionProperty;
import net.minecraft.world.level.block.state.properties.*;

public class ChoppedBlockStateProperties extends BlockStateProperties {
    public static final BooleanProperty REFLECTIVE = BooleanProperty.create("reflective");
    public static final BooleanProperty FRUIT_BEARING = BooleanProperty.create("fruit_bearing");
    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");

    public static final EnumProperty<CropSectionProperty> CROP_SECTION = EnumProperty.create("section", CropSectionProperty.class);

    public static final IntegerProperty BLOCK_LIGHT_ABSORPTION = IntegerProperty.create("light_absorption", 0, 15);
    public static final IntegerProperty BLOCK_LIGHT_EMISSION = IntegerProperty.create("light_emission", 0, 15);
    public static final IntegerProperty FERTILITY = IntegerProperty.create("fertility", 0, 10);
    public static final IntegerProperty CULTIVAR = IntegerProperty.create("cultivar", 0, 2);

}
