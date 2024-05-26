package net.joey.chopped.registry;

import net.joey.chopped.Chopped;
import net.joey.chopped.registry.groups.*;
import net.joey.chopped.world.features.ChoppedConfiguredFeatures;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ChoppedRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Chopped.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Chopped.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Chopped.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Chopped.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Chopped.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Chopped.MOD_ID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Chopped.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        SOUND_EVENTS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        FEATURES.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
        DATA_COMPONENTS.register(eventBus);

        ChoppedBlocks.register();
        ChoppedItems.register();
        ChoppedSounds.register();
        ChoppedBlockEntities.register();
        ChoppedConfiguredFeatures.register();
        ChoppedTabs.register();
        ChoppedDataComponents.register();
    }
}
