package net.day.chopped.world.ores;

import net.day.chopped.configs.ChoppedCommonConfig;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class ChoppedOreGeneration {
    public static void generateOres(final BiomeLoadingEvent event) {

        Biome.BiomeCategory biomeCat = event.getCategory();

        //temporary, supposed to be any hot and dry biome, need to update for compat
        if (biomeCat == Biome.BiomeCategory.DESERT || biomeCat == Biome.BiomeCategory.MESA || biomeCat == Biome.BiomeCategory.UNDERGROUND || biomeCat == Biome.BiomeCategory.SAVANNA) {
            if (ChoppedCommonConfig.CHROMIUM_ORE_GENERATE.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(ChoppedOres.CHROMIUM_ORE_PLACED));
            }
        }

        if (biomeCat == Biome.BiomeCategory.EXTREME_HILLS || biomeCat == Biome.BiomeCategory.ICY || biomeCat == Biome.BiomeCategory.MOUNTAIN || biomeCat == Biome.BiomeCategory.TAIGA) {
            if (true /*config*/) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(ChoppedOres.HIMALAYAN_SALT_ORE_PLACED));
            }
        }

        if (biomeCat == Biome.BiomeCategory.OCEAN) {
            if (true /*config*/) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(ChoppedOres.SEA_SALT_DEPOSIT_PLACED));
            }
        }

    }

    public static class OreReplaceTargets {
        public static final List<OreConfiguration.TargetBlockState> ORE_CHROMIUM_TARGET_LIST = List.of(
                OreConfiguration.target(TargetRules.STONE_ORE_TARGET, ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(TargetRules.DEEPSLATE_ORE_TARGET, ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get().defaultBlockState())
        );

        public static final List<OreConfiguration.TargetBlockState> ORE_HIMALAYAN_SALT_TARGET_LIST = List.of(
                OreConfiguration.target(TargetRules.STONE_ORE_TARGET, ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_ORE.get().defaultBlockState())
        );
        public static final List<OreConfiguration.TargetBlockState> ORE_SEA_SALT_TARGET_LIST = List.of(
                OreConfiguration.target(TargetRules.REGULAR_SAND, ChoppedBlocks.BLOCKS_SEA_SALT.get().defaultBlockState())
        );
    }

    public static final class TargetRules {
        public static final RuleTest STONE_ORE_TARGET = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        public static final RuleTest DEEPSLATE_ORE_TARGET = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        public static final RuleTest REGULAR_SAND = getTargetRuleFromBlockTag("forge:ore_bearing_ground/sand/colorless_sand");


        public static RuleTest getTargetRuleFromBlockTag(String tagResourceLocation) {
            TagKey<Block> tag = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(tagResourceLocation));
            return new TagMatchTest(tag);
        }
    }
}
