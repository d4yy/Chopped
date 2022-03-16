package net.day.chopped.blocks.materials;

import net.day.chopped.blocks.ChoppedBlock;
import net.day.chopped.blocks.ChoppedBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class ChromiumBlock extends ChoppedBlock {
    public static final BooleanProperty REFLECTIVE = ChoppedBlockStateProperties.REFLECTIVE;
    public static final IntegerProperty BLOCK_LIGHT_ABSORPTION = ChoppedBlockStateProperties.BLOCK_LIGHT_ABSORPTION;
    public static final IntegerProperty BLOCK_LIGHT_EMISSION = ChoppedBlockStateProperties.BLOCK_LIGHT_EMISSION;

    public ChromiumBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(REFLECTIVE, Boolean.valueOf(false)).setValue(BLOCK_LIGHT_ABSORPTION, Integer.valueOf(0)).setValue(BLOCK_LIGHT_EMISSION, Integer.valueOf(0)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(REFLECTIVE);
        pBuilder.add(BLOCK_LIGHT_ABSORPTION);
        pBuilder.add(BLOCK_LIGHT_EMISSION);
    }

    private BlockPos getReflectionSourcePos(BlockState state, ServerLevel level, BlockPos  pos) {
        int l = 0;
        BlockPos p = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (level.getBrightness(LightLayer.SKY, pos.relative(direction)) - level.getSkyDarken() >= l) {
                l = level.getBrightness(LightLayer.SKY, pos.relative(direction)) - level.getSkyDarken();
                p = pos.relative(direction);
            }
        }

        for (Direction direction : Direction.Plane.VERTICAL) {
            if (level.getBrightness(LightLayer.SKY, pos.relative(direction)) - level.getSkyDarken() >= l) {
                l = level.getBrightness(LightLayer.SKY, pos.relative(direction)) - level.getSkyDarken();
                p = pos.relative(direction);
            }
        }

        return p;
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        BlockPos lightInPos = getReflectionSourcePos(pState, pLevel, pPos);
        int lightIn;
        int lightOut;

        if (lightInPos != null) { //
            lightIn = pLevel.getBrightness(LightLayer.SKY, lightInPos) - pLevel.getSkyDarken();
        } else {
            lightIn = 0;
        }

        lightOut = Math.min((int) (lightIn * 1.5), 15);

        pLevel.setBlock(pPos, pState.setValue(REFLECTIVE, Boolean.valueOf(lightIn > 0)).setValue(BLOCK_LIGHT_ABSORPTION, lightIn).setValue(BLOCK_LIGHT_EMISSION, lightOut), 3);

        pLevel.scheduleTick(pPos, this, 4);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        Boolean r = (Math.random() <= 0.25) ? true : false;

        if (pState.getValue(REFLECTIVE) && r) {
            spawnParticles(pLevel, pPos);
        }
    }

    private static void spawnParticles(Level pLevel, BlockPos pPos) {
        double d0 = 0.5625D;
        Random random = pLevel.random;

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
}
