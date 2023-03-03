package net.day.chopped.util.datagen;

import net.day.chopped.blocks.crops.ChoppedCropBlock;
import net.day.chopped.registry.ChoppedRegistry;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.day.chopped.registry.groups.ChoppedItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ChoppedBlockLootTables extends BlockLootSubProvider {
    public ChoppedBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ChoppedBlocks.BLOCKS_RAW_CHROMIUM_BLOCK.get());
        dropSelf(ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK.get());
        dropSelf(ChoppedBlocks.BLOCKS_TINTED_CHROMIUM_BLOCK.get());
        dropSelf(ChoppedBlocks.BLOCKS_STAINLESS_STEEL_BLOCK.get());
        dropSelf(ChoppedBlocks.BLOCKS_DAMASCUS_STEEL_BLOCK.get());
        dropSelf(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_BLOCK.get());
        dropSelf(ChoppedBlocks.BLOCKS_SEA_SALT_BLOCK.get());

        add(ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get(),
                block -> createOreDrop(ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get(), ChoppedItems.ITEMS_RAW_CHROMIUM.get()));
        add(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get(),
                block -> createOreDrop(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get(), ChoppedItems.ITEMS_RAW_CHROMIUM.get()));

        add(ChoppedBlocks.BLOCKS_ONION_CROP.get(),
                block -> createCropDrops(ChoppedBlocks.BLOCKS_ONION_CROP.get(), ChoppedItems.ITEMS_ONION.get(), ChoppedItems.ITEMS_ONION.get(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ChoppedBlocks.BLOCKS_ONION_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChoppedCropBlock.AGE, 7).hasProperty(ChoppedCropBlock.SECTION, "stalkbase"))));
        add(ChoppedBlocks.BLOCKS_TOMATO_CROP.get(),
                block -> createCropDrops(ChoppedBlocks.BLOCKS_TOMATO_CROP.get(), ChoppedItems.ITEMS_TOMATO.get(), ChoppedItems.ITEMS_TOMATO_SEEDS.get(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ChoppedBlocks.BLOCKS_TOMATO_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChoppedCropBlock.AGE, 7).hasProperty(ChoppedCropBlock.SECTION, "stalkbase"))));
        add(ChoppedBlocks.BLOCKS_CORN_CROP.get(),
                block -> createMultiCropDrops(ChoppedBlocks.BLOCKS_CORN_CROP.get(), ChoppedItems.ITEMS_CORN.get(), ChoppedItems.ITEMS_CORN.get(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ChoppedBlocks.BLOCKS_CORN_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChoppedCropBlock.AGE, 7).hasProperty(ChoppedCropBlock.SECTION, "stalkbase")),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ChoppedBlocks.BLOCKS_CORN_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChoppedCropBlock.AGE, 7).hasProperty(ChoppedCropBlock.SECTION, "top"))));
    }

    //2 "crop bearing" blockstates
    protected LootTable.Builder createMultiCropDrops(Block pCropBlock, Item pGrownCropItem, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition, LootItemCondition.Builder altDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem).when(pDropGrownCropCondition).when(altDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem))))
                .withPool(LootPool.lootPool().when(pDropGrownCropCondition).when(altDropGrownCropCondition).add(LootItem.lootTableItem(pSeedsItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    //3 "crop bearing" blockstates
    protected LootTable.Builder createMultiCropDrops(Block pCropBlock, Item pGrownCropItem, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition, LootItemCondition.Builder altDropGrownCropCondition, LootItemCondition.Builder secondAltDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem).when(pDropGrownCropCondition).when(altDropGrownCropCondition).when(secondAltDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem))))
                .withPool(LootPool.lootPool().when(pDropGrownCropCondition).when(altDropGrownCropCondition).when(secondAltDropGrownCropCondition).add(LootItem.lootTableItem(pSeedsItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ChoppedRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
