package de.tyrannus.crystaltweaks;

import com.google.common.collect.MapMaker;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.Objects;

public class DamableFakePlayer extends FakePlayer {

    private record FakePlayerKey(ServerWorld world, GameProfile profile) { }
    private static final Map<FakePlayerKey, DamableFakePlayer> FAKE_PLAYER_MAP = new MapMaker().weakValues().makeMap();

    protected DamableFakePlayer(ServerWorld world, GameProfile profile) {
        super(world, profile);
    }

    public static DamableFakePlayer get(ServerWorld world, GameProfile profile) {
        Objects.requireNonNull(world, "World may not be null.");
        Objects.requireNonNull(profile, "Game profile may not be null.");

        return FAKE_PLAYER_MAP.computeIfAbsent(new FakePlayerKey(world, profile), key -> new DamableFakePlayer(key.world, key.profile));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return false;
    }
}
