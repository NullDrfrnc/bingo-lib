package com.nullptrexc.bingolib.init.blocks;

import com.nullptrexc.bingolib.BingoLib;
import com.nullptrexc.bingolib.domain.blockentities.TrophyBlockEntity;
import com.nullptrexc.bingolib.domain.blocks.TrophyBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<TrophyBlockEntity> TROPHY_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            BingoLib.id("trophy"),
            FabricBlockEntityTypeBuilder.create(TrophyBlockEntity::new, ModBlocks.TROPHY).build()
    );
    
    public static void init() {}
}
