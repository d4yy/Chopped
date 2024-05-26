package net.joey.chopped.registry.groups;

import net.joey.chopped.Chopped;
import net.joey.chopped.registry.ChoppedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ChoppedSounds {

    public static final Supplier<SoundEvent> CASSETTE_WHAT_IT_DO = registerSoundEvents("cassette.what_it_do");
    public static final Supplier<SoundEvent> CASSETTE_BE_A_KING = registerSoundEvents("cassette.be_a_king");
    public static final Supplier<SoundEvent> CASSETTE_LET_ME_OHH = registerSoundEvents("cassette.let_me_ohh");
    public static final Supplier<SoundEvent> CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON = registerSoundEvents("cassette.drinking_with_my_headphones_on");
    public static final Supplier<SoundEvent> CASSETTE_BIG_SHIPS = registerSoundEvents("cassette.big_ships");

    public static void register() {}

    private static Supplier<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(Chopped.MOD_ID, name);
        return ChoppedRegistry.SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

}
