package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.versions.forge.ForgeVersion;

public class ChoppedTags {
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
