package com.jadonvb;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.entity.fakeplayer.FakePlayer;
import net.minestom.server.entity.fakeplayer.FakePlayerOption;
import net.minestom.server.event.player.PlayerEntityInteractEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class NPC {

    private FakePlayer fakePlayer;
    private final PlayerSkin playerSkin;

    public NPC(FakePlayer fakePlayer, Skin skin) {
        playerSkin = new PlayerSkin(skin.getValue(),skin.getSignature());
        this.fakePlayer = fakePlayer;
        initiateFakePlayer();
        initiateEvents();
    }

    private void initiateFakePlayer() {
        fakePlayer.spawn();
        fakePlayer.teleport(new Pos(0.5,10,-3.5,0,0));
        fakePlayer.setSkin(playerSkin);
        fakePlayer.setCustomName(Component.text("Hoihoi",TextColor.color(255, 137, 217)));
        fakePlayer.setCustomNameVisible(true);
    }

    private void initiateEvents() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerEntityInteractEvent.class, event -> {
            if (event.getHand().equals(Player.Hand.OFF)) {
                return;
            }

            if (event.getTarget().equals(fakePlayer)) {
                Audiences.players().sendMessage(Component.text("houihoi"));
            }
        });
    }

    public FakePlayer getFakePlayer() {
        return fakePlayer;
    }
}
