package net.day.chopped.registry.groups;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChoppedTabs {
    public static final CreativeModeTab CHOPPED_TAB = new CreativeModeTab("choppedTab")
    {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.SMOKER);
        }
    };
}
