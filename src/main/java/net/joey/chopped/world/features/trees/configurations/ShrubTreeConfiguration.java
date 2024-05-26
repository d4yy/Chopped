package net.joey.chopped.world.features.trees.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

import java.util.List;

public class ShrubTreeConfiguration extends ChoppedTreeConfiguration {
    public static final Codec<ShrubTreeConfiguration> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
            BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter(instance -> instance.trunkProvider),
            BlockStateProvider.CODEC.fieldOf("foliage_provider").forGetter(instance -> instance.foliageProvider),
            BlockStateProvider.CODEC.fieldOf("vine_provider").forGetter(instance -> instance.vineProvider),
            BlockStateProvider.CODEC.fieldOf("hanging_provider").forGetter(instance -> instance.hangingProvider),
            BlockStateProvider.CODEC.fieldOf("trunk_fruit_provider").forGetter(instance -> instance.trunkFruitProvider),
            BlockStateProvider.CODEC.fieldOf("alt_foliage_provider").forGetter(instance -> instance.altFoliageProvider),
            Codec.INT.fieldOf("min_height").forGetter(instance -> instance.minHeight),
            Codec.INT.fieldOf("max_height").forGetter(instance -> instance.maxHeight),
            TreeDecorator.CODEC.listOf().fieldOf("decorators").forGetter(instance -> instance.decorators),
            Codec.FLOAT.fieldOf("leaf_chance_even").forGetter(instance -> instance.leafChanceEven),
            Codec.FLOAT.fieldOf("leaf_chance_odd").forGetter(instance -> instance.leafChanceOdd)
        ).apply(builder, ShrubTreeConfiguration::new);
    });

    public final float leafChanceEven;
    public final float leafChanceOdd;

    protected ShrubTreeConfiguration(BlockStateProvider trunkProvider, BlockStateProvider foliageProvider, BlockStateProvider vineProvider, BlockStateProvider hangingProvider, BlockStateProvider trunkFruitProvider, BlockStateProvider altFoliageProvider, int minHeight, int maxHeight, List<TreeDecorator> decorators, float leafChanceEven, float leafChanceOdd) {
        super(trunkProvider, foliageProvider, vineProvider, hangingProvider, trunkFruitProvider, altFoliageProvider, minHeight, maxHeight, decorators);
        this.leafChanceEven = leafChanceEven;
        this.leafChanceOdd = leafChanceOdd;
    }

    public static class Builder extends ChoppedTreeConfiguration.Builder<ShrubTreeConfiguration.Builder> {
        private float leafChanceEven;
        private float leafChanceOdd;

        public Builder() {
            this.minHeight = 4;
            this.maxHeight = 6;
            this.leafChanceEven = 1.0F;
            this.leafChanceOdd = 1.0F;
        }

        public ShrubTreeConfiguration.Builder leafChance(float a, float b) {
            this.leafChanceEven = a;
            this.leafChanceOdd = b;
            return this;
        }

        public ShrubTreeConfiguration build() {
            return new ShrubTreeConfiguration(this.trunkProvider, this.foliageProvider, this.vineProvider, this.hangingProvider, this.trunkFruitProvider, this.altFoliageProvider, this.minHeight, this.maxHeight, this.decorators, this.leafChanceEven, this.leafChanceOdd);
        }
    }
}
