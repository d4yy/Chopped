package net.day.chopped.blocks.crops;

import net.day.chopped.blocks.ChoppedBlockStateProperties;
import net.day.chopped.registry.groups.ChoppedItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public abstract class FruitBearingLeavesBlock extends LeavesBlock {
    public static final BooleanProperty FRUIT_BEARING = ChoppedBlockStateProperties.FRUIT_BEARING;
    public static final BooleanProperty FERTILE = ChoppedBlockStateProperties.FERTILE;
    public static final IntegerProperty FERTILITY = ChoppedBlockStateProperties.FERTILITY;
    public static final IntegerProperty CULTIVAR = ChoppedBlockStateProperties.CULTIVAR;

    //time until ripe in approximate minutes
    private int timeUntilRipe = 30;
    private int elapsedTicks = 0;
    private int bonusAmount;
    private Float bonusChance;

    protected FruitBearingLeavesBlock(int bonusAmount, Float bonusChance) {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES));
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FRUIT_BEARING, false)
            .setValue(FERTILE, false)
            .setValue(FERTILITY, Integer.valueOf(0))
            .setValue(DISTANCE, Integer.valueOf(7))
            .setValue(PERSISTENT, Boolean.valueOf(false))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
            .setValue(CULTIVAR, 0)
        );
        this.bonusAmount = bonusAmount;
        this.bonusChance = bonusChance;
    }

    protected abstract Item[] getCultivarItems();

    protected Item[] getBonusItems() {
        return new Item[0];
    }

    private int fertilityRandomiser() {
        Random rand = new Random();
        double p = 0.2;
        double u = rand.nextDouble();
        int k = (int) Math.ceil(Math.log(1 - (1 - Math.pow(p, 10)) * u) / Math.log(1 - p)); //truncated geometric distribution for fertility level probability
        return Math.min(k, 10);
    }

    private int adjustAgeByFertility(BlockState blockState) {
        int fertility = blockState.getValue(FERTILITY);
        int result = 0;

        result = Math.random() <= Math.pow(0.1, 10 - fertility) ? 1 : 0; //higher fertility grows faster exponentially

        return result;
    }

    private Item getCultivarItem(BlockState blockState) {
        return getCultivarItems()[blockState.getValue(CULTIVAR)];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FRUIT_BEARING);
        pBuilder.add(FERTILE);
        pBuilder.add(FERTILITY);
        pBuilder.add(CULTIVAR);
        pBuilder.add(DISTANCE, PERSISTENT, WATERLOGGED);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return !pState.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

        if (!pState.getValue(PERSISTENT) && !pState.getValue(FERTILE)) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(FERTILE, true).setValue(FERTILITY, fertilityRandomiser()));
        }

        if (this.decaying(pState)) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
            return;
        }

        //randomTick gets called ~1/s so wait ~ a minute before allowing age adjustment
        if (this.elapsedTicks < 60) {
            this.elapsedTicks++;
            return;
        }

        if (pState.getValue(FERTILE)) {
            if (this.timeUntilRipe > 0) {
                this.timeUntilRipe -= adjustAgeByFertility(pState);
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FRUIT_BEARING, true));
                this.timeUntilRipe = 0;
            }
        }

        this.elapsedTicks = 0;

    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getValue(FRUIT_BEARING)) {
            this.timeUntilRipe = 30;
            this.elapsedTicks = 0;

            if (pPlayer.getItemInHand(pHand).getItem() != ChoppedItems.FRUIT_BASKET.get()) {
                popResourceFromFace(pLevel, pPos, pHit.getDirection(), new ItemStack(getCultivarItem(pState), new Random().nextInt(3) + 1));

                if (getBonusItems().length > 0) {
                    for (Item bonusItem : getBonusItems()) {
                        if (Math.random() <= this.bonusChance)
                            popResourceFromFace(pLevel, pPos, pHit.getDirection(), new ItemStack(bonusItem, this.bonusAmount));
                    }
                }
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FRUIT_BEARING, false));
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

}
