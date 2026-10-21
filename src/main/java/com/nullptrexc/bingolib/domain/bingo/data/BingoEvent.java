package com.nullptrexc.bingolib.domain.bingo.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;

public class BingoEvent implements Comparable<BingoEvent>{
    private String id = "";
    private String name = "";

    public BingoEvent() {
    }

    public BingoEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BingoEvent(BingoEvent.Data packed) {
        this.id = packed.id;
        this.name = packed.name;
    }

    public BingoEvent.Data packed() {
        return new BingoEvent.Data(
                this.id,
                this.name
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

    public record Data(String id, String name) implements Comparable<Data> {
        public static final Codec<BingoEvent.Data> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                        Codec.STRING.fieldOf("id").forGetter(BingoEvent.Data::id),
                        Codec.STRING.fieldOf("name").forGetter(BingoEvent.Data::name)
                ).apply(i, BingoEvent.Data::new)
        );

        @Override
        public int compareTo(@NonNull Data o) {
            return this.name().compareTo(o.name());
        }
    }
}