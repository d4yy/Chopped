package net.day.chopped.blocks.materials;

import net.day.chopped.blocks.ChoppedBlock;
import net.day.chopped.blocks.ChoppedBlockStateProperties;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ChromiumBlock extends ChoppedBlock {
    public static final BooleanProperty REFLECTIVE = ChoppedBlockStateProperties.REFLECTIVE;
    public static final IntegerProperty BLOCK_LIGHT_ABSORPTION = ChoppedBlockStateProperties.BLOCK_LIGHT_ABSORPTION;
    public static final IntegerProperty BLOCK_LIGHT_EMISSION = ChoppedBlockStateProperties.BLOCK_LIGHT_EMISSION;

    public ChromiumBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(REFLECTIVE, Boolean.valueOf(false)).setValue(BLOCK_LIGHT_ABSORPTION, Integer.valueOf(0)).setValue(BLOCK_LIGHT_EMISSION, Integer.valueOf(0)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(REFLECTIVE);
        pBuilder.add(BLOCK_LIGHT_ABSORPTION);
        pBuilder.add(BLOCK_LIGHT_EMISSION);
    }

    private BlockPos getReflectionSourcePos(BlockState state, ServerLevel level, BlockPos  pos) {
        int l = 0;
        BlockPos p = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (level.getRawBrightness(pos.relative(direction), level.getSkyDarken()) >= l && (level.isEmptyBlock(pos.relative(direction)) || !level.getBlockState(pos.relative(direction)).canOcclude())) {
                l = level.getRawBrightness(pos.relative(direction), level.getSkyDarken());
                p = pos.relative(direction);
            }
        }

        for (Direction direction : Direction.Plane.VERTICAL) {
            if (level.getRawBrightness(pos.relative(direction), level.getSkyDarken()) >= l && (level.isEmptyBlock(pos.relative(direction)) || !level.getBlockState(pos.relative(direction)).canOcclude())) {
                l = level.getRawBrightness(pos.relative(direction), level.getSkyDarken());
                p = pos.relative(direction);
            }
        }

        return p;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRand) {
        BlockPos lightInPos = getReflectionSourcePos(pState, pLevel, pPos);
        int lightIn;
        int lightOut;

        if (lightInPos != null) { //
            lightIn = pLevel.getRawBrightness(lightInPos, pLevel.getSkyDarken());
        } else {
            lightIn = 0;
        }

        lightOut = Math.min(lightIn, 15);

        pLevel.setBlock(pPos, pState.setValue(REFLECTIVE, Boolean.valueOf(lightIn > 0)).setValue(BLOCK_LIGHT_ABSORPTION, lightIn).setValue(BLOCK_LIGHT_EMISSION, lightOut), 3);

        pLevel.scheduleTick(pPos, this, 4);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRand) {
        Boolean r = Math.random() <= (pState.getValue(BLOCK_LIGHT_EMISSION)/15.0);

        if (pState.getValue(REFLECTIVE) && r) {
            spawnParticles(pLevel, pPos);
        }
    }

    private static void spawnParticles(Level pLevel, BlockPos pPos) {
        RandomSource random = pLevel.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pPos.relative(direction);
            if (!pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getStepX() : (double) random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getStepY() : (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getStepZ() : (double) random.nextFloat();
                pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, (double) pPos.getX() + d1, (double) pPos.getY() + d2, (double) pPos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack item = pPlayer.getItemInHand(pHand);

        if(item.is(Items.AMETHYST_SHARD)) {
            if (pLevel.isClientSide) pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 2, 1);
            pLevel.setBlock(pPos, ChoppedBlocks.BLOCKS_TINTED_CHROMIUM_BLOCK.get().defaultBlockState(), 3);
            item.shrink(1);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
