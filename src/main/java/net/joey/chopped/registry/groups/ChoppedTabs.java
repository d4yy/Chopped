package net.joey.chopped.registry.groups;

import net.joey.chopped.registry.ChoppedRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class ChoppedTabs {
    //public static CreativeModeTab TAB_MAIN;

    public static final Supplier<CreativeModeTab> TAB_MAIN = ChoppedRegistry.CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder().title(Component.translatable("tabs.chopped.main_tab")).icon(() -> new ItemStack(Items.SMOKER)).displayItems((pParameters, pOutput) -> {
        for(var i : ChoppedRegistry.ITEMS.getEntries()) {
            pOutput.accept(i.get());
        }
        /*
        for(var i : ChoppedRegistry.BLOCKS.getEntries()) {
            pOutput.accept(i.get());
        }*/
    }).build());

    public static void register() {}

}
