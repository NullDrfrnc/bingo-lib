package com.nullptrexc.bingolib.domain.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.UUID;

public class Participant implements Comparable<Participant> {
    private UUID uuid;
    private String name;

    public Participant(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
    
    public Participant(Participant.Data packed) {
        this.uuid = packed.uuid();
        this.name = packed.name();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public int compareTo(@NonNull Participant o) {
        return this.uuid.compareTo(o.uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Participant setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Participant setName(String name) {
        this.name = name;
        return this;
    }

    public Participant.Data packed() {
        return new Participant.Data(
                this.uuid,
                this.name
        );
    }

    public record Data(UUID uuid, String name) implements Comparable<Data> {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                        UUIDUtil.STRING_CODEC.fieldOf("uuid").forGetter(Data::uuid),
                        Codec.STRING.fieldOf("name").forGetter(Data::name)
                ).apply(i, Data::new)
        );

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            Data data = (Data) o;
            return Objects.equals(uuid, data.uuid);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(uuid);
        }

        @Override
        public int compareTo(Participant.@NonNull Data o) {
            return uuid.compareTo(o.uuid());
        }
    }
}
