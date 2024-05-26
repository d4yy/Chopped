package net.joey.chopped.world.features.trees.types;

import com.mojang.serialization.Codec;
import net.joey.chopped.world.features.trees.configurations.ShrubTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class ShrubTreeFeature extends ChoppedTreeFeature<ShrubTreeConfiguration> {
    public ShrubTreeFeature(Codec<ShrubTreeConfiguration> codec) {
        super(codec);
    }

    @Override //AT
    protected boolean doPlace(WorldGenLevel world, RandomSource random, BlockPos startPos, BiConsumer<BlockPos, BlockState> roots, BiConsumer<BlockPos, BlockState> logs, FoliagePlacer.FoliageSetter leaves, TreeConfiguration configBase) {
        ShrubTreeConfiguration config = (ShrubTreeConfiguration) configBase;

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
        ArrayList<Direction> placedDirections = new ArrayList<>(Arrays.stream(horizontalAxis).toList());

        for (int i = 0; i < height; i++) {
            Direction randomDir = placedDirections.get(random.nextInt(placedDirections.size()));
            this.placeLog(world, topPos.relative(randomDir, 1).below(i), randomDir.getAxis(), logs, config);
            this.generateLeafLayer(world, topPos.relative(randomDir, 1).below(i), 3, leaves, config);
            if (placedDirections.size() == 1) {
                placedDirections.addAll(Arrays.stream(horizontalAxis).toList());
                placedDirections.remove(randomDir);
            } else {
                placedDirections.remove(randomDir);
            }
        }

        return true;
    }

    public void generateLeafLayer(LevelAccessor world, BlockPos pos, int radius, FoliagePlacer.FoliageSetter leaves, ShrubTreeConfiguration config) {
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
        int outerDensity = (int) (innerDensity / 1.5);
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