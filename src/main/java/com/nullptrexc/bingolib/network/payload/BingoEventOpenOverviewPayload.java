package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventOpenOverviewPayload() implements CustomPacketPayload {
    public static final Type<BingoEventOpenOverviewPayload> TYPE = new Type<>(BingoLib.id("open_event_overview"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventOpenOverviewPayload> CODEC = StreamCodec.unit(new BingoEventOpenOverviewPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
