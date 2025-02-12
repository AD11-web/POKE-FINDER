package com.example.project_1;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("types")
    private List<TypeWrapper> types;

    public String getName() {
        return name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<TypeWrapper> getTypes() {
        return types;
    }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        @SerializedName("other")
        private Other other;

        public String getFrontDefault() {
            return frontDefault;
        }

        public Other getOther() {
            return other;
        }
    }

    public static class Other {
        @SerializedName("official-artwork")
        private OfficialArtwork officialArtwork;

        public OfficialArtwork getOfficialArtwork() {
            return officialArtwork;
        }
    }

    public static class OfficialArtwork {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    public static class TypeWrapper {
        @SerializedName("type")
        private PokemonType type; // Changed from Type to PokemonType

        public PokemonType getType() {
            return type;
        }
    }

    public static class PokemonType { // Changed class name from Type to PokemonType
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
