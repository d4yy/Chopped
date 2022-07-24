package net.day.chopped.world.features;

import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ChoppedPlacedFeatures {

    public static final RegistryObject<PlacedFeature> CHROMIUM_ORE_PLACED = ChoppedRegistry.PLACED_FEATURES.register("chromium_ore_placed", () -> new PlacedFeature(ChoppedConfiguredFeatures.CHROMIUM_ORE.getHolder().get(), List.of(
            HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(30), 1)),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(120)
    )));

    public static final RegistryObject<PlacedFeature> HIMALAYAN_SALT_ORE_PLACED = ChoppedRegistry.PLACED_FEATURES.register("himalayan_salt_ore_placed", () -> new PlacedFeature(ChoppedConfiguredFeatures.HIMALAYAN_SALT_ORE.getHolder().get(), List.of(
            HeightRangePlacement.uniform(VerticalAnchor.absolute(164), VerticalAnchor.top()),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(120)
    )));

    public static final RegistryObject<PlacedFeature> SEA_SALT_DEPOSIT_PLACED = ChoppedRegistry.PLACED_FEATURES.register("sea_salt_deposit_placed", () -> new PlacedFeature(ChoppedConfiguredFeatures.SEA_SALT_DEPOSIT.getHolder().get(), List.of(
            HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(64)),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(120)
    )));

    public static void register() {}
}
