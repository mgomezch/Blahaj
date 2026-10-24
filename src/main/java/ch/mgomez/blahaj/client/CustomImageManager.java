package ch.mgomez.blahaj.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import ch.mgomez.blahaj.BlahajMod;
import ch.mgomez.blahaj.BlahajVariant;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomImageManager {

    private static final List<ResourceLocation> registeredTextures = new ArrayList<>();
    private static File customDir = null;
    private static long lastLoadedDirMtime = -1L;
    private static int lastLoadedFileCount = -1;

    public static File getCustomDir() {
        if (customDir == null) {
            File baseConfig;
            try {
                baseConfig = Loader.instance()
                    .getConfigDir();
            } catch (Throwable t) {
                baseConfig = new File(Minecraft.getMinecraft().mcDataDir, "config");
            }
            customDir = new File(baseConfig, "blahaj/custom");
        }
        return customDir;
    }

    public static void ensureDirectoryExists() {
        File dir = getCustomDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File readme = new File(dir, "README.txt");
        if (!readme.exists()) {
            try (FileWriter writer = new FileWriter(readme)) {
                writer.write("Bl\u00e5haj Custom Flat Images\n");
                writer.write("-------------------------\n");
                writer.write("Drop flat transparent .png images into this directory.\n");
                writer
                    .write("The file name (without .png) becomes the keyword used to match the item on an anvil!\n\n");
                writer.write("For example:\n");
                writer.write("  - \"cool_cat.png\" -> Rename your Bl\u00e5haj to \"Cool Cat\" on an anvil!\n");
                writer.write("  - \"space_shark.png\" -> Rename your Bl\u00e5haj to \"Space Shark\"!\n\n");
                writer.write("Images can be any resolution with transparency (PNG format).\n");
                writer.write("They will automatically be rendered as flat items in inventory and in hand.\n");
                writer.write("Entering or reloading a world will automatically refresh these images.\n");
            } catch (IOException e) {
                BlahajMod.LOG.warn("Failed to write README.txt to custom images directory", e);
            }
        }
    }

    public static synchronized int reloadCustomImages() {
        ensureDirectoryExists();
        File dir = getCustomDir();
        File[] pngFiles = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName()
                    .toLowerCase(Locale.ROOT);
                return pathname.isFile() && name.endsWith(".png");
            }
        });

        if (pngFiles == null) {
            pngFiles = new File[0];
        }

        TextureManager textureManager = Minecraft.getMinecraft()
            .getTextureManager();

        // 1. Delete previous textures from Minecraft's TextureManager
        for (ResourceLocation loc : registeredTextures) {
            try {
                textureManager.deleteTexture(loc);
            } catch (Throwable t) {
                BlahajMod.LOG.warn("Failed to delete dynamic texture: " + loc, t);
            }
        }
        registeredTextures.clear();
        BlahajVariant.clearCustomVariants();

        // 2. Load each PNG file
        int loadedCount = 0;
        for (File file : pngFiles) {
            try {
                String rawName = file.getName();
                String baseName = rawName.substring(0, rawName.lastIndexOf('.'));
                String id = baseName.toLowerCase(Locale.ROOT);

                BufferedImage rawImg = ImageIO.read(file);
                if (rawImg == null) {
                    BlahajMod.LOG.warn("Could not decode image from " + file.getName());
                    continue;
                }

                // Ensure ARGB for proper OpenGL alpha rendering
                BufferedImage argbImg;
                if (rawImg.getType() == BufferedImage.TYPE_INT_ARGB) {
                    argbImg = rawImg;
                } else {
                    argbImg = new BufferedImage(rawImg.getWidth(), rawImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = argbImg.createGraphics();
                    g2d.drawImage(rawImg, 0, 0, null);
                    g2d.dispose();
                }

                DynamicTexture dynamicTexture = new DynamicTexture(argbImg);
                String safeId = id.replaceAll("[^a-z0-9_.-]", "_");
                ResourceLocation loc = new ResourceLocation("blahaj", "custom/" + safeId);
                textureManager.loadTexture(loc, dynamicTexture);
                registeredTextures.add(loc);

                // Build display name and keywords
                String displayName = formatDisplayName(baseName);
                List<String> keywords = generateKeywords(baseName);

                BlahajVariant variant = new BlahajVariant(
                    id,
                    displayName,
                    loc,
                    argbImg.getWidth(),
                    argbImg.getHeight(),
                    keywords);

                BlahajVariant.registerCustomVariant(variant);
                loadedCount++;
            } catch (Throwable t) {
                BlahajMod.LOG.error("Failed to load custom Bl\u00e5haj image: " + file.getName(), t);
            }
        }

        lastLoadedDirMtime = dir.lastModified();
        lastLoadedFileCount = pngFiles.length;
        BlahajMod.LOG
            .info("Loaded " + loadedCount + " custom flat Bl\u00e5haj variant image(s) from " + dir.getAbsolutePath());
        return loadedCount;
    }

    public static boolean hasDirectoryChanged() {
        File dir = getCustomDir();
        if (!dir.exists()) return false;
        long mtime = dir.lastModified();
        if (mtime != lastLoadedDirMtime) return true;
        File[] files = dir.listFiles();
        int count = files != null ? files.length : 0;
        return count != lastLoadedFileCount;
    }

    public static String formatDisplayName(String baseName) {
        String clean = baseName.replace('_', ' ')
            .replace('-', ' ')
            .trim();
        if (clean.isEmpty()) return baseName;
        String[] words = clean.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i].isEmpty()) continue;
            if (i > 0) sb.append(' ');
            sb.append(Character.toUpperCase(words[i].charAt(0)));
            if (words[i].length() > 1) {
                sb.append(
                    words[i].substring(1)
                        .toLowerCase(Locale.ROOT));
            }
        }
        return sb.toString();
    }

    public static List<String> generateKeywords(String baseName) {
        List<String> kw = new ArrayList<>();
        String lower = baseName.toLowerCase(Locale.ROOT);
        kw.add(lower);

        String spaceForm = lower.replace('_', ' ')
            .replace('-', ' ')
            .trim();
        if (!spaceForm.equals(lower) && !kw.contains(spaceForm)) {
            kw.add(spaceForm);
        }

        String underForm = lower.replace(' ', '_')
            .replace('-', '_')
            .trim();
        if (!underForm.equals(lower) && !kw.contains(underForm)) {
            kw.add(underForm);
        }

        // Add individual words if multi-word
        String[] parts = spaceForm.split("\\s+");
        if (parts.length > 1) {
            for (String p : parts) {
                if (p.length() > 2 && !kw.contains(p)) {
                    kw.add(p);
                }
            }
        }

        return kw;
    }
}
