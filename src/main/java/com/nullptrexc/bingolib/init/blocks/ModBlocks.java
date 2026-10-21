package com.nullptrexc.bingolib.init.blocks;

import com.nullptrexc.bingolib.domain.blocks.TrophyBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.impl.datagen.client.SoundTypeBuilderImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModBlocks {
    public static final Block TROPHY = register(
            ModBlockIds.TROPHY,
            TrophyBlock::new,
            TrophyBlock.Properties.of().sound(SoundType.COPPER).noOcclusion(),
            props -> props
                    .stacksTo(1)
                    .fireResistant()
    );
    
    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register((creativeTab) -> {
            creativeTab.accept(ModBlocks.TROPHY.asItem());
        });
    }

    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties, UnaryOperator<Item.Properties> itemProperties) {
        Block block = register(id.block(), blockFactory, properties);

        Item.Properties baseProps = new Item.Properties()
                .useBlockDescriptionPrefix()
                .setId(id.item());

        BlockItem blockItem = new BlockItem(block, itemProperties.apply(baseProps));
        Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

        return block;
    }

    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        return register(id, blockFactory, properties, UnaryOperator.identity());
    }

    private static Block register(ResourceKey<Block> id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        Block block = blockFactory.apply(properties.setId(id));

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }
}
