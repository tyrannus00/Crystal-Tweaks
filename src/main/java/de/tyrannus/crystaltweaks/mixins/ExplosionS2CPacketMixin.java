package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExplosionS2CPacket.class)
public class ExplosionS2CPacketMixin {

    @ModifyVariable(
            method = "<init>(DDDFLjava/util/List;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private static @Nullable Vec3d modifyPlayerVelocity(Vec3d value) {
        if (Config.disableExplosionKnockback) {
            return null;
        }

        return value;
    }
}