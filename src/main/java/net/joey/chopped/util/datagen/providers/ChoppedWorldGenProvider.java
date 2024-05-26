package net.joey.chopped.util.datagen.providers;

import net.joey.chopped.Chopped;
import net.joey.chopped.world.ChoppedBiomeModifiers;
import net.joey.chopped.world.features.ChoppedConfiguredFeatures;
import net.joey.chopped.world.features.ChoppedPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ChoppedWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, ChoppedConfiguredFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, ChoppedPlacedFeatures::bootstrap)
        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ChoppedBiomeModifiers::bootstrap);

    public ChoppedWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(Chopped.MOD_ID));
    }

}