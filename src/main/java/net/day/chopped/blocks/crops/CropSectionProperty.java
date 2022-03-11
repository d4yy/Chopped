package net.day.chopped.blocks.crops;

import net.minecraft.util.StringRepresentable;

public enum CropSectionProperty implements StringRepresentable {
    STALKBASE("stalkbase"),
    STALK1("stalk1"),
    STALK2("stalk2"),
    TOP("top");

    private final String name;

    private CropSectionProperty(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
