package net.joey.chopped.blocks.crops;

import net.joey.chopped.Chopped;
import net.joey.chopped.blocks.ChoppedBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MultiCropBlock extends CropBlock {
    private final int MAX_CROP_HEIGHT;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final BooleanProperty GROWN = BooleanProperty.create("grown");
    public static final EnumProperty<CropSectionProperty> SECTION = ChoppedBlockStateProperties.CROP_SECTION;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };


    public MultiCropBlock(int height) {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        this.registerDefaultState(this.stateDefinition.any().setValue(SECTION, CropSectionProperty.STALKBASE).setValue(AGE, Integer.valueOf(0)).setValue(GROWN, Boolean.valueOf(false)));
        this.MAX_CROP_HEIGHT = height;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
        pBuilder.add(SECTION);
        pBuilder.add(GROWN);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.asItem();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[pState.getValue(AGE)];
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;

        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            if (pLevel.isEmptyBlock(pPos.above())) {
                int i;
                for (i = 1; pLevel.getBlockState(pPos.below(i)).is(this); ++i) {
                }

                if (i < MAX_CROP_HEIGHT || (!isMaxAge(pState) && i == MAX_CROP_HEIGHT)) {
                    Chopped.LOGGER.warn("growing...");
                    int j = pState.getValue(AGE);
                    if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(pLevel, pPos, pState, true)) {
                        if (j == 6) {
                            CropSectionProperty sec = (Math.random() <= 0.5) ? CropSectionProperty.STALK1 : CropSectionProperty.STALK2;
                            if (i < MAX_CROP_HEIGHT && i < (MAX_CROP_HEIGHT - 1)) {
                                //set to either stalk1 or stalk2 random
                                pLevel.setBlockAndUpdate(pPos.above(), this.defaultBlockState().setValue(SECTION, sec));
                            } else if (i == MAX_CROP_HEIGHT - 1) {
                                //set top to top block if at max height
                                pLevel.setBlockAndUpdate(pPos.above(), this.defaultBlockState().setValue(SECTION, CropSectionProperty.TOP));
                            }
                            //update age
                            pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(j + 1)), 3);
                            net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(pLevel, pPos, pState);
                        } else {
                            pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(j + 1)), 3);
                            net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(pLevel, pPos, pState);
                        }
                    }
                } else if (!pState.getValue(GROWN)) {
                    //entire crop grown
                    Chopped.LOGGER.warn("crop grown");
                    int m;
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(GROWN, true));
                    for(m = 1; pLevel.getBlockState(pPos.below(m)).is(this); ++m) {
                        pLevel.setBlockAndUpdate(pPos.below(m), pLevel.getBlockState(pPos.below()).setValue(GROWN, true));
                    }
                }
            }
        }
    }

    public final boolean isGrown(BlockState pState) {
        return pState.getValue(GROWN);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) {
        return !this.isGrown(pState) || !this.isMaxAge(pState);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState soil = pLevel.getBlockState(pPos.below());
        if (soil.canSustainPlant(pLevel, pPos.below(), Direction.UP, this)) return true;

        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (blockstate.is(this) && blockstate.getValue(AGE) == 7) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        int i;
        int y;
        for(i = 1; pLevel.getBlockState(pPos.below(i)).is(this); ++i) {
            pLevel.destroyBlock(pPos.below(i), true);
        }
        for (y = 0; pLevel.getBlockState(pPos.above(y)).is(this); ++y) {
            pLevel.destroyBlock(pPos.below(y-1), true);
        }
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        int i;
        int y;
        for(i = 1; pLevel.getBlockState(pPos.below(i)).is(this); ++i) {
        }
        for (y = 0; pLevel.getBlockState(pPos.above(y)).is(this); ++y) {
        } //get # above

        return (i <= MAX_CROP_HEIGHT && !isMaxAge(pState) || !isGrown(pState)) || pLevel.getBlockState(pPos.above()).is(this) && ((((y - 1) + i) < MAX_CROP_HEIGHT) || !isMaxAge(pLevel.getBlockState(pPos.above(y - 1))) || !isGrown(pLevel.getBlockState(pPos.above(y-1))));
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        int i;
        int y;
        for(i = 1; pLevel.getBlockState(pPos.below(i)).is(this); ++i) {
        }
        for (y = 0; pLevel.getBlockState(pPos.above(y)).is(this); ++y) {
        } //get # above

        if (pLevel.isEmptyBlock(pPos.above())) {
            pLevel.getBlockState(pPos).randomTick(pLevel, pPos, pRandom);
        } else if (pLevel.getBlockState(pPos.above()).is(this) && ((((y - 1) + i) < MAX_CROP_HEIGHT) || !isMaxAge(pLevel.getBlockState(pPos.above(y - 1))) || !isGrown(pLevel.getBlockState(pPos.above(y-1))))) {
            try {
                pLevel.getBlockState(pPos.above(y - 1)).randomTick(pLevel, pPos.above(y - 1), pRandom);
            } catch (IllegalArgumentException exception) {
                Chopped.LOGGER.warn("Cannot fertilize dead crop, this is intended");
            }
        }
    }
}
