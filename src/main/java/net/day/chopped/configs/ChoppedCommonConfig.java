package net.day.chopped.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ChoppedCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> CHROMIUM_ORE_GENERATE;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHROMIUM_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHROMIUM_ORE_VEIN_SIZE;

    static {
        BUILDER.push("Sloshed Common Config");

        BUILDER.push("Chromium ore generation");
        CHROMIUM_ORE_GENERATE = BUILDER.comment("## Generate Chromium ore? (Boolean, Default: true) ###")
                .define("Generate Chromium", true);
        CHROMIUM_ORE_VEINS_PER_CHUNK = BUILDER.comment("## Vein Sparsity (Integer, Default: 12) ###")
                .define("Vein sparsity", 12);
        CHROMIUM_ORE_VEIN_SIZE = BUILDER.comment("## Average ore blocks per vein (Integer, Default 8) ###")
                .define("Vein Size", 4);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
