package com.nullptrexc.bingolib.client;

import com.nullptrexc.bingolib.client.init.networking.NetworkingInitialization;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;

public class BingoLibClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		NetworkingInitialization.init();
	}
}