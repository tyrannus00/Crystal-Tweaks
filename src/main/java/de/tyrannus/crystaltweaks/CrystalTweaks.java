package de.tyrannus.crystaltweaks;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class CrystalTweaks implements ModInitializer {

    // TODO Crystals, Beds & Anchors Explosion strengths?
    // TODO Make xp and arrows unaffected by explosion knock back
    // TODO Server Side fake players with corresponding command

    public static final String MOD_ID = "crystal-tweaks";

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, Config.class);

        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.STYLING_PHASE, (sender, message) -> {
            if (Config.greenText && message.getString().startsWith(">")) {
                return CompletableFuture.completedFuture(message.copy().styled(style -> style.withColor(Color.GREEN.getRGB())));
            }

            return CompletableFuture.completedFuture(message);
        });
    }
}