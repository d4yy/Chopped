package net.joey.chopped.blocks.crops;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CropSectionProperty implements StringRepresentable {
    STALKBASE("stalkbase"),
    STALK1("stalk1"),
    STALK2("stalk2"),
    TOP("top");

    private final String name;

    CropSectionProperty(String name) {
        this.name = name;
    }

    @NotNull
    public String getSerializedName() {
        return this.name;
    }
}
