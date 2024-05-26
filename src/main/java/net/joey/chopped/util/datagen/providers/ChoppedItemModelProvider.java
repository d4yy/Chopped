package net.joey.chopped.util.datagen.providers;

import net.joey.chopped.Chopped;
import net.joey.chopped.registry.groups.ChoppedBlocks;
import net.joey.chopped.registry.groups.ChoppedItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ChoppedItemModelProvider extends ItemModelProvider {

    public ChoppedItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Chopped.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ChoppedItems.ITEMS_RAW_CHROMIUM);
        simpleItem(ChoppedItems.ITEMS_CHROMIUM_INGOT);
        simpleItem(ChoppedItems.ITEMS_SEA_SALT_PINCH);
        simpleItem(ChoppedItems.ITEMS_TOMATO);
        simpleItem(ChoppedItems.ITEMS_TOMATO_SEEDS);
        simpleItem(ChoppedItems.ITEMS_CORN);
        simpleItem(ChoppedItems.ITEMS_ONION);
        simpleItem(ChoppedItems.ITEMS_CASSETTE_WHAT_IT_DO);
        simpleItem(ChoppedItems.ITEMS_CASSETTE_BE_A_KING);
        simpleItem(ChoppedItems.ITEMS_CASSETTE_LET_ME_OHH);
        simpleItem(ChoppedItems.ITEMS_CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON);
        simpleItem(ChoppedItems.ITEMS_CASSETTE_BIG_SHIPS);
        simpleItem(ChoppedItems.ITEMS_GREEN_APPLE);
        simpleItem(ChoppedItems.ITEMS_POISONOUS_APPLE);
        simpleItem(ChoppedItems.FRUIT_BASKET);


        saplingItem(ChoppedBlocks.BLOCKS_APPLE_SAPLING);
    }

    private ItemModelBuilder simpleItem(Supplier<Item> itemRegistryObject) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(itemRegistryObject.get()).getPath(),
            new ResourceLocation("item/generated")).texture("layer0",
            new ResourceLocation(Chopped.MOD_ID, "item/" + BuiltInRegistries.ITEM.getKey(itemRegistryObject.get()).getPath()));
    }

    private ItemModelBuilder saplingItem(Supplier<Block> itemRegistryObject) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(itemRegistryObject.get()).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Chopped.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(itemRegistryObject.get()).getPath()));
    }
}
