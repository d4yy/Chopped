package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.registry.groups.ChoppedItems;
import net.day.chopped.registry.groups.ChoppedTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ChoppedItemTagsProvider extends ItemTagsProvider {
    public ChoppedItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, TagsProvider<Block> blockTagsProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, blockTagsProvider, Chopped.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ChoppedTags.Items.FRUIT_BASKET_ALLOWED).add(
            Items.APPLE,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.CHORUS_FRUIT,
            ChoppedItems.ITEMS_GRANNY_SMITH_APPLE.get(),
            ChoppedItems.ITEMS_POISONOUS_APPLE.get(),
            ChoppedItems.ITEMS_SWEET_ORANGE.get(),
            ChoppedItems.ITEMS_BITTER_ORANGE.get(),
            ChoppedItems.ITEMS_MANDARIN_ORANGE.get()
        );
    }
}
