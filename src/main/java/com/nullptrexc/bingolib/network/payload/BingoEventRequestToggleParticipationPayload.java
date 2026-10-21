package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventRequestToggleParticipationPayload(String event_id) implements CustomPacketPayload {
    
    public static final Type<BingoEventRequestToggleParticipationPayload> TYPE = new Type<>(BingoLib.id("request_toggle_participation"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventRequestToggleParticipationPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            BingoEventRequestToggleParticipationPayload::event_id,
            BingoEventRequestToggleParticipationPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
