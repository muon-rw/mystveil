package dev.sacrum.mystveil;

import dev.sacrum.mystveil.dimension.ModDimensionEffects;
import dev.sacrum.mystveil.mixin.client.accessors.DimensionEffectsAccessor;
import net.fabricmc.api.ClientModInitializer;

public class MystveilClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		DimensionEffectsAccessor.getEffects().put(Mystveil.path("mystveil"), new ModDimensionEffects());
	}
}