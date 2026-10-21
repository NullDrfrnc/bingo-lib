package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nullptrexc.bingolib.network.payload.BingoEventOpenOverviewPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class OpenBingoEventScreenCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = player(ctx);
        ServerPlayNetworking.send(player, new BingoEventOpenOverviewPayload());
        return SUCCESS;
    }
} 
