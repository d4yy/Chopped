package net.joey.chopped.items;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ChoppedBlockItem extends BlockItem {
    public ChoppedBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (I18n.exists(getDescriptionId(pStack) + ".tooltip")) {
            pTooltipComponents.add(Component.translatable(getDescriptionId(pStack) + ".tooltip"));
        }
        this.getBlock().appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
