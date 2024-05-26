package net.joey.chopped.registry.groups;

import net.joey.chopped.items.ChoppedBundleContents;
import net.joey.chopped.items.ChoppedBundleItem;
import net.joey.chopped.items.ChoppedItem;
import net.joey.chopped.items.ChoppedSeedItem;
import net.joey.chopped.registry.ChoppedRegistry;
    import net.minecraft.world.item.Item;
    import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import org.apache.commons.lang3.math.Fraction;

import java.util.List;
import java.util.function.Supplier;

public class ChoppedItems {
    public static final Supplier<Item> ITEMS_RAW_CHROMIUM = ChoppedRegistry.ITEMS.register("raw_chromium", () -> new ChoppedItem());
    public static final Supplier<Item> ITEMS_CHROMIUM_INGOT = ChoppedRegistry.ITEMS.register("chromium_ingot", () -> new ChoppedItem());

    public static final Supplier<Item> ITEMS_SEA_SALT_PINCH = ChoppedRegistry.ITEMS.register("sea_salt_pinch", () -> new ChoppedItem());

    public static final Supplier<Item> ITEMS_CASSETTE_WHAT_IT_DO = ChoppedRegistry.ITEMS.register("cassette_what_it_do", () -> new RecordItem(0, ChoppedSounds.CASSETTE_WHAT_IT_DO, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 4920));
    public static final Supplier<Item> ITEMS_CASSETTE_BE_A_KING = ChoppedRegistry.ITEMS.register("cassette_be_a_king", () -> new RecordItem(1, ChoppedSounds.CASSETTE_BE_A_KING, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 4600));
    public static final Supplier<Item> ITEMS_CASSETTE_LET_ME_OHH = ChoppedRegistry.ITEMS.register("cassette_let_me_ohh", () -> new RecordItem(2, ChoppedSounds.CASSETTE_LET_ME_OHH, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 3300));
    public static final Supplier<Item> ITEMS_CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON = ChoppedRegistry.ITEMS.register("cassette_drinking_with_my_headphones_on", () -> new RecordItem(3, ChoppedSounds.CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 6000));
    public static final Supplier<Item> ITEMS_CASSETTE_BIG_SHIPS = ChoppedRegistry.ITEMS.register("cassette_big_ships", () -> new RecordItem(4, ChoppedSounds.CASSETTE_BIG_SHIPS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 4480));

    public static final Supplier<Item> FRUIT_BASKET = ChoppedRegistry.ITEMS.register("fruit_basket", () ->
        new ChoppedBundleItem(7, ChoppedTags.Items.FRUIT_BASKET_ALLOWED,
            new Item.Properties().stacksTo(1).component(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), new ChoppedBundleContents(List.of(), Fraction.ZERO, 7, ChoppedTags.Items.FRUIT_BASKET_ALLOWED))
        )
    );

    public static final Supplier<Item> ITEMS_GREEN_APPLE = ChoppedRegistry.ITEMS.register("green_apple", () -> new ChoppedItem());
    public static final Supplier<Item> ITEMS_POISONOUS_APPLE = ChoppedRegistry.ITEMS.register("poisonous_apple", () -> new ChoppedItem());
    public static final Supplier<Item> ITEMS_SWEET_ORANGE = ChoppedRegistry.ITEMS.register("sweet_orange", () -> new ChoppedItem());
    public static final Supplier<Item> ITEMS_BITTER_ORANGE = ChoppedRegistry.ITEMS.register("bitter_orange", () -> new ChoppedItem());
    public static final Supplier<Item> ITEMS_MANDARIN_ORANGE = ChoppedRegistry.ITEMS.register("mandarin_orange", () -> new ChoppedItem());


    public static final Supplier<Item> ITEMS_TOMATO = ChoppedRegistry.ITEMS.register("tomato", () -> new ChoppedItem());
    public static final Supplier<Item> ITEMS_TOMATO_SEEDS = ChoppedRegistry.ITEMS.register("tomato_seeds", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_TOMATO_CROP.get()));
    public static final Supplier<Item> ITEMS_CORN = ChoppedRegistry.ITEMS.register("corn", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_CORN_CROP.get()));
    public static final Supplier<Item> ITEMS_ONION = ChoppedRegistry.ITEMS.register("onion", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_ONION_CROP.get()));
    //public static final RegistryObject<Item> ITEMS_MAGIC_BEANS = ChoppedRegistry.ITEMS.register("magic_beans", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_GIANT_BEANSTALK.get()));

    public static void register() {}
}
