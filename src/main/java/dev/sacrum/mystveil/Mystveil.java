package dev.sacrum.mystveil;

import dev.sacrum.mystveil.block.ModBlocks;
import dev.sacrum.mystveil.item.ModItems;
import dev.sacrum.mystveil.worldgen.features.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mystveil implements ModInitializer {
	public static final String namespace = "mystveil";
    public static final Logger LOGGER = LoggerFactory.getLogger(namespace);
	public static Identifier path(String id) {
		return new Identifier(namespace, id);
	}
	@Override
	public void onInitialize() {
		LOGGER.info("Loading" + namespace);
		ModItems.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModFeatures.registerModFeatures();
	}
}