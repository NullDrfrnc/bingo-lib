package com.nullptrexc.bingolib.domain.commands.bingo;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nullptrexc.bingolib.domain.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.AbstractCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractBingoLibCommand extends AbstractCommand {
    protected static BingoEventManager eventmanager() {
        return BingoEventManager.getInstance();
    }
    
    protected static String getEventName(CommandContext<CommandSourceStack> ctx) {
        return StringArgumentType.getString(ctx, "event_name");
    }

    protected static String getEventId(CommandContext<CommandSourceStack> ctx) {
        return StringArgumentType.getString(ctx, "event_id");
    }
    
    protected static ServerPlayer player(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        return ctx.getSource().getPlayerOrException();
    }
    
    protected static MinecraftServer server(CommandContext<CommandSourceStack> ctx) {
        return ctx.getSource().getServer();
    }
    
//    protected static String getBingoId(CommandContext<CommandSourceStack> ctx) {
//        return StringArgumentType.getString(ctx, "bingo_id");
//    }
//    
//    protected static String getBingoName(CommandContext<CommandSourceStack> ctx) {
//        return StringArgumentType.getString(ctx, "bingo_name");
//    }
    
//    protected static <T, R> String commaFormatString(Collection<T> collection, Function<? super T, ? extends R> mapper) {
//        return collection.stream()
//                .map(mapper)
//                .map(String::valueOf)
//                .collect(Collectors.joining(", "));
//    }
//    
//    protected static String commaFormatPlayers(Collection<ServerPlayer> collection) {
//        return commaFormatString(collection, server_player -> server_player.getName().getString());
//    }
}
