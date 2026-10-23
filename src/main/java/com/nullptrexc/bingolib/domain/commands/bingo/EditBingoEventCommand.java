package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import com.nullptrexc.bingolib.domain.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.Results;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class EditBingoEventCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) {
        String bingo_id = getEventId(ctx);
        String bingo_name = getEventName(ctx);

        switch (BingoEventManager.getInstance().update_event(server(ctx), bingo_id, bingo_name)) {
            case Results.SUCCESS -> {
                ctx.getSource().sendSuccess(() -> Component.translatable("command.bingolib.event.edit.success", bingo_id), false);
                return SUCCESS;
            }
            case NOT_FOUND -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.not_found", bingo_id));
                return FAIL;
            }
            case Results.FAIL -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.edit.fail", bingo_id));
                return FAIL;
            }
        }
        
        return FAIL;
    }
}
