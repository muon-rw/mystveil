package dev.sacrum.mystveil.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
@Environment(EnvType.CLIENT)
public class ModDimensionEffects extends DimensionEffects {
    public ModDimensionEffects() {
        super(Float.NaN, false, DimensionEffects.SkyType.NORMAL, false, true);
    }

    //@Override
    //public float @Nullable [] getFogColorOverride(float skyAngle, float tickDelta) {
    //    return null;
    //}

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return true;
    }
}
