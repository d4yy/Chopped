package net.day.chopped.blocks.crops;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class ChoppedCropBlock extends CropBlock {
    private final int MAX_CROP_HEIGHT;
    private final ItemLike SEED_ITEM;
    public final int AGE_PER_SECTION;
    public static final EnumProperty<Half> SECTION = BlockStateProperties.HALF;
    private static final VoxelShape STALK_SHAPE = Block.box(14.0D, 0.0D, 14.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape[] TOP_SHAPE = new VoxelShape[] {
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 0.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 2.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 4.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 6.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 8.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 10.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 12.0D, 14.0D),
            Block.box(14.0D, 0.0D, 14.0D, 14.0D, 14.0D, 14.0D)
    };

    public ChoppedCropBlock(int height, ItemLike seedItem) {
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        this.MAX_CROP_HEIGHT = height;
        this.SEED_ITEM = seedItem;
        this.AGE_PER_SECTION = ((int) Math.floor(8/MAX_CROP_HEIGHT)) - 1;
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.HALF, Half.BOTTOM).setValue(AGE, 0));
    }

    public int getMaxHeight(int height) {
        return MAX_CROP_HEIGHT;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SEED_ITEM;
    }

    @Override
    public boolean canSurvive(BlockState stateIn, LevelReader worldIn, BlockPos posIn) {
        Boolean isClear;
        BlockPos posBelow = posIn.below();

        if (stateIn.getValue(SECTION) == Half.BOTTOM) {
            if (stateIn.getValue(AGE) < AGE_PER_SECTION) {
                isClear = isAir(worldIn.getBlockState(posIn.above()));
            } else {
                if (worldIn.getBlockState(posIn.above()).getBlock() == this) {
                    isClear = worldIn.getBlockState(posIn.above()).getValue(SECTION) == Half.TOP;
                } else {
                    isClear = false;
                }
            }
            return worldIn.getBlockState(posBelow).canSustainPlant(worldIn, posBelow, Direction.UP, this) && isClear;
        } else {
            if (worldIn.getBlockState(posBelow).getBlock() == this) {
                return worldIn.getBlockState(posBelow).getValue(SECTION) == Half.BOTTOM && worldIn.getBlockState(posBelow).getValue(AGE) >= AGE_PER_SECTION;
            } else {
                return false;
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState stateIn, BlockGetter worldIn, BlockPos posIn, CollisionContext contextIn) {
        if (stateIn.getValue(SECTION) == Half.BOTTOM) {
            return STALK_SHAPE;
        } else {
            return TOP_SHAPE[stateIn.getValue(this.getAgeProperty())];
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState stateIn, ServerLevel worldIn, BlockPos posIn, Random randomIn) {
        if (!worldIn.isAreaLoaded(posIn, 1))
            return;
        if (worldIn.getRawBrightness(posIn, 0) >= 9) {
            int i = this.getAge(stateIn);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, worldIn, posIn);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, posIn, stateIn,
                        randomIn.nextInt((int) (25.0F / f) + 1) == 0)) {
                    worldIn.setBlock(posIn, this.getStateForAge(i + 1).setValue(SECTION, stateIn.getValue(SECTION)), 2);

                    if (stateIn.getValue(SECTION) == Half.BOTTOM && worldIn.isEmptyBlock(posIn.above()) && i + 1 >= (MAX_AGE - AGE_PER_SECTION)) {
                        worldIn.setBlock(posIn.above(), this.getStateForAge(i + 1).setValue(SECTION, Half.TOP), 2);
                    }

                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, posIn, stateIn);
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState stateIn) {
        return !this.isMaxAge(stateIn) && stateIn.getValue(SECTION) == Half.BOTTOM;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builderIn) {
        builderIn.add(SECTION);
        super.createBlockStateDefinition(builderIn);
    }

    @Override
    public void growCrops(Level worldIn, BlockPos posIn, BlockState stateIn) {
        int i = this.getAge(stateIn) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        worldIn.setBlock(posIn, this.getStateForAge(i).setValue(SECTION, stateIn.getValue(SECTION)), 2);

        if (i >= (MAX_AGE - AGE_PER_SECTION) && worldIn.isEmptyBlock(posIn.above()) && stateIn.getValue(SECTION) == Half.BOTTOM) {
            worldIn.setBlock(posIn.above(), this.getStateForAge(i).setValue(SECTION, Half.TOP), 2);
        }
    }
}
