package com.nullptrexc.bingolib.domain.bingo.manager;

import com.mojang.brigadier.context.CommandContext;
import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.bingo.data.BingoEvent;
import com.nullptrexc.bingolib.domain.bingo.saved_data.SavedBingoEventData;
import com.nullptrexc.bingolib.domain.commands.Results;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.BubbleColumnBlock;

import java.util.List;

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
}
