package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedSounds {

    public static RegistryObject<SoundEvent> WHAT_IT_DO = registerSoundEvents("what_it_do");

    public static void register() {}

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(Chopped.MOD_ID, name);
        return ChoppedRegistry.SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

}
