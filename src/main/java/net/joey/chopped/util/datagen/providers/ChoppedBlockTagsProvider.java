package net.joey.chopped.util.datagen.providers;

import net.joey.chopped.Chopped;
import net.joey.chopped.registry.groups.ChoppedBlocks;
import net.joey.chopped.registry.groups.ChoppedTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ChoppedBlockTagsProvider extends BlockTagsProvider {
    public ChoppedBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Chopped.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ChoppedTags.Blocks.APPLEWOOD_LOGS).add(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get(), ChoppedBlocks.BLOCKS_APPLEWOOD.get(), ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get(), ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get());
        tag(ChoppedTags.Blocks.CITRUS_LOGS).add(ChoppedBlocks.BLOCKS_CITRUS_LOG.get(), ChoppedBlocks.BLOCKS_CITRUS_WOOD.get(), ChoppedBlocks.BLOCKS_STRIPPED_CITRUS_LOG.get(), ChoppedBlocks.BLOCKS_STRIPPED_CITRUS_WOOD.get());

        //Vanilla Tag Additions
        tag(BlockTags.LOGS_THAT_BURN).addTags(ChoppedTags.Blocks.APPLEWOOD_LOGS, ChoppedTags.Blocks.CITRUS_LOGS);
        tag(BlockTags.LEAVES).add(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get(), ChoppedBlocks.BLOCKS_ORANGE_LEAVES.get());

        tag(BlockTags.BEACON_BASE_BLOCKS).add(ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK.get());

        //Tool Tiers
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get(), ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get());

        //Tool Types
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(
                ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get(),
                ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get()
            );

        tag(BlockTags.MINEABLE_WITH_SHOVEL).
            add(
              ChoppedBlocks.BLOCKS_SEA_SALT.get()
            );
    }
}
