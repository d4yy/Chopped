package net.joey.chopped.items;

import net.joey.chopped.registry.groups.ChoppedDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

//@EventBusSubscriber(modid = Chopped.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ChoppedBundleItem extends Item {
    private static final int BAR_COLOR = Mth.color(1.0F, 0.843F, 0.0F);
    public static int maxStacks = 1;
    private static TagKey<?> tagWhitelist;

    public ChoppedBundleItem(int stackSize, TagKey<?> whitelist, Item.Properties pProperties) {
        super(pProperties);
        maxStacks = stackSize;
        tagWhitelist = whitelist;
    }

    public static float getFullnessDisplay(ItemStack pStack) {
        ChoppedBundleContents bundlecontents = pStack.getOrDefault(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), ChoppedBundleContents.EMPTY);
        return bundlecontents.weight().floatValue();
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ChoppedBundleContents bundlecontents = pStack.get(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get());
            if (bundlecontents == null) {
                return false;
            } else {
                ItemStack itemstack = pSlot.getItem();
                ChoppedBundleContents.Mutable bundlecontents$mutable = new ChoppedBundleContents.Mutable(bundlecontents);
                if (itemstack.isEmpty()) {
                    this.playRemoveOneSound(pPlayer);
                    ItemStack itemstack1 = bundlecontents$mutable.removeOne();
                    if (itemstack1 != null) {
                        ItemStack itemstack2 = pSlot.safeInsert(itemstack1);
                        bundlecontents$mutable.tryInsert(itemstack2);
                    }
                } else if (itemstack.getItem().canFitInsideContainerItems()) {
                    int i = bundlecontents$mutable.tryTransfer(pSlot, pPlayer);
                    if (i > 0) {
                        this.playInsertSound(pPlayer);
                    }
                }

                pStack.set(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), bundlecontents$mutable.toImmutable());
                return true;
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(
        ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess
    ) {
        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            ChoppedBundleContents bundlecontents = pStack.get(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get());
            if (bundlecontents == null) {
                return false;
            } else {
                ChoppedBundleContents.Mutable bundlecontents$mutable = new ChoppedBundleContents.Mutable(bundlecontents);
                if (pOther.isEmpty()) {
                    ItemStack itemstack = bundlecontents$mutable.removeOne();
                    if (itemstack != null) {
                        this.playRemoveOneSound(pPlayer);
                        pAccess.set(itemstack);
                    }
                } else {
                    int i = bundlecontents$mutable.tryInsert(pOther);
                    if (i > 0) {
                        this.playInsertSound(pPlayer);
                    }
                }

                pStack.set(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), bundlecontents$mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (dropContents(itemstack, pPlayer)) {
            this.playDropContentsSound(pPlayer);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack pStack) {
        ChoppedBundleContents bundlecontents = pStack.getOrDefault(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), ChoppedBundleContents.EMPTY);
        return bundlecontents.weight().compareTo(Fraction.ZERO) > 0;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        ChoppedBundleContents bundlecontents = pStack.getOrDefault(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), ChoppedBundleContents.EMPTY);
        return Math.min(1 + Mth.mulAndTruncate(bundlecontents.weight(), 12), 13);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return BAR_COLOR;
    }

    private static boolean dropContents(ItemStack pStack, Player pPlayer) {
        ChoppedBundleContents bundlecontents = pStack.get(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get());
        if (bundlecontents != null && !bundlecontents.isEmpty()) {
            pStack.set(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), ChoppedBundleContents.EMPTY);
            if (pPlayer instanceof ServerPlayer) {
                bundlecontents.itemsCopy().forEach(p_330078_ -> pPlayer.drop(p_330078_, true));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
        return !pStack.has(DataComponents.HIDE_TOOLTIP) && !pStack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
            ? Optional.ofNullable(pStack.get(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get())).map(ChoppedBundleTooltip::new)
            : Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        ChoppedBundleContents bundlecontents = pStack.get(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get());
        if (bundlecontents != null) {
            int i = Mth.mulAndTruncate(bundlecontents.weight(), 64);
            pTooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", i, 64 * maxStacks).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void onDestroyed(ItemEntity pItemEntity) {
        ChoppedBundleContents bundlecontents = pItemEntity.getItem().get(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get());
        if (bundlecontents != null) {
            pItemEntity.getItem().set(ChoppedDataComponents.CHOPPED_BUNDLE_CONTENTS.get(), ChoppedBundleContents.EMPTY);
            ItemUtils.onContainerDestroyed(pItemEntity, bundlecontents.itemsCopy());
        }
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }
}
