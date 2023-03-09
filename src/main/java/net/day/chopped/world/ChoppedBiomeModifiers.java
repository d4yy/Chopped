package net.day.chopped.world;

import net.day.chopped.Chopped;
import net.day.chopped.world.features.ChoppedPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ChoppedBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CHROMIUM_ORE = key("chromium_ore");
    public static final ResourceKey<BiomeModifier> ADD_HIMALAYAN_SALT_ORE = key("himalayan_salt_ore");
    public static final ResourceKey<BiomeModifier> ADD_SEA_SALT_DEPOSIT = key("sea_salt_deposit");

    public static final ResourceKey<BiomeModifier> ADD_APPLE_TREE_RED_DELICIOUS = key("apple_tree_red_delicious");
    public static final ResourceKey<BiomeModifier> ADD_APPLE_TREE_GRANNY_SMITH = key("apple_tree_granny_smith");


    public static void bootstrap(final BootstapContext<BiomeModifier> context) {
        final var biomes = context.lookup(Registries.BIOME);
        final var features = context.lookup(Registries.PLACED_FEATURE);

        context.register(ADD_CHROMIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_DRY_OVERWORLD),
            feature(features, ChoppedPlacedFeatures.CHROMIUM_ORE_PLACED),
            GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_HIMALAYAN_SALT_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_PEAK),
            feature(features, ChoppedPlacedFeatures.HIMALAYAN_SALT_ORE_PLACED),
            GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_SEA_SALT_DEPOSIT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, BiomeTags.IS_OCEAN),
            feature(features, ChoppedPlacedFeatures.SEA_SALT_DEPOSIT_PLACED),
            GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_APPLE_TREE_RED_DELICIOUS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_PLAINS),
            feature(features, ChoppedPlacedFeatures.APPLE_TREE_RED_DELICIOUS_PLACED),
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_APPLE_TREE_GRANNY_SMITH, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
            tag(biomes, Tags.Biomes.IS_PLAINS),
            feature(features, ChoppedPlacedFeatures.APPLE_TREE_GRANNY_SMITH_PLACED),
            GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> key(final String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Chopped.MOD_ID, name));
    }

    private static HolderSet<Biome> tag(final HolderGetter<Biome> holderGetter, final TagKey<Biome> key) {
        return holderGetter.getOrThrow(key);
    }

    private static HolderSet<PlacedFeature> feature(final HolderGetter<PlacedFeature> holderGetter, final ResourceKey<PlacedFeature> feature) {
        return HolderSet.direct(holderGetter.getOrThrow(feature));
    }
}
