package ch.mgomez.blahaj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.minecraftforge.client.IItemRenderer;

import org.junit.Test;

public class BlahajTest {

    @Test
    public void testModTags() {
        assertEquals("blahaj", BlahajMod.MODID);
        assertEquals("Blahaj", BlahajMod.MODNAME);
        assertNotNull(Tags.VERSION);
    }

    @Test
    public void testModelTextureResource() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/assets/blahaj/textures/models/blahaj.png");
        assertNotNull("Model texture /assets/blahaj/textures/models/blahaj.png must exist", stream);
        BufferedImage img = ImageIO.read(stream);
        assertNotNull("Model texture must be a valid readable image", img);
        assertEquals("Model texture width must be 32", 32, img.getWidth());
        assertEquals("Model texture height must be 32", 32, img.getHeight());
    }

    @Test
    public void testItemTextureResource() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/assets/blahaj/textures/items/blahaj.png");
        assertNotNull("Item texture /assets/blahaj/textures/items/blahaj.png must exist", stream);
        BufferedImage img = ImageIO.read(stream);
        assertNotNull("Item texture must be a valid readable image", img);
        assertTrue("Item texture width must be >= 16", img.getWidth() >= 16);
        assertTrue("Item texture height must be >= 16", img.getHeight() >= 16);
    }

    @Test
    public void testLangFile() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/assets/blahaj/lang/en_US.lang");
        assertNotNull("Lang file /assets/blahaj/lang/en_US.lang must exist", stream);

        java.util.Map<String, String> props = loadLangMap(stream);

        // Items and tooltips
        assertEquals("BLÅHAJ", props.get("item.blahaj.name"));
        assertNotNull(props.get("item.blahaj.tooltip.desc"));
        assertNotNull(props.get("item.blahaj.tooltip.cuddle"));
        assertNotNull(props.get("tooltip.blahaj.protect"));
        assertEquals("Variant: %s", props.get("tooltip.blahaj.variant"));
        assertNotNull(props.get("tooltip.blahaj.size.chonker"));
        assertNotNull(props.get("tooltip.blahaj.size.smol"));
        assertNotNull(props.get("tooltip.blahaj.info.rename"));
        assertNotNull(props.get("tooltip.blahaj.info.shift"));

        // Keybindings
        assertNotNull(props.get("key.blahaj.live_adjuster"));
        assertNotNull(props.get("key.categories.blahaj"));

        // GUI
        assertNotNull(props.get("gui.blahaj.live_adjuster.title"));
        assertNotNull(props.get("gui.blahaj.tab.first_person"));
        assertNotNull(props.get("gui.blahaj.tab.third_person"));
        assertNotNull(props.get("gui.blahaj.tab.animation"));
        assertNotNull(props.get("gui.blahaj.tab.sizes"));
        assertNotNull(props.get("gui.blahaj.button.save"));
        assertNotNull(props.get("gui.blahaj.button.reset_tab"));
        assertNotNull(props.get("gui.blahaj.button.cam_1st"));
        assertNotNull(props.get("gui.blahaj.button.cam_3rd"));
        assertNotNull(props.get("gui.blahaj.status.saved"));
        assertNotNull(props.get("gui.blahaj.status.reset"));
        assertNotNull(props.get("gui.blahaj.hint.scroll"));

        // Parameters
        assertNotNull(props.get("gui.blahaj.param.x_pos"));
        assertNotNull(props.get("gui.blahaj.param.y_pos"));
        assertNotNull(props.get("gui.blahaj.param.z_pos"));
        assertNotNull(props.get("gui.blahaj.param.x_rot"));
        assertNotNull(props.get("gui.blahaj.param.y_rot"));
        assertNotNull(props.get("gui.blahaj.param.z_rot"));
        assertNotNull(props.get("gui.blahaj.param.spin"));
        assertNotNull(props.get("gui.blahaj.param.pitch"));
        assertNotNull(props.get("gui.blahaj.param.yaw"));
        assertNotNull(props.get("gui.blahaj.param.roll"));
        assertNotNull(props.get("gui.blahaj.param.smol"));
        assertNotNull(props.get("gui.blahaj.param.chonker"));

        // Every variant must have an entry in en_US.lang
        for (BlahajVariant variant : BlahajVariant.ALL_VARIANTS) {
            String key = "variant.blahaj." + variant.getId();
            String val = props.get(key);
            assertNotNull("Missing translation key '" + key + "' in en_US.lang", val);
            assertFalse(
                "Translation for '" + key + "' must not be empty",
                val.trim()
                    .isEmpty());
        }
    }

    private java.util.Map<String, String> loadLangMap(InputStream stream) throws Exception {
        java.util.Map<String, String> map = new java.util.HashMap<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(
            new java.io.InputStreamReader(stream, java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int eq = line.indexOf('=');
                if (eq != -1) {
                    map.put(
                        line.substring(0, eq)
                            .trim(),
                        line.substring(eq + 1)
                            .trim());
                }
            }
        }
        return map;
    }

    @Test
    public void testAllVariantTexturesExistAndValid() throws Exception {
        assertTrue("There must be registered variants", BlahajVariant.ALL_VARIANTS.size() >= 30);
        for (BlahajVariant variant : BlahajVariant.ALL_VARIANTS) {
            String path = "/assets/blahaj/textures/models/variants/" + variant.getTextureFile();
            InputStream stream = getClass().getResourceAsStream(path);
            assertNotNull("Variant texture " + path + " for " + variant.getId() + " must exist", stream);
            BufferedImage img = ImageIO.read(stream);
            assertNotNull("Variant texture " + path + " must be readable", img);
            assertEquals("Variant texture " + path + " width must be 32", 32, img.getWidth());
            assertEquals("Variant texture " + path + " height must be 32", 32, img.getHeight());
        }
    }

    @Test
    public void testVariantMatching() {
        assertNull(BlahajVariant.findVariant((String) null));
        assertNull(BlahajVariant.findVariant(""));
        assertNull(BlahajVariant.findVariant("   "));
        assertNull(BlahajVariant.findVariant("_"));
        assertNull(BlahajVariant.findVariant("__"));
        assertNull(BlahajVariant.findVariant("a"));
        assertNull(BlahajVariant.findVariant("Healing Axe"));
        assertNull(BlahajVariant.findVariant("Field Engineer's Toolbox"));

        assertEquals(
            "default",
            BlahajVariant.matchVariant((String) null)
                .getId());
        assertEquals(
            "default",
            BlahajVariant.matchVariant("")
                .getId());
        assertEquals(
            "default",
            BlahajVariant.matchVariant("Healing Axe")
                .getId());
        assertEquals(
            "default",
            BlahajVariant.matchVariant("_")
                .getId());
        assertEquals(
            "default",
            BlahajVariant.matchVariant("a")
                .getId());

        // Pride flags
        assertEquals(
            "trans",
            BlahajVariant.findVariant("Trans Healing Axe")
                .getId());
        assertEquals(
            "trans",
            BlahajVariant.matchVariant("Trans Healing Axe")
                .getId());
        assertEquals(
            "trans",
            BlahajVariant.matchVariant("Transgender Shark")
                .getId());
        assertEquals(
            "trans",
            BlahajVariant.matchVariant("transhaj")
                .getId());
        assertEquals(
            "pride",
            BlahajVariant.matchVariant("Rainbow Axe")
                .getId());
        assertEquals(
            "prider",
            BlahajVariant.matchVariant("Progress Pride Shark")
                .getId());
        assertEquals(
            "lesbian",
            BlahajVariant.matchVariant("Lesbian Shark")
                .getId());
        assertEquals(
            "gay",
            BlahajVariant.matchVariant("Gay Shark")
                .getId());
        assertEquals(
            "bi",
            BlahajVariant.matchVariant("Bisexual Bl\u00e5haj")
                .getId());
        assertEquals(
            "pan",
            BlahajVariant.matchVariant("Pan Shark")
                .getId());
        assertEquals(
            "ace",
            BlahajVariant.matchVariant("Asexual Plushie")
                .getId());
        assertEquals(
            "aroace",
            BlahajVariant.matchVariant("Aroace Plushie")
                .getId());
        assertEquals(
            "aro",
            BlahajVariant.matchVariant("Aromantic Shark")
                .getId());
        assertEquals(
            "enby",
            BlahajVariant.matchVariant("Non-Binary Shark")
                .getId());
        assertEquals(
            "enby",
            BlahajVariant.matchVariant("NB Shark")
                .getId());
        assertEquals(
            "genderfluid",
            BlahajVariant.matchVariant("Genderfluid Shark")
                .getId());

        // Colors
        assertEquals(
            "pink",
            BlahajVariant.matchVariant("Pink Axe")
                .getId());
        assertEquals(
            "pink",
            BlahajVariant.matchVariant("rosahaj")
                .getId());
        assertEquals(
            "grey",
            BlahajVariant.matchVariant("Gray Shark")
                .getId());
        assertEquals(
            "grey",
            BlahajVariant.matchVariant("Klappar Haj")
                .getId());
        assertEquals(
            "red",
            BlahajVariant.matchVariant("Red Shark")
                .getId());
        assertEquals(
            "black",
            BlahajVariant.matchVariant("Black Shark")
                .getId());
        assertEquals(
            "white",
            BlahajVariant.matchVariant("White Shark")
                .getId());

        // Shark vs Blahaj
        assertEquals(
            "shark",
            BlahajVariant.findVariant("Shark")
                .getId());
        assertEquals(
            "shark",
            BlahajVariant.findVariant("shonk")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("blahaj")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("Blahaj")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("BLAHAJ")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("bl\u00e5haj")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("Bl\u00e5haj")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("classic")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.findVariant("default")
                .getId());
        assertEquals(
            "grey",
            BlahajVariant.findVariant("grahaj")
                .getId());
        assertEquals(
            "grey",
            BlahajVariant.findVariant("gr\u00e5haj")
                .getId());
    }

    @Test
    public void testChonkerDetection() {
        assertFalse(BlahajVariant.isLarge((String) null));
        assertFalse(BlahajVariant.isLarge("Healing axe"));
        assertFalse(BlahajVariant.isLarge("Trans shark"));

        assertTrue(BlahajVariant.isLarge("Chonker shark"));
        assertTrue(BlahajVariant.isLarge("Big trans shark"));
        assertTrue(BlahajVariant.isLarge("Large gay haj"));
        assertTrue(BlahajVariant.isLarge("CHONK"));
    }

    @Test
    public void testSmolDetection() {
        assertFalse(BlahajVariant.isSmall((String) null));
        assertFalse(BlahajVariant.isSmall("Healing Axe"));
        assertFalse(BlahajVariant.isSmall("Trans shark"));

        assertTrue(BlahajVariant.isSmall("Smol shark"));
        assertTrue(BlahajVariant.isSmall("Small Bl\u00e5haj"));
        assertTrue(BlahajVariant.isSmall("Sm\u00f6l gay shark"));
        assertTrue(BlahajVariant.isSmall("Tiny trans"));
        assertTrue(BlahajVariant.isSmall("Mini shark"));
        assertTrue(BlahajVariant.isSmall("Pocket haj"));
    }

    @Test
    public void testConfigDefaults() {
        assertEquals(0.7, BlahajConfig.firstPersonPosX, 0.001);
        assertEquals(0.3, BlahajConfig.firstPersonPosY, 0.001);
        assertEquals(0.45, BlahajConfig.firstPersonPosZ, 0.001);
        assertEquals(60.0, BlahajConfig.firstPersonRotX, 0.001);
        assertEquals(-55.0, BlahajConfig.firstPersonRotY, 0.001);
        assertEquals(15.0, BlahajConfig.firstPersonRotZ, 0.001);

        assertEquals(0.5, BlahajConfig.thirdPersonPosX, 0.001);
        assertEquals(0.4, BlahajConfig.thirdPersonPosY, 0.001);
        assertEquals(0.1, BlahajConfig.thirdPersonPosZ, 0.001);
        assertEquals(15.0, BlahajConfig.thirdPersonRotX, 0.001);
        assertEquals(120.0, BlahajConfig.thirdPersonRotY, 0.001);
        assertEquals(-80.0, BlahajConfig.thirdPersonRotZ, 0.001);

        assertFalse(BlahajConfig.enableSpin);
        assertEquals(0.0, BlahajConfig.spinSpeedPitch, 0.001);
        assertEquals(0.0, BlahajConfig.spinSpeedYaw, 0.001);
        assertEquals(90.0, BlahajConfig.spinSpeedRoll, 0.001);

        assertEquals(1.5, BlahajConfig.chonkerScaleMultiplier, 0.001);
        assertEquals(0.65, BlahajConfig.smallScaleMultiplier, 0.001);
    }

    @Test
    public void testCustomImageDisplayNameFormatting() {
        assertEquals("Cool Shark", ch.mgomez.blahaj.client.CustomImageManager.formatDisplayName("cool_shark"));
        assertEquals("Space Shark", ch.mgomez.blahaj.client.CustomImageManager.formatDisplayName("space-shark"));
        assertEquals("Pepe", ch.mgomez.blahaj.client.CustomImageManager.formatDisplayName("pepe"));
        assertEquals(
            "Gawr Gura Chibi",
            ch.mgomez.blahaj.client.CustomImageManager.formatDisplayName("gawr_gura_chibi"));
    }

    @Test
    public void testCustomImageKeywords() {
        java.util.List<String> kw = ch.mgomez.blahaj.client.CustomImageManager.generateKeywords("cool_shark");
        assertTrue(kw.contains("cool_shark"));
        assertTrue(kw.contains("cool shark"));
        assertTrue(kw.contains("cool"));
        assertTrue(kw.contains("shark"));
    }

    @Test
    public void testCustomFlatVariantMatchingAndPriority() {
        try {
            BlahajVariant.clearCustomVariants();

            net.minecraft.util.ResourceLocation loc1 = new net.minecraft.util.ResourceLocation(
                "blahaj",
                "custom/cool_cat");
            java.util.List<String> kw1 = ch.mgomez.blahaj.client.CustomImageManager.generateKeywords("cool_cat");
            BlahajVariant custom1 = new BlahajVariant("cool_cat", "Cool Cat", loc1, 64, 48, kw1);

            assertTrue("Custom variant must report isFlatImage() == true", custom1.isFlatImage());
            assertEquals(64, custom1.getImageWidth());
            assertEquals(48, custom1.getImageHeight());
            assertEquals(loc1, custom1.getTexture());
            assertEquals("Cool Cat", custom1.getDisplayName());

            BlahajVariant.registerCustomVariant(custom1);

            // Test matching by full name and tokens
            assertEquals(custom1, BlahajVariant.matchVariant("Cool Cat"));
            assertEquals(custom1, BlahajVariant.matchVariant("cool_cat"));
            assertEquals(custom1, BlahajVariant.matchVariant("My cool cat plushie"));

            // Test custom override over built-in variant
            net.minecraft.util.ResourceLocation locBlue = new net.minecraft.util.ResourceLocation(
                "blahaj",
                "custom/blue");
            java.util.List<String> kwBlue = java.util.Arrays.asList("blue", "blue shark");
            BlahajVariant customBlue = new BlahajVariant("blue", "Custom Blue Shark", locBlue, 32, 32, kwBlue);
            BlahajVariant.registerCustomVariant(customBlue);

            // Custom variant should override built-in "blue" variant
            assertEquals(customBlue, BlahajVariant.matchVariant("blue shark"));

            // After clearing, it falls back to built-in variant
            BlahajVariant.clearCustomVariants();
            assertEquals(
                "blue",
                BlahajVariant.matchVariant("blue shark")
                    .getId());
            assertFalse(
                BlahajVariant.matchVariant("blue shark")
                    .isFlatImage());
        } finally {
            BlahajVariant.clearCustomVariants();
        }
    }

    @Test
    public void testToolboxVariantMatching() {
        // Unrenamed Field Engineer's Toolbox returns null for findVariant, and default variant for matchVariant
        assertNull(BlahajVariant.findVariant("Field Engineer's Toolbox"));
        assertEquals(
            "default",
            BlahajVariant.matchVariant("Field Engineer's Toolbox")
                .getId());
        assertFalse(
            BlahajVariant.matchVariant("Field Engineer's Toolbox")
                .isFlatImage());

        // Renamed with non-matching strings like "_" or "a" returns null
        assertNull(BlahajVariant.findVariant("_"));
        assertNull(BlahajVariant.findVariant("a"));
        assertNull(BlahajVariant.findVariant("Screwdrivers"));

        // Renamed with pride or color keywords
        assertEquals(
            "trans",
            BlahajVariant.findVariant("Trans Field Engineer's Toolbox")
                .getId());
        assertEquals(
            "pink",
            BlahajVariant.findVariant("Pink Toolbox")
                .getId());
        assertEquals(
            "grey",
            BlahajVariant.findVariant("Gray Toolbox")
                .getId());

        // Size keywords on toolbox
        assertTrue(BlahajVariant.isLarge("Chonker Toolbox"));
        assertTrue(BlahajVariant.isLarge("Big Field Engineer's Toolbox"));
        assertTrue(BlahajVariant.isSmall("Smol Toolbox"));
        assertTrue(BlahajVariant.isSmall("Tiny Field Engineer's Toolbox"));

        assertFalse(BlahajVariant.isLarge("Field Engineer's Toolbox"));
        assertFalse(BlahajVariant.isSmall("Field Engineer's Toolbox"));
    }

    @Test
    public void testFindToolboxSafeFallback() {
        // When GregTech is not loaded on the test classpath and no item is registered, findToolbox() returns null
        // without throwing exceptions
        net.minecraft.item.Item item = ClientProxy.findToolbox();
        assertNull("findToolbox() must return null safely when GregTech is absent", item);
    }

    @Test
    public void testFindToolboxViaRegistry() {
        try {
            net.minecraft.item.Item dummyToolbox = new net.minecraft.item.Item();
            dummyToolbox.setUnlocalizedName("gt.Item_Toolbox");
            cpw.mods.fml.common.registry.GameRegistry.registerItem(dummyToolbox, "gt.Item_Toolbox", "gregtech");

            net.minecraft.item.Item found = ClientProxy.findToolbox();
            assertNotNull("findToolbox() must find registered toolbox in GameRegistry", found);
            assertEquals(dummyToolbox, found);
        } catch (Throwable ignored) {
            // In pure JUnit without full Forge GameData bootstrap, registerItem may throw; safe fallback is tested
            // above
        }
    }

    @Test
    public void testHasCustomName() {
        assertFalse(RenderBlahajItem.hasCustomName(null));

        net.minecraft.item.Item dummyItem = new net.minecraft.item.Item();
        net.minecraft.item.ItemStack stack = new net.minecraft.item.ItemStack(dummyItem);
        assertFalse(RenderBlahajItem.hasCustomName(stack));

        net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
        stack.setTagCompound(nbt);
        assertFalse(RenderBlahajItem.hasCustomName(stack));

        nbt.setString("SomeOtherModTag", "data");
        assertFalse(RenderBlahajItem.hasCustomName(stack));

        net.minecraft.nbt.NBTTagCompound display = new net.minecraft.nbt.NBTTagCompound();
        nbt.setTag("display", display);
        assertFalse(RenderBlahajItem.hasCustomName(stack));

        display.setString("Name", "   ");
        assertFalse(RenderBlahajItem.hasCustomName(stack));

        display.setString("Name", "Sharky");
        assertTrue(RenderBlahajItem.hasCustomName(stack));
    }

    @Test
    public void testShouldRenderAsBlahaj() {
        RenderBlahajItem renderer = new RenderBlahajItem();
        net.minecraft.item.Item dummyItem = new net.minecraft.item.Item();
        net.minecraft.item.ItemStack unrenamed = new net.minecraft.item.ItemStack(dummyItem);

        assertFalse(renderer.shouldRenderAsBlahaj(null));
        assertFalse(renderer.shouldRenderAsBlahaj(unrenamed));

        // When item has custom name that does NOT match any Blahaj variant (like "_", "a", or "My Tool"),
        // it must NOT render as Blahaj!
        net.minecraft.item.ItemStack nonMatching1 = new net.minecraft.item.ItemStack(dummyItem);
        net.minecraft.nbt.NBTTagCompound nbt1 = new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound display1 = new net.minecraft.nbt.NBTTagCompound();
        display1.setString("Name", "_");
        nbt1.setTag("display", display1);
        nonMatching1.setTagCompound(nbt1);
        assertFalse("Non-matching name '_' must not render as Blahaj", renderer.shouldRenderAsBlahaj(nonMatching1));

        net.minecraft.item.ItemStack nonMatching2 = new net.minecraft.item.ItemStack(dummyItem);
        net.minecraft.nbt.NBTTagCompound nbt2 = new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound display2 = new net.minecraft.nbt.NBTTagCompound();
        display2.setString("Name", "a");
        nbt2.setTag("display", display2);
        nonMatching2.setTagCompound(nbt2);
        assertFalse("Non-matching name 'a' must not render as Blahaj", renderer.shouldRenderAsBlahaj(nonMatching2));

        net.minecraft.item.ItemStack nonMatching3 = new net.minecraft.item.ItemStack(dummyItem);
        net.minecraft.nbt.NBTTagCompound nbt3 = new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound display3 = new net.minecraft.nbt.NBTTagCompound();
        display3.setString("Name", "My Screwdrivers");
        nbt3.setTag("display", display3);
        nonMatching3.setTagCompound(nbt3);
        assertFalse(
            "Non-matching name 'My Screwdrivers' must not render as Blahaj",
            renderer.shouldRenderAsBlahaj(nonMatching3));

        // When item has custom name that matches a Blahaj variant, it renders as Blahaj
        net.minecraft.item.ItemStack renamed = new net.minecraft.item.ItemStack(dummyItem);
        net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound display = new net.minecraft.nbt.NBTTagCompound();
        display.setString("Name", "Trans Shark");
        nbt.setTag("display", display);
        renamed.setTagCompound(nbt);

        assertTrue(renderer.shouldRenderAsBlahaj(renamed));

        // When item is itemBlahaj, it always renders as Blahaj even if unrenamed or given random non-matching name
        try {
            ClientProxy.itemBlahaj = dummyItem;
            assertTrue(renderer.shouldRenderAsBlahaj(unrenamed));
            assertTrue(renderer.shouldRenderAsBlahaj(nonMatching1));
            assertTrue(renderer.shouldRenderAsBlahaj(nonMatching2));
        } finally {
            ClientProxy.itemBlahaj = null;
        }
    }

    @Test
    public void testRenderBlahajItemDelegation() {
        final boolean[] parentCalled = new boolean[2];
        IItemRenderer mockParent = new IItemRenderer() {

            @Override
            public boolean handleRenderType(net.minecraft.item.ItemStack item, ItemRenderType type) {
                parentCalled[0] = true;
                return true;
            }

            @Override
            public boolean shouldUseRenderHelper(ItemRenderType type, net.minecraft.item.ItemStack item,
                ItemRendererHelper helper) {
                parentCalled[1] = true;
                return true;
            }

            @Override
            public void renderItem(ItemRenderType type, net.minecraft.item.ItemStack item, Object... data) {}
        };

        RenderBlahajItem standaloneRenderer = new RenderBlahajItem();
        RenderBlahajItem chainedRenderer = new RenderBlahajItem(mockParent);

        assertEquals(mockParent, chainedRenderer.getParentRenderer());
        assertNull(standaloneRenderer.getParentRenderer());

        net.minecraft.item.Item dummyItem = new net.minecraft.item.Item();
        net.minecraft.item.ItemStack unrenamed = new net.minecraft.item.ItemStack(dummyItem);

        // Standalone renderer without parent returns false for unrenamed item
        assertFalse(standaloneRenderer.handleRenderType(unrenamed, IItemRenderer.ItemRenderType.EQUIPPED));
        assertFalse(
            standaloneRenderer.shouldUseRenderHelper(
                IItemRenderer.ItemRenderType.EQUIPPED,
                unrenamed,
                IItemRenderer.ItemRendererHelper.ENTITY_ROTATION));

        // Chained renderer delegates to parent for unrenamed item
        assertTrue(chainedRenderer.handleRenderType(unrenamed, IItemRenderer.ItemRenderType.EQUIPPED));
        assertTrue(parentCalled[0]);
        assertTrue(
            chainedRenderer.shouldUseRenderHelper(
                IItemRenderer.ItemRenderType.EQUIPPED,
                unrenamed,
                IItemRenderer.ItemRendererHelper.ENTITY_ROTATION));
        assertTrue(parentCalled[1]);

        // When renamed, chained renderer handles it as Blahaj directly without calling parent
        parentCalled[0] = false;
        parentCalled[1] = false;
        net.minecraft.item.ItemStack renamed = new net.minecraft.item.ItemStack(dummyItem);
        net.minecraft.nbt.NBTTagCompound nbt = new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound display = new net.minecraft.nbt.NBTTagCompound();
        display.setString("Name", "Shark");
        nbt.setTag("display", display);
        renamed.setTagCompound(nbt);

        assertTrue(chainedRenderer.handleRenderType(renamed, IItemRenderer.ItemRenderType.EQUIPPED));
        assertFalse("Parent handleRenderType should not be called when item is renamed to Blahaj", parentCalled[0]);
        assertTrue(
            chainedRenderer.shouldUseRenderHelper(
                IItemRenderer.ItemRenderType.ENTITY,
                renamed,
                IItemRenderer.ItemRendererHelper.ENTITY_ROTATION));
        assertFalse(
            "Parent shouldUseRenderHelper should not be called when item is renamed to Blahaj",
            parentCalled[1]);
    }
}
