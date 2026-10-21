package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import com.nullptrexc.bingolib.domain.commands.Results;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class RemoveBingoEventCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) {
        String bingo_id = getEventId(ctx);
        
        switch (eventmanager().remove_event(server(ctx), bingo_id)) {
            case Results.SUCCESS -> {
                ctx.getSource().sendSuccess(() -> Component.translatable("command.bingolib.event.remove.success", bingo_id), false);
                return SUCCESS;
            }
            case NOT_FOUND -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.not_found", bingo_id));
                return FAIL;
            }
            case Results.FAIL -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.remove.fail", bingo_id));
                return FAIL;
            }
        }
        
        return FAIL;
    }
}
