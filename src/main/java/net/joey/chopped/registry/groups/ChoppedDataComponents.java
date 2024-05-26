package net.joey.chopped.registry.groups;

import net.joey.chopped.Chopped;
import net.joey.chopped.items.ChoppedBundleContents;
import net.joey.chopped.registry.ChoppedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ChoppedDataComponents {
    public static final Supplier<DataComponentType<ChoppedBundleContents>> CHOPPED_BUNDLE_CONTENTS = ChoppedRegistry.DATA_COMPONENTS.registerComponentType("chopped_bundle_contents", builder -> builder.persistent(ChoppedBundleContents.CODEC).networkSynchronized(ChoppedBundleContents.STREAM_CODEC).cacheEncoding());

    public static void register () {}
}
