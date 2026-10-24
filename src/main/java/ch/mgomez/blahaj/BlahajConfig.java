package ch.mgomez.blahaj;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Configuration options for Blåhaj rendering, animations, and sizes.
 * Persisted to config/blahaj.cfg and editable in-game via the mod options menu.
 */
public class BlahajConfig {

    public static final BlahajConfig INSTANCE = new BlahajConfig();
    private static Configuration config;

    // Categories
    public static final String CAT_FIRST_PERSON = "first_person";
    public static final String CAT_THIRD_PERSON = "third_person";
    public static final String CAT_ANIMATION = "animation";
    public static final String CAT_SIZES = "sizes";

    public static final double DEFAULT_FP_POS_X = 0.7;
    public static final double DEFAULT_FP_POS_Y = 0.3;
    public static final double DEFAULT_FP_POS_Z = 0.45;
    public static final double DEFAULT_FP_ROT_X = 60.0;
    public static final double DEFAULT_FP_ROT_Y = -55.0;
    public static final double DEFAULT_FP_ROT_Z = 15.0;

    public static final double DEFAULT_TP_POS_X = 0.5;
    public static final double DEFAULT_TP_POS_Y = 0.4;
    public static final double DEFAULT_TP_POS_Z = 0.1;
    public static final double DEFAULT_TP_ROT_X = 15.0;
    public static final double DEFAULT_TP_ROT_Y = 120.0;
    public static final double DEFAULT_TP_ROT_Z = -80.0;

    public static final boolean DEFAULT_ENABLE_SPIN = false;
    public static final double DEFAULT_SPIN_PITCH = 0.0;
    public static final double DEFAULT_SPIN_YAW = 0.0;
    public static final double DEFAULT_SPIN_ROLL = 90.0;

    public static final double DEFAULT_CHONKER_SCALE = 1.5;
    public static final double DEFAULT_SMALL_SCALE = 0.65;

    public static double firstPersonPosX = DEFAULT_FP_POS_X;
    public static double firstPersonPosY = DEFAULT_FP_POS_Y;
    public static double firstPersonPosZ = DEFAULT_FP_POS_Z;
    public static double firstPersonRotX = DEFAULT_FP_ROT_X;
    public static double firstPersonRotY = DEFAULT_FP_ROT_Y;
    public static double firstPersonRotZ = DEFAULT_FP_ROT_Z;

    public static double thirdPersonPosX = DEFAULT_TP_POS_X;
    public static double thirdPersonPosY = DEFAULT_TP_POS_Y;
    public static double thirdPersonPosZ = DEFAULT_TP_POS_Z;
    public static double thirdPersonRotX = DEFAULT_TP_ROT_X;
    public static double thirdPersonRotY = DEFAULT_TP_ROT_Y;
    public static double thirdPersonRotZ = DEFAULT_TP_ROT_Z;

    public static boolean enableSpin = DEFAULT_ENABLE_SPIN;
    public static double spinSpeedPitch = DEFAULT_SPIN_PITCH;
    public static double spinSpeedYaw = DEFAULT_SPIN_YAW;
    public static double spinSpeedRoll = DEFAULT_SPIN_ROLL;

    public static double chonkerScaleMultiplier = DEFAULT_CHONKER_SCALE;
    public static double smallScaleMultiplier = DEFAULT_SMALL_SCALE;

