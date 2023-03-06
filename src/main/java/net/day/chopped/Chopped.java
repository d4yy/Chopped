package net.day.chopped;

import net.day.chopped.configs.ChoppedCommonConfig;
import net.day.chopped.registry.ChoppedRegistry;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.day.chopped.registry.groups.ChoppedTabs;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Chopped.MOD_ID)
public class Chopped {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "chopped";

    public Chopped() {
        ChoppedRegistry.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ChoppedCommonConfig.SPEC, "chopped-common.toml");

        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ChoppedTabs::registerTabs);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Chopped.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class ClientEvents {
        private static final ColorResolver COLOR_RESOLVER = (pBiome, pX, pZ) -> pBiome.getFoliageColor();
        private static final int APPLE_FOLIAGE = 12899607;

        @SubscribeEvent
        static void registerColorResolver(RegisterColorHandlersEvent.ColorResolvers event) {
            event.register(COLOR_RESOLVER);
        }

        @SubscribeEvent
        static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
            //event.register(((pState, pLevel, pPos, pTintIndex) -> pLevel == null || pPos == null ? 0 : pLevel.getBlockTint(pPos, COLOR_RESOLVER)), ChoppedBlocks.BLOCKS_APPLE_LEAVES.get());
        }

        @SubscribeEvent
        static void registerItemColor(RegisterColorHandlersEvent.Item event) {
            /*event.register((itemColor, item) -> {
                BlockState blockstate = ((BlockItem)itemColor.getItem()).getBlock().defaultBlockState();
                return APPLE_FOLIAGE;
            }, ChoppedBlocks.BLOCKS_APPLE_LEAVES.get());*/
        }
    }

    @SubscribeEvent
    public void blockPlaceEvent(final BlockEvent.EntityPlaceEvent event) { //force send a tick to new chromium block
        if (event.getPlacedBlock().is(ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK.get())) {
            event.getLevel().scheduleTick(event.getPos(), ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK.get(), 4);
        }
    }
}
