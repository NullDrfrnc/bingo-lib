package com.nullptrexc.bingolib.client.init.networking;

import com.nullptrexc.bingolib.client.gui.screens.BingoEventsCrudScreen;
import com.nullptrexc.bingolib.client.gui.screens.BingoEventsOverviewScreen;
import com.nullptrexc.bingolib.network.payload.BingoEventGetAllPayload;
import com.nullptrexc.bingolib.network.payload.BingoEventGetEventPayload;
import com.nullptrexc.bingolib.network.payload.BingoEventOpenOverviewPayload;
import com.nullptrexc.bingolib.network.payload.BingoEventOperationSuccessfullPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

public class NetworkingInitialization {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(BingoEventGetAllPayload.TYPE, (payload, context) -> {
            Minecraft minecraft = context.client();

            if (minecraft.gui.screen() instanceof BingoEventsOverviewScreen overview) {
                overview.setEvents(payload.events());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(BingoEventOpenOverviewPayload.TYPE, (payload, context) -> {
            Minecraft minecraft = context.client();

            minecraft.setScreenAndShow(new BingoEventsOverviewScreen());
        });

        ClientPlayNetworking.registerGlobalReceiver(BingoEventGetEventPayload.TYPE, (payload, context) -> {
            Minecraft minecraft = context.client();

            if (minecraft.gui.screen() instanceof BingoEventsCrudScreen crud) {
                crud.setEvent(payload.event());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(BingoEventOperationSuccessfullPayload.TYPE, (payload, context) -> {
            Minecraft minecraft = context.client();

            if (minecraft.gui.screen() instanceof BingoEventsCrudScreen crud) {
                Component title = crud.isCreate()
                        ? Component.translatable("gui.bingolib.events.toast.create.successfull.title")
                        : Component.translatable("gui.bingolib.events.toast.edit.successfull.title");

                Component body = crud.isCreate()
                        ? Component.translatable("gui.bingolib.events.toast.create.successfull.body", crud.getEventName())
                        : Component.translatable("gui.bingolib.events.toast.edit.successfull.body", crud.getEventName());

                minecraft.gui.toastManager().addToast(
                        new SystemToast(new SystemToast.SystemToastId(), title, body)
                );

                minecraft.gui.setScreen(new BingoEventsOverviewScreen());
            } else if (minecraft.gui.screen() instanceof BingoEventsOverviewScreen overview) {
                Component title = Component.translatable("gui.bingolib.events.toast.delete.successfull.title");
                Component body = Component.translatable("gui.bingolib.events.toast.delete.successfull.body");

                minecraft.gui.toastManager().addToast(
                        new SystemToast(new SystemToast.SystemToastId(), title, body)
                );
            }
        });
    }
}