    public static void init(File file) {
        if (config == null) {
            config = new Configuration(file);
            FMLCommonHandler.instance()
                .bus()
                .register(INSTANCE);
            syncConfig();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void reload() {
        if (config == null) return;
        config.load();
        syncConfig();
    }

    public static void save() {
        if (config == null) return;

        config.get(CAT_FIRST_PERSON, "posX", DEFAULT_FP_POS_X)
            .set(firstPersonPosX);
        config.get(CAT_FIRST_PERSON, "posY", DEFAULT_FP_POS_Y)
            .set(firstPersonPosY);
        config.get(CAT_FIRST_PERSON, "posZ", DEFAULT_FP_POS_Z)
            .set(firstPersonPosZ);
        config.get(CAT_FIRST_PERSON, "rotX", DEFAULT_FP_ROT_X)
            .set(firstPersonRotX);
        config.get(CAT_FIRST_PERSON, "rotY", DEFAULT_FP_ROT_Y)
            .set(firstPersonRotY);
        config.get(CAT_FIRST_PERSON, "rotZ", DEFAULT_FP_ROT_Z)
            .set(firstPersonRotZ);

        config.get(CAT_THIRD_PERSON, "posX", DEFAULT_TP_POS_X)
            .set(thirdPersonPosX);
        config.get(CAT_THIRD_PERSON, "posY", DEFAULT_TP_POS_Y)
            .set(thirdPersonPosY);
        config.get(CAT_THIRD_PERSON, "posZ", DEFAULT_TP_POS_Z)
            .set(thirdPersonPosZ);
        config.get(CAT_THIRD_PERSON, "rotX", DEFAULT_TP_ROT_X)
            .set(thirdPersonRotX);
        config.get(CAT_THIRD_PERSON, "rotY", DEFAULT_TP_ROT_Y)
            .set(thirdPersonRotY);
        config.get(CAT_THIRD_PERSON, "rotZ", DEFAULT_TP_ROT_Z)
            .set(thirdPersonRotZ);

        config.get(CAT_ANIMATION, "enableSpin", DEFAULT_ENABLE_SPIN)
            .set(enableSpin);
        config.get(CAT_ANIMATION, "spinSpeedPitch", DEFAULT_SPIN_PITCH)
            .set(spinSpeedPitch);
        config.get(CAT_ANIMATION, "spinSpeedYaw", DEFAULT_SPIN_YAW)
            .set(spinSpeedYaw);
        config.get(CAT_ANIMATION, "spinSpeedRoll", DEFAULT_SPIN_ROLL)
            .set(spinSpeedRoll);

        config.get(CAT_SIZES, "chonkerScaleMultiplier", DEFAULT_CHONKER_SCALE)
            .set(chonkerScaleMultiplier);
        config.get(CAT_SIZES, "smallScaleMultiplier", DEFAULT_SMALL_SCALE)
            .set(smallScaleMultiplier);

        config.save();
    }

    public static void resetDefaultsFirstPerson() {
        firstPersonPosX = DEFAULT_FP_POS_X;
        firstPersonPosY = DEFAULT_FP_POS_Y;
        firstPersonPosZ = DEFAULT_FP_POS_Z;
        firstPersonRotX = DEFAULT_FP_ROT_X;
        firstPersonRotY = DEFAULT_FP_ROT_Y;
        firstPersonRotZ = DEFAULT_FP_ROT_Z;
    }

    public static void resetDefaultsThirdPerson() {
        thirdPersonPosX = DEFAULT_TP_POS_X;
        thirdPersonPosY = DEFAULT_TP_POS_Y;
        thirdPersonPosZ = DEFAULT_TP_POS_Z;
        thirdPersonRotX = DEFAULT_TP_ROT_X;
        thirdPersonRotY = DEFAULT_TP_ROT_Y;
        thirdPersonRotZ = DEFAULT_TP_ROT_Z;
    }

    public static void resetDefaultsAnimation() {
        enableSpin = DEFAULT_ENABLE_SPIN;
        spinSpeedPitch = DEFAULT_SPIN_PITCH;
        spinSpeedYaw = DEFAULT_SPIN_YAW;
        spinSpeedRoll = DEFAULT_SPIN_ROLL;
    }

    public static void resetDefaultsSizes() {
        chonkerScaleMultiplier = DEFAULT_CHONKER_SCALE;
        smallScaleMultiplier = DEFAULT_SMALL_SCALE;
    }

    public static void syncConfig() {
        if (config == null) return;

        // First person
        config.setCategoryComment(CAT_FIRST_PERSON, "First-person lateral position and angle settings.");
        config.setCategoryLanguageKey(CAT_FIRST_PERSON, "blahaj.config.category.first_person");
        firstPersonPosX = config
            .get(CAT_FIRST_PERSON, "posX", DEFAULT_FP_POS_X, "Lateral X offset in first-person.", -10.0, 10.0)
            .setLanguageKey("blahaj.config.first_person.posX")
            .getDouble();
        firstPersonPosY = config
            .get(CAT_FIRST_PERSON, "posY", DEFAULT_FP_POS_Y, "Vertical Y offset in first-person.", -10.0, 10.0)
            .setLanguageKey("blahaj.config.first_person.posY")
            .getDouble();
        firstPersonPosZ = config
            .get(CAT_FIRST_PERSON, "posZ", DEFAULT_FP_POS_Z, "Depth Z offset in first-person.", -10.0, 10.0)
            .setLanguageKey("blahaj.config.first_person.posZ")
            .getDouble();
        firstPersonRotX = config
            .get(CAT_FIRST_PERSON, "rotX", DEFAULT_FP_ROT_X, "Pitch rotation (X axis) in first-person.", -360.0, 360.0)
            .setLanguageKey("blahaj.config.first_person.rotX")
            .getDouble();
        firstPersonRotY = config
            .get(CAT_FIRST_PERSON, "rotY", DEFAULT_FP_ROT_Y, "Yaw rotation (Y axis) in first-person.", -360.0, 360.0)
            .setLanguageKey("blahaj.config.first_person.rotY")
            .getDouble();
        firstPersonRotZ = config
            .get(
                CAT_FIRST_PERSON,
                "rotZ",
                DEFAULT_FP_ROT_Z,
                "Roll rotation (Z axis, along length) in first-person.",
                -360.0,
                360.0)
            .setLanguageKey("blahaj.config.first_person.rotZ")
            .getDouble();

        // Third person
        config
            .setCategoryComment(CAT_THIRD_PERSON, "Third-person lateral position and angle settings on player model.");
        config.setCategoryLanguageKey(CAT_THIRD_PERSON, "blahaj.config.category.third_person");
        thirdPersonPosX = config
            .get(
                CAT_THIRD_PERSON,
                "posX",
                DEFAULT_TP_POS_X,
                "Lateral X offset on player model in third-person.",
                -10.0,
                10.0)
            .setLanguageKey("blahaj.config.third_person.posX")
            .getDouble();
        thirdPersonPosY = config
            .get(
                CAT_THIRD_PERSON,
                "posY",
                DEFAULT_TP_POS_Y,
                "Vertical Y offset on player model in third-person.",
                -10.0,
                10.0)
            .setLanguageKey("blahaj.config.third_person.posY")
            .getDouble();
        thirdPersonPosZ = config
            .get(
                CAT_THIRD_PERSON,
                "posZ",
                DEFAULT_TP_POS_Z,
                "Depth Z offset on player model in third-person.",
                -10.0,
                10.0)
            .setLanguageKey("blahaj.config.third_person.posZ")
            .getDouble();
        thirdPersonRotX = config
            .get(CAT_THIRD_PERSON, "rotX", DEFAULT_TP_ROT_X, "Pitch rotation (X axis) in third-person.", -360.0, 360.0)
            .setLanguageKey("blahaj.config.third_person.rotX")
            .getDouble();
        thirdPersonRotY = config
            .get(CAT_THIRD_PERSON, "rotY", DEFAULT_TP_ROT_Y, "Yaw rotation (Y axis) in third-person.", -360.0, 360.0)
            .setLanguageKey("blahaj.config.third_person.rotY")
            .getDouble();
        thirdPersonRotZ = config
            .get(
                CAT_THIRD_PERSON,
                "rotZ",
                DEFAULT_TP_ROT_Z,
                "Roll rotation (Z axis, along length) in third-person.",
                -360.0,
                360.0)
            .setLanguageKey("blahaj.config.third_person.rotZ")
            .getDouble();

        // Animation
        config.setCategoryComment(CAT_ANIMATION, "Continuous spinning animation and speed settings.");
        config.setCategoryLanguageKey(CAT_ANIMATION, "blahaj.config.category.animation");
        enableSpin = config
            .get(
                CAT_ANIMATION,
                "enableSpin",
                DEFAULT_ENABLE_SPIN,
                "Whether the Bl\u00e5haj continuously spins while held.")
            .setLanguageKey("blahaj.config.animation.enableSpin")
            .getBoolean();
        spinSpeedPitch = config
            .get(
                CAT_ANIMATION,
                "spinSpeedPitch",
                DEFAULT_SPIN_PITCH,
                "Continuous spin speed around pitch axis (X) in degrees per second.",
                -3600.0,
                3600.0)
            .setLanguageKey("blahaj.config.animation.spinSpeedPitch")
            .getDouble();
        spinSpeedYaw = config
            .get(
                CAT_ANIMATION,
                "spinSpeedYaw",
                DEFAULT_SPIN_YAW,
                "Continuous spin speed around yaw axis (Y) in degrees per second.",
                -3600.0,
                3600.0)
            .setLanguageKey("blahaj.config.animation.spinSpeedYaw")
            .getDouble();
        spinSpeedRoll = config
            .get(
                CAT_ANIMATION,
                "spinSpeedRoll",
                DEFAULT_SPIN_ROLL,
                "Continuous spin speed along model length (Z) in degrees per second.",
                -3600.0,
                3600.0)
            .setLanguageKey("blahaj.config.animation.spinSpeedRoll")
            .getDouble();

        // Sizes
        config.setCategoryComment(CAT_SIZES, "Size scale multipliers for named variants.");
        config.setCategoryLanguageKey(CAT_SIZES, "blahaj.config.category.sizes");
        chonkerScaleMultiplier = config
            .get(
                CAT_SIZES,
                "chonkerScaleMultiplier",
                DEFAULT_CHONKER_SCALE,
                "Scale multiplier applied for chonker/big/large variants.",
                0.1,
                10.0)
            .setLanguageKey("blahaj.config.sizes.chonkerScaleMultiplier")
            .getDouble();
        smallScaleMultiplier = config
            .get(
                CAT_SIZES,
                "smallScaleMultiplier",
                DEFAULT_SMALL_SCALE,
                "Scale multiplier applied for small/smol/sm\u00f6l variants.",
                0.1,
                10.0)
            .setLanguageKey("blahaj.config.sizes.smallScaleMultiplier")
            .getDouble();

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(BlahajMod.MODID)) {
            syncConfig();
        }
    }
}
