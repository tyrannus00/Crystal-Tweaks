package de.tyrannus.crystaltweaks;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

public class CrystalTweaks implements ModInitializer {

    // TODO Crystals, Beds & Anchors Explosion strengths?
    // TODO Remove knock back caused by explosions?
    // TODO Make xp and arrows unaffected by explosion knock back

    public final String MOD_ID = "crystal-tweaks";

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, Config.class);
    }
}