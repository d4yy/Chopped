package net.joey.chopped.world;

import net.joey.chopped.Chopped;
import net.joey.chopped.world.features.ChoppedPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ChoppedBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CHROMIUM_ORE = key("chromium_ore");
    public static final ResourceKey<BiomeModifier> ADD_HIMALAYAN_SALT_ORE = key("himalayan_salt_ore");
    public static final ResourceKey<BiomeModifier> ADD_SEA_SALT_DEPOSIT = key("sea_salt_deposit");

    public static final ResourceKey<BiomeModifier> ADD_APPLE_TREES = key("apple_trees");


    public static void bootstrap(final BootstrapContext<BiomeModifier> context) {
        final var biomes = context.lookup(Registries.BIOME);
        final var features = context.lookup(Registries.PLACED_FEATURE);

        context.register(ADD_CHROMIUM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_DRY_OVERWORLD),
            features(features, ChoppedPlacedFeatures.CHROMIUM_ORE_PLACED),
            GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_HIMALAYAN_SALT_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_MOUNTAIN_PEAK),
            features(features, ChoppedPlacedFeatures.HIMALAYAN_SALT_ORE_PLACED),
            GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_SEA_SALT_DEPOSIT, new BiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, BiomeTags.IS_OCEAN),
            features(features, ChoppedPlacedFeatures.SEA_SALT_DEPOSIT_PLACED),
            GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_APPLE_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_PLAINS),
            features(features, ChoppedPlacedFeatures.APPLE_TREE_RED_PLACED, ChoppedPlacedFeatures.APPLE_TREE_GREEN_PLACED),
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));

    }

    private static ResourceKey<BiomeModifier> key(final String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Chopped.MOD_ID, name));
    }

    private static HolderSet<Biome> tag(final HolderGetter<Biome> holderGetter, final TagKey<Biome> key) {
        return holderGetter.getOrThrow(key);
    }

    private static HolderSet<PlacedFeature> features(final HolderGetter<PlacedFeature> holderGetter, final ResourceKey<PlacedFeature>... feature) {
        return HolderSet.direct(holderGetter::getOrThrow, feature);
    }
}