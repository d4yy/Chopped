package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.blocks.ChoppedBlock;
import net.day.chopped.blocks.ChoppedFallingBlock;
import net.day.chopped.blocks.crops.ChoppedCropBlock;
import net.day.chopped.items.ChoppedItem;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoppedBlocks {

    //ORE BLOCKS

    public static final RegistryObject<Block> BLOCKS_CHROMIUM_ORE = register("chromium_ore", () ->
            new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
            )
    );

    public static final RegistryObject<Block> BLOCKS_DEEPSLATE_CHROMIUM_ORE = register("deepslate_chromium_ore", () ->
            new OreBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_ORE.get())
                    .color(MaterialColor.DEEPSLATE)
                    .strength(4.5F, 3.0F)
                    .sound(SoundType.DEEPSLATE)
            )
    );

    public static final RegistryObject<Block> BLOCKS_RAW_CHROMIUM_BLOCK = register("raw_chromium_block", () ->
            new ChoppedBlock(BlockBehaviour.Properties.copy(BLOCKS_CHROMIUM_ORE.get())
            )
    );



    public static final RegistryObject<Block> BLOCKS_HIMALAYAN_SALT_ORE = register("himalayan_salt_ore", () ->
            new ChoppedBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F, 1.0F)
            )
    );

    public static final RegistryObject<Block> BLOCKS_SEA_SALT = register("sea_salt", () ->
            new ChoppedFallingBlock(16777215, BlockBehaviour.Properties.of(Material.SAND)
                    .requiresCorrectToolForDrops()
                    .strength(1.0F, 1.0F)
                    .sound(SoundType.SAND)
            )
    );

    public static final RegistryObject<Block> BLOCK_TEST_CROP_4 = register("test_crop", () ->
            new ChoppedCropBlock(4, Items.IRON_INGOT)
    );



    public static void register() {}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return ChoppedRegistry.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        ChoppedRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(ChoppedTabs.CHOPPED_TAB)));
        return ret;
    }
}
