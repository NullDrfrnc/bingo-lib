package com.nullptrexc.bingolib.domain.blockentities;

import com.nullptrexc.bingolib.init.blocks.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;

public class TrophyBlockEntity extends BlockEntity {
    private ItemLore lore = ItemLore.EMPTY;
    public TrophyBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.TROPHY_BLOCK_ENTITY, worldPosition, blockState);
    }

    public static void clientTick(Level level, BlockPos p, BlockState s, TrophyBlockEntity entity) {
        for (Player player : level.players()) {
            if (player.pick(player.blockInteractionRange(), 0, false) instanceof BlockHitResult hit && hit.getBlockPos().equals(entity.getBlockPos())) {
                player.sendOverlayMessage(entity.getLoreComponent());
            } 
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.lore = components.getOrDefault(DataComponents.LORE, ItemLore.EMPTY);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.LORE, this.lore);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("lore", ItemLore.CODEC, this.lore);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.lore = input.read("lore", ItemLore.CODEC).orElse(ItemLore.EMPTY);
    }
    
    public Component getLoreComponent() {
        if(lore.lines().isEmpty()) return Component.literal("");
        return lore.lines().getFirst();
    }
}
