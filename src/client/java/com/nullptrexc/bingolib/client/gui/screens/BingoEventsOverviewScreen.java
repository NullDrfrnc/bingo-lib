package com.nullptrexc.bingolib.client.gui.screens;

import com.nullptrexc.bingolib.client.gui.BingoLibDefaults;
import com.nullptrexc.bingolib.domain.bingo.data.BingoEvent;
import com.nullptrexc.bingolib.network.payload.BingoEventDeletePayload;
import com.nullptrexc.bingolib.network.payload.BingoEventRequestAllEventsPayload;
import com.nullptrexc.bingolib.network.payload.BingoEventRequestToggleParticipationPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BingoEventsOverviewScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui.bingolib.event.overview.title");

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 33, 60);
    
    private BingoEventsList list;
    private Button createButton;
    private Button editButton;
    private Button removeButton;
    protected Button joinButton;

    private List<BingoEvent.Data> events;

    public BingoEventsOverviewScreen() {
        super(TITLE);
    }

    
    @Override
    protected void init() {
        layout.addTitleHeader(this.title, this.font);

        this.fetchData();

        this.list = layout.addToContents(
                new BingoEventsList.Builder(minecraft, events)
                        .width(this.width)
                        .height(this.height)
                        .headerHeight(BingoLibDefaults.HEADER_HEIGHT)
                        .onEntrySelect(this::updateButtonStatus)
                        .build()
        );

        LinearLayout footer = this.layout.addToFooter(LinearLayout.vertical().spacing(BingoLibDefaults.FOOTER_SPACING));
        footer.defaultCellSetting().alignVerticallyMiddle();

        LinearLayout topFooterButtons = footer.addChild(LinearLayout.horizontal().spacing(BingoLibDefaults.FOOTER_SPACING));
        LinearLayout bottomFooterButtons = footer.addChild(LinearLayout.horizontal());
        
        createButton = topFooterButtons.addChild(Button.builder(Component.translatable("gui.bingolib.buttons.create"), button -> this.onCreate()).width(59).build());
        editButton = topFooterButtons.addChild(Button.builder(Component.translatable("gui.bingolib.buttons.edit"), button -> list.getSelectedOpt().ifPresent(this::onModify)).width(59).build());
        removeButton = topFooterButtons.addChild(Button.builder(Component.translatable("gui.bingolib.buttons.remove"), button -> list.getSelectedOpt().ifPresent(this::onDelete)).width(59).build());
        joinButton = topFooterButtons.addChild(Button.builder(Component.translatable("gui.bingolib.buttons.join"), button -> list.getSelectedOpt().ifPresent(this::onJoinOrLeave)).width(59).build());
        
        this.updateButtonStatus(null);
        
        bottomFooterButtons.addChild(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).width(248).build());
        
        layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    private void updateButtonStatus(BingoEvent.Data data) {
        if (this.editButton != null && this.removeButton != null && this.joinButton != null) {
            boolean condition = data != null;
            
            setCreateButtonActive();
            setEditButtonActive(condition);
            setRemoveButtonActive(condition);
            setJoinButtonActive(condition);
        }
    }

    private void setCreateButtonActive() {
        if (minecraft.player != null && !minecraft.player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
            this.createButton.active = false;
            return;
        }
        this.createButton.active = true;
    }

    private void setEditButtonActive(boolean status) {
        if (minecraft.player != null && !minecraft.player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
            this.editButton.active = false;
            return;
        }
        this.editButton.active = status;
    }
    
    private void setRemoveButtonActive(boolean status) {
        if (minecraft.player != null && !minecraft.player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
            this.removeButton.active = false;
            return;
        }
        this.removeButton.active = status;
    }
    
    private void setJoinButtonActive(boolean status) {
        this.joinButton.active = status;
    }
    
    protected void fetchData() {
        ClientPlayNetworking.send(new BingoEventRequestAllEventsPayload());
        // Set to null so edit buttons are disabled initially
        this.updateButtonStatus(null);
    }

    private void onCreate() {
        this.minecraft.gui.setScreen(new BingoEventsCrudScreen());
    }

    private void onModify(BingoEventsList.BingoEventEntry bingoEventEntry) {
        this.minecraft.gui.setScreen(new BingoEventsCrudScreen(bingoEventEntry.event.id()));
    }

    private void onJoinOrLeave(BingoEventsList.BingoEventEntry bingoEventEntry) {
        ClientPlayNetworking.send(new BingoEventRequestToggleParticipationPayload(bingoEventEntry.event.id()));
    }

    private void onDelete(BingoEventsList.BingoEventEntry bingoEventEntry) {
        this.minecraft
                .gui.setScreen(
                        new ConfirmScreen(
                                confirmed -> {
                                    this.minecraft.gui.setScreen(this);
                                    if (confirmed) {
                                        bingoEventEntry.doDeleteEntry();
                                    }
                                },
                                Component.translatable("gui.bingolib.event.delete.question"),
                                Component.translatable("gui.bingolib.event.delete.warning", bingoEventEntry.name),
                                Component.translatable("gui.bingolib.event.delete.button"),
                                CommonComponents.GUI_CANCEL
                        )
                );
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        this.list.updateSize(this.width, this.layout);
    }

    public void setEvents(List<BingoEvent.Data> events) {
        this.events = events;
        if (this.list != null) {
            list.setEvents(events);
        }
    }

    public void reload() {
        ClientPlayNetworking.send(new BingoEventRequestAllEventsPayload());
    }

    public static class BingoEventsList extends ObjectSelectionList<BingoEventsOverviewScreen.BingoEventsList.BingoEventEntry> {
        private final @Nullable Consumer<BingoEvent.Data> onEntrySelect;

        public BingoEventsList(Minecraft minecraft, int width, int height, int headerHeight,  List<BingoEvent.Data> events, Consumer<BingoEvent.Data> onEntrySelect) {
            super(minecraft, width, height, headerHeight, 16);
            
            if (events == null) {
                events = List.of();
            }
            
            for (BingoEvent.Data event : events) {
                this.addEntry(new BingoEventsOverviewScreen.BingoEventsList.BingoEventEntry(minecraft, this, event));
            }

            this.onEntrySelect = onEntrySelect;
        }

        public Optional<BingoEventsList.BingoEventEntry> getSelectedOpt() {
            BingoEventsList.BingoEventEntry selected = this.getSelected();
            return selected instanceof BingoEventsList.BingoEventEntry entry ? Optional.of(entry) : Optional.empty();
        }

        @Override
        public void setSelected(BingoEventsOverviewScreen.BingoEventsList.@Nullable BingoEventEntry selected) {
            super.setSelected(selected);
            if (this.onEntrySelect != null) {
                this.onEntrySelect.accept(selected instanceof BingoEventsOverviewScreen.BingoEventsList.BingoEventEntry entry ? entry.event : null);
            }
            
            if (this.minecraft.gui.screen() instanceof BingoEventsOverviewScreen overview) {
                Component message = !isPlayerInEvent()
                        ? Component.translatable("gui.bingolib.buttons.join")
                        : Component.translatable("gui.bingolib.buttons.leave");
                
                overview.joinButton.setMessage(message);
            }
        }

        public void setEvents(List<BingoEvent.Data> events) {
            this.setSelected(null);
            this.clearEntries();
            for (BingoEvent.Data event : events) {
                this.addEntry(new BingoEventEntry(this.minecraft, this, event));
            }
        }
        
        protected boolean isPlayerInEvent() {
            BingoEventEntry entry = this.getSelectedOpt().orElse(null);
            
            if(this.minecraft.player == null || entry == null)
                return false;
            
            return entry.event.players().stream().anyMatch(uuid -> this.minecraft.player.getUUID().equals(uuid));
        }
        
        public class BingoEventEntry extends ObjectSelectionList.Entry<BingoEventsOverviewScreen.BingoEventsList.BingoEventEntry> {
            private final Minecraft minecraft;
            private final BingoEventsList list;
            private BingoEvent.Data event;
            private Component name;

            public BingoEventEntry(Minecraft minecraft, BingoEventsList list, BingoEvent.Data event) {
                this.minecraft = minecraft;
                this.list = list;
                this.event = event;
                name = Component.literal(event.name());
            }
            
            public void doDeleteEntry() {
                ClientPlayNetworking.send(new BingoEventDeletePayload(this.event.id()));
                if(minecraft.gui.screen() instanceof BingoEventsOverviewScreen screen) {
                    screen.fetchData();
                }
            }
            
            @Override
            public @NonNull Component getNarration() {
                return Component.translatable(
                        "narrator.select",
                        name);
            }

            @Override
            public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
                int y = this.getContentYMiddle() - 9 / 2;
                graphics.text(minecraft.font, this.name, this.getContentX() + 2, y, -1);
            }
        }

        private void returnToScreen(Screen old) {
            minecraft.gui.setScreen(old);
        }

        public static class Builder {
            private final Minecraft minecraft;
            private final List<BingoEvent.Data> events;
            private int width;
            private int height;
            private int headerHeight;
            private @Nullable Consumer<BingoEvent.Data> onEntrySelect = null;

            public Builder(final Minecraft minecraft, List<BingoEvent.Data> events) {
                this.minecraft = minecraft;
                this.events = events;
            }
            
            public BingoEventsList.Builder width(final int width) {
                this.width = width;
                return this;
            }
            
            public BingoEventsList.Builder height(final int height) {
                this.height = height;
                return this;
            }
            
            public BingoEventsList.Builder headerHeight(final int headerHeight) {
                this.headerHeight = headerHeight;
                return this;
            }

            public BingoEventsList.Builder onEntrySelect(final Consumer<BingoEvent.Data> consumer) {
                this.onEntrySelect = consumer;
                return this;
            }

            public BingoEventsList build() {
                return new BingoEventsList(this.minecraft, this.width, this.height, this.headerHeight, this.events, this.onEntrySelect);
            }
        }
    }
}
