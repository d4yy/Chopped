package net.day.chopped.items;

import net.day.chopped.blocks.crops.CultivarType;
import net.day.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.day.chopped.registry.groups.ChoppedTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class ChoppedBundleItem extends ChoppedItem {
    private static int maxWeight = 64;
    private static TagKey<?> allowedTag;
    private static final int BAR_COLOR = Mth.color(1.0F, 0.843F, 0.0F);

    public ChoppedBundleItem(int maxWeight, TagKey<?> allowedTag) {
        super();
        ChoppedBundleItem.maxWeight = maxWeight;
        ChoppedBundleItem.allowedTag = allowedTag;
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pAction != ClickAction.SECONDARY) {
            return false;
        } else if (pSlot.getItem().getTags().anyMatch(allowedTag::equals)) {
            ItemStack itemstack = pSlot.getItem();
            if (itemstack.isEmpty()) {
                this.playRemoveOneSound(pPlayer);
                removeOne(pStack).ifPresent((stack) -> {
                    add(pStack, pSlot.safeInsert(stack));
                });
            } else if (itemstack.getItem().canFitInsideContainerItems()) {
                int i = (maxWeight - getContentWeight(pStack)) / getWeight(itemstack);
                int j = add(pStack, pSlot.safeTake(itemstack.getCount(), i, pPlayer));
                if (j > 0) {
                    this.playInsertSound(pPlayer);
                }
            }
            return true;
        } else if (pSlot.getItem().isEmpty()) {
            this.playRemoveOneSound(pPlayer);
            removeOne(pStack).ifPresent((stack) -> add(pStack, pSlot.safeInsert(stack)));
            return true;
        }
        return false;
    }

    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.getTags().anyMatch(allowedTag::equals)) {
                if (pOther.isEmpty()) {
                    removeOne(pStack).ifPresent((stack) -> {
                        this.playRemoveOneSound(pPlayer);
                        pAccess.set(stack);
                    });
                } else {
                    int i = add(pStack, pOther);
                    if (i > 0) {
                        this.playInsertSound(pPlayer);
                        pOther.shrink(i);
                    }
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching()) {
            ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
            if (dropContents(itemstack, pPlayer)) {
                this.playDropContentsSound(pPlayer);
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState state = pContext.getLevel().getBlockState(blockPos);

        if (allowedTag == ChoppedTags.Items.FRUIT_BASKET_ALLOWED && state.getProperties().contains(FruitBearingLeavesBlock.FRUIT_BEARING)) {
            if (state.getValue(FruitBearingLeavesBlock.FRUIT_BEARING)) {
                ItemStack yieldStack = new ItemStack(getCultivarTypeAndItems(state)[state.getValue(FruitBearingLeavesBlock.CULTIVAR)], new Random().nextInt(3) + 1);
                int i = add(pContext.getItemInHand(), yieldStack);
                yieldStack.shrink(i);
                state.getBlock().popResourceFromFace(level, blockPos, pContext.getClickedFace(), yieldStack);
                level.setBlockAndUpdate(blockPos, state.setValue(FruitBearingLeavesBlock.FRUIT_BEARING, false));
                if (getContentWeight(pContext.getItemInHand()) == maxWeight) {
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
                this.playInsertSound(pContext.getPlayer());
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.PASS;
    }

    private Item[] getCultivarTypeAndItems(BlockState blockState) {
        if (blockState.getBlock() == ChoppedBlocks.BLOCKS_APPLE_LEAVES.get()) {
            return CultivarType.APPLE;
        } else {
            return CultivarType.ORANGE;
        }
    }

    public boolean isBarVisible(ItemStack pStack) {
        return getContentWeight(pStack) > 0;
    }

    public int getBarWidth(ItemStack pStack) {
        return Math.min(1 + 12 * getContentWeight(pStack) / maxWeight, 13);
    }

    public int getBarColor(ItemStack pStack) {
        return BAR_COLOR;
    }

    private static int add(ItemStack pBundleStack, ItemStack pInsertedStack) {
        if (!pInsertedStack.isEmpty() && pInsertedStack.getItem().canFitInsideContainerItems()) {
            CompoundTag compoundtag = pBundleStack.getOrCreateTag();
            if (!compoundtag.contains("Items")) {
                compoundtag.put("Items", new ListTag());
            }
            int i = getContentWeight(pBundleStack);
            int j = getWeight(pInsertedStack);
            int k = Math.min(pInsertedStack.getCount(), (maxWeight - i) / j);
            if (k == 0) {
                return 0;
            } else {
                ListTag listtag = compoundtag.getList("Items", 10);
                Optional<CompoundTag> optional = getMatchingItem(pInsertedStack, listtag);
                if (optional.isPresent()) {
                    CompoundTag compoundtag1 = optional.get();
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    ItemStack singleStack = pInsertedStack.copy();
                    if (itemstack.getCount() < itemstack.getMaxStackSize()) {
                        int toFill = itemstack.getMaxStackSize() - itemstack.getCount();

                        if (itemstack.getCount() + k > itemstack.getMaxStackSize()) {
                            itemstack.grow(toFill);

                            ItemStack itemstackOverflow = pInsertedStack.copy();
                            itemstackOverflow.setCount(k - toFill);
                            CompoundTag compoundtag3 = new CompoundTag();
                            itemstackOverflow.save(compoundtag3);
                            listtag.add(0, compoundtag3);
                        } else {
                            itemstack.grow(k);
                        }

                        itemstack.save(compoundtag1);
                        listtag.remove(compoundtag1);
                        listtag.add(0, compoundtag1);
                    } else {
                        singleStack.setCount(k);
                        CompoundTag compoundtag2 = new CompoundTag();
                        singleStack.save(compoundtag2);
                        listtag.add(0, compoundtag2);
                    }
                } else {
                    ItemStack itemstack1 = pInsertedStack.copy();
                    itemstack1.setCount(k);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, compoundtag2);
                }
                return k;
            }
        } else {
            return 0;
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack pStack, ListTag pList) {
        return pStack.getItem() instanceof ChoppedBundleItem ? Optional.empty() : pList.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((tag) -> {
            return ItemStack.isSameItemSameTags(ItemStack.of(tag), pStack) && ItemStack.of(tag).getCount() < ItemStack.of(tag).getMaxStackSize();
        }).findFirst();
    }

    private static int getWeight(ItemStack pStack) {
        if ((pStack.is(Items.BEEHIVE) || pStack.is(Items.BEE_NEST)) && pStack.hasTag()) {
            CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
            if (compoundtag != null && !compoundtag.getList("Bees", 10).isEmpty()) {
                return 64;
            }
        }
        return 64 / pStack.getMaxStackSize();
    }

    private static int getContentWeight(ItemStack pStack) {
        return getContents(pStack).mapToInt((p_186356_) -> {
            return getWeight(p_186356_) * p_186356_.getCount();
        }).sum();
    }

    private static Optional<ItemStack> removeOne(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains("Items")) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList("Items", 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    pStack.removeTagKey("Items");
                }

                return Optional.of(itemstack);
            }
        }
    }

    private static boolean dropContents(ItemStack pStack, Player pPlayer) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains("Items")) {
            return false;
        } else {
            if (pPlayer instanceof ServerPlayer) {
                ListTag listtag = compoundtag.getList("Items", 10);

                for (int i = 0; i < listtag.size(); ++i) {
                    CompoundTag compoundtag1 = listtag.getCompound(i);
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    pPlayer.drop(itemstack, true);
                }
            }

            pStack.removeTagKey("Items");
            return true;
        }
    }

    private static Stream<ItemStack> getContents(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList("Items", 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    public Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getContents(pStack).forEach(nonnulllist::add);
        return Optional.of(new BundleTooltip(nonnulllist, getContentWeight(pStack)));
    }

    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", getContentWeight(pStack), maxWeight).withStyle(ChatFormatting.GRAY));
    }

    public void onDestroyed(ItemEntity pItemEntity) {
        ItemUtils.onContainerDestroyed(pItemEntity, getContents(pItemEntity.getItem()));
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.getLevel().getRandom().nextFloat() * 0.4F);
    }
}
