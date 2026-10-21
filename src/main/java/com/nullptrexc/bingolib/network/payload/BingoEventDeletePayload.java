package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record BingoEventDeletePayload(String event_id) implements CustomPacketPayload {
    public static final Type<BingoEventDeletePayload> TYPE = new Type<>(BingoLib.id("delete_event"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventDeletePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            BingoEventDeletePayload::event_id,
            BingoEventDeletePayload::new
    );
    
    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
