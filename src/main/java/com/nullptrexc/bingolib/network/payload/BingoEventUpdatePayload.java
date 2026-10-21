package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.bingo.data.BingoEvent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventUpdatePayload(BingoEvent.Data event) implements CustomPacketPayload {
    public static final Type<BingoEventUpdatePayload> TYPE = new Type<>(BingoLib.id("update_event"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventUpdatePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BingoEvent.Data.CODEC),
            BingoEventUpdatePayload::event,
            BingoEventUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
