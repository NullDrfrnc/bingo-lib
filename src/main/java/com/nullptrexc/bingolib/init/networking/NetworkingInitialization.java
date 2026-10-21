package com.nullptrexc.bingolib.init.networking;

import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.bingo.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.Results;
import com.nullptrexc.bingolib.network.payload.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

public class NetworkingInitialization {
    public static void init() {
        // server -> client

        PayloadTypeRegistry.clientboundPlay().register(
                BingoEventGetAllPayload.TYPE,
                BingoEventGetAllPayload.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                BingoEventOpenOverviewPayload.TYPE,
                BingoEventOpenOverviewPayload.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                BingoEventGetEventPayload.TYPE,
                BingoEventGetEventPayload.CODEC
        );

        PayloadTypeRegistry.clientboundPlay().register(
                BingoEventOperationSuccessfullPayload.TYPE,
                BingoEventOperationSuccessfullPayload.CODEC
        );
        
        PayloadTypeRegistry.clientboundPlay().register(
                BingoEventToggledParticipationSuccesfully.TYPE,
                BingoEventToggledParticipationSuccesfully.CODEC
        );

        // client -> server

        PayloadTypeRegistry.serverboundPlay().register(
                BingoEventRequestAllEventsPayload.TYPE,
                BingoEventRequestAllEventsPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(BingoEventRequestAllEventsPayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            MinecraftServer server = context.server();

            ServerPlayNetworking.send(player, new BingoEventGetAllPayload(
                    BingoEventManager.getInstance().get_packed_events(server)
            ));
        });

        PayloadTypeRegistry.serverboundPlay().register(
                BingoEventDeletePayload.TYPE,
                BingoEventDeletePayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(BingoEventDeletePayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            MinecraftServer server = context.server();

            if (!checkPermissions(player, BingoEventDeletePayload.TYPE)) {
                return;
            }

            Results result = BingoEventManager.getInstance().remove_event(server, payload.event_id());


            switch (result) {
                case SUCCESS -> ServerPlayNetworking.send(
                        player,
                        new BingoEventOperationSuccessfullPayload()
                );
            }
        });

        PayloadTypeRegistry.serverboundPlay().register(
                BingoEventRequestEventPayload.TYPE,
                BingoEventRequestEventPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(BingoEventRequestEventPayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            MinecraftServer server = context.server();

            if (!checkPermissions(player, BingoEventRequestEventPayload.TYPE)) {
                return;
            }

            ServerPlayNetworking.send(player, new BingoEventGetEventPayload(
                    BingoEventManager.getInstance().get_event(server, payload.bingo_id()).packed()
            ));
        });

        PayloadTypeRegistry.serverboundPlay().register(
                BingoEventCreatePayload.TYPE,
                BingoEventCreatePayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(BingoEventCreatePayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            MinecraftServer server = context.server();

            if (!checkPermissions(player, BingoEventCreatePayload.TYPE)) {
                return;
            }

            Results result = BingoEventManager.getInstance().create_event(server, payload.event());

            switch (result) {
                case SUCCESS -> ServerPlayNetworking.send(
                        player,
                        new BingoEventOperationSuccessfullPayload()
                );
            }
        });

        PayloadTypeRegistry.serverboundPlay().register(
                BingoEventUpdatePayload.TYPE,
                BingoEventUpdatePayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(BingoEventUpdatePayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            MinecraftServer server = context.server();

            if (!checkPermissions(player, BingoEventUpdatePayload.TYPE)) {
                return;
            }

            Results result = BingoEventManager.getInstance().update_event(server, payload.event());

            switch (result) {
                case SUCCESS -> ServerPlayNetworking.send(
                        player,
                        new BingoEventOperationSuccessfullPayload()
                );
            }
        });
        
        PayloadTypeRegistry.serverboundPlay().register(
                BingoEventRequestToggleParticipationPayload.TYPE,
                BingoEventRequestToggleParticipationPayload.CODEC
        );
        
        ServerPlayNetworking.registerGlobalReceiver(BingoEventRequestToggleParticipationPayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            MinecraftServer server = context.server();
            
            Results result = BingoEventManager.getInstance().toggle_event_participation(server, payload.event_id(), player.getUUID());
            
            String event_name = BingoEventManager.getInstance().get_event(server, payload.event_id()).getName();
            
            switch (result) {
                case JOINED -> ServerPlayNetworking.send(
                        player,
                        new BingoEventToggledParticipationSuccesfully(event_name, true)
                );
                case LEFT -> ServerPlayNetworking.send(
                        player,
                        new BingoEventToggledParticipationSuccesfully(event_name, false)
                );
            }
        });
    }

    private static boolean checkPermissions(ServerPlayer player, CustomPacketPayload.Type type) {
        if (!player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
            // Warning to show someone might be tempering with our endpoints
            BingoLib.LOGGER.error("Player [{}] is not permitted to execute this [{}]", player.getName().getString(), type.id());
            return false;
        }

        return true;
    }
}
