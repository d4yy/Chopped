package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.items.ChoppedBundleItem;
import net.day.chopped.items.ChoppedItem;
import net.day.chopped.items.ChoppedSeedItem;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoppedItems {
    public static final RegistryObject<Item> ITEMS_RAW_CHROMIUM = ChoppedRegistry.ITEMS.register("raw_chromium", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_CHROMIUM_INGOT = ChoppedRegistry.ITEMS.register("chromium_ingot", ChoppedItem::new);

    public static final RegistryObject<Item> ITEMS_SLAG = ChoppedRegistry.ITEMS.register("slag", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_STAINLESS_STEEL_INGOT = ChoppedRegistry.ITEMS.register("stainless_steel_ingot", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_DAMASCUS_STEEL_INGOT = ChoppedRegistry.ITEMS.register("damascus_steel_ingot", ChoppedItem::new);

    public static final RegistryObject<Item> ITEMS_CASSETTE_WHAT_IT_DO = ChoppedRegistry.ITEMS.register("cassette_what_it_do", () -> new RecordItem(16, ChoppedSounds.CASSETTE_WHAT_IT_DO, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 4920));
    public static final RegistryObject<Item> ITEMS_CASSETTE_BE_A_KING = ChoppedRegistry.ITEMS.register("cassette_be_a_king", () -> new RecordItem(17, ChoppedSounds.CASSETTE_BE_A_KING, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 4600));
    public static final RegistryObject<Item> ITEMS_CASSETTE_LET_ME_OHH = ChoppedRegistry.ITEMS.register("cassette_let_me_ohh", () -> new RecordItem(18, ChoppedSounds.CASSETTE_LET_ME_OHH, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 3300));
    public static final RegistryObject<Item> ITEMS_CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON = ChoppedRegistry.ITEMS.register("cassette_drinking_with_my_headphones_on", () -> new RecordItem(19, ChoppedSounds.CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 6000));
    public static final RegistryObject<Item> ITEMS_CASSETTE_BIG_SHIPS = ChoppedRegistry.ITEMS.register("cassette_big_ships", () -> new RecordItem(19, ChoppedSounds.CASSETTE_BIG_SHIPS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 4480));

    public static final RegistryObject<Item> FRUIT_BASKET = ChoppedRegistry.ITEMS.register("fruit_basket", () -> new ChoppedBundleItem(1280, ChoppedTags.Items.FRUIT_BASKET_ALLOWED));

    public static final RegistryObject<Item> ITEMS_GRANNY_SMITH_APPLE = ChoppedRegistry.ITEMS.register("granny_smith_apple", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_POISONOUS_APPLE = ChoppedRegistry.ITEMS.register("poisonous_apple", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_SWEET_ORANGE = ChoppedRegistry.ITEMS.register("sweet_orange", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_BITTER_ORANGE = ChoppedRegistry.ITEMS.register("bitter_orange", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_MANDARIN_ORANGE = ChoppedRegistry.ITEMS.register("mandarin_orange", ChoppedItem::new);


    public static final RegistryObject<Item> ITEMS_TOMATO = ChoppedRegistry.ITEMS.register("tomato", ChoppedItem::new);
    public static final RegistryObject<Item> ITEMS_TOMATO_SEEDS = ChoppedRegistry.ITEMS.register("tomato_seeds", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_TOMATO_CROP.get()));
    public static final RegistryObject<Item> ITEMS_CORN = ChoppedRegistry.ITEMS.register("corn", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_CORN_CROP.get()));
    public static final RegistryObject<Item> ITEMS_ONION = ChoppedRegistry.ITEMS.register("onion", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_ONION_CROP.get()));
    //public static final RegistryObject<Item> ITEMS_MAGIC_BEANS = ChoppedRegistry.ITEMS.register("magic_beans", () -> new ChoppedSeedItem(ChoppedBlocks.BLOCKS_GIANT_BEANSTALK.get()));

    public static void register() {}
}
