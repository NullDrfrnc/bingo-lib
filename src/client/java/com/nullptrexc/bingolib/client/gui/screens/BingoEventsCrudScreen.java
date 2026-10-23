package com.nullptrexc.bingolib.client.gui.screens;

import com.nullptrexc.bingolib.client.gui.BingoLibDefaults;
import com.nullptrexc.bingolib.client.gui.components.BingoLibButtons;
import com.nullptrexc.bingolib.client.gui.components.ListContainer;
import com.nullptrexc.bingolib.domain.data.BingoEvent;
import com.nullptrexc.bingolib.network.payload.BingoEventCreatePayload;
import com.nullptrexc.bingolib.network.payload.BingoEventRequestEventPayload;
import com.nullptrexc.bingolib.network.payload.BingoEventUpdatePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class BingoEventsCrudScreen extends Screen {
    private static final Component CREATE_TITLE = Component.translatable("gui.bingolib.event.create.title");
    private static final Component EDIT_TITLE = Component.translatable("gui.bingolib.event.edit.title");
    private static final Component EVENT_NAME = Component.translatable("gui.bingolib.event.event_name");

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(
            this,
            BingoLibDefaults.HEADER_HEIGHT,
            BingoLibDefaults.FOOTER_HEIGHT
    );
    
    private ListContainer list;

    private BingoEvent event = new BingoEvent();
    private EditBox edit_name;
    private Button save_button;

    private boolean isCreate;
    private boolean dataRequested;

    private String bingo_id;

    protected BingoEventsCrudScreen(String bingo_id) {
        super(EDIT_TITLE);
        this.isCreate = false;
        this.bingo_id = bingo_id;
    }

    protected BingoEventsCrudScreen() {
        super(CREATE_TITLE);
        this.isCreate = true;
        this.bingo_id = "";
    }

    @Override
    protected void init() {
        layout.addTitleHeader(this.title, this.font);

        this.initFooter();
        
        this.list = this.layout.addToContents(
                new ListContainer.Builder(this.minecraft)
                        .width(this.width)
                        .height(this.height)
                        .build()
        );

        edit_name = new EditBox(this.font, EVENT_NAME);
        
        edit_name.setResponder(update -> {
            BingoEventsCrudScreen.this.event.setName(update);
            this.save_button.active = !update.isBlank();
        });
        
        edit_name.setValue(this.event.getName());

        list.addLabeledRow(EVENT_NAME, edit_name);
        
        if (!this.isCreate && !this.dataRequested) {
            this.fetchData();
        } else {
            this.save_button.active = false;
        }
        
        layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    private void fetchData() {
        ClientPlayNetworking.send(new BingoEventRequestEventPayload(bingo_id));
        this.dataRequested = true;
    }

    private void initFooter() {
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(BingoLibDefaults.FOOTER_SPACING));

        footer.defaultCellSetting().alignVerticallyMiddle();
        footer.addChild(BingoLibButtons.getDoubleButton(CommonComponents.GUI_CANCEL, _ -> this.onBack()));
        save_button = footer.addChild(BingoLibButtons.getDoubleButton(Component.translatable("gui.bingolib.buttons.save"), _ -> this.onSave()));
    }
    
    private void onBack() {
        minecraft.gui.setScreen(new BingoEventsOverviewScreen());
    }
    
    private void onSave() {
        if(this.isCreate) {
            ClientPlayNetworking.send(new BingoEventCreatePayload(event.packed()));
        } else {
            ClientPlayNetworking.send(new BingoEventUpdatePayload(event.packed()));
        }
    }

    public void setEvent(BingoEvent.Data event) {
        this.event.setName(event.name());
        this.event.setId(event.id());

        this.updateInputs();
    }
    
    public boolean isCreate() {
        return this.isCreate;
    }

    public void updateInputs() {
        this.edit_name.setValue(this.event.getName());
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        this.list.updateSize(this.width, this.layout);
    }

    public String getEventName() {
        return this.event.getName();
    }
}
