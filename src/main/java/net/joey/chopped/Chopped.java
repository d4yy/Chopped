package net.joey.chopped;

import net.joey.chopped.configs.ChoppedCommonConfig;
import net.joey.chopped.items.ChoppedBundleContents;
import net.joey.chopped.items.ChoppedBundleItem;
import net.joey.chopped.items.ChoppedBundleTooltip;
import net.joey.chopped.items.ChoppedClientBundleTooltip;
import net.joey.chopped.registry.ChoppedRegistry;
import net.joey.chopped.registry.groups.ChoppedBlocks;
import net.joey.chopped.registry.groups.ChoppedItems;
import net.joey.chopped.registry.groups.ChoppedTabs;
import net.joey.chopped.util.BlockTools;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.level.ColorResolver;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Chopped.MOD_ID)
public class Chopped {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "chopped";

    public Chopped(ModContainer container, IEventBus eventBus) {
        NeoForge.EVENT_BUS.register(this);
        //IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ChoppedRegistry.register(eventBus);

        container.registerConfig(ModConfig.Type.COMMON, ChoppedCommonConfig.SPEC, "chopped-common.toml");

        eventBus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(BlockTools::registerBlockActions);
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = Chopped.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ClientEvents {
        private static final ColorResolver COLOR_RESOLVER = (pBiome, pX, pZ) -> pBiome.getFoliageColor();
        private static final int APPLE_FOLIAGE = 12899607;

        @SubscribeEvent
        public static void registerClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(ChoppedBundleTooltip.class, t -> new ChoppedClientBundleTooltip(t.contents()));
        }

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
