package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nullptrexc.bingolib.domain.bingo.data.BingoEvent;
import com.nullptrexc.bingolib.domain.bingo.manager.BingoEventManager;
import com.nullptrexc.bingolib.network.payload.BingoEventGetAllPayload;
import com.nullptrexc.bingolib.network.payload.BingoEventOpenOverviewPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class OpenBingoEventScreenCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = getPlayer(ctx);
        ServerPlayNetworking.send(player, new BingoEventOpenOverviewPayload());
        return SUCCESS;
    }
} 
