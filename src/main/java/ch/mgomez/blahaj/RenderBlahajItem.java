package ch.mgomez.blahaj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderBlahajItem implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
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

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        BlahajVariant variant = BlahajVariant.matchVariant(item);
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
}
