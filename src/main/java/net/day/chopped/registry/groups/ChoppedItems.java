package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.items.ChoppedItem;
import net.day.chopped.items.ChoppedSeedItem;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoppedItems {
    public static final RegistryObject<Item> ITEMS_RAW_CHROMIUM = ChoppedRegistry.ITEMS.register("raw_chromium", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_CHROMIUM_INGOT = ChoppedRegistry.ITEMS.register("chromium_ingot", ChoppedItem::new);

    public static final RegistryObject<Item> ITEMS_STAINLESS_STEEL_INGOT = ChoppedRegistry.ITEMS.register("stainless_steel_ingot", ChoppedItem::new);

    public static final RegistryObject<Item> ITEMS_TOMATO = ChoppedRegistry.ITEMS.register("tomato", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_TOMATO_SEEDS = ChoppedRegistry.ITEMS.register("tomato_seeds", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCK_TOMATO_CROP.get()));

    public static final RegistryObject<Item> ITEMS_CORN = ChoppedRegistry.ITEMS.register("corn", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCK_CORN_CROP.get()));
    public static final RegistryObject<Item> ITEMS_ONION = ChoppedRegistry.ITEMS.register("onion", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCK_ONION_CROP.get()));

    public static void register() {}
}
