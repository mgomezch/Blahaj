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

    public static Item extraUtilsHealingAxe = null;
    public static Item gregtechToolbox = null;

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
                registerHealingAxeRenderer();
                registerToolboxRenderer();
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

        registerSpecialItemRenderers();

        ch.mgomez.blahaj.client.CustomImageManager.ensureDirectoryExists();
        ch.mgomez.blahaj.client.CustomImageManager.reloadCustomImages();
    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {
        super.loadComplete(event);
        registerSpecialItemRenderers();
    }

    public static void registerSpecialItemRenderers() {
        registerHealingAxeRenderer();
        registerToolboxRenderer();
    }

    public static void registerHealingAxeRenderer() {
        if (extraUtilsHealingAxe == null) {
            extraUtilsHealingAxe = findHealingAxe();
        }
        if (extraUtilsHealingAxe != null) {
            net.minecraftforge.client.IItemRenderer parent = getRegisteredItemRenderer(extraUtilsHealingAxe);
            RenderBlahajItem renderer = new RenderBlahajItem(parent);
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

    public static void registerToolboxRenderer() {
        if (gregtechToolbox == null) {
            gregtechToolbox = findToolbox();
        }
        if (gregtechToolbox != null) {
            net.minecraftforge.client.IItemRenderer parent = getRegisteredItemRenderer(gregtechToolbox);
            RenderBlahajItem renderer = new RenderBlahajItem(parent);
            MinecraftForgeClient.registerItemRenderer(gregtechToolbox, renderer);
            BlahajMod.LOG.info(
                "Successfully registered 3D Bl\u00e5haj plushie renderer for GregTech toolbox ("
                    + gregtechToolbox.getUnlocalizedName()
                    + ")!");
        } else {
            BlahajMod.LOG.info("GregTech toolbox not found; skipping toolbox renderer registration.");
        }
    }

    public static net.minecraftforge.client.IItemRenderer getRegisteredItemRenderer(Item item) {
        if (item == null) return null;
        try {
            Field f = MinecraftForgeClient.class.getDeclaredField("customItemRenderers");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.Map<Item, net.minecraftforge.client.IItemRenderer> map = (java.util.Map<Item, net.minecraftforge.client.IItemRenderer>) f
                .get(null);
            if (map != null) {
                net.minecraftforge.client.IItemRenderer r = map.get(item);
                if (r instanceof RenderBlahajItem) {
                    return ((RenderBlahajItem) r).getParentRenderer();
                }
                return r;
            }
        } catch (Throwable ignored) {}
        return null;
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

    public static Item findToolbox() {
        // 1. Direct field access via GregTech ItemList reflection
        try {
            Class<?> clazz = Class.forName("gregtech.api.enums.ItemList");
            Field field = clazz.getField("ToolBox");
            Object obj = field.get(null);
            if (obj != null) {
                java.lang.reflect.Method method = obj.getClass()
                    .getMethod("getItem");
                Object item = method.invoke(obj);
                if (item instanceof Item) {
                    return (Item) item;
                }
            }
        } catch (Throwable ignored) {}

        // 2. GameRegistry lookup across known modids and names
        String[] modIds = new String[] { "gregtech", "GregTech", "gregtech_addon" };
        String[] itemNames = new String[] { "gt.Item_Toolbox", "Item_Toolbox", "ToolBox", "toolbox" };
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
                String name = Item.itemRegistry.getNameForObject(item);
                if ((name != null && name.toLowerCase(java.util.Locale.ROOT)
                    .contains("toolbox")) || (unlocalized != null
                        && unlocalized.toLowerCase(java.util.Locale.ROOT)
                            .contains("toolbox"))) {
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
        if (gregtechToolbox == null) {
            gregtechToolbox = findToolbox();
        }
        boolean isHealingAxe = extraUtilsHealingAxe != null && event.itemStack.getItem() == extraUtilsHealingAxe;
        boolean isToolbox = gregtechToolbox != null && event.itemStack.getItem() == gregtechToolbox;
        boolean isBlahajItem = itemBlahaj != null && event.itemStack.getItem() == itemBlahaj;

        boolean isBlahaj = isBlahajItem
            || ((isHealingAxe || isToolbox) && RenderBlahajItem.hasCustomName(event.itemStack));

        if (isBlahaj) {
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
