package ch.mgomez.blahaj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderBlahajItem implements IItemRenderer {

    private final IItemRenderer parentRenderer;

    public RenderBlahajItem() {
        this(null);
    }

    public RenderBlahajItem(IItemRenderer parentRenderer) {
        this.parentRenderer = parentRenderer;
    }

    public IItemRenderer getParentRenderer() {
        return parentRenderer;
    }

    public static boolean hasCustomName(ItemStack stack) {
        if (stack == null || !stack.hasTagCompound()) {
            return false;
        }
        NBTTagCompound nbt = stack.getTagCompound();
        if (!nbt.hasKey("display", 10)) {
            return false;
        }
        NBTTagCompound display = nbt.getCompoundTag("display");
        if (!display.hasKey("Name", 8)) {
            return false;
        }
        String name = display.getString("Name");
        return name != null && !name.trim()
            .isEmpty();
    }

    public boolean shouldRenderAsBlahaj(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (ClientProxy.itemBlahaj != null && item.getItem() == ClientProxy.itemBlahaj) {
            return true;
        }
        if (!hasCustomName(item)) {
            return false;
        }
        return BlahajVariant.findVariant(item) != null;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (shouldRenderAsBlahaj(item)) {
            return true;
        }
        if (parentRenderer != null) {
            return parentRenderer.handleRenderType(item, type);
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (shouldRenderAsBlahaj(item)) {
            switch (helper) {
                case ENTITY_ROTATION:
                case ENTITY_BOBBING:
                    return true;
                case EQUIPPED_BLOCK:
                case BLOCK_3D:
                case INVENTORY_BLOCK:
                default:
                    return false;
            }
        }
        if (parentRenderer != null) {
            return parentRenderer.shouldUseRenderHelper(type, item, helper);
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (shouldRenderAsBlahaj(item)) {
            renderBlahaj(type, item, data);
            return;
        }
        if (parentRenderer != null) {
            parentRenderer.renderItem(type, item, data);
        }
    }

    private void renderBlahaj(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        BlahajVariant variant = BlahajVariant.matchVariant(item);
        if (variant.isFlatImage()) {
            renderFlatItem(type, item, variant);
            GL11.glPopMatrix();
            return;
        }

        ResourceLocation texture = variant.getTexture();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        boolean large = BlahajVariant.isLarge(item);
        boolean small = BlahajVariant.isSmall(item);
        float sizeMultiplier = 1.0F;
        if (large) {
            sizeMultiplier = (float) BlahajConfig.chonkerScaleMultiplier;
        } else if (small) {
            sizeMultiplier = (float) BlahajConfig.smallScaleMultiplier;
        }

        switch (type) {
            case ENTITY:
                GL11.glTranslatef(0.0F, 0.25F, 0.0F);
                GL11.glScalef(1.2F * sizeMultiplier, 1.2F * sizeMultiplier, 1.2F * sizeMultiplier);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;

            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(
                    (float) BlahajConfig.firstPersonPosX,
                    (float) BlahajConfig.firstPersonPosY,
                    (float) BlahajConfig.firstPersonPosZ);
                if (BlahajConfig.firstPersonRotY != 0.0) {
                    GL11.glRotatef((float) BlahajConfig.firstPersonRotY, 0.0F, 1.0F, 0.0F);
                }
                if (BlahajConfig.firstPersonRotX != 0.0) {
                    GL11.glRotatef((float) BlahajConfig.firstPersonRotX, 1.0F, 0.0F, 0.0F);
                }
                if (BlahajConfig.firstPersonRotZ != 0.0) {
                    GL11.glRotatef((float) BlahajConfig.firstPersonRotZ, 0.0F, 0.0F, 1.0F);
                }
                GL11.glScalef(1.4F * sizeMultiplier, 1.4F * sizeMultiplier, 1.4F * sizeMultiplier);
                break;

            case EQUIPPED:
                GL11.glTranslatef(
                    (float) BlahajConfig.thirdPersonPosX,
                    (float) BlahajConfig.thirdPersonPosY,
                    (float) BlahajConfig.thirdPersonPosZ);
                if (BlahajConfig.thirdPersonRotY != 0.0) {
                    GL11.glRotatef((float) BlahajConfig.thirdPersonRotY, 0.0F, 1.0F, 0.0F);
                }
                if (BlahajConfig.thirdPersonRotX != 0.0) {
                    GL11.glRotatef((float) BlahajConfig.thirdPersonRotX, 1.0F, 0.0F, 0.0F);
                }
                if (BlahajConfig.thirdPersonRotZ != 0.0) {
                    GL11.glRotatef((float) BlahajConfig.thirdPersonRotZ, 0.0F, 0.0F, 1.0F);
                }
                GL11.glScalef(1.2F * sizeMultiplier, 1.2F * sizeMultiplier, 1.2F * sizeMultiplier);
                break;

            case INVENTORY:
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glTranslatef(8.0F, 8.0F, 0.0F);
                float invScale = 15.0F * sizeMultiplier;
                GL11.glScalef(invScale, -invScale, invScale);
                GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(225.0F, 0.0F, 1.0F, 0.0F);
                break;

            default:
                break;
        }

        if (BlahajConfig.enableSpin && type != ItemRenderType.INVENTORY) {
            double timeSec = (System.currentTimeMillis() % 86400000L) / 1000.0;
            float spinPitch = (float) ((timeSec * BlahajConfig.spinSpeedPitch) % 360.0);
            float spinYaw = (float) ((timeSec * BlahajConfig.spinSpeedYaw) % 360.0);
            float spinRoll = (float) ((timeSec * BlahajConfig.spinSpeedRoll) % 360.0);

            if (spinYaw != 0.0F) {
                GL11.glRotatef(spinYaw, 0.0F, 1.0F, 0.0F);
            }
            if (spinPitch != 0.0F) {
                GL11.glRotatef(spinPitch, 1.0F, 0.0F, 0.0F);
            }
            if (spinRoll != 0.0F) {
                GL11.glRotatef(spinRoll, 0.0F, 0.0F, 1.0F);
            }
        }

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        ModelBlahaj.instance.render(1.0F);

        GL11.glDisable(GL11.GL_BLEND);
        if (type == ItemRenderType.INVENTORY) {
            RenderHelper.enableStandardItemLighting();
        }

        GL11.glPopMatrix();
    }

    private void renderFlatItem(ItemRenderType type, ItemStack item, BlahajVariant variant) {
        boolean large = BlahajVariant.isLarge(item);
        boolean small = BlahajVariant.isSmall(item);
        float sizeMultiplier = 1.0F;
        if (large) {
            sizeMultiplier = (float) BlahajConfig.chonkerScaleMultiplier;
        } else if (small) {
            sizeMultiplier = (float) BlahajConfig.smallScaleMultiplier;
        }

        int imgW = variant.getImageWidth();
        int imgH = variant.getImageHeight();
        if (imgW <= 0) imgW = 16;
        if (imgH <= 0) imgH = 16;
        float aspect = (float) imgW / (float) imgH;

        if (type == ItemRenderType.INVENTORY) {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            Minecraft.getMinecraft().renderEngine.bindTexture(variant.getTexture());

            float drawW = 16.0F;
            float drawH = 16.0F;
            if (aspect > 1.0F) {
                drawH = 16.0F / aspect;
            } else if (aspect < 1.0F) {
                drawW = 16.0F * aspect;
            }

            float startX = (16.0F - drawW) / 2.0F;
            float startY = (16.0F - drawH) / 2.0F;

            if (sizeMultiplier != 1.0F) {
                GL11.glTranslatef(8.0F, 8.0F, 0.0F);
                GL11.glScalef(sizeMultiplier, sizeMultiplier, 1.0F);
                GL11.glTranslatef(-8.0F, -8.0F, 0.0F);
            }

            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(startX, startY + drawH, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(startX + drawW, startY + drawH, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(startX + drawW, startY, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(startX, startY, 0.0D, 0.0D, 0.0D);
            tessellator.draw();

            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            return;
        }

        // Held in hand (first person, third person) or dropped in world (entity)
        if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-0.5F, 0.0F, -0.03125F);
        }

        if (sizeMultiplier != 1.0F) {
            GL11.glTranslatef(0.5F, 0.5F, 0.03125F);
            GL11.glScalef(sizeMultiplier, sizeMultiplier, sizeMultiplier);
            GL11.glTranslatef(-0.5F, -0.5F, -0.03125F);
        }

        if (aspect > 1.0F) {
            float scaleY = 1.0F / aspect;
            GL11.glTranslatef(0.0F, 0.5F * (1.0F - scaleY), 0.0F);
            GL11.glScalef(1.0F, scaleY, 1.0F);
        } else if (aspect < 1.0F) {
            float scaleX = aspect;
            GL11.glTranslatef(0.5F * (1.0F - scaleX), 0.0F, 0.0F);
            GL11.glScalef(scaleX, 1.0F, 1.0F);
        }

        if (BlahajConfig.enableSpin) {
            double timeSec = (System.currentTimeMillis() % 86400000L) / 1000.0;
            float spinPitch = (float) ((timeSec * BlahajConfig.spinSpeedPitch) % 360.0);
            float spinYaw = (float) ((timeSec * BlahajConfig.spinSpeedYaw) % 360.0);
            float spinRoll = (float) ((timeSec * BlahajConfig.spinSpeedRoll) % 360.0);

            GL11.glTranslatef(0.5F, 0.5F, 0.03125F);
            if (spinYaw != 0.0F) {
                GL11.glRotatef(spinYaw, 0.0F, 1.0F, 0.0F);
            }
            if (spinPitch != 0.0F) {
                GL11.glRotatef(spinPitch, 1.0F, 0.0F, 0.0F);
            }
            if (spinRoll != 0.0F) {
                GL11.glRotatef(spinRoll, 0.0F, 0.0F, 1.0F);
            }
            GL11.glTranslatef(-0.5F, -0.5F, -0.03125F);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(variant.getTexture());
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int widthSteps = Math.min(Math.max(imgW, 16), 128);
        int heightSteps = Math.min(Math.max(imgH, 16), 128);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 1.0F, 0.0F, 0.0F, 1.0F, widthSteps, heightSteps, 0.0625F);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
