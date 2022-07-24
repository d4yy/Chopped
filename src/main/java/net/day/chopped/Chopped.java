package net.day.chopped;

import net.day.chopped.configs.ChoppedCommonConfig;
import net.day.chopped.registry.ChoppedRegistry;
import net.day.chopped.registry.groups.ChoppedBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Chopped.MOD_ID)
public class Chopped {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "chopped";

    public Chopped() {
        ChoppedRegistry.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ChoppedCommonConfig.SPEC, "chopped-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

    }

    @SubscribeEvent
    public void blockPlaceEvent(final BlockEvent.EntityPlaceEvent event) { //force send a tick to new chromium block
        if (event.getPlacedBlock().is(ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK.get())) {
            event.getLevel().scheduleTick(event.getPos(), ChoppedBlocks.BLOCKS_CHROMIUM_BLOCK.get(), 4);
        }
    }
}
