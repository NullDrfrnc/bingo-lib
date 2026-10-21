package com.nullptrexc.bingolib.network.payload;

import com.mojang.serialization.Codec;
import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventRequestAllEventsPayload() implements CustomPacketPayload {
    public static final Type<BingoEventRequestAllEventsPayload> TYPE = new Type<>(BingoLib.id("request_all_events"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventRequestAllEventsPayload> CODEC = StreamCodec.unit(new BingoEventRequestAllEventsPayload());
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
