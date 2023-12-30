package dev.sacrum.mystveil.item;

import dev.sacrum.mystveil.Mystveil;
import dev.sacrum.mystveil.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItems {
    public static final Item SEED = registerItem("seed", new Item(new FabricItemSettings()));
    private static void addToNatureCategory(FabricItemGroupEntries entries) {
        entries.add(SEED);
        entries.add(ModBlocks.MYSTVEIL_DIRT_BLOCK);
        entries.add(ModBlocks.MYSTVEIL_GRASS_BLOCK);
    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Mystveil.path(name), item);
    }
    public static void registerModItems() {
        Mystveil.LOGGER.info("Registering" + Mystveil.namespace + "Items");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addToNatureCategory);
    }
    public static void registerItemGroups() {
        Mystveil.LOGGER.info("Registering" + Mystveil.namespace + "Item Groups");
    }
    public static final ItemGroup MYSTVEIL = Registry.register(Registries.ITEM_GROUP,
            Mystveil.path("mystveil"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.mystveil"))
                    .icon(() -> new ItemStack(ModItems.SEED)).entries((displayContext, entries) -> {
                    }).build());
}
