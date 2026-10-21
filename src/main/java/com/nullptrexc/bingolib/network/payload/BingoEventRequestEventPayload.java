package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventRequestEventPayload(String bingo_id) implements CustomPacketPayload {
    public static final Type<BingoEventRequestEventPayload> TYPE = new Type<>(BingoLib.id("request_event"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventRequestEventPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            BingoEventRequestEventPayload::bingo_id,
            BingoEventRequestEventPayload::new
    );
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
