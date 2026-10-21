package com.nullptrexc.bingolib.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;

public class BingoLibEnglishLanguageProvider extends FabricLanguageProvider {
    public BingoLibEnglishLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {

        /* Block */

        translationBuilder.add("block.bingolib.trophy", "Copper Trophy");

        /* Items */

        translationBuilder.add("item.bingolib.trophy.lore", "%1$s won bingo: %2$s");

        /* Commands */

        translationBuilder.add("command.bingolib.failure_to_override", "The command you just ran does not contain a valid execute and ran the AbstractCommand's execute function instead");
        translationBuilder.add("command.bingolib.create.fail", "Failed to create [%1$s]");
        translationBuilder.add("command.bingolib.remove.fail", "Failed to remove [%1$s]");
        translationBuilder.add("command.bingolib.not_found", "Failed to find [%1$s]");

        /* Commands - Events */

        translationBuilder.add("command.bingolib.event.create.success", "Created event [%1$s]");
        translationBuilder.add("command.bingolib.event.create.already_exists", "A event already exists by that name");

        translationBuilder.add("command.bingolib.event.remove.success", "Removed event [%1$s]");

        /* Commands - Bingo's */

        translationBuilder.add("command.bingolib.bingo.create.success", "Created bingo [%1$s]");
        translationBuilder.add("command.bingolib.bingo.create.already_exists", "A bingo already exists by that name");

        translationBuilder.add("command.bingolib.bingo.remove.success", "Removed bingo [%1$s]");
        translationBuilder.add("command.bingolib.bingo.remove.fail", "Failed to remove [%1$s]");

        translationBuilder.add("command.bingolib.bingo.list.success", "There are %1$d bingo(s): [%2$s]");
        translationBuilder.add("command.bingolib.bingo.list.none_found", "There are no bingos");

        translationBuilder.add("command.bingolib.bingo.info.success.id", "ID: %1$s");
        translationBuilder.add("command.bingolib.bingo.info.success.name", "Name: %1$s");
        translationBuilder.add("command.bingolib.bingo.info.success.participants", "Participants: [%1$s]");
        translationBuilder.add("command.bingolib.bingo.info.not_found", "Could not find bingo");

        translationBuilder.add("command.bingolib.bingo.join.success", "Added %1$s to bingo [%2$s]");
        translationBuilder.add("command.bingolib.bingo.join.fail", "%1$s is already in bingo [%2$s]");
        translationBuilder.add("command.bingolib.bingo.join.player_not_found", "Could not find player");

        translationBuilder.add("command.bingolib.bingo.join.force.success", "Added %1$s to bingo [%2$s]");
        translationBuilder.add("command.bingolib.bingo.join.force.fail", "One or more players were already in the bingo [%1$s]");

        translationBuilder.add("command.bingolib.bingo.leave.success", "Removed %1$s from bingo [%2$s]");
        translationBuilder.add("command.bingolib.bingo.leave.fail", "%1$s wasn't in bingo [%2$s]");
        translationBuilder.add("command.bingolib.bingo.leave.player_not_found", "Could not find player");

        translationBuilder.add("command.bingolib.bingo.leave.force.success", "Removed %1$s from bingo [%2$s]");
        translationBuilder.add("command.bingolib.bingo.leave.force.fail", "One or more players weren't in the bingo [%1$s]");

        translationBuilder.add("command.bingolib.bingo.not_found", "Bingo [%1$s] does not exist");

        /* gui */

        translationBuilder.add("gui.bingolib.buttons.create", "Create");
        translationBuilder.add("gui.bingolib.buttons.modify", "Edit");
        translationBuilder.add("gui.bingolib.buttons.remove", "Delete");
        translationBuilder.add("gui.bingolib.buttons.save", "Save");
        translationBuilder.add("gui.bingolib.buttons.events.save", "Save event");

        /* gui - toasts */
        
        translationBuilder.add("gui.bingolib.events.toast.create.successfull.title", "Created event");
        translationBuilder.add("gui.bingolib.events.toast.create.successfull.body", "%1$s was saved successfully!");

        translationBuilder.add("gui.bingolib.events.toast.edit.successfull.title", "Edited event");
        translationBuilder.add("gui.bingolib.events.toast.edit.successfull.body", "%1$s was updated successfully!");

        translationBuilder.add("gui.bingolib.events.toast.delete.successfull.title", "Deleted event");
        translationBuilder.add("gui.bingolib.events.toast.delete.successfull.body", "Event was deleted successfully!");
        
        /* gui - events */

        translationBuilder.add("gui.bingolib.events.overview.title", "Events");
        
        translationBuilder.add("gui.bingolib.events.event_name", "Name");
        
        translationBuilder.add("gui.bingolib.events.create.title", "Create event");
        
        translationBuilder.add("gui.bingolib.events.edit.title", "Edit event");
        
        translationBuilder.add("gui.bingolib.events.delete.question", "Are you sure you want to delete this event?");
        translationBuilder.add("gui.bingolib.events.delete.warning", "%1$s will be deleted");
        translationBuilder.add("gui.bingolib.events.delete.button", "Delete");
    }
}
