package com.jadonvb;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.entity.fakeplayer.FakePlayer;
import net.minestom.server.entity.fakeplayer.FakePlayerOption;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Server {
    private InstanceContainer instanceContainer;

    public void startServer() {
        MinecraftServer minecraftServer = MinecraftServer.init();

        DimensionType fullBrightDimensionType = DimensionType.builder(NamespaceID.from("ja90n:lobby"))
                .ambientLight(2.0f)
                .build();
        MinecraftServer.getDimensionTypeManager().addDimension(fullBrightDimensionType);

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceContainer = instanceManager.createInstanceContainer(new AnvilLoader(Path.of("Lobby")));

        MojangAuth.init();

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0.5,10,0.5,180,0));
        });

        initiateFakePlayer();

        // Start the server
        minecraftServer.start("0.0.0.0", 25567);
    }

    private void initiateFakePlayer() {
        FakePlayerOption fakePlayerOption = new FakePlayerOption();
        fakePlayerOption.setInTabList(false);
        FakePlayer.initPlayer(UUID.randomUUID(),"Hoihoi",fakePlayerOption, fakePlayer -> {
            new NPC(fakePlayer,Skin.SMIKKELBEER);
        } );
    }



}
