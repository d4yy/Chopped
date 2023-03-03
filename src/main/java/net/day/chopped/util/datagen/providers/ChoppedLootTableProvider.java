package net.day.chopped.util.datagen.providers;

import net.day.chopped.util.datagen.ChoppedBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ChoppedLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ChoppedBlockLootTables::new, LootContextParamSets.BLOCK)));
    }
}
