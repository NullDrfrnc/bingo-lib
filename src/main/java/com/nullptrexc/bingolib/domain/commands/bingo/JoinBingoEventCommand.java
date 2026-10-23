package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nullptrexc.bingolib.domain.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.Results;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class JoinBingoEventCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        MinecraftServer server = server(ctx);
        ServerPlayer player = player(ctx);
        String event_id = getEventId(ctx);

        Results result = BingoEventManager.getInstance().join_event(server, event_id, player.getUUID());

        switch (result) {
            case JOINED -> {
                ctx.getSource().sendSuccess(() -> Component.translatable("command.bingolib.event.join.success", player.getName(), event_id), false);
                return SUCCESS;
            }
            case ALREADY_EXISTS -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.event.join.already_exists", player.getName(), event_id));
                return FAIL;
            }
            case FAIL -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.join.fail", event_id));
                return FAIL;
            }
        }
        return FAIL;
    }
}
