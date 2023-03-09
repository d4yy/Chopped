package net.day.chopped.world.features.trees.growers;

import net.day.chopped.world.features.ChoppedConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AppleTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return new Random().nextBoolean() ? ChoppedConfiguredFeatures.APPLE_TREE_RED_DELICIOUS : ChoppedConfiguredFeatures.APPLE_TREE_GRANNY_SMITH;
    }
}
