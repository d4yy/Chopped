package net.day.chopped.world.features;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class FeatureTargetRules {
    public static class OreReplaceTargets {
        public static final Supplier<List<OreConfiguration.TargetBlockState>> ORE_CHROMIUM_TARGET_LIST = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(TargetRules.STONE_ORE_TARGET, ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(TargetRules.DEEPSLATE_ORE_TARGET, ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get().defaultBlockState())
        ));
        public static final Supplier<List<OreConfiguration.TargetBlockState>> ORE_HIMALAYAN_SALT_TARGET_LIST = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(TargetRules.STONE_ORE_TARGET, ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_ORE.get().defaultBlockState())
        ));
        public static final Supplier<List<OreConfiguration.TargetBlockState>> ORE_SEA_SALT_TARGET_LIST = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(TargetRules.REGULAR_SAND, ChoppedBlocks.BLOCKS_SEA_SALT_BLOCK.get().defaultBlockState())
        ));
    }

    public static final class TargetRules {
        public static final RuleTest STONE_ORE_TARGET = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        public static final RuleTest DEEPSLATE_ORE_TARGET = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        public static final RuleTest REGULAR_SAND = getTargetRuleFromBlockTag("forge:ore_bearing_ground/sand/colorless_sand");


        public static RuleTest getTargetRuleFromBlockTag(String tagResourceLocation) {
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, new ResourceLocation(tagResourceLocation));
            return new TagMatchTest(tag);
        }
    }
}
