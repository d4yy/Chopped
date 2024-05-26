package net.joey.chopped.util.datagen.providers;

import net.joey.chopped.Chopped;
import net.joey.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.joey.chopped.blocks.crops.MultiCropBlock;
import net.joey.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

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
        blockWithItem(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT);
        blockWithItem(ChoppedBlocks.BLOCKS_SEA_SALT);
        blockWithItem(ChoppedBlocks.BLOCKS_CHROMIUM_ORE);
        blockWithItem(ChoppedBlocks.BLOCKS_DEEPSLATE_CHROMIUM_ORE);
        blockWithItem(ChoppedBlocks.BLOCKS_HIMALAYAN_SALT_ORE);

        logBlock(((RotatedPillarBlock) ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()));
        axisBlock((RotatedPillarBlock) ChoppedBlocks.BLOCKS_APPLEWOOD.get(), blockTexture(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()), blockTexture(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get()));
        axisBlock((RotatedPillarBlock) ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get(), new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log"),
            new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log_top"));
        axisBlock((RotatedPillarBlock) ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get(), new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log"),
            new ResourceLocation(Chopped.MOD_ID, "block/stripped_applewood_log"));
        blockWithItem(ChoppedBlocks.BLOCKS_APPLEWOOD_PLANKS);
        saplingBlock(ChoppedBlocks.BLOCKS_APPLE_SAPLING);
        simpleBlockItem(ChoppedBlocks.BLOCKS_APPLEWOOD_LOG.get(), models().withExistingParent("chopped:applewood_log", "minecraft:block/cube_column"));
        simpleBlockItem(ChoppedBlocks.BLOCKS_APPLEWOOD.get(), models().withExistingParent("chopped:applewood", "minecraft:block/cube_column"));
        simpleBlockItem(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD_LOG.get(), models().withExistingParent("chopped:stripped_applewood_log", "minecraft:block/cube_column"));
        simpleBlockItem(ChoppedBlocks.BLOCKS_STRIPPED_APPLEWOOD.get(), models().withExistingParent("chopped:stripped_applewood", "minecraft:block/cube_column"));

        //need textures for datagen
        multiCropBlock(ChoppedBlocks.BLOCKS_ONION_CROP, "crop");
        multiCropBlock(ChoppedBlocks.BLOCKS_TOMATO_CROP, "crop");
        multiCropBlock(ChoppedBlocks.BLOCKS_CORN_CROP, "cross");

        fruitBearingLeavesBlock(ChoppedBlocks.BLOCKS_APPLE_LEAVES);
        fruitBearingLeavesBlock(ChoppedBlocks.BLOCKS_ORANGE_LEAVES);
    }

    private void blockWithItem(Supplier<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(Supplier<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
            models().cross(getName(blockRegistryObject.get()), blockTexture(blockRegistryObject.get())).renderType("cutout")
        );
    }

    private void multiCropBlock(Supplier<Block> blockRegistryObject, String cropModelShape) {
        getVariantBuilder(blockRegistryObject.get())
            .forAllStates(blockState -> {
                ModelFile file = null;
                String blockName = getName(blockRegistryObject.get());
                String stageName = switch (blockState.getValue(MultiCropBlock.SECTION)) {
                    case STALKBASE -> "/stalkbase_stage_";
                    case STALK1 -> "/stalk1_stage_";
                    case STALK2 -> "/stalk2_stage_";
                    case TOP -> "/stalktop_stage_";
                };
                String grownAppend = blockState.getValue(MultiCropBlock.GROWN) ? "_grown" : "";
                String finalName;
                String stage = blockState.getValue(MultiCropBlock.AGE).toString();
                ResourceLocation parent = new ResourceLocation("block/" + cropModelShape);


                finalName = blockName + stageName + stage;

                if (blockState.getValue(MultiCropBlock.AGE) == 7) finalName += grownAppend;

                file = models().singleTexture("block/" + finalName, parent, cropModelShape, new ResourceLocation(Chopped.MOD_ID, "block/" + finalName)).renderType("cutout");

                return ConfiguredModel.builder().modelFile(file).build();
            });
    }

    private void fruitBearingLeavesBlock(Supplier<Block> blockRegistryObject) {
        getVariantBuilder(blockRegistryObject.get())
            .forAllStates(blockState -> {
                ModelFile file = null;
                String blockName = getName(blockRegistryObject.get());
                String cultivarName = "cultivar" + blockState.getValue(FruitBearingLeavesBlock.CULTIVAR);
                String bearingName;
                String finalName;

                if (!blockState.getValue(FruitBearingLeavesBlock.FRUIT_BEARING)) {
                    bearingName = "";
                } else if (blockState.getValue(FruitBearingLeavesBlock.FERTILITY) > 5) {
                    bearingName = "_bearing";
                } else {
                    bearingName = "_bearing_alt";
                }

                finalName = blockName + "/" + cultivarName + bearingName;

                file = models().singleTexture("block/" + finalName, new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + finalName)).renderType("cutout_mipped");

                return ConfiguredModel.builder().modelFile(file).build();
            });
        simpleBlockItem(blockRegistryObject.get(), models().singleTexture(getName(blockRegistryObject.get()), new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + getName(blockRegistryObject.get()) + "/cultivar0")));
    }

    private String getName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
