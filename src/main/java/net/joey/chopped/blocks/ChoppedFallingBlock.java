package net.joey.chopped.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ChoppedFallingBlock extends FallingBlock {
    private final int dustColor;

    public ChoppedFallingBlock(int dustColor, Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return null;
    }

    public int getDustColor(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return this.dustColor;
    }
}
