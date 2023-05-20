package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void removeAttackCoolDown(float baseTime, CallbackInfoReturnable<Float> cir) {
        if (Config.noWeaponCooldown) {
            cir.setReturnValue(1f);
        }
    }
}