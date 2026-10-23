package com.nullptrexc.bingolib.domain.data;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;

public class BingoEvent implements Comparable<BingoEvent>{
    private String id = "";
    private String name = "";
    private Set<Participant> players = Sets.newHashSet();

    public BingoEvent() {
    }

    // Command constructor
    public BingoEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // GUI constructor
    public BingoEvent(String id, String name, Set<Participant> players) {
        this.id = id;
        this.name = name;
        this.players = players;
    }
    
    // Data constructor
    public BingoEvent(BingoEvent.Data data) {
        this.id = data.id();
        this.name = data.name();
        this.players = data.players_unpacked();
    }

    public BingoEvent.Data packed() {
        return new BingoEvent.Data(
                this.id,
                this.name,
                this.players.stream().map(Participant::packed).toList()
        );
    }

    @Override
    public int compareTo(@NonNull BingoEvent o) {
        return this.getName().compareTo(o.getName());
    }

    public String getId() {
        return id;
    }

    public BingoEvent setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BingoEvent setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Participant> getPlayers() {
        return players;
    }

    public BingoEvent setPlayers(Set<Participant> players) {
        this.players = players;
        return this;
    }

    public boolean addPlayer(Participant player) {
        return this.players.add(player);
    }

    public boolean removePlayer(Participant player) {
        return this.players.remove(player);
    }

    public record Data(String id, String name, List<Participant.Data> players) implements Comparable<Data> {
        public static final Codec<BingoEvent.Data> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                        Codec.STRING.fieldOf("id").forGetter(BingoEvent.Data::id),
                        Codec.STRING.fieldOf("name").forGetter(BingoEvent.Data::name),
                        Participant.Data.CODEC.listOf().fieldOf("players").forGetter(Data::players)
                ).apply(i, BingoEvent.Data::new)
        );

        @Override
        public int compareTo(@NonNull Data o) {
            return this.name().compareTo(o.name());
        }

        public Set<Participant> players_unpacked() {
            return Sets.newHashSet(players.stream().map(Participant::new).toList());
        }
    }
}