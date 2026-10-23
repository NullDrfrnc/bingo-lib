package com.nullptrexc.bingolib.domain.manager;

import com.nullptrexc.bingolib.domain.data.BingoEvent;
import com.nullptrexc.bingolib.domain.data.Participant;
import com.nullptrexc.bingolib.domain.saved_data.SavedBingoEventData;
import com.nullptrexc.bingolib.domain.commands.Results;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.UUID;

public class BingoEventManager {
    private static BingoEventManager INSTANCE = null;

    private BingoEventManager() {
    }

    public static BingoEventManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BingoEventManager();
        }
        return INSTANCE;
    }

    public Results create_event(MinecraftServer server, String event_name) {
        return create_event(
                server,
                event_name.toLowerCase().replace(" ", "_"),
                event_name
        );
    }

    public Results create_event(MinecraftServer server, String event_id, String event_name) {
        return data(server).add(new BingoEvent(
                event_id,
                event_name
        ));
    }
    
    public Results create_event(MinecraftServer server, BingoEvent.Data event) {
        return create_event(
                server,
                event.name()
        );
    }

    public Results remove_event(MinecraftServer server, String bingo_id) {
        return data(server).remove(bingo_id);
    }

    public Results update_event(MinecraftServer server, BingoEvent.Data event) {
        return data(server).update(event);
    }

    public Results update_event(MinecraftServer server, String bingo_id, String bingo_name) {
        return data(server).update(bingo_id, bingo_name);
    }

    public BingoEvent get_event(MinecraftServer server, String bingo_id) {
        return data(server).get(bingo_id);
    }
    
    public List<BingoEvent> get_events(MinecraftServer server) {
        return data(server).list();
    }
    
    public List<BingoEvent.Data> get_packed_events(MinecraftServer server) {
        return data(server).list().stream().map(BingoEvent::packed).toList();
    }

    private SavedBingoEventData data(MinecraftServer server) {
        return SavedBingoEventData.get(server);
    }

    public Results toggle_event_participation(MinecraftServer server, String event_id,  UUID uuid) {
        return data(server).toggle_event_participation(event_id, bingo_player(server, uuid));
    }
    
    public Results join_event(MinecraftServer server, String event_id, UUID uuid) {
        return data(server).join_event(event_id, bingo_player(server, uuid));
    }
    
    public Results leave_event(MinecraftServer server, String event_id, UUID uuid) {
        return data(server).leave_event(event_id, bingo_player(server, uuid));
    }
    
    public Participant bingo_player(MinecraftServer server, UUID uuid) {
        ServerPlayer player = server.getPlayerList().getPlayer(uuid);
        
        if (player == null) 
            return null;
        
        return new Participant(player.getUUID(), player.getName().getString());
    }
}
