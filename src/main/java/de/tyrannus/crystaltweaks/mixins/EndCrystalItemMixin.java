package de.tyrannus.crystaltweaks.mixins;

import de.tyrannus.crystaltweaks.Config;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalItem.class)
public class EndCrystalItemMixin {

    @Inject(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;up()Lnet/minecraft/util/math/BlockPos;"),
            cancellable = true
    )
    private void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        // Checking for a second air block above to simulate 1.12 behaviour

        if (Config.needDoubleAir) {
            BlockPos pos = context.getBlockPos().up(2);

            if (!context.getWorld().isAir(pos)) {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }

    @Redirect(
            method = "useOnBlock",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/util/math/Box"
            )
    )
    private Box redirectBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        // Modifying the box being checked for intersecting entities

        if (Config.smallEntityBox) {
            return new Box(x1, y1, z1, x2, y1 + 1, z2);
        }

        return new Box(x1, y1, z1, x2, y2, z2);
    }
}