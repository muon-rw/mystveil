package dev.sacrum.mystveil.worldgen.features;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import dev.sacrum.mystveil.Mystveil;
import dev.sacrum.mystveil.mixin.accessors.StructureTemplateAccessor;
import dev.sacrum.mystveil.worldgen.features.configs.NbtFeatureConfig;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
// Functionality courtesy of and heavily based on work by TelepathicGrunt. Many thanks!
public class NbtFeature <T extends NbtFeatureConfig> extends Feature<T> {

    private static final Identifier EMPTY = new Identifier("minecraft", "empty");
    public static <T> T getRandomEntry(List<Pair<T, Integer>> rlList, Random random) {
        double totalWeight = 0.0;

        // Compute the total weight of all items together.
        for (Pair<T, Integer> pair : rlList) {
            totalWeight += pair.getSecond();
        }

        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = random.nextFloat() * totalWeight; index < rlList.size() - 1; ++index) {
            randomWeightPicked -= rlList.get(index).getSecond();
            if (randomWeightPicked <= 0.0) break;
        }

        return rlList.get(index).getFirst();
    }
    public NbtFeature(Codec<T> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(FeatureContext<T> context) {
        Identifier nbtRL = getRandomEntry(context.getConfig().nbtResourcelocationsAndWeights, context.getRandom());

        StructureTemplateManager structureManager = context.getWorld().toServerWorld().getStructureTemplateManager();
        Optional<StructureTemplate> template = structureManager.getTemplate(nbtRL);
        if(template.isEmpty()) {
            Mystveil.LOGGER.error("Identifier to the specified nbt file was not found! : {}", nbtRL);
            return false;
        }
        BlockRotation rotation = BlockRotation.random(context.getRandom());

        // For proper offsetting the feature so it rotate properly around position parameter.
        BlockPos halfLengths = new BlockPos(
                template.get().getSize().getX() / 2,
                template.get().getSize().getY() / 2,
                template.get().getSize().getZ() / 2);

        BlockPos.Mutable mutable = new BlockPos.Mutable().set(context.getOrigin());

        // offset the feature's position
        BlockPos position = context.getOrigin().up(context.getConfig().structureYOffset);

        StructurePlacementData placementsettings = (new StructurePlacementData()).setRotation(rotation).setPosition(halfLengths).setIgnoreEntities(false);
        Registry<StructureProcessorList> processorListRegistry = context.getWorld().toServerWorld().getServer().getRegistryManager().get(RegistryKeys.PROCESSOR_LIST);
        StructureProcessorList emptyProcessor = processorListRegistry.get(EMPTY);

        Optional<StructureProcessorList> processor = processorListRegistry.getOrEmpty(context.getConfig().processor);
        processor.orElse(emptyProcessor).getList().forEach(placementsettings::addProcessor); // add all processors
        mutable.set(position).move(-halfLengths.getX(), 0, -halfLengths.getZ()); // pivot
        template.get().place(context.getWorld(), mutable, mutable, placementsettings, context.getRandom(), Block.NO_REDRAW);

        // Post-processors
        // For all processors that are sensitive to neighboring blocks such as vines.
        // Post processors will place the blocks themselves so we will not do anything with the return of Structure.process
        placementsettings.clearProcessors();
        Optional<StructureProcessorList> postProcessor = processorListRegistry.getOrEmpty(context.getConfig().postProcessor);
        postProcessor.orElse(emptyProcessor).getList().forEach(placementsettings::addProcessor); // add all post processors
        List<StructureTemplate.StructureBlockInfo> list = placementsettings.getRandomBlockInfos(((StructureTemplateAccessor)template.get()).getBlocks(), mutable).getAll();
        StructureTemplate.process(context.getWorld(), mutable, mutable, placementsettings, list);

        return true;
    }
}