package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RespawnAnchorBlock.class)
public class RespawnAnchorBlockMixin {

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void noBedExplosions(BlockState state, World world, BlockPos explodedPos, CallbackInfo ci) {
        if (Config.noAnchorExplosions) {
            ci.cancel();
        }
    }
}