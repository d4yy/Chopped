package net.day.chopped.blocks;

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

    public int getDustColor(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return this.dustColor;
    }
}
