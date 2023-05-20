package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntityMixin extends Entity {

    public EndCrystalEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Min age checking

        if (!source.isIn(DamageTypeTags.IS_EXPLOSION) && age < Config.crystalMinAge) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean redirectExplosiveCheck(DamageSource source, TagKey<DamageType> tag) {
        // Making the crystals also explode when destroyed by other crystals

        if (Config.crystalsAlwaysExplode) {
            return false;
        }

        return source.isIn(tag);
    }
}