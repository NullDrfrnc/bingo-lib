package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.data.BingoEvent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventCreatePayload(BingoEvent.Data event) implements CustomPacketPayload {
    public static final Type<BingoEventCreatePayload> TYPE = new Type<>(BingoLib.id("create_event"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventCreatePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BingoEvent.Data.CODEC),
            BingoEventCreatePayload::event,
            BingoEventCreatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
