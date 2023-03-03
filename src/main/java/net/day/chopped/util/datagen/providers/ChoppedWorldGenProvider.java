package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.world.features.ChoppedConfiguredFeatures;
import net.day.chopped.world.features.ChoppedPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ChoppedWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ChoppedConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ChoppedPlacedFeatures::bootstrap);

    public ChoppedWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(Chopped.MOD_ID));
    }

}
