package net.day.chopped.world.features.tree_growers;

import net.day.chopped.world.features.ChoppedConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class AppleTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return Math.random() <= 0.5 ? ChoppedConfiguredFeatures.APPLE_TREE_RED_DELICIOUS : ChoppedConfiguredFeatures.APPLE_TREE_GRANNY_SMITH;
    }
}
