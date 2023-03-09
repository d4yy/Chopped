package net.day.chopped.world.features.trees.growers;

import net.day.chopped.world.features.ChoppedConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class OrangeTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return switch (pRandom.nextInt(1, 4)) {
            case 1 -> ChoppedConfiguredFeatures.ORANGE_TREE_SWEET;
            case 2 -> ChoppedConfiguredFeatures.ORANGE_TREE_BITTER;
            case 3 -> ChoppedConfiguredFeatures.ORANGE_TREE_MANDARIN;
            default -> null;
        };
    }
}
