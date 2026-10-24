package ch.mgomez.blahaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BlahajVariant {

    private final String id;
    private final String displayName;
    private final String textureFile;
    private final List<String> keywords;
    private final boolean lesser;
    private final boolean flatImage;
    private final int imageWidth;
    private final int imageHeight;
    private ResourceLocation cachedTexture;

    public BlahajVariant(String id, String displayName, String textureFile, List<String> alternatives, boolean lesser) {
        this.id = id;
        this.displayName = displayName;
        this.textureFile = textureFile;
        this.lesser = lesser;
        this.flatImage = false;
        this.imageWidth = 0;
        this.imageHeight = 0;

        List<String> kw = new ArrayList<>();
        kw.add(id);
        if (alternatives != null) {
            kw.addAll(alternatives);
        }
        this.keywords = Collections.unmodifiableList(kw);
    }

    public BlahajVariant(String id, String displayName, String textureFile, List<String> alternatives) {
        this(id, displayName, textureFile, alternatives, false);
    }

    public BlahajVariant(String id, String displayName, String textureFile) {
        this(id, displayName, textureFile, Collections.emptyList(), false);
    }

    public BlahajVariant(String id, String displayName, ResourceLocation texture, int imageWidth, int imageHeight,
        List<String> alternatives) {
        this.id = id;
        this.displayName = displayName;
        this.textureFile = null;
        this.cachedTexture = texture;
        this.lesser = false;
        this.flatImage = true;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        List<String> kw = new ArrayList<>();
        kw.add(id);
        if (alternatives != null) {
            kw.addAll(alternatives);
        }
        this.keywords = Collections.unmodifiableList(kw);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        if (flatImage) {
            return displayName;
        }
        String key = "variant.blahaj." + id;
        if (net.minecraft.util.StatCollector.canTranslate(key)) {
            return net.minecraft.util.StatCollector.translateToLocal(key);
        }
        return displayName;
    }

    public List<String> getKeywords() {
        String trans = getDisplayName();
        if (trans != null && !trans.equalsIgnoreCase(displayName)
            && !keywords.contains(trans.toLowerCase(Locale.ROOT))) {
            List<String> list = new ArrayList<>(keywords);
            list.add(trans.toLowerCase(Locale.ROOT));
            return Collections.unmodifiableList(list);
        }
        return keywords;
    }

    public boolean isLesser() {
        return lesser;
    }

    public boolean isFlatImage() {
        return flatImage;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getTextureFile() {
        return textureFile;
    }

    public ResourceLocation getTexture() {
        if (cachedTexture == null && textureFile != null) {
            cachedTexture = new ResourceLocation("blahaj", "textures/models/variants/" + textureFile);
        }
        return cachedTexture;
    }

    public static final List<String> LARGE_KEYWORDS = Arrays.asList("large", "big", "chonker", "chonk");
    public static final List<String> SMALL_KEYWORDS = Arrays
        .asList("small", "smol", "sm\u00f6l", "smolhaj", "tiny", "mini", "pocket");

    public static final List<BlahajVariant> ALL_VARIANTS = new ArrayList<>();
    public static final List<BlahajVariant> CUSTOM_VARIANTS = new ArrayList<>();
    public static final BlahajVariant DEFAULT_VARIANT = new BlahajVariant("default", "Classic blue", "blue_shark.png");

    public static synchronized void registerCustomVariant(BlahajVariant variant) {
        CUSTOM_VARIANTS.add(variant);
    }

    public static synchronized void clearCustomVariants() {
        CUSTOM_VARIANTS.clear();
    }

    public static synchronized List<BlahajVariant> getCustomVariants() {
        return new ArrayList<>(CUSTOM_VARIANTS);
    }

    static {
        // Colors
        register(
            new BlahajVariant(
                "grey",
                "Grey shark",
                "grey_shark.png",
                Arrays.asList("gray", "grahaj", "gråhaj", "klappar haj")));
        register(new BlahajVariant("red", "Red shark", "red_shark.png"));
        register(new BlahajVariant("orange", "Orange shark", "orange_shark.png"));
        register(new BlahajVariant("yellow", "Yellow shark", "yellow_shark.png"));
        register(new BlahajVariant("lime", "Lime shark", "lime_shark.png"));
        register(new BlahajVariant("green", "Green shark", "green_shark.png"));
        register(new BlahajVariant("cyan", "Cyan shark", "cyan_shark.png"));
        register(new BlahajVariant("blue", "Classic blue shark", "blue_shark.png"));
        register(new BlahajVariant("purple", "Purple shark", "purple_shark.png"));
        register(new BlahajVariant("magenta", "Magenta shark", "magenta_shark.png"));
        register(new BlahajVariant("pink", "Pink shark", "pink_shark.png", Arrays.asList("rosahaj")));
        register(new BlahajVariant("black", "Black shark", "black_shark.png"));
        register(new BlahajVariant("white", "White shark", "white_shark.png"));

        // Pride flags
        register(
            new BlahajVariant(
                "trans",
                "Transgender pride shark",
                "trans_shark.png",
                Arrays.asList("transgender", "transhaj")));
        register(
            new BlahajVariant("pride", "Rainbow pride shark", "pride_shark.png", Arrays.asList("rainbow", "lgbt")));
        register(
            new BlahajVariant(
                "prider",
                "Progress pride shark",
                "prider_shark.png",
                Arrays.asList("progress", "lgbtqia", "lgbtq+")));
        register(new BlahajVariant("lesbian", "Lesbian pride shark", "lesbian_shark.png", Arrays.asList("yuri")));
        register(
            new BlahajVariant("gay", "Gay pride shark", "gay_shark.png", Arrays.asList("homosexual", "yaoi", "mlm")));
        register(new BlahajVariant("bi", "Bisexual pride shark", "bi_shark.png", Arrays.asList("bisexual")));
        register(new BlahajVariant("pan", "Pansexual pride shark", "pan_shark.png", Arrays.asList("pansexual")));
        register(new BlahajVariant("ace", "Asexual pride shark", "ace_shark.png", Arrays.asList("asexual")));
        register(new BlahajVariant("aroace", "Aroace pride shark", "aroace_shark.png"));
        register(new BlahajVariant("aro", "Aromantic pride shark", "aro_shark.png", Arrays.asList("aromantic")));
        register(
            new BlahajVariant(
                "enby",
                "Non-Binary pride shark",
                "enby_shark.png",
                Arrays.asList("non binary", "non-binary", "nb")));
        register(new BlahajVariant("genderfluid", "Genderfluid pride shark", "genderfluid_shark.png"));
        register(new BlahajVariant("genderqueer", "Genderqueer pride shark", "genderqueer_shark.png"));
        register(new BlahajVariant("agender", "Agender pride shark", "agender_shark.png"));
        register(new BlahajVariant("intersex", "Intersex pride shark", "intersex_shark.png"));
        register(new BlahajVariant("poly", "Polysexual pride shark", "poly_shark.png", Arrays.asList("polysexual")));
        register(new BlahajVariant("demiboy", "Demiboy pride shark", "demiboy_shark.png"));
        register(new BlahajVariant("demigirl", "Demigirl pride shark", "demigirl_shark.png"));
        register(new BlahajVariant("demisexual", "Demisexual pride shark", "demisexual_shark.png"));
        register(new BlahajVariant("demiromantic", "Demiromantic pride shark", "demiromantic_shark.png"));
        register(
            new BlahajVariant(
                "greysexual",
                "Greysexual pride shark",
                "greysexual_shark.png",
                Arrays.asList("graysexual")));
        register(
            new BlahajVariant(
                "greyromantic",
                "Greyromantic pride shark",
                "greyromantic_shark.png",
                Arrays.asList("grayromantic")));
        register(
            new BlahajVariant("greyrose", "Greyrose pride shark", "greyrose_shark.png", Arrays.asList("grayrose")));

        // Generic shark matches (marked lesser so specific variants take priority)
        register(
            new BlahajVariant(
                "shark",
                "Natural shark",
                "shark_shark.png",
                Arrays.asList(
                    "shork",
                    "shonk",
                    "sharky",
                    "sharkie",
                    "chomper",
                    "chompy",
                    "megalodon",
                    "meg",
                    "meggy",
                    "gawr"),
                true));

        register(
            new BlahajVariant(
                "blahaj",
                "Classic Bl\u00e5haj",
                "blue_shark.png",
                Arrays.asList("blahaj", "bl\u00e5haj", "haj", "haai", "hai", "default", "classic"),
                true));
    }

    private static void register(BlahajVariant v) {
        ALL_VARIANTS.add(v);
    }

    public static BlahajVariant findVariant(ItemStack stack) {
        if (stack == null || !stack.hasDisplayName()) return null;
        return findVariant(stack.getDisplayName());
    }

    public static BlahajVariant findVariant(String nameStr) {
        if (nameStr == null || nameStr.trim()
            .isEmpty()) {
            return null;
        }

        String lower = nameStr.toLowerCase(Locale.ROOT);
        String lowerNorm = lower.replace('\u00e5', 'a')
            .replace('\u00e4', 'a')
            .replace('\u00f6', 'o');
        String[] rawTokens = lower.split("[ \\-_,.:;!?'\"()\\[\\]{}]+");
        Set<String> tokenSet = new HashSet<>();
        for (String t : rawTokens) {
            String trimmed = t.trim();
            if (!trimmed.isEmpty()) {
                tokenSet.add(trimmed);
                tokenSet.add(
                    trimmed.replace('\u00e5', 'a')
                        .replace('\u00e4', 'a')
                        .replace('\u00f6', 'o'));
            }
        }

        if (tokenSet.isEmpty()) {
            return null;
        }

        // 1. Check custom flat image variants first
        BlahajVariant bestCustomMatch = null;
        int bestCustomKeywordLen = -1;

        synchronized (CUSTOM_VARIANTS) {
            for (BlahajVariant v : CUSTOM_VARIANTS) {
                for (String kw : v.getKeywords()) {
                    if (kw == null) continue;
                    String kwLower = kw.toLowerCase(Locale.ROOT)
                        .trim();
                    if (kwLower.isEmpty()) continue;

                    String[] kwRawTokens = kwLower.split("[ \\-_]+");
                    List<String> kwTokens = new ArrayList<>();
                    for (String kt : kwRawTokens) {
                        String trimmed = kt.trim();
                        if (!trimmed.isEmpty()) {
                            kwTokens.add(trimmed);
                        }
                    }
                    if (kwTokens.isEmpty()) continue;

                    boolean matches = true;
                    for (String kt : kwTokens) {
                        String ktNorm = kt.replace('\u00e5', 'a')
                            .replace('\u00e4', 'a')
                            .replace('\u00f6', 'o');
                        boolean tokenMatch = tokenSet.contains(kt) || tokenSet.contains(ktNorm);
                        boolean subMatch = lower.contains(kt) || lowerNorm.contains(ktNorm);
                        if (!tokenMatch && (kt.length() < 3 || !subMatch)) {
                            matches = false;
                            break;
                        }
                    }
                    if (matches) {
                        int len = kw.length();
                        if (bestCustomMatch == null || len > bestCustomKeywordLen) {
                            bestCustomMatch = v;
                            bestCustomKeywordLen = len;
                        }
                    }
                }
            }
        }

        if (bestCustomMatch != null) {
            return bestCustomMatch;
        }

        // 2. Check built-in plushie variants
        BlahajVariant bestMatch = null;
        int bestKeywordLen = -1;

        for (BlahajVariant v : ALL_VARIANTS) {
            for (String kw : v.getKeywords()) {
                if (kw == null) continue;
                String kwLower = kw.toLowerCase(Locale.ROOT)
                    .trim();
                if (kwLower.isEmpty()) continue;

                String[] kwRawTokens = kwLower.split("[ \\-_]+");
                List<String> kwTokens = new ArrayList<>();
                for (String kt : kwRawTokens) {
                    String trimmed = kt.trim();
                    if (!trimmed.isEmpty()) {
                        kwTokens.add(trimmed);
                    }
                }
                if (kwTokens.isEmpty()) continue;

                boolean matches = true;
                for (String kt : kwTokens) {
                    String ktNorm = kt.replace('\u00e5', 'a')
                        .replace('\u00e4', 'a')
                        .replace('\u00f6', 'o');
                    boolean tokenMatch = tokenSet.contains(kt) || tokenSet.contains(ktNorm);
                    boolean subMatch = lower.contains(kt) || lowerNorm.contains(ktNorm);
                    if (!tokenMatch && (kt.length() < 3 || !subMatch)) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    int len = kw.length();
                    if (bestMatch == null || (!v.isLesser() && bestMatch.isLesser())
                        || (len > bestKeywordLen && (v.isLesser() == bestMatch.isLesser()))) {
                        bestMatch = v;
                        bestKeywordLen = len;
                    }
                }
            }
        }

        return bestMatch;
    }

    public static BlahajVariant matchVariant(ItemStack stack) {
        if (stack == null) return DEFAULT_VARIANT;
        String nameStr = null;
        if (stack.hasDisplayName()) {
            nameStr = stack.getDisplayName();
        }
        return matchVariant(nameStr);
    }

    public static BlahajVariant matchVariant(String nameStr) {
        BlahajVariant v = findVariant(nameStr);
        return v != null ? v : DEFAULT_VARIANT;
    }

    public static boolean isLarge(ItemStack stack) {
        if (stack == null || !stack.hasDisplayName()) return false;
        return isLarge(stack.getDisplayName());
    }

    public static boolean isLarge(String nameStr) {
        if (nameStr == null) return false;
        String lower = nameStr.toLowerCase(Locale.ROOT);
        for (String kw : LARGE_KEYWORDS) {
            if (lower.contains(kw)) return true;
        }
        return false;
    }

    public static boolean isSmall(ItemStack stack) {
        if (stack == null || !stack.hasDisplayName()) return false;
        return isSmall(stack.getDisplayName());
    }

    public static boolean isSmall(String nameStr) {
        if (nameStr == null) return false;
        String lower = nameStr.toLowerCase(Locale.ROOT);
        for (String kw : SMALL_KEYWORDS) {
            if (lower.contains(kw)) return true;
        }
        return false;
    }
}
