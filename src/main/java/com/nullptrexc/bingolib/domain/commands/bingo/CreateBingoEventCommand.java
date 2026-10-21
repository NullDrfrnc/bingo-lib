package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class CreateBingoEventCommand extends AbstractBingoLibCommand {
    public static int execute(CommandContext<CommandSourceStack> ctx) {
        String event_name = getEventName(ctx);
        
        switch (eventmanager().create_event(server(ctx), event_name)) {
            case SUCCESS -> {
                ctx.getSource().sendSuccess(() -> Component.translatable("command.bingolib.event.create.success", event_name), true);
                return SUCCESS;
            }
            case ALREADY_EXISTS -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.event.create.already_exists"));
                return FAIL;
            }
            case FAIL -> {
                ctx.getSource().sendFailure(Component.translatable("command.bingolib.create.fail", event_name));
                return FAIL;
            }
        }
        
        return FAIL;
    }
}
