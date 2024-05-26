package net.joey.chopped.util.datagen.providers;

import net.joey.chopped.Chopped;
import net.joey.chopped.registry.groups.ChoppedItems;
import net.joey.chopped.registry.groups.ChoppedTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ChoppedItemTagsProvider extends ItemTagsProvider {
    public ChoppedItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, blockTagsProvider, Chopped.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ChoppedTags.Items.FRUIT_BASKET_ALLOWED).add(Items.APPLE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.CHORUS_FRUIT, ChoppedItems.ITEMS_GREEN_APPLE.get(), ChoppedItems.ITEMS_POISONOUS_APPLE.get(), ChoppedItems.ITEMS_SWEET_ORANGE.get(), ChoppedItems.ITEMS_BITTER_ORANGE.get(), ChoppedItems.ITEMS_MANDARIN_ORANGE.get());

        //Vanilla Tag Additions
        tag(ItemTags.MUSIC_DISCS).add(ChoppedItems.ITEMS_CASSETTE_WHAT_IT_DO.get(), ChoppedItems.ITEMS_CASSETTE_LET_ME_OHH.get(), ChoppedItems.ITEMS_CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON.get(), ChoppedItems.ITEMS_CASSETTE_BE_A_KING.get(), ChoppedItems.ITEMS_CASSETTE_BIG_SHIPS.get());
    }
}
