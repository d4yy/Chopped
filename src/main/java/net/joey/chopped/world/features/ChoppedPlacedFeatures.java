package net.joey.chopped.world.features;

import net.joey.chopped.Chopped;
import net.joey.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ChoppedPlacedFeatures {

    public static final ResourceKey<PlacedFeature> CHROMIUM_ORE_PLACED = registerKey("chromium_ore_placed");
    public static final ResourceKey<PlacedFeature> HIMALAYAN_SALT_ORE_PLACED = registerKey("himalayan_salt_ore_placed");

    public static final ResourceKey<PlacedFeature> SEA_SALT_DEPOSIT_PLACED = registerKey("sea_salt_deposit_placed");

    public static final ResourceKey<PlacedFeature> APPLE_TREE_RED_PLACED = registerKey("apple_tree_red_placed");
    public static final ResourceKey<PlacedFeature> APPLE_TREE_GREEN_PLACED = registerKey("apple_tree_green_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CHROMIUM_ORE_PLACED, configuredFeatures.getOrThrow(ChoppedConfiguredFeatures.CHROMIUM_ORE), List.of(
            HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(30), 1)),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(50)
        ));
        register(context, HIMALAYAN_SALT_ORE_PLACED, configuredFeatures.getOrThrow(ChoppedConfiguredFeatures.HIMALAYAN_SALT_ORE), List.of(
            HeightRangePlacement.uniform(VerticalAnchor.absolute(200), VerticalAnchor.top()),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(30)
        ));

        register(context, SEA_SALT_DEPOSIT_PLACED, configuredFeatures.getOrThrow(ChoppedConfiguredFeatures.SEA_SALT_DEPOSIT), List.of(
            HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(64)),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(50)
        ));


        register(context, APPLE_TREE_RED_PLACED, configuredFeatures.getOrThrow(ChoppedConfiguredFeatures.APPLE_TREE_RED),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.01F, 1), ChoppedBlocks.BLOCKS_APPLE_SAPLING.get())
        );

        register(context, APPLE_TREE_GREEN_PLACED, configuredFeatures.getOrThrow(ChoppedConfiguredFeatures.APPLE_TREE_GREEN),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.01F, 1), ChoppedBlocks.BLOCKS_APPLE_SAPLING.get())
        );

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Chopped.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}

