package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nullptrexc.bingolib.domain.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.Results;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class LeaveBingoEventCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        MinecraftServer server = server(ctx);
        String event_id = getEventId(ctx);
        ServerPlayer player = player(ctx);

        Results result = BingoEventManager.getInstance().leave_event(server, event_id, player.getUUID());
        
        switch (result) {
            case LEFT -> {
                ctx.getSource().sendSuccess(() -> Component.translatable("command.bingolib.event.leave.success", player.getName(), event_id), false);
                return SUCCESS;
            }
            case NOT_FOUND -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.event.leave.not_found", player.getName(), event_id));
                return FAIL;
            }
            case FAIL -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.leave.fail", event_id));
                return FAIL;
            }
        }
        
        return FAIL;
    }
}
