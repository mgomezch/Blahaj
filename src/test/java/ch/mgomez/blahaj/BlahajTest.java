package ch.mgomez.blahaj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

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
        assertEquals("Healing Blåhaj", props.get("item.extrautils:defoliageAxe.name"));
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

        // Pride flags
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

        // Characters & fun
        assertEquals(
            "bread",
            BlahajVariant.matchVariant("Toast Shark")
                .getId());
        assertEquals(
            "bread",
            BlahajVariant.matchVariant("Baguette")
                .getId());
        assertEquals(
            "whale",
            BlahajVariant.matchVariant("Bl\u00e5vingad")
                .getId());
        assertEquals(
            "bridget",
            BlahajVariant.matchVariant("Brisket")
                .getId());
        assertEquals(
            "bridget",
            BlahajVariant.matchVariant("Basket")
                .getId());
        assertEquals(
            "astolfo_bean",
            BlahajVariant.matchVariant("Astolfo Bean")
                .getId());
        assertEquals(
            "mahiro_school",
            BlahajVariant.matchVariant("Mahiro Oyama")
                .getId());
        assertEquals(
            "mahiro_lazy",
            BlahajVariant.matchVariant("Lazy Mahiro")
                .getId());
        assertEquals(
            "mahiro_lazy",
            BlahajVariant.matchVariant("Eepy Mahiro")
                .getId());

        // Shark vs Blahaj
        assertEquals(
            "shark",
            BlahajVariant.matchVariant("Shark")
                .getId());
        assertEquals(
            "blahaj",
            BlahajVariant.matchVariant("Bl\u00e5haj")
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
        assertTrue(BlahajVariant.isSmall("Mini bread"));
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
}
