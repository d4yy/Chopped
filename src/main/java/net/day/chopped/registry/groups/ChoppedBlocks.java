package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.blocks.ChoppedBlock;
import net.day.chopped.blocks.ChoppedFallingBlock;
import net.day.chopped.blocks.ChoppedFlammableRotatedPillarBlock;
import net.day.chopped.blocks.crops.CultivarType;
import net.day.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.day.chopped.blocks.crops.MultiCropBlock;
import net.day.chopped.blocks.materials.ChromiumBlock;
import net.day.chopped.items.ChoppedBlockItem;
import net.day.chopped.items.ChoppedSeedItem;
import net.day.chopped.registry.ChoppedRegistry;
import net.day.chopped.world.features.trees.growers.AppleTreeGrower;
import net.day.chopped.world.features.trees.growers.OrangeTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.day.chopped.blocks.materials.ChromiumBlock.BLOCK_LIGHT_EMISSION;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoppedBlocks {

    public static final RegistryObject<Block> BLOCKS_CHROMIUM_ORE = register("chromium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(6.0F, 6.0F)));
    public static final RegistryObject<Block> BLOCKS_DEEPSLATE_CHROMIUM_ORE = register("deepslate_chromium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_ORE.get()).color(MaterialColor.DEEPSLATE).strength(8.5F, 6.0F).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> BLOCKS_RAW_CHROMIUM_BLOCK = register("raw_chromium_block", () -> new ChoppedBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_ORE.get())));

    public static final RegistryObject<Block> BLOCKS_CHROMIUM_BLOCK = register("chromium_block", () -> new ChromiumBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10.0F, 600.0F).sound(SoundType.METAL).lightLevel(s -> {
        return s.getValue(BLOCK_LIGHT_EMISSION);
    })));
    public static final RegistryObject<Block> BLOCKS_TINTED_CHROMIUM_BLOCK = register("tinted_chromium_block", () -> new ChoppedBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_BLOCK.get()).lightLevel(s -> {
        return 0;
    })));

    public static final RegistryObject<Block> BLOCKS_STAINLESS_STEEL_BLOCK = register("stainless_steel_block", () -> new ChoppedBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(4.0F, 300.0F).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> BLOCKS_DAMASCUS_STEEL_BLOCK = register("damascus_steel_block", () -> new ChoppedBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(10.0F, 600.0F).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> BLOCKS_HIMALAYAN_SALT_ORE = register("himalayan_salt_ore", () -> new ChoppedBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 1.0F).noLootTable()));
    public static final RegistryObject<Block> BLOCKS_HIMALAYAN_SALT_BLOCK = register("himalayan_salt_block", () -> new ChoppedFallingBlock(Integer.parseInt("D2BAA5", 16), BlockBehaviour.Properties.of(Material.SAND).requiresCorrectToolForDrops().strength(2.0F, 1.0F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> BLOCKS_SEA_SALT_BLOCK = register("sea_salt_block", () -> new ChoppedFallingBlock(Integer.parseInt("FFFFFF", 16), BlockBehaviour.Properties.of(Material.SAND).requiresCorrectToolForDrops().strength(1.0F, 1.0F).sound(SoundType.SAND)));

    public static final RegistryObject<Block> BLOCKS_TOMATO_CROP = registerNoItem("tomato_crop", () -> new MultiCropBlock(2));
    public static final RegistryObject<Block> BLOCKS_CORN_CROP = registerNoItem("corn_crop", () -> new MultiCropBlock(2));
    public static final RegistryObject<Block> BLOCKS_ONION_CROP = registerNoItem("onion_crop", () -> new MultiCropBlock(1));
    //public static final RegistryObject<Block> BLOCKS_GIANT_BEANSTALK = registerNoItem("giant_beanstalk_crop", () -> new ChoppedCropBlock(20));

    public static final RegistryObject<Block> BLOCKS_APPLEWOOD_LOG = register("applewood_log", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_APPLEWOOD = register("applewood", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_STRIPPED_APPLEWOOD_LOG = register("stripped_applewood_log", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_STRIPPED_APPLEWOOD = register("stripped_applewood", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_APPLEWOOD_PLANKS = register("applewood_planks", () -> new ChoppedBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(5.0F)) {
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20;
        }
    });

    public static final RegistryObject<Block> BLOCKS_CITRUS_LOG = register("citrus_log", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_CITRUS = register("citrus_wood", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_STRIPPED_CITRUS_LOG = register("stripped_citrus_log", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_STRIPPED_CITRUS = register("stripped_citrus_wood", () -> new ChoppedFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLOCKS_CITRUS_PLANKS = register("citrus_planks", () -> new ChoppedBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(5.0F)) {
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20;
        }
    });

    public static final RegistryObject<Block> BLOCKS_APPLE_SAPLING = register("apple_sapling", () -> new SaplingBlock(new AppleTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> BLOCKS_APPLE_LEAVES = register("apple_leaves", () -> new FruitBearingLeavesBlock(1, 0.001F) {
        @Override
        protected Item[] getCultivarItems() {
            return CultivarType.APPLE;
        }

        @Override
        protected Item[] getBonusItems() {
            return new Item[]{ChoppedItems.ITEMS_POISONOUS_APPLE.get()};
        }
    });
    public static final RegistryObject<Block> BLOCKS_ORANGE_SAPLING = register("orange_sapling", () -> new SaplingBlock(new OrangeTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> BLOCKS_ORANGE_LEAVES = register("orange_leaves", () -> new FruitBearingLeavesBlock(0, 0.0F) {
        @Override
        protected Item[] getCultivarItems() {
            return CultivarType.ORANGE;
        }
    });

    public static void register() {
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return ChoppedRegistry.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        ChoppedRegistry.ITEMS.register(name, () -> new ChoppedBlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    @Deprecated
    private static <T extends Block> RegistryObject<T> registerSeedItem(String name, Supplier<T> block, String iName) {
        RegistryObject<T> ret = registerNoItem(name, block);
        ChoppedRegistry.ITEMS.register(iName, () -> new ChoppedSeedItem(ret.get()));
        return ret;
    }
}
