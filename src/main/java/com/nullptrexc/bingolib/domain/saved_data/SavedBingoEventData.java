package com.nullptrexc.bingolib.domain.saved_data;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.data.BingoEvent;
import com.nullptrexc.bingolib.domain.commands.Results;
import com.nullptrexc.bingolib.domain.data.Participant;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SavedBingoEventData extends SavedData {
    private static final Codec<SavedBingoEventData> CODEC = Codec.list(BingoEvent.Data.CODEC).xmap(
            SavedBingoEventData::new,
            SavedBingoEventData::getData
    );
    public static final SavedDataType<SavedBingoEventData> TYPE = new SavedDataType<>(
            BingoLib.id("bingo_events"),
            SavedBingoEventData::new,
            CODEC,
            null
    );

    private Set<BingoEvent> bingo_events = Sets.newHashSet();

    public SavedBingoEventData() {
    }

    public Results add(BingoEvent event) {
        if (!verifyBingo(event)) {
            return Results.FAIL;
        }

        if (get(event.getId()) != null) {
            return Results.ALREADY_EXISTS;
        }

        bingo_events.add(event);
        setDirty();
        return Results.SUCCESS;
    }

    public Results remove(String bingo_id) {
        BingoEvent event = get(bingo_id);

        if (event == null) {
            return Results.NOT_FOUND;
        }

        if (bingo_events.remove(event)) {
            setDirty();
            return Results.SUCCESS;
        }

        return Results.FAIL;
    }

    public BingoEvent get(String bingo_id) {
        return bingo_events.stream()
                .filter(bingo -> bingo.getId().equals(bingo_id))
                .findFirst()
                .orElse(null);
    }

    public Results update(BingoEvent.Data event) {
        BingoEvent managed = get(event.id());

        if (!verifyBingo(event) || managed == null) {
            return Results.FAIL;
        }

        managed.setName(event.name());
        managed.setPlayers(Sets.newHashSet(event.players_unpacked()));

        this.setDirty();
        return Results.SUCCESS;
    }

    public Results update(String bingo_id, String bingo_name) {
        BingoEvent managed = get(bingo_id);

        if (managed == null) {
            return Results.FAIL;
        }

        managed.setName(bingo_name);

        this.setDirty();
        return Results.SUCCESS;
    }

    public List<BingoEvent> list() {
        return bingo_events.stream().sorted().toList();
    }

    protected boolean verifyBingo(BingoEvent event) {
        return !(event.getId().isBlank() || event.getName().isBlank());
    }

    protected boolean verifyBingo(BingoEvent.Data packed) {
        return !(packed.id().isBlank() || packed.name().isBlank());
    }

    public static SavedBingoEventData get(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    private SavedBingoEventData(List<BingoEvent.Data> packed_list) {
        bingo_events = packed_list.stream().map(BingoEvent::new).collect(Collectors.toCollection(HashSet::new));
    }

    private List<BingoEvent.Data> getData() {
        return bingo_events.stream().map(BingoEvent::packed).toList();
    }

    public Results toggle_event_participation(String event_id, Participant player) {
        if (player == null) 
            return Results.NOT_FOUND;
        
        BingoEvent event = get(event_id);

        boolean result = event.addPlayer(player);

        if (result) {
            setDirty();
            return Results.JOINED;
        }

        result = event.removePlayer(player);

        if (result) {
            setDirty();
            return Results.LEFT;
        }

        return Results.FAIL;
    }

    public Results join_event(String event_id, Participant player) {
        if (player == null)
            return Results.NOT_FOUND;
        
        boolean result = get(event_id).addPlayer(player);

        if (result) {
            setDirty();
            return Results.JOINED;
        }

        return Results.ALREADY_EXISTS;
    }

    public Results leave_event(String event_id, Participant player) {
        if (player == null)
            return Results.NOT_FOUND;
        
        boolean result = get(event_id).removePlayer(player);

        if (result) {
            setDirty();
            return Results.LEFT;
        }

        return Results.NOT_FOUND;
    }
}
