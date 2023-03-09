package net.day.chopped.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class BlockTools {
    public final class BlockPropertyCondenser {
        private final BlockBehaviour.Properties blockProperties;

        public BlockPropertyCondenser(Material material, Function<BlockState, MaterialColor> mapColours) {
            this.blockProperties = Block.Properties.of(material, mapColours).sound(soundMatcher(material));
        }

        public BlockPropertyCondenser(Material material, MaterialColor materialColor) {
            this(material, state -> materialColor);
        }

        public BlockPropertyCondenser requiresTool() {
            this.blockProperties.requiresCorrectToolForDrops();

            return this;
        }

        public BlockPropertyCondenser soundType(SoundType soundType) {
            this.blockProperties.sound(soundType);

            return this;
        }

        public BlockPropertyCondenser strength(float hardness) {
            return strength(hardness, hardness);
        }

        public BlockPropertyCondenser strength(float hardness, float resistance) {
            this.blockProperties.strength(hardness, resistance);

            return this;
        }

        public BlockPropertyCondenser lightEmission(int light) {
            return lightEmission(state -> light);
        }

        public BlockPropertyCondenser lightEmission(ToIntFunction<BlockState> light) {
            this.blockProperties.lightLevel(light);

            return this;
        }

        public BlockPropertyCondenser randomTicks() {
            this.blockProperties.randomTicks();

            return this;
        }

        public BlockPropertyCondenser isAir() {
            this.blockProperties.air();

            return this;
        }

        public BlockPropertyCondenser dynamicShape() {
            this.blockProperties.dynamicShape();

            return this;
        }

        public BlockPropertyCondenser friction(float f) {
            this.blockProperties.friction(f);

            return this;
        }

        public BlockPropertyCondenser emissive() {
            return emissive((state, world, pos) -> true);
        }

        public BlockPropertyCondenser emissive(BlockBehaviour.StatePredicate when) {
            this.blockProperties.emissiveRendering(when);

            return this;
        }

        public BlockPropertyCondenser postProcess() {
            return postProcess((state, world, pos) -> true);
        }

        public BlockPropertyCondenser postProcess(BlockBehaviour.StatePredicate when) {
            this.blockProperties.hasPostProcess(when);

            return this;
        }

        public BlockPropertyCondenser speedMultiplier(float speedMod) {
            this.blockProperties.speedFactor(speedMod);

            return this;
        }

        public BlockPropertyCondenser jumpMultiplier(float jumpFactor) {
            this.blockProperties.jumpFactor(jumpFactor);

            return this;
        }

        public BlockPropertyCondenser willNotDropLoot() {
            this.blockProperties.noLootTable();

            return this;
        }

        public BlockPropertyCondenser willNotOcclude() {
            this.blockProperties.noOcclusion();

            return this;
        }

        public BlockPropertyCondenser willNotCollide() {
            this.blockProperties.noCollission();
            willBlockView((state, world, pos) -> false);

            return this;
        }

        public BlockPropertyCondenser willNotBlockView() {
            return willBlockView((state, world, pos) -> false);
        }

        public BlockPropertyCondenser willBlockView(BlockBehaviour.StatePredicate when) {
            this.blockProperties.isViewBlocking(when);

            return this;
        }

        public BlockPropertyCondenser willNotConductRedstone() {
            return willConductRedstone((state, world, pos) -> false);
        }

        public BlockPropertyCondenser willConductRedstone(BlockBehaviour.StatePredicate when) {
            this.blockProperties.isRedstoneConductor(when);

            return this;
        }

        public BlockPropertyCondenser willNotSpawn() {
            return willSpawnThese((state, world, pos, entityType) -> false);
        }

        public BlockPropertyCondenser willSpawnThese(BlockBehaviour.StateArgumentPredicate<EntityType<?>> when) {
            this.blockProperties.isValidSpawn(when);

            return this;
        }

        public BlockPropertyCondenser willNotSuffocate() {
            return willSuffocate((state, world, pos) -> false);
        }

        public BlockPropertyCondenser willSuffocate(BlockBehaviour.StatePredicate when) {
            this.blockProperties.isSuffocating(when);

            return this;
        }

        public Block.Properties get() {
            return this.blockProperties;
        }

        private static SoundType soundMatcher(Material material) {
            if (material == Material.WOOD) {
                return SoundType.WOOD;
            } else if (material == Material.GLASS) {
                return SoundType.GLASS;
            } else if (material == Material.DIRT) {
                return SoundType.GRAVEL;
            } else if (material == Material.PLANT || material == Material.GRASS) {
                return SoundType.GRASS;
            } else if (material == Material.TOP_SNOW || material == Material.SNOW) {
                return SoundType.SNOW;
            } else if (material == Material.SAND) {
                return SoundType.SAND;
            } else if (material == Material.WOOL) {
                return SoundType.WOOL;
            } else if (material == Material.METAL || material == Material.HEAVY_METAL) {
                return SoundType.METAL;
            } else {
                return SoundType.STONE;
            }
        }

    }
}
