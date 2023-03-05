package net.day.chopped.world.features;

import net.day.chopped.Chopped;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ChoppedConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<? ,?>> CHROMIUM_ORE_KEY = registerKey("chromium_ore");
    public static final ResourceKey<ConfiguredFeature<? ,?>> HIMALAYAN_SALT_ORE_KEY = registerKey("himalayan_salt_ore");

    public static final ResourceKey<ConfiguredFeature<? ,?>> SEA_SALT_DEPOSIT_KEY = registerKey("sea_salt_deposit");

    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_KEY = registerKey("apple_tree");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        register(context, CHROMIUM_ORE_KEY, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_CHROMIUM_TARGET_LIST.get(), 8));
        register(context, HIMALAYAN_SALT_ORE_KEY, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_HIMALAYAN_SALT_TARGET_LIST.get(), 16));

        register(context, SEA_SALT_DEPOSIT_KEY, Feature.ORE, new OreConfiguration(FeatureTargetRules.OreReplaceTargets.ORE_SEA_SALT_TARGET_LIST.get(), 24));

        register(context, APPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()), new BendingTrunkPlacer(5, 1, 1, 3, UniformInt.of(2, 4)),
                BlockStateProvider.simple(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get()), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(3), 50),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()
        );
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Chopped.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<? ,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block logBlock, Block leavesBlock, int pBaseHeight, int pHeightRandA, int pHeightRandB, int canopySize) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(logBlock), new StraightTrunkPlacer(pBaseHeight, pHeightRandA, pHeightRandB), BlockStateProvider.simple(leavesBlock), new BlobFoliagePlacer(ConstantInt.of(canopySize), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }
}
