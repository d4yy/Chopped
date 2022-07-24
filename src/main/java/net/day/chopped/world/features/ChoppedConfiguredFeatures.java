package net.day.chopped.world.features;

import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedConfiguredFeatures {

    public static final RegistryObject<ConfiguredFeature<?, ?>> CHROMIUM_ORE = ChoppedRegistry.CONFIGURED_FEATURES.register("chromium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_CHROMIUM_TARGET_LIST.get(), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> HIMALAYAN_SALT_ORE = ChoppedRegistry.CONFIGURED_FEATURES.register("himalayan_salt_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_HIMALAYAN_SALT_TARGET_LIST.get(), 8)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SEA_SALT_DEPOSIT = ChoppedRegistry.CONFIGURED_FEATURES.register("sea_salt_deposit", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_SEA_SALT_TARGET_LIST.get(), 16)));

    public static void register() {}
}
