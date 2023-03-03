package net.day.chopped.items;

import net.day.chopped.registry.groups.ChoppedTabs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class ChoppedSeedItem extends ItemNameBlockItem {

    public ChoppedSeedItem(Block block) {
        this(block, new Item.Properties());
    }

    public ChoppedSeedItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        Player player = pContext.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (!this.mustSurvive() || pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(Blocks.FARMLAND)) && pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), collisioncontext);
    }


}
