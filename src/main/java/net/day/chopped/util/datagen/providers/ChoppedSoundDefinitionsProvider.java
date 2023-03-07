package net.day.chopped.util.datagen.providers;

import net.day.chopped.Chopped;
import net.day.chopped.registry.groups.ChoppedSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ChoppedSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public ChoppedSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Chopped.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

        add(ChoppedSounds.CASSETTE_WHAT_IT_DO, getCassetteDefinition("what_it_do"));
        add(ChoppedSounds.CASSETTE_BE_A_KING, getCassetteDefinition("be_a_king"));
        add(ChoppedSounds.CASSETTE_LET_ME_OHH, getCassetteDefinition("let_me_ohh"));
        add(ChoppedSounds.CASSETTE_DRINKING_WTH_MY_HEADPHONES_ON, getCassetteDefinition("drinking_wth_my_headphones_on"));
        add(ChoppedSounds.CASSETTE_BIG_SHIPS, getCassetteDefinition("big_ships"));

    }

    private SoundDefinition getCassetteDefinition(String name) {
        return definition().with(sound(new ResourceLocation(Chopped.MOD_ID, "cassette." + name)).stream().attenuationDistance(48));
    }

}
