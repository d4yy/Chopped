package net.joey.chopped.world.features.trees.types;

import com.mojang.serialization.Codec;
import net.joey.chopped.world.features.trees.configurations.DroopingTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

import java.util.function.BiConsumer;

public class DroopingTreeFeature extends ChoppedTreeFeature<DroopingTreeConfiguration> {
    public DroopingTreeFeature(Codec<DroopingTreeConfiguration> codec) {
        super(codec);
    }

    @Override //AT
    protected boolean doPlace(WorldGenLevel world, RandomSource random, BlockPos startPos, BiConsumer<BlockPos, BlockState> roots, BiConsumer<BlockPos, BlockState> logs, FoliagePlacer.FoliageSetter leaves, TreeConfiguration configBase) {
        DroopingTreeConfiguration config = (DroopingTreeConfiguration) configBase;

        while (startPos.getY() > 1 && canReplace(world, startPos.below())) {
            startPos = startPos.below();
        }

        int height = random.nextInt(config.minHeight, config.maxHeight);

        BlockPos pos = startPos;

        BlockPos topPos = pos.offset(0, height, 0);
        for (int y = 0; y <= height; y++) {
            if (!this.placeLog(world, pos.above(y), logs, config)) {
                return true;
            }
        }

        Direction[] horizontalAxis = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        Direction firstBranchDirection = horizontalAxis[random.nextInt(4)];

        //Branch 1
        this.placeLog(world, topPos.relative(firstBranchDirection, 1), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, 1), 2, leaves, config);
        this.placeLog(world, topPos.relative(firstBranchDirection, 2), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, 2), 2, leaves, config);
        this.placeLog(world, topPos.relative(firstBranchDirection, 3).below(), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, 3).below(), 2, leaves, config);
        this.placeLog(world, topPos.relative(firstBranchDirection, 4).below(2), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, 4).below(2), 2, leaves, config);

        //Branch 2
        this.placeLog(world, topPos.relative(firstBranchDirection, -1).below(2), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, -1).below(2), 2, leaves, config);
        this.placeLog(world, topPos.relative(firstBranchDirection, -2).below(2), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, -2).below(2), 2, leaves, config);
        this.placeLog(world, topPos.relative(firstBranchDirection, -3).below(3), firstBranchDirection.getAxis(), logs, config);
        this.generateLeafLayer(world, topPos.relative(firstBranchDirection, -3).below(3), 2, leaves, config);

        return true;
    }

    public void generateLeafLayer(LevelAccessor world, BlockPos pos, int radius, FoliagePlacer.FoliageSetter leaves, DroopingTreeConfiguration config) {
        int innerDensity = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos leafPos = pos.offset(dx, dy, dz);
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    if (distance <= radius) {
                        this.placeLeaves(world, leafPos, leaves, config);
                        innerDensity++;
                    }
                }
            }
        }
        int outerRadius = radius + 1;
        int outerDensity = innerDensity / 2;
        for (int dx = -outerRadius; dx <= outerRadius; dx++) {
            for (int dy = -outerRadius; dy <= outerRadius; dy++) {
                for (int dz = -outerRadius; dz <= outerRadius; dz++) {
                    BlockPos leafPos = pos.offset(dx, dy, dz);
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    if (distance > radius && distance <= outerRadius && world.getRandom().nextInt(outerDensity) == 0) {
                        this.placeLeaves(world, leafPos, leaves, config);
                    }
                }
            }
        }
    }
}