package com.nullptrexc.bingolib.init.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.nullptrexc.bingolib.domain.bingo.data.BingoEvent;
import com.nullptrexc.bingolib.domain.bingo.manager.BingoEventManager;
import com.nullptrexc.bingolib.domain.commands.bingo.CreateBingoEventCommand;
import com.nullptrexc.bingolib.domain.commands.bingo.CreateBingoEventWithIdCommand;
import com.nullptrexc.bingolib.domain.commands.bingo.OpenBingoEventScreenCommand;
import com.nullptrexc.bingolib.domain.commands.bingo.RemoveBingoEventCommand;
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

            LiteralArgumentBuilder<CommandSourceStack> create = Commands.literal("create");

            LiteralArgumentBuilder<CommandSourceStack> remove = Commands.literal("remove");

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
                            )
            );
        });
    }
}
