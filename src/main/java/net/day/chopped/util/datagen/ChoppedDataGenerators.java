package net.day.chopped.util.datagen;

import net.day.chopped.Chopped;
import net.day.chopped.util.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChoppedDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        BlockTagsProvider blockTagsProvider = new ChoppedBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        generator.addProvider(true, blockTagsProvider);
        generator.addProvider(true, new ChoppedItemTagsProvider(packOutput, lookupProvider, blockTagsProvider, existingFileHelper));
        generator.addProvider(true, new ChoppedRecipeProvider(packOutput));
        generator.addProvider(true, ChoppedLootTableProvider.create(packOutput));
        generator.addProvider(true, new ChoppedItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ChoppedBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ChoppedSoundDefinitionsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ChoppedWorldGenProvider(packOutput, lookupProvider));
    }
}
