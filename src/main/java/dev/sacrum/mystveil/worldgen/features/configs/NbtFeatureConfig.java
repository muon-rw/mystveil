package dev.sacrum.mystveil.worldgen.features.configs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public class NbtFeatureConfig implements FeatureConfig {
    public static final Codec<NbtFeatureConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            Identifier.CODEC.fieldOf("processors").forGetter(nbtFeatureConfig -> nbtFeatureConfig.processor),
            Identifier.CODEC.fieldOf("post_processors").orElse(new Identifier("minecraft:empty")).forGetter(nbtFeatureConfig -> nbtFeatureConfig.postProcessor),
            Codec.mapPair(Identifier.CODEC.fieldOf("resourcelocation"), Codecs.POSITIVE_INT.fieldOf("weight")).codec().listOf().fieldOf("nbt_entries").forGetter(nbtFeatureConfig -> nbtFeatureConfig.nbtResourcelocationsAndWeights),
            Codec.INT.fieldOf("structure_y_offset").orElse(0).forGetter(nbtFeatureConfig -> nbtFeatureConfig.structureYOffset)
    ).apply(configInstance, NbtFeatureConfig::new));

    public final List<Pair<Identifier, Integer>> nbtResourcelocationsAndWeights;
    public final Identifier processor;
    public final Identifier postProcessor;
    public final int structureYOffset;

    public NbtFeatureConfig(Identifier processor,
                            Identifier postProcessor,
                            List<Pair<Identifier, Integer>> nbtIdentifiersAndWeights,
                            int structureYOffset)
    {
        this.nbtResourcelocationsAndWeights = nbtIdentifiersAndWeights;
        this.processor = processor;
        this.postProcessor = postProcessor;
        this.structureYOffset = structureYOffset;
    }
}