package com.nullptrexc.bingolib.domain.bingo.data;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import org.jspecify.annotations.NonNull;
import org.lwjgl.vulkan.VkThrottleHintSubmitInfoSEC;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BingoEvent implements Comparable<BingoEvent>{
    private String id = "";
    private String name = "";
    private Set<UUID> players = Sets.newHashSet();

    public BingoEvent() {
    }

    // Command constructor
    public BingoEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // GUI constructor
    public BingoEvent(String id, String name, Set<UUID> players) {
        this.id = id;
        this.name = name;
        this.players = players;
    }

    public BingoEvent(BingoEvent.Data packed) {
        this.id = packed.id;
        this.name = packed.name;
    }

    public BingoEvent.Data packed() {
        return new BingoEvent.Data(
                this.id,
                this.name,
                this.players.stream().toList()
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

    public Set<UUID> getPlayers() {
        return players;
    }

    public BingoEvent setPlayers(Set<UUID> players) {
        this.players = players;
        return this;
    }

    public record Data(String id, String name, List<UUID> players) implements Comparable<Data> {
        public static final Codec<BingoEvent.Data> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                        Codec.STRING.fieldOf("id").forGetter(BingoEvent.Data::id),
                        Codec.STRING.fieldOf("name").forGetter(BingoEvent.Data::name),
                        UUIDUtil.STRING_CODEC.listOf().optionalFieldOf("players", List.of()).forGetter(BingoEvent.Data::players)
                ).apply(i, BingoEvent.Data::new)
        );

        @Override
        public int compareTo(@NonNull Data o) {
            return this.name().compareTo(o.name());
        }
    }
}