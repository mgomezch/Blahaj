package ch.mgomez.blahaj;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {

    private static Item extraUtilsHealingAxe = null;

    public static net.minecraft.client.settings.KeyBinding keyLiveAdjuster = new net.minecraft.client.settings.KeyBinding(
        "key.blahaj.live_adjuster",
        Keyboard.KEY_NONE,
        "key.categories.blahaj");

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(this);
        cpw.mods.fml.common.FMLCommonHandler.instance()
            .bus()
            .register(this);
        cpw.mods.fml.client.registry.ClientRegistry.registerKeyBinding(keyLiveAdjuster);
        net.minecraftforge.client.ClientCommandHandler.instance
            .registerCommand(new ch.mgomez.blahaj.client.CommandBlahaj());
    }

    private long lastWorldLoadReloadTime = 0L;

    @SubscribeEvent
    public void onWorldLoad(net.minecraftforge.event.world.WorldEvent.Load event) {
        if (event.world != null && event.world.isRemote) {
            if (System.currentTimeMillis() - lastWorldLoadReloadTime > 2000L) {
                lastWorldLoadReloadTime = System.currentTimeMillis();
                ch.mgomez.blahaj.client.CustomImageManager.reloadCustomImages();
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent event) {
        if (keyLiveAdjuster.isPressed()) {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new ch.mgomez.blahaj.client.gui.GuiBlahajLiveAdjuster());
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        RenderBlahajItem renderer = new RenderBlahajItem();
        if (itemBlahaj != null) {
            MinecraftForgeClient.registerItemRenderer(itemBlahaj, renderer);
        }

        registerHealingAxeRenderer();

        ch.mgomez.blahaj.client.CustomImageManager.ensureDirectoryExists();
        ch.mgomez.blahaj.client.CustomImageManager.reloadCustomImages();
    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {
        super.loadComplete(event);
        registerHealingAxeRenderer();
    }

    public static void registerHealingAxeRenderer() {
        if (extraUtilsHealingAxe == null) {
            extraUtilsHealingAxe = findHealingAxe();
        }
        if (extraUtilsHealingAxe != null) {
            RenderBlahajItem renderer = new RenderBlahajItem();
            MinecraftForgeClient.registerItemRenderer(extraUtilsHealingAxe, renderer);
            BlahajMod.LOG.info(
                "Successfully registered 3D Bl\u00e5haj plushie renderer for ExtraUtilities healing axe ("
                    + extraUtilsHealingAxe.getUnlocalizedName()
                    + ")!");
        } else {
            BlahajMod.LOG.warn(
                "ExtraUtilities healing axe not found; 3D Bl\u00e5haj renderer active for standalone plushie only.");
        }
    }

    public static Item findHealingAxe() {
        // 1. Direct field access via reflection
        try {
            Class<?> clazz = Class.forName("com.rwtema.extrautils.ExtraUtils");
            Field field = clazz.getField("healingAxe");
            Object obj = field.get(null);
            if (obj instanceof Item) {
                return (Item) obj;
            }
        } catch (Throwable ignored) {}

        // 2. GameRegistry lookup across known modids and names
        String[] modIds = new String[] { "ExtraUtilities", "ExtraUtils", "extrautils" };
        String[] itemNames = new String[] { "defoliageAxe", "healingAxe" };
        for (String modId : modIds) {
            for (String itemName : itemNames) {
                Item item = GameRegistry.findItem(modId, itemName);
                if (item != null) {
                    return item;
                }
            }
        }

        // 3. Fallback: scan Item.itemRegistry
        for (Object obj : Item.itemRegistry) {
            if (obj instanceof Item) {
                Item item = (Item) obj;
                String unlocalized = item.getUnlocalizedName();
                if (unlocalized != null
                    && (unlocalized.contains("defoliageAxe") || unlocalized.contains("healingAxe"))) {
                    return item;
                }
            }
        }

        return null;
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (event.itemStack == null) return;
        if (extraUtilsHealingAxe == null) {
            extraUtilsHealingAxe = findHealingAxe();
        }
        boolean isHealingAxe = extraUtilsHealingAxe != null && event.itemStack.getItem() == extraUtilsHealingAxe;
        boolean isBlahajItem = itemBlahaj != null && event.itemStack.getItem() == itemBlahaj;

        if (isHealingAxe || isBlahajItem) {
            if (isHealingAxe) {
                event.toolTip.add(
                    EnumChatFormatting.AQUA
                        + net.minecraft.util.StatCollector.translateToLocal("tooltip.blahaj.protect"));
            }

            BlahajVariant variant = BlahajVariant.matchVariant(event.itemStack);
            if (variant != BlahajVariant.DEFAULT_VARIANT) {
                String rawVariant = net.minecraft.util.StatCollector.translateToLocal("tooltip.blahaj.variant");
                if (rawVariant.contains("%s")) {
                    event.toolTip.add(
                        EnumChatFormatting.GOLD + net.minecraft.util.StatCollector.translateToLocalFormatted(
                            "tooltip.blahaj.variant",
                            EnumChatFormatting.YELLOW + variant.getDisplayName()));
                } else {
                    event.toolTip.add(
                        EnumChatFormatting.GOLD + rawVariant
                            + " "
                            + EnumChatFormatting.YELLOW
                            + variant.getDisplayName());
                }
            }
            if (BlahajVariant.isLarge(event.itemStack)) {
                event.toolTip.add(
                    EnumChatFormatting.LIGHT_PURPLE
                        + net.minecraft.util.StatCollector.translateToLocal("tooltip.blahaj.size.chonker"));
            } else if (BlahajVariant.isSmall(event.itemStack)) {
                event.toolTip.add(
                    EnumChatFormatting.AQUA
                        + net.minecraft.util.StatCollector.translateToLocal("tooltip.blahaj.size.smol"));
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                event.toolTip.add(
                    EnumChatFormatting.DARK_AQUA
                        + net.minecraft.util.StatCollector.translateToLocal("tooltip.blahaj.info.rename"));
            } else {
                event.toolTip.add(
                    EnumChatFormatting.DARK_GRAY
                        + net.minecraft.util.StatCollector.translateToLocal("tooltip.blahaj.info.shift"));
            }
        }
    }
}
