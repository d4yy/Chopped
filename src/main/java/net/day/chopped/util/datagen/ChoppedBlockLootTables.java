package net.day.chopped.util.datagen;

import net.day.chopped.blocks.crops.MultiCropBlock;
import net.day.chopped.blocks.crops.CropSectionProperty;
import net.day.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.day.chopped.registry.ChoppedRegistry;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.day.chopped.registry.groups.ChoppedItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ChoppedBlockLootTables extends BlockLootSubProvider {
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

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
        dropSelf(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get());
        dropSelf(ChoppedBlocks.BLOCKS_APPLEWOOD.get());
        dropSelf(ChoppedBlocks.BLOCKS_APPLEWOOD_PLANKS.get());
        dropSelf(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get());
        dropSelf(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get());
        dropSelf(ChoppedBlocks.BLOCKS_APPLE_SAPLING.get());

        add(ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get(),
            block -> createOreDrop(ChoppedBlocks.BLOCKS_CHROMIUM_ORE.get(), ChoppedItems.ITEMS_RAW_CHROMIUM.get())
        );
        add(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get(),
            block -> createOreDrop(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE.get(), ChoppedItems.ITEMS_RAW_CHROMIUM.get())
        );

        add(ChoppedBlocks.BLOCKS_ONION_CROP.get(),
            block -> createMultiCropDrops(ChoppedBlocks.BLOCKS_ONION_CROP.get(), ChoppedItems.ITEMS_ONION.get(), ChoppedItems.ITEMS_ONION.get(), 1.0F, 3.0F, 0.0F)
        );
        add(ChoppedBlocks.BLOCKS_TOMATO_CROP.get(),
            block -> createMultiCropDrops(ChoppedBlocks.BLOCKS_TOMATO_CROP.get(), ChoppedItems.ITEMS_TOMATO.get(), ChoppedItems.ITEMS_TOMATO_SEEDS.get(), 1.0F, 3.0F, 0.0F)
        );
        add(ChoppedBlocks.BLOCKS_CORN_CROP.get(),
            block -> createMultiCropDrops(ChoppedBlocks.BLOCKS_CORN_CROP.get(), ChoppedItems.ITEMS_CORN.get(), ChoppedItems.ITEMS_CORN.get(), 1.0F, 2.0F, 0.0F)
        );

        add(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get(),
            block -> createFruitBearingLeavesDrops(ChoppedBlocks.BLOCKS_APPLE_LEAVES.get(), ChoppedBlocks.BLOCKS_APPLE_SAPLING.get(), Items.APPLE, NORMAL_LEAVES_SAPLING_CHANCES)
        );
    }

    protected LootTable.Builder createMultiCropDrops(Block cropBlock, Item yieldItem, Item seedItem, Float minYield, Float maxYield, Float bonusChance, Item... bonusItem) {
        LootItemCondition.Builder sufficientAgePropertyCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MultiCropBlock.AGE, 7));
        LootItemCondition.Builder stalkBasePropertyCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MultiCropBlock.SECTION, CropSectionProperty.STALKBASE));

        LootTable.Builder table = LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(yieldItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(minYield, maxYield))).when(sufficientAgePropertyCondition).otherwise(LootItem.lootTableItem(seedItem).when(stalkBasePropertyCondition)))
            ).withPool(LootPool.lootPool().when(sufficientAgePropertyCondition)
                .add(LootItem.lootTableItem(seedItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)).when(stalkBasePropertyCondition))
            );

        if (bonusItem.length != 0) {
            for (Item bonus : bonusItem) {
                table.withPool(LootPool.lootPool().add(LootItem.lootTableItem(bonus)).setRolls(ConstantValue.exactly(bonusChance)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).when(sufficientAgePropertyCondition)));
            }
        }

        return this.applyExplosionDecay(cropBlock, table);
    }

    protected LootTable.Builder createFruitBearingLeavesDrops(Block pLeavesBlock, Block pSaplingBlock, Item yieldItem, float... pChances) {
        return this.createLeavesDrops(pLeavesBlock, pSaplingBlock, pChances).withPool(
            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(
                pLeavesBlock, LootItem.lootTableItem(yieldItem)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pLeavesBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FruitBearingLeavesBlock.FRUIT_BEARING, true))).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
            )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ChoppedRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
