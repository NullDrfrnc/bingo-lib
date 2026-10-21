package com.nullptrexc.bingolib.client.gui.components;

import com.nullptrexc.bingolib.client.gui.BingoLibDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListContainer extends ContainerObjectSelectionList<ListContainer.Row> {
    private final int SPACING;
    private final int LABEL_WIDTH;
    
    public ListContainer(Minecraft minecraft, int width, int height, int y, int itemHeight, int spacing, int label_width) {
        super(minecraft, width, height, y, itemHeight);
        this.SPACING = spacing;
        this.LABEL_WIDTH = label_width;
    }
    
    public void addRow(Consumer<LinearLayout> filler) {
        LinearLayout row = LinearLayout.horizontal().spacing(SPACING);
        row.defaultCellSetting().alignVerticallyMiddle();
        filler.accept(row);
        this.addEntry(new Row(row));
    }
    
    public void addLabeledRow(Component label, AbstractWidget widget) {
        widget.setWidth(this.getRowWidth() - this.LABEL_WIDTH - this.SPACING);
        
        this.addRow(row -> {
            row.addChild(new StringWidget(this.LABEL_WIDTH, widget.getHeight(), label, this.minecraft.font));
            row.addChild(widget);
        });
    }

    public class Row extends ContainerObjectSelectionList.Entry<ListContainer.Row> {
        private final LinearLayout row;
        private final List<AbstractWidget> widgets = new ArrayList<>();

        Row(LinearLayout row) {
            this.row = row;
            this.row.visitWidgets(this.widgets::add);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.widgets;
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            this.row.setPosition(getContentX(), getContentY());
            this.row.arrangeElements();
            this.widgets.forEach(widget -> widget.extractRenderState(graphics, mouseX, mouseY, a));
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.widgets;
        }
    }
    
    public static class Builder {
        private final Minecraft minecraft;
        private int width = 0;
        private int height = 0;
        private int y = BingoLibDefaults.HEADER_HEIGHT;
        private int item_height = BingoLibDefaults.LIST_ITEM_HEIGHT;
        private int spacing = BingoLibDefaults.LIST_SPACING;
        private int label_width = BingoLibDefaults.LIST_LABEL_WIDTH;
        
        public Builder(final Minecraft minecraft) {
            this.minecraft = minecraft;
        }
        
        public Builder width(final int width) {
            this.width = width;
            return this;
        }
        
        public Builder height(final int height) {
            this.height = height;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }
        
        public Builder spacing(int spacing) {
            this.spacing = spacing;
            return this;
        }
        
        public Builder item_height(int item_height) {
            this.item_height = item_height;
            return this;
        }
        
        public Builder label_width(int label_width) {
            this.label_width = label_width;
            return this;
        }
        
        public ListContainer build() {
            return new ListContainer(this.minecraft, this.width, this.height, this.y, this.item_height, this.spacing, this.label_width);
        }
    }
}