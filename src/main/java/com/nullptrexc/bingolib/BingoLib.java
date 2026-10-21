package com.nullptrexc.bingolib;

import com.nullptrexc.bingolib.init.blocks.ModBlockEntities;
import com.nullptrexc.bingolib.init.blocks.ModBlocks;
import com.nullptrexc.bingolib.init.commands.CommandInitialization;
import com.nullptrexc.bingolib.init.networking.NetworkingInitialization;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BingoLib implements ModInitializer {
	public static final String MOD_ID = "bingolib";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModBlockEntities.init();
		CommandInitialization.init();
		NetworkingInitialization.init();
    }

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
