package net.joey.chopped.registry.groups;

import net.joey.chopped.blocks.ChoppedBlock;
import net.joey.chopped.blocks.ChoppedFallingBlock;
import net.joey.chopped.blocks.crops.CultivarType;
import net.joey.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.joey.chopped.blocks.crops.MultiCropBlock;
import net.joey.chopped.blocks.materials.ChromiumBlock;
import net.joey.chopped.items.ChoppedBlockItem;
import net.joey.chopped.items.ChoppedSeedItem;
import net.joey.chopped.registry.ChoppedRegistry;
import net.joey.chopped.world.features.ChoppedConfiguredFeatures;
import net.joey.chopped.world.features.trees.ChoppedTreeGrower;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.Optional;
import java.util.function.Supplier;

import static net.joey.chopped.blocks.materials.ChromiumBlock.BLOCK_LIGHT_EMISSION;
import static net.joey.chopped.registry.ChoppedRegistry.BLOCKS;

public class ChoppedBlocks {

    public static final Supplier<Block> BLOCKS_CHROMIUM_ORE = register("chromium_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(6.0F, 6.0F)));
    public static final Supplier<Block> BLOCKS_DEEPSLATE_CHROMIUM_ORE = register("deepslate_chromium_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(BLOCKS_CHROMIUM_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(8.5F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final Supplier<Block> BLOCKS_RAW_CHROMIUM_BLOCK = register("raw_chromium_block", () -> new ChoppedBlock(BlockBehaviour.Properties.ofFullCopy(BLOCKS_CHROMIUM_ORE.get())));

    public static final Supplier<Block> BLOCKS_CHROMIUM_BLOCK = register("chromium_block", () -> new ChromiumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(10.0F, 600.0F).sound(SoundType.METAL).lightLevel(s -> s.getValue(BLOCK_LIGHT_EMISSION))));
    public static final Supplier<Block> BLOCKS_TINTED_CHROMIUM_BLOCK = register("tinted_chromium_block", () -> new ChoppedBlock(BlockBehaviour.Properties.ofFullCopy(BLOCKS_CHROMIUM_BLOCK.get()).lightLevel(s -> 0)));

    public static final Supplier<Block> BLOCKS_STAINLESS_STEEL_BLOCK = register("stainless_steel_block", () -> new ChoppedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(4.0F, 300.0F).sound(SoundType.COPPER)));
    public static final Supplier<Block> BLOCKS_DAMASCUS_STEEL_BLOCK = register("damascus_steel_block", () -> new ChoppedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(10.0F, 600.0F).sound(SoundType.NETHERITE_BLOCK)));
    public static final Supplier<Block> BLOCKS_HIMALAYAN_SALT_ORE = register("himalayan_salt_ore", () -> new ChoppedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 1.0F).noLootTable()));
    public static final Supplier<Block> BLOCKS_HIMALAYAN_SALT = register("himalayan_salt", () -> new ChoppedFallingBlock(Integer.parseInt("D2BAA5", 16), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.0F).sound(SoundType.SAND)));
    public static final Supplier<Block> BLOCKS_SEA_SALT = register("sea_salt", () -> new ChoppedFallingBlock(Integer.parseInt("FFFFFF", 16), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.0F).sound(SoundType.SAND)));

    public static final Supplier<Block> BLOCKS_TOMATO_CROP = registerNoItem("tomato_crop", () -> new MultiCropBlock(2));
    public static final Supplier<Block> BLOCKS_CORN_CROP = registerNoItem("corn_crop", () -> new MultiCropBlock(2));
    public static final Supplier<Block> BLOCKS_ONION_CROP = registerNoItem("onion_crop", () -> new MultiCropBlock(1));
    //public static final Supplier<Block> BLOCKS_GIANT_BEANSTALK = registerNoItem("giant_beanstalk_crop", () -> new ChoppedCropBlock(20));

    public static final ChoppedTreeGrower APPLE_GROWER = new ChoppedTreeGrower("apple_grower", Optional.of(ChoppedConfiguredFeatures.APPLE_TREE_RED), Optional.of(ChoppedConfiguredFeatures.APPLE_TREE_GREEN));

    public static final Supplier<Block> BLOCKS_APPLE_SAPLING = register("apple_sapling", () -> new SaplingBlock(APPLE_GROWER, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final Supplier<Block> BLOCKS_APPLEWOOD_LOG = register("applewood_log", () -> log(MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_ORANGE));
    public static final Supplier<Block> BLOCKS_APPLEWOOD = register("applewood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(2.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> BLOCKS_STRIPPED_APPLEWOOD_LOG = register("stripped_applewood_log", () -> log(MapColor.TERRACOTTA_ORANGE, MapColor.TERRACOTTA_ORANGE));
    public static final Supplier<Block> BLOCKS_STRIPPED_APPLEWOOD = register("stripped_applewood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(2.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> BLOCKS_APPLEWOOD_PLANKS = register("applewood_planks", () -> new ChoppedBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> BLOCKS_APPLE_LEAVES = register("apple_leaves", () -> new FruitBearingLeavesBlock(1, 0.001F) {
        @Override
        public Item[] getCultivarItems() {
            return CultivarType.APPLE;
        }

        @Override
        public Item[] getBonusItems() {
            return new Item[]{ChoppedItems.ITEMS_POISONOUS_APPLE.get()};
        }
    });

    public static final ChoppedTreeGrower ORANGE_GROWER = new ChoppedTreeGrower("apple_grower", Optional.of(ChoppedConfiguredFeatures.ORANGE_TREE_SWEET), Optional.of(ChoppedConfiguredFeatures.ORANGE_TREE_BITTER), Optional.of(ChoppedConfiguredFeatures.ORANGE_TREE_MANDARIN));

    public static final Supplier<Block> BLOCKS_ORANGE_SAPLING = register("orange_sapling", () -> new SaplingBlock(ORANGE_GROWER, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final Supplier<Block> BLOCKS_CITRUS_LOG = register("citrus_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final Supplier<Block> BLOCKS_CITRUS_WOOD = register("citrus_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final Supplier<Block> BLOCKS_STRIPPED_CITRUS_LOG = register("stripped_citrus_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final Supplier<Block> BLOCKS_STRIPPED_CITRUS_WOOD = register("stripped_citrus_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final Supplier<Block> BLOCKS_CITRUS_PLANKS = register("citrus_planks", () -> new ChoppedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> BLOCKS_ORANGE_LEAVES = register("orange_leaves", () -> new FruitBearingLeavesBlock(0, 0.0F) {
        @Override
        public Item[] getCultivarItems() {
            return CultivarType.ORANGE;
        }
    });

    public static void register() {
    }

    private static RotatedPillarBlock log(MapColor pTopColor, MapColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor).strength(2.0F).sound(SoundType.WOOD));
    }

    private static <T extends Block> Supplier<T> registerNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> Supplier<T> register(String name, Supplier<T> block) {
        Supplier<T> ret = registerNoItem(name, block);
        ChoppedRegistry.ITEMS.register(name, () -> new ChoppedBlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    @Deprecated
    private static <T extends Block> Supplier<T> registerSeedItem(String name, Supplier<T> block, String iName) {
        Supplier<T> ret = registerNoItem(name, block);
        ChoppedRegistry.ITEMS.register(iName, () -> new ChoppedSeedItem(ret.get()));
        return ret;
    }
}
