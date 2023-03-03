package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.blocks.ChoppedBlock;
import net.day.chopped.blocks.ChoppedFallingBlock;
import net.day.chopped.blocks.crops.ChoppedCropBlock;
import net.day.chopped.blocks.materials.ChromiumBlock;
import net.day.chopped.items.ChoppedBlockItem;
import net.day.chopped.items.ChoppedSeedItem;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.day.chopped.blocks.materials.ChromiumBlock.BLOCK_LIGHT_EMISSION;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoppedBlocks {

    //CHROMIUM

    public static final RegistryObject<Block> BLOCKS_CHROMIUM_ORE = register("chromium_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F, 6.0F)
            )
    );

    public static final RegistryObject<Block> BLOCKS_DEEPSLATE_CHROMIUM_ORE = register("deepslate_chromium_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_ORE.get())
                    .color(MaterialColor.DEEPSLATE)
                    .strength(8.5F, 6.0F)
                    .sound(SoundType.DEEPSLATE)
            )
    );

    public static final RegistryObject<Block> BLOCKS_RAW_CHROMIUM_BLOCK = register("raw_chromium_block", () ->
            new ChoppedBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_ORE.get())
            )
    );

    public static final RegistryObject<Block> BLOCKS_CHROMIUM_BLOCK = register("chromium_block", () ->
            new ChromiumBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(10.0F, 600.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(s -> s.getValue(BLOCK_LIGHT_EMISSION))
            )
    );

    public static final RegistryObject<Block> BLOCKS_TINTED_CHROMIUM_BLOCK = register("tinted_chromium_block", () ->
            new ChoppedBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_BLOCK.get())
                    .lightLevel(s -> 0)
            )
    );

    public static final RegistryObject<Block> BLOCKS_STAINLESS_STEEL_BLOCK = register("stainless_steel_block", () ->
            new ChoppedBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 300.0F)
                    .sound(SoundType.COPPER)
            )
    );

    public static final RegistryObject<Block> BLOCKS_DAMASCUS_STEEL_BLOCK = register("damascus_steel_block", () ->
            new ChoppedBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(10.0F, 600.0F)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
    );



    //SALT

    public static final RegistryObject<Block> BLOCKS_HIMALAYAN_SALT_ORE = register("himalayan_salt_ore", () ->
            new ChoppedBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F, 1.0F)
                    .noLootTable()
            )
    );

    public static final RegistryObject<Block> BLOCKS_HIMALAYAN_SALT_BLOCK = register("himalayan_salt_block", () ->
            new ChoppedFallingBlock(Integer.parseInt("D2BAA5", 16), BlockBehaviour.Properties.of(Material.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F, 1.0F)
                    .sound(SoundType.SAND)
            )
    );

    public static final RegistryObject<Block> BLOCKS_SEA_SALT_BLOCK = register("sea_salt_block", () ->
            new ChoppedFallingBlock(Integer.parseInt("FFFFFF", 16), BlockBehaviour.Properties.of(Material.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(1.0F, 1.0F)
                    .sound(SoundType.SAND)
            )
    );



    //CROPS

    public static final RegistryObject<Block> BLOCKS_TOMATO_CROP = registerNoItem("tomato_crop", () ->
            new ChoppedCropBlock(2)
    );

    public static final RegistryObject<Block> BLOCKS_CORN_CROP = registerNoItem("corn_crop", () ->
            new ChoppedCropBlock(2)
    );
    public static final RegistryObject<Block> BLOCKS_ONION_CROP = registerNoItem("onion_crop", () ->
            new ChoppedCropBlock(1)
    );

    /*public static final RegistryObject<Block> BLOCKS_GIANT_BEANSTALK = registerNoItem("giant_beanstalk_crop", () ->
            new ChoppedCropBlock(20)
    );*/



    //REGISTRY

    public static void register() {}

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
