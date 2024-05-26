package net.joey.chopped.items;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.component.BundleContents;

public record ChoppedBundleTooltip(ChoppedBundleContents contents) implements TooltipComponent {
}
