package com.nullptrexc.bingolib.datagen.loot_provider;

import com.nullptrexc.bingolib.init.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;

import java.lang.classfile.constantpool.ConstantValueEntry;
import java.util.concurrent.CompletableFuture;

public class BingoLibBlockLootTableProvider extends FabricBlockLootSubProvider {
    public BingoLibBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        add(ModBlocks.TROPHY, block -> 
                createSingleItemTable(block)
                        .apply(CopyComponentsFunction
                                .copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                .include(DataComponents.LORE)));
    }
}
