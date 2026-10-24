package ch.mgomez.blahaj;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static Item itemBlahaj;

    public void preInit(FMLPreInitializationEvent event) {
        BlahajConfig.init(event.getSuggestedConfigurationFile());
        itemBlahaj = new ItemBlahaj();
        GameRegistry.registerItem(itemBlahaj, "blahaj");
    }

    public void init(FMLInitializationEvent event) {
        // Recipe 1: Blue wool + white wool + pink dye
        GameRegistry.addRecipe(
            new ItemStack(itemBlahaj),
            "BBB",
            "BWB",
            " P ",
            'B',
            new ItemStack(Blocks.wool, 1, 11),
            'W',
            new ItemStack(Blocks.wool, 1, 0),
            'P',
            new ItemStack(Items.dye, 1, 9));

        // Recipe 2: Light blue wool + white wool + pink dye
        GameRegistry.addRecipe(
            new ItemStack(itemBlahaj),
            "BBB",
            "BWB",
            " P ",
            'B',
            new ItemStack(Blocks.wool, 1, 3),
            'W',
            new ItemStack(Blocks.wool, 1, 0),
            'P',
            new ItemStack(Items.dye, 1, 9));
    }

    public void postInit(FMLPostInitializationEvent event) {}

    public void loadComplete(cpw.mods.fml.common.event.FMLLoadCompleteEvent event) {}
}
