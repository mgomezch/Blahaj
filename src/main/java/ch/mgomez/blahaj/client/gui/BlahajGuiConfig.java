package ch.mgomez.blahaj.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import ch.mgomez.blahaj.BlahajConfig;
import ch.mgomez.blahaj.BlahajMod;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class BlahajGuiConfig extends GuiConfig {

    public BlahajGuiConfig(GuiScreen parent) {
        super(
            parent,
            getConfigElements(),
            BlahajMod.MODID,
            false,
            false,
            GuiConfig.getAbridgedConfigPath(
                BlahajConfig.getConfig()
                    .toString()));
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();
        if (BlahajConfig.getConfig() != null) {
            for (String catName : BlahajConfig.getConfig()
                .getCategoryNames()) {
                if (catName.indexOf('.') == -1) {
                    ConfigCategory category = BlahajConfig.getConfig()
                        .getCategory(catName);
                    list.add(new ConfigElement(category));
                }
            }
        }
        return list;
    }
}
