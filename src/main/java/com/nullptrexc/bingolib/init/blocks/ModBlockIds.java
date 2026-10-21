package com.nullptrexc.bingolib.init.blocks;

import com.nullptrexc.bingolib.BingoLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class ModBlockIds {
    public static final BlockItemId TROPHY = create("trophy");
    
    private static BlockItemId create(String name) {
        Identifier id = BingoLib.id(name);
        return BlockItemId.create(id, id);
    }

    private static ResourceKey<Block> createWithoutItem(String name) {
        Identifier id = BingoLib.id(name);
        return ResourceKey.create(Registries.BLOCK, id);
    }
}
