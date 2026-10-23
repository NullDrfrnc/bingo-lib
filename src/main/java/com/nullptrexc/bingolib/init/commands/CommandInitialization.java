package com.nullptrexc.bingolib.init.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.nullptrexc.bingolib.domain.data.BingoEvent;
import com.nullptrexc.bingolib.domain.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.bingo.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;

public class CommandInitialization {
    private static final SuggestionProvider<CommandSourceStack> EVENT_IDS_SUGGESTIONS = (ctx, builder) -> SharedSuggestionProvider.suggest(
            BingoEventManager.getInstance().get_events(ctx.getSource().getServer()).stream().map(BingoEvent::getId).toList(),
            builder
    );

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            RequiredArgumentBuilder<CommandSourceStack, String> event_id = Commands.argument("event_id", StringArgumentType.word());

            RequiredArgumentBuilder<CommandSourceStack, String> event_name = Commands.argument("event_name", StringArgumentType.string());

            LiteralArgumentBuilder<CommandSourceStack> event = Commands.literal("event");
            
            LiteralArgumentBuilder<CommandSourceStack> join = Commands.literal("join");
            
            LiteralArgumentBuilder<CommandSourceStack> leave = Commands.literal("leave");

            LiteralArgumentBuilder<CommandSourceStack> create = Commands.literal("create").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));

            LiteralArgumentBuilder<CommandSourceStack> remove = Commands.literal("remove").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));
            
            LiteralArgumentBuilder<CommandSourceStack> edit = Commands.literal("edit").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));

            dispatcher.register(
                    // /event
                    event.executes(OpenBingoEventScreenCommand::execute)
                            .then(
                                    create.then(
                                            // /event create <event_name> [<event_id>]
                                            event_name.executes(CreateBingoEventCommand::execute).then(event_id.executes(CreateBingoEventWithIdCommand::execute))
                                    )
                            ).then(
                                    remove.then(
                                            // /event remove <event_id>
                                            event_id.suggests(EVENT_IDS_SUGGESTIONS).executes(RemoveBingoEventCommand::execute)
                                    )
                            ).then(
                                    edit.then(
                                            // /event edit <event_id> <event_name>
                                            event_id.suggests(EVENT_IDS_SUGGESTIONS).then(event_name.executes(EditBingoEventCommand::execute))
                                    )
                            ).then(
                                    join.then(
                                            event_id.suggests(EVENT_IDS_SUGGESTIONS).executes(JoinBingoEventCommand::execute)
                                    )
                            ).then(
                                    leave.then(
                                            event_id.suggests(EVENT_IDS_SUGGESTIONS).executes(LeaveBingoEventCommand::execute)
                                    )
                            )
            );
        });
    }
}
