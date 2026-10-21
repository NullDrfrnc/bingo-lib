package com.nullptrexc.bingolib.network.payload;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BingoEventOperationSuccessfullPayload() implements CustomPacketPayload {
    public static final Type<BingoEventOperationSuccessfullPayload> TYPE = new Type<>(BingoLib.id("operation_successfull"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BingoEventOperationSuccessfullPayload> CODEC = StreamCodec.unit(new BingoEventOperationSuccessfullPayload());
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
