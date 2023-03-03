package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedBlockStateProvider extends BlockStateProvider {

    public ChoppedBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Chopped.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ChoppedBlocks.BLOCKS_RAW_CHROMIUM_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_TINTED_CHROMIUM_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_STAINLESS_STEEL_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_DAMASCUS_STEEL_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_SEA_SALT_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_CHROMIUM_ORE);
        blockWithItem(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE);
        blockWithItem(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_ORE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
