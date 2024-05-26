package net.joey.chopped.util.datagen.providers;

import net.joey.chopped.util.datagen.ChoppedBlockLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


public class ChoppedLootTableProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> pRegistries) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ChoppedBlockLootTables::new, LootContextParamSets.BLOCK)), pRegistries);
    }
}
