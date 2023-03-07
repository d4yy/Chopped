package net.day.chopped.registry.groups;

import net.day.chopped.Chopped;
import net.day.chopped.registry.ChoppedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

public class ChoppedSounds {

    public static final RegistryObject<SoundEvent> CASSETTE_WHAT_IT_DO = registerSoundEvents("cassette.what_it_do");
    public static final RegistryObject<SoundEvent> CASSETTE_BE_A_KING = registerSoundEvents("cassette.be_a_king");
    public static final RegistryObject<SoundEvent> CASSETTE_LET_ME_OHH = registerSoundEvents("cassette.let_me_ohh");
    public static final RegistryObject<SoundEvent> CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON = registerSoundEvents("cassette.drinking_with_my_headphones_on");

    public static void register() {}

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(Chopped.MOD_ID, name);
        return ChoppedRegistry.SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

}
