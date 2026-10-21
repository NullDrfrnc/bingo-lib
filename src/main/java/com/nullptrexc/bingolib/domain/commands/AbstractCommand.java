package com.nullptrexc.bingolib.domain.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.lwjgl.system.macosx.LibC;
import org.slf4j.Logger;

public abstract class AbstractCommand {
    protected static final Logger LOGGER = BingoLib.LOGGER;
    
    protected static final int FAIL = -1;
    protected static final int SUCCESS = 1;
    
    public static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ctx.getSource().sendFailure(Component.translatable("command.bingolib.bingo.failure_to_override"));
        BingoLib.LOGGER.error(Component.translatable("command.bingolib.bingo.failure_to_override").getString());
        return FAIL;
    }
}
