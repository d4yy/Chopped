package net.day.chopped.blocks.crops;

import net.day.chopped.blocks.ChoppedBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class FruitBearingLeavesBlock extends LeavesBlock {
    public static final BooleanProperty FRUIT_BEARING = ChoppedBlockStateProperties.FRUIT_BEARING;
    public static final BooleanProperty FERTILE = ChoppedBlockStateProperties.FERTILE;
    public static final IntegerProperty FERTILITY = ChoppedBlockStateProperties.FERTILITY;

    public final Item[] YIELD_ITEM;

    private int timeUntilRipe = 100;
    private int elapsedTicks = 0;

    public FruitBearingLeavesBlock(Item... yieldItem) {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES));
        this.registerDefaultState(this.stateDefinition.any().setValue(FRUIT_BEARING, false).setValue(FERTILE, false).setValue(FERTILITY, Integer.valueOf(0)).setValue(DISTANCE, Integer.valueOf(7)).setValue(PERSISTENT, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
        this.YIELD_ITEM = yieldItem;
    }

    private int fertilityRandomiser() {
        Random rand = new Random();
        double p = 0.2;
        double u = rand.nextDouble();
        int k = (int) Math.ceil(Math.log(1 - (1 - Math.pow(p, 10)) * u) / Math.log(1 - p));
        return Math.min(k, 10);
    }

    private int adjustAgeByFertility(BlockState blockState) {
        int multiplier = blockState.getValue(FERTILITY);
        int result = 0;

        result = Math.random() <= (0.1 * multiplier) ? 1 : 0;

        return result;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FRUIT_BEARING);
        pBuilder.add(FERTILE);
        pBuilder.add(FERTILITY);
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

        if (this.elapsedTicks < 1200) {
            elapsedTicks++;
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

        elapsedTicks = 0;

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
