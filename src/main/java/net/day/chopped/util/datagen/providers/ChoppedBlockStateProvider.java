package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.blocks.crops.ChoppedCropBlock;
import net.day.chopped.blocks.crops.FruitBearingLeavesBlock;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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

        //need textures for datagen
        multiCropBlock(ChoppedBlocks.BLOCKS_ONION_CROP, "crop");
        multiCropBlock(ChoppedBlocks.BLOCKS_TOMATO_CROP, "crop");
        multiCropBlock(ChoppedBlocks.BLOCKS_CORN_CROP, "cross");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
            models().cross(getName(blockRegistryObject.get()), blockTexture(blockRegistryObject.get())).renderType("cutout")
        );
    }

    private void multiCropBlock(RegistryObject<Block> blockRegistryObject, String cropModelShape) {
        getVariantBuilder(blockRegistryObject.get())
            .forAllStates(blockState -> {
                ModelFile file = null;
                String blockName = getName(blockRegistryObject.get());

                String stage = blockState.getValue(ChoppedCropBlock.AGE).toString();
                ResourceLocation parent = new ResourceLocation("block/" + cropModelShape);

                ModelFile stalkBase = models().singleTexture("block/" + blockName + "/stalkbase_stage_" + stage, parent, cropModelShape, new ResourceLocation(Chopped.MOD_ID, "block/" + blockName + "/stalkbase_stage_" + stage)).renderType("cutout");
                ModelFile stalk1 = models().singleTexture("block/" + blockName + "/stalk1_stage_" + stage, parent, cropModelShape, new ResourceLocation(Chopped.MOD_ID, "block/" + blockName + "/stalk1_stage_" + stage)).renderType("cutout");
                ModelFile stalk2 = models().singleTexture("block/" + blockName + "/stalk2_stage_" + stage, parent, cropModelShape, new ResourceLocation(Chopped.MOD_ID, "block/" + blockName + "/stalk2_stage_" + stage)).renderType("cutout");
                ModelFile stalkTop = models().singleTexture("block/" + blockName + "/stalktop_stage_" + stage, parent, cropModelShape, new ResourceLocation(Chopped.MOD_ID, "block/" + blockName + "/stalktop_stage_" + stage)).renderType("cutout");

                file = switch (blockState.getValue(ChoppedCropBlock.SECTION)) {
                    case STALKBASE -> stalkBase;
                    case STALK1 -> stalk1;
                    case STALK2 -> stalk2;
                    case TOP -> stalkTop;
                };

                return ConfiguredModel.builder().modelFile(file).build();
            });
    }

    private void fruitBearingLeavesBlock(RegistryObject<Block> blockRegistryObject) {
        getVariantBuilder(blockRegistryObject.get())
            .forAllStates(blockState -> {
                ModelFile file = null;
                String blockName = getName(blockRegistryObject.get());
                String cultivarName = "_cultivar" + blockState.getValue(FruitBearingLeavesBlock.CULTIVAR);
                String bearingName;
                String finalName;

                if (!blockState.getValue(FruitBearingLeavesBlock.FRUIT_BEARING)) {
                    bearingName = "";
                } else if (blockState.getValue(FruitBearingLeavesBlock.FERTILITY) > 5) {
                    bearingName = "_bearing";
                } else {
                    bearingName = "_bearing_alt";
                }

                finalName = blockName + cultivarName + bearingName;

                file = models().singleTexture(finalName, new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + finalName)).renderType("cutout_mipped");

                return ConfiguredModel.builder().modelFile(file).build();
            });
        simpleBlockItem(blockRegistryObject.get(), models().singleTexture(getName(blockRegistryObject.get()), new ResourceLocation("block/leaves"), "all", new ResourceLocation(Chopped.MOD_ID, "block/" + getName(blockRegistryObject.get()))));
    }

    private String getName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
}
