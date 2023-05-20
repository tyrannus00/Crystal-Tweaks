package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void removeFallDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (Config.noFalldamage && source.isIn(DamageTypeTags.IS_FALL)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LimbAnimator;setSpeed(F)V"
            )
    )
    private void removeDamageCooldown(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (Config.noDamageCooldown) {
            timeUntilRegen = 0;
        }
    }

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;scheduleVelocityUpdate()V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void cancelExplosionKnockback(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, float f, boolean bl, float g, boolean bl2, Entity entity2) {
        if (Config.disableExplosionKnockback && source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            velocityModified = false;
        }
    }
}