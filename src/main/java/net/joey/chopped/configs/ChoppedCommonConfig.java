package net.joey.chopped.configs;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ChoppedCommonConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Boolean> CHROMIUM_ORE_GENERATE;
    public static final ModConfigSpec.ConfigValue<Integer> CHROMIUM_ORE_VEINS_PER_CHUNK;
    public static final ModConfigSpec.ConfigValue<Integer> CHROMIUM_ORE_VEIN_SIZE;

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
