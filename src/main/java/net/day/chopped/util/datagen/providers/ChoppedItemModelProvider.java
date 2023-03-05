package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.day.chopped.registry.groups.ChoppedItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedItemModelProvider extends ItemModelProvider {

    public ChoppedItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Chopped.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ChoppedItems.ITEMS_RAW_CHROMIUM);
        simpleItem(ChoppedItems.ITEMS_CHROMIUM_INGOT);
        simpleItem(ChoppedItems.ITEMS_SLAG);
        simpleItem(ChoppedItems.ITEMS_STAINLESS_STEEL_INGOT);
        simpleItem(ChoppedItems.ITEMS_DAMASCUS_STEEL_INGOT);
        simpleItem(ChoppedItems.ITEMS_TOMATO);
        simpleItem(ChoppedItems.ITEMS_TOMATO_SEEDS);
        simpleItem(ChoppedItems.ITEMS_CORN);
        simpleItem(ChoppedItems.ITEMS_ONION);

        saplingItem(ChoppedBlocks.BLOCKS_APPLE_SAPLING);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> itemRegistryObject) {
        return withExistingParent(itemRegistryObject.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Chopped.MOD_ID, "item/" + itemRegistryObject.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> itemRegistryObject) {
        return withExistingParent(itemRegistryObject.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Chopped.MOD_ID, "block/" + itemRegistryObject.getId().getPath()));
    }
}
