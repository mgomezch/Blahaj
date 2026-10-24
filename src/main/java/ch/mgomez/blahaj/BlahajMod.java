package ch.mgomez.blahaj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Bl\u00e5haj for GTNH.
 *
 * Transforms the Extra Utilities Healing Axe into a Bl\u00e5haj plushie
 * with size, color, pride flags, and other variants based on item names.
 */
@Mod(
    modid = BlahajMod.MODID,
    name = BlahajMod.MODNAME,
    version = Tags.VERSION,
    dependencies = "after:ExtraUtilities;after:ExtraUtils",
    guiFactory = "ch.mgomez.blahaj.client.gui.BlahajGuiFactory")
public class BlahajMod {

    public static final String MODID = "blahaj";
    public static final String MODNAME = "Blahaj";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "ch.mgomez.blahaj.ClientProxy", serverSide = "ch.mgomez.blahaj.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }
}
