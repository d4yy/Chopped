package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedTabs {
    public static CreativeModeTab TAB_MAIN;

    public static void registerTabs(CreativeModeTabEvent.Register event) {
        TAB_MAIN = event.registerCreativeModeTab(new ResourceLocation(Chopped.MOD_ID, "main_tab"),builder -> builder
                .icon(() -> new ItemStack(Items.SMOKER))
                .title(Component.translatable("tabs.chopped.main_tab"))
                .displayItems((featureFlags, output, hasOp) -> {
                    for(RegistryObject<Item> i : ChoppedRegistry.ITEMS.getEntries()) {
                        output.accept(i.get());
                    }
                    for(RegistryObject<Block> i : ChoppedRegistry.BLOCKS.getEntries()) {
                        output.accept(i.get());
                    }
                })
        );
    }
}
