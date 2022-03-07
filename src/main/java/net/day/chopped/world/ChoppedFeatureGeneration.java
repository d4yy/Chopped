package net.day.chopped.world;

import net.day.chopped.Chopped;
import net.day.chopped.world.ores.ChoppedOreGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chopped.MOD_ID)
public class ChoppedFeatureGeneration {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {

        //Ore gen
        ChoppedOreGeneration.generateOres(event);

    }
}
