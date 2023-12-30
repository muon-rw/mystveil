package dev.sacrum.mystveil.block;

import dev.sacrum.mystveil.Mystveil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block MYSTVEIL_GRASS_BLOCK = registerBlock("grass_block",
            new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).luminance(2)));
    public static final Block MYSTVEIL_DIRT_BLOCK = registerBlock("dirt_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).luminance(1)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Mystveil.path(name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Mystveil.path(name),
            new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
        Mystveil.LOGGER.info("Registering" + Mystveil.namespace + "Blocks");
    }
}
