package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.data.BingoEvent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record BingoEventGetAllPayload(List<BingoEvent.Data> events) implements CustomPacketPayload {
    public static final Type<BingoEventGetAllPayload> TYPE = new Type<>(BingoLib.id("get_all_events"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventGetAllPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BingoEvent.Data.CODEC).apply(ByteBufCodecs.list()),
            BingoEventGetAllPayload::events,
            BingoEventGetAllPayload::new
    );
    
    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
