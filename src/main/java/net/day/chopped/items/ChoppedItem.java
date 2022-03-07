package net.day.chopped.items;

import net.day.chopped.registry.groups.ChoppedTabs;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ChoppedItem extends Item {
    public ChoppedItem() {
        this(new Item.Properties());
    }

    public ChoppedItem(Item.Properties properties) {
        super(properties.tab(ChoppedTabs.CHOPPED_TAB));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (I18n.exists(getDescriptionId(pStack) + ".tooltip")) {
            pTooltipComponents.add(new TranslatableComponent(getDescriptionId(pStack) + ".tooltip"));
        }
    }
}
