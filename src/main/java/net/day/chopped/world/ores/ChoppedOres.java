package net.day.chopped.world.ores;

import net.day.chopped.configs.ChoppedCommonConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ChoppedOres {
    //Chromium
    public static final ConfiguredFeature<?, ?> CHROMIUM_ORE = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ChoppedOreGeneration.OreReplaceTargets.ORE_CHROMIUM_TARGET_LIST, ChoppedCommonConfig.CHROMIUM_ORE_VEIN_SIZE.get()));
    public static final PlacedFeature CHROMIUM_ORE_PLACED = new PlacedFeature(Holder.direct(CHROMIUM_ORE), List.of(
            HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(30), 1)),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(ChoppedCommonConfig.CHROMIUM_ORE_VEINS_PER_CHUNK.get())

    ));

    public static final ConfiguredFeature<?, ?> HIMALAYAN_SALT_ORE = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ChoppedOreGeneration.OreReplaceTargets.ORE_HIMALAYAN_SALT_TARGET_LIST, 8 /*temp amount*/));
    public static final PlacedFeature HIMALAYAN_SALT_ORE_PLACED = new PlacedFeature(Holder.direct(HIMALAYAN_SALT_ORE), List.of(
            HeightRangePlacement.uniform(VerticalAnchor.absolute(164), VerticalAnchor.top()),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(30 /*temp amount*/)
    ));

    public static final ConfiguredFeature<?, ?> SEA_SALT_DEPOSIT = new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ChoppedOreGeneration.OreReplaceTargets.ORE_SEA_SALT_TARGET_LIST, 16 /*temp amount*/));
    public static final PlacedFeature SEA_SALT_DEPOSIT_PLACED = new PlacedFeature(Holder.direct(SEA_SALT_DEPOSIT), List.of(
            HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(64)),
            BiomeFilter.biome(),
            InSquarePlacement.spread(),
            CountPlacement.of(30)
    ));
}
