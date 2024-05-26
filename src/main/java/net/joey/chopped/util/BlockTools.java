package net.joey.chopped.util;

import com.google.common.collect.Maps;
import net.joey.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class BlockTools {

    public static void registerBlockActions() {
        //Vanilla uses FireBlock#bootStrap
        addFlammableBlock(ChoppedBlocks.BLOCKS_APPLEWOOD_PLANKS.get(), 5, 20);
        addFlammableBlock(ChoppedBlocks.BLOCKS_CITRUS_PLANKS.get(), 5, 20);
        addFlammableBlock(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_CITRUS_LOG.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_STRIPPED_CITRUS_LOG.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_STRIPPED_CITRUS_WOOD.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_APPLEWOOD.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_CITRUS_WOOD.get(), 5, 5);
        addFlammableBlock(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get(), 30, 60);
        addFlammableBlock(ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get(), 30, 60);

        //Vanilla uses AxeItem STRIPPABLES field
        addStrippableBlock(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get(), ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get());
        addStrippableBlock(ChoppedBlocks.BLOCKS_APPLEWOOD.get(), ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get());
        addStrippableBlock(ChoppedBlocks.BLOCKS_CITRUS_LOG.get(), ChoppedBlocks.BLOCKS_STRIPPED_CITRUS_LOG.get());
        addStrippableBlock(ChoppedBlocks.BLOCKS_CITRUS_WOOD.get(), ChoppedBlocks.BLOCKS_STRIPPED_CITRUS_WOOD.get());
    }

    public static void addFlammableBlock(Block block, int flammability, int speed) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, flammability, speed); //AT
    }

    public static void addStrippableBlock(Block block, Block stripped) {
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES); //AT
        AxeItem.STRIPPABLES.put(block, stripped);
    }
}
