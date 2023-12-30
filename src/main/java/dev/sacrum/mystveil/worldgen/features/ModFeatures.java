package dev.sacrum.mystveil.worldgen.features;

import dev.sacrum.mystveil.Mystveil;
import dev.sacrum.mystveil.worldgen.features.configs.NbtFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
public class ModFeatures {
    public static final Feature<NbtFeatureConfig> NBT_FEATURE = registerFeature("nbt_feature", new NbtFeature(NbtFeatureConfig.CODEC));
    private static Feature registerFeature(String name, Feature feature) {
        return Registry.register(Registries.FEATURE, Mystveil.path(name), feature);
    }
    public static void registerModFeatures() {
        Mystveil.LOGGER.info("Registering" + Mystveil.namespace + "Features");
    }
}
