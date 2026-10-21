package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventToggledParticipationSuccesfully(String event_name, boolean joined) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BingoEventToggledParticipationSuccesfully> TYPE =
            new CustomPacketPayload.Type<>(BingoLib.id("toggled_participation_succesfully"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventToggledParticipationSuccesfully> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            BingoEventToggledParticipationSuccesfully::event_name,
            ByteBufCodecs.BOOL,
            BingoEventToggledParticipationSuccesfully::joined,
            BingoEventToggledParticipationSuccesfully::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}