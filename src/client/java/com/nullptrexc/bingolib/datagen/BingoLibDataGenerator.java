package com.nullptrexc.bingolib.datagen;

import com.nullptrexc.bingolib.datagen.lang.BingoLibEnglishLanguageProvider;
import com.nullptrexc.bingolib.datagen.loot_provider.BingoLibBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BingoLibDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		
		pack.addProvider(BingoLibEnglishLanguageProvider::new);
		pack.addProvider(BingoLibBlockLootTableProvider::new);
	}
}
