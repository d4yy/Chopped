package net.day.chopped.world.features;

import net.day.chopped.Chopped;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ChoppedConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<? ,?>> CHROMIUM_ORE_KEY = registerKey("chromium_ore");
    public static final ResourceKey<ConfiguredFeature<? ,?>> HIMALAYAN_SALT_ORE_KEY = registerKey("himalayan_salt_ore");

    public static final ResourceKey<ConfiguredFeature<? ,?>> SEA_SALT_DEPOSIT_KEY = registerKey("sea_salt_deposit");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        register(context, CHROMIUM_ORE_KEY, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_CHROMIUM_TARGET_LIST.get(), 8));
        register(context, HIMALAYAN_SALT_ORE_KEY, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_HIMALAYAN_SALT_TARGET_LIST.get(), 16));

        register(context, SEA_SALT_DEPOSIT_KEY, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_SEA_SALT_TARGET_LIST.get(), 24));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Chopped.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<? ,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
