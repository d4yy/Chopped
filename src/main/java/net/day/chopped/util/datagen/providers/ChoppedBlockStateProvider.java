package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedBlockStateProvider extends BlockStateProvider {

    public ChoppedBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Chopped.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ChoppedBlocks.BLOCKS_RAW_CHROMIUM_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_TINTED_CHROMIUM_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_STAINLESS_STEEL_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_DAMASCUS_STEEL_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_SEA_SALT_BLOCK);
        blockWithItem(ChoppedBlocks.BLOCKS_CHROMIUM_ORE);
        blockWithItem(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE);
        blockWithItem(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_ORE);

        logBlock(((RotatedPillarBlock) ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()));
        axisBlock((RotatedPillarBlock) ChoppedBlocks.BLOCKS_APPLEWOOD.get(), blockTexture(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()), blockTexture(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()));
        axisBlock((RotatedPillarBlock) ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get(), new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log"),
                new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log_top"));
        axisBlock((RotatedPillarBlock) ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get(), new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log"),
                new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log"));

        fruitBearingLeavesBlock(ChoppedBlocks.BLOCKS_APPLE_LEAVES);
        blockWithItem(ChoppedBlocks.BLOCKS_APPLEWOOD_PLANKS);

        saplingBlock(ChoppedBlocks.BLOCKS_APPLE_SAPLING);

        simpleBlockItem(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get(), models().withExistingParent("chopped:applewood_log", "minecraft:block/cube_column"));
        simpleBlockItem(ChoppedBlocks.BLOCKS_APPLEWOOD.get(), models().withExistingParent("chopped:applewood", "minecraft:block/cube_column"));
        simpleBlockItem(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get(), models().withExistingParent("chopped:stripped_applewood_log", "minecraft:block/cube_column"));
        simpleBlockItem(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get(), models().withExistingParent("chopped:stripped_applewood", "minecraft:block/cube_column"));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
            models().cross(getName(blockRegistryObject.get()), blockTexture(blockRegistryObject.get())).renderType("cutout")
        );
    }

    private void fruitBearingLeavesBlock(RegistryObject<Block> blockRegistryObject) {
        getVariantBuilder(blockRegistryObject.get())
                .forAllStates(blockState -> {
                    ModelFile file = null;

                    ModelFile notBearing = models().singleTexture(getName(blockRegistryObject.get()), new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + getName(blockRegistryObject.get())));
                    ModelFile bearing = models().singleTexture(getName(blockRegistryObject.get()) + "_bearing", new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + getName(blockRegistryObject.get()) + "_bearing"));
                    ModelFile bearingAlt = models().singleTexture(getName(blockRegistryObject.get()) + "_bearing_alt", new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + getName(blockRegistryObject.get()) + "_bearing_alt"));

                    if (!blockState.getValue(FruitBearingLeavesBlock.FRUIT_BEARING)) {
                        file = notBearing;
                    } else if (blockState.getValue(FruitBearingLeavesBlock.FERTILITY) > 5) {
                        file = bearing;
                    } else {
                        file = bearingAlt;
                    }

                    return ConfiguredModel.builder().modelFile(file).build();
                });

        simpleBlockItem(blockRegistryObject.get(), models().singleTexture(getName(blockRegistryObject.get()), new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + getName(blockRegistryObject.get()))));
    }

    private String getName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
}
