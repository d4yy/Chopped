package net.day.chopped.items;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class ChoppedBlockItem extends BlockItem {
    public ChoppedBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (I18n.exists(getDescriptionId(pStack) + ".tooltip")) {
            pTooltip.add(Component.translatable(getDescriptionId(pStack) + ".tooltip"));
        }
        this.getBlock().appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
