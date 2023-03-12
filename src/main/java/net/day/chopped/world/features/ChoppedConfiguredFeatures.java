package net.day.chopped.world.features;

import net.day.chopped.Chopped;
import net.day.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.day.chopped.registry.ChoppedRegistry;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.day.chopped.world.features.trees.configurations.DroopingTreeConfiguration;
import net.day.chopped.world.features.trees.configurations.ShrubTreeConfiguration;
import net.day.chopped.world.features.trees.types.ChoppedTreeFeature;
import net.day.chopped.world.features.trees.types.DroopingTreeFeature;
import net.day.chopped.world.features.trees.types.ShrubTreeFeature;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ChoppedConfiguredFeatures {
    private static HolderGetter<Block> blocks;

    public static final ResourceKey<ConfiguredFeature<? ,?>> CHROMIUM_ORE = registerKey("chromium_ore");
    public static final ResourceKey<ConfiguredFeature<? ,?>> HIMALAYAN_SALT_ORE = registerKey("himalayan_salt_ore");

    public static final ResourceKey<ConfiguredFeature<? ,?>> SEA_SALT_DEPOSIT = registerKey("sea_salt_deposit");

    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_RED_DELICIOUS = registerKey("apple_tree_red_delicious");
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_GRANNY_SMITH = registerKey("apple_tree_granny_smith");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_TREE_SWEET = registerKey("orange_tree_sweet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_TREE_BITTER = registerKey("orange_tree_bitter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_TREE_MANDARIN = registerKey("orange_tree_mandarin");

    public static final ChoppedTreeFeature<DroopingTreeConfiguration> DROOPING_TREE_BASE = register("drooping_tree_base", new DroopingTreeFeature(DroopingTreeConfiguration.CODEC));
    public static final ChoppedTreeFeature<ShrubTreeConfiguration> SHRUB_TREE_BASE = register("shrub_tree_base", new ShrubTreeFeature(ShrubTreeConfiguration.CODEC));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
         blocks = context.lookup(Registries.BLOCK);

        register(context, CHROMIUM_ORE, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_CHROMIUM_TARGET_LIST.get(), 8));
        register(context, HIMALAYAN_SALT_ORE, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_HIMALAYAN_SALT_TARGET_LIST.get(), 16));

        register(context, SEA_SALT_DEPOSIT, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_SEA_SALT_TARGET_LIST.get(), 24));

        register(context, APPLE_TREE_RED_DELICIOUS, DROOPING_TREE_BASE, new DroopingTreeConfiguration.Builder().trunk(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get())).foliage(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get().defaultBlockState().setValue(FruitBearingLeavesBlock.CULTIVAR, 0))).build());
        register(context, APPLE_TREE_GRANNY_SMITH, DROOPING_TREE_BASE, new DroopingTreeConfiguration.Builder().trunk(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get())).foliage(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get().defaultBlockState().setValue(FruitBearingLeavesBlock.CULTIVAR, 1))).build());

        register(context, ORANGE_TREE_SWEET, SHRUB_TREE_BASE, new ShrubTreeConfiguration.Builder().trunk(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_CITRUS_LOG.get())).foliage(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get().defaultBlockState().setValue(FruitBearingLeavesBlock.CULTIVAR, 0))).build());
        register(context, ORANGE_TREE_BITTER, SHRUB_TREE_BASE, new ShrubTreeConfiguration.Builder().trunk(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_CITRUS_LOG.get())).foliage(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get().defaultBlockState().setValue(FruitBearingLeavesBlock.CULTIVAR, 1))).build());
        register(context, ORANGE_TREE_MANDARIN, SHRUB_TREE_BASE, new ShrubTreeConfiguration.Builder().trunk(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_CITRUS_LOG.get())).foliage(BlockStateProvider.simple(ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get().defaultBlockState().setValue(FruitBearingLeavesBlock.CULTIVAR, 2))).build());

        //register(context, ORANGE_TREE_SWEET, Feature.TREE, createCitrusTree(0, ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get()));
        //register(context, ORANGE_TREE_BITTER, Feature.TREE, createCitrusTree(1, ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get()));
        //register(context, ORANGE_TREE_MANDARIN, Feature.TREE, createCitrusTree(2, ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get()));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Chopped.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<? ,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value)
    {
        ChoppedRegistry.FEATURES.register(key, () -> value);
        return value;
    }

    public static void register() {}
}
