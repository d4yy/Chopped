package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ChoppedTags {
    public static class Blocks {
        public static final TagKey<Block> APPLEWOOD_LOGS = tag("applewood_logs");
        public static final TagKey<Block> CITRUS_LOGS = tag("citrus_logs");

        private static TagKey<Block> tag(final String name) {
            return BlockTags.create(new ResourceLocation(Chopped.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(final String name) {
            return BlockTags.create(new ResourceLocation(ForgeVersion.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> FRUIT_BASKET_ALLOWED = tag("fruit_basket_allowed");

        private static TagKey<Item> tag(final String name) {
            return ItemTags.create(new ResourceLocation(Chopped.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(final String name) {
            return ItemTags.create(new ResourceLocation(ForgeVersion.MOD_ID, name));
        }
    }
}
