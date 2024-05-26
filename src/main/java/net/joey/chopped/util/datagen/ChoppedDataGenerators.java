package net.joey.chopped.util.datagen;

import net.joey.chopped.Chopped;
import net.joey.chopped.util.datagen.providers.*;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Chopped.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ChoppedDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        CompletableFuture<HolderLookup.Provider> lookupProvider2 = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());
        BlockTagsProvider blockTagsProvider = new ChoppedBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        generator.addProvider(true, blockTagsProvider);
        generator.addProvider(true, new ChoppedItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        //generator.addProvider(true, new ChoppedRecipeProvider(packOutput));
        generator.addProvider(true, ChoppedLootTableProvider.create(packOutput, lookupProvider));
        generator.addProvider(true, new ChoppedItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ChoppedBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ChoppedSoundDefinitionsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ChoppedWorldGenProvider(packOutput, lookupProvider));
    }
}
