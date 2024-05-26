package net.joey.chopped.world.features.trees;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class ChoppedTreeGrower extends TreeGrower {
    private final Optional<ResourceKey<ConfiguredFeature<?, ?>>>[] trees;

    public ChoppedTreeGrower(String pName, Optional<ResourceKey<ConfiguredFeature<?, ?>>>... configuredTrees) {
        super(pName, Optional.empty(), Optional.empty(), Optional.empty());
        this.trees = configuredTrees;
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pFlowers) {
        return trees[pRandom.nextInt(trees.length)].get();
    }
}
