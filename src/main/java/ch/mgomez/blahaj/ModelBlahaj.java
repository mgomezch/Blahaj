package ch.mgomez.blahaj;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

/**
 * Original Bl\u00e5haj 3D model geometry & concept by Hibi (hibiii/Blahaj), public domain.
 */
public class ModelBlahaj {

    public static final ModelBlahaj instance = new ModelBlahaj();
    private int displayList = -1;

    public void render(float scale) {
        if (displayList < 0) {
            compileDisplayList();
        }
        GL11.glPushMatrix();
        if (scale != 1.0F) {
            GL11.glScalef(scale, scale, scale);
        }
        GL11.glCallList(displayList);
        GL11.glPopMatrix();
    }

    private void compileDisplayList() {
        displayList = GL11.glGenLists(1);
        GL11.glNewList(displayList, GL11.GL_COMPILE);
        renderDirect();
        GL11.glEndList();
    }

    private void renderDirect() {
        Tessellator t = Tessellator.instance;
        // Element: shork
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.125f, 0.125f, -0.5f, 0.28125f, 0.28125f);
        t.addVertexWithUV(0.125f, -0.125f, -0.5f, 0.28125f, 0.40625f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.5f, 0.40625f, 0.40625f);
        t.addVertexWithUV(-0.125f, 0.125f, -0.5f, 0.40625f, 0.28125f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.125f, 0.125f, 0.0625f, 0.0f, 0.28125f);
        t.addVertexWithUV(0.125f, -0.125f, 0.0625f, 0.0f, 0.40625f);
        t.addVertexWithUV(0.125f, -0.125f, -0.5f, 0.28125f, 0.40625f);
        t.addVertexWithUV(0.125f, 0.125f, -0.5f, 0.28125f, 0.28125f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.125f, 0.125f, 0.0625f, 0.6875f, 0.28125f);
        t.addVertexWithUV(-0.125f, -0.125f, 0.0625f, 0.6875f, 0.40625f);
        t.addVertexWithUV(0.125f, -0.125f, 0.0625f, 0.8125f, 0.40625f);
        t.addVertexWithUV(0.125f, 0.125f, 0.0625f, 0.8125f, 0.28125f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.125f, 0.125f, -0.5f, 0.40625f, 0.28125f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.5f, 0.40625f, 0.40625f);
        t.addVertexWithUV(-0.125f, -0.125f, 0.0625f, 0.6875f, 0.40625f);
        t.addVertexWithUV(-0.125f, 0.125f, 0.0625f, 0.6875f, 0.28125f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.125f, 0.125f, -0.5f, 0.40625f, 0.28125f);
        t.addVertexWithUV(-0.125f, 0.125f, 0.0625f, 0.40625f, 0.0f);
        t.addVertexWithUV(0.125f, 0.125f, 0.0625f, 0.28125f, 0.0f);
        t.addVertexWithUV(0.125f, 0.125f, -0.5f, 0.28125f, 0.28125f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.125f, -0.125f, 0.0625f, 0.53125f, 0.0f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.5f, 0.53125f, 0.28125f);
        t.addVertexWithUV(0.125f, -0.125f, -0.5f, 0.40625f, 0.28125f);
        t.addVertexWithUV(0.125f, -0.125f, 0.0625f, 0.40625f, 0.0f);
        t.draw();

        // Element: tail
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.0625f, 0.21875f, 0.625f);
        t.addVertexWithUV(0.0625f, -0.0625f, 0.0625f, 0.21875f, 0.6875f);
        t.addVertexWithUV(-0.0625f, -0.0625f, 0.0625f, 0.28125f, 0.6875f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.0625f, 0.28125f, 0.625f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.5f, 0.0f, 0.625f);
        t.addVertexWithUV(0.0625f, -0.0625f, 0.5f, 0.0f, 0.6875f);
        t.addVertexWithUV(0.0625f, -0.0625f, 0.0625f, 0.21875f, 0.6875f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.0625f, 0.21875f, 0.625f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.5f, 0.5f, 0.625f);
        t.addVertexWithUV(-0.0625f, -0.0625f, 0.5f, 0.5f, 0.6875f);
        t.addVertexWithUV(0.0625f, -0.0625f, 0.5f, 0.5625f, 0.6875f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.5f, 0.5625f, 0.625f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.0625f, 0.28125f, 0.625f);
        t.addVertexWithUV(-0.0625f, -0.0625f, 0.0625f, 0.28125f, 0.6875f);
        t.addVertexWithUV(-0.0625f, -0.0625f, 0.5f, 0.5f, 0.6875f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.5f, 0.5f, 0.625f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.0625f, 0.28125f, 0.625f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.5f, 0.28125f, 0.40625f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.5f, 0.21875f, 0.40625f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.0625f, 0.21875f, 0.625f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.0625f, -0.0625f, 0.5f, 0.34375f, 0.40625f);
        t.addVertexWithUV(-0.0625f, -0.0625f, 0.0625f, 0.34375f, 0.625f);
        t.addVertexWithUV(0.0625f, -0.0625f, 0.0625f, 0.28125f, 0.625f);
        t.addVertexWithUV(0.0625f, -0.0625f, 0.5f, 0.28125f, 0.40625f);
        t.draw();

        // Element: tail base
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.09375f, 0.09375f, 0.0625f, 0.0625f, 0.75f);
        t.addVertexWithUV(0.09375f, -0.09375f, 0.0625f, 0.0625f, 0.84375f);
        t.addVertexWithUV(-0.09375f, -0.09375f, 0.0625f, 0.15625f, 0.84375f);
        t.addVertexWithUV(-0.09375f, 0.09375f, 0.0625f, 0.15625f, 0.75f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.09375f, 0.09375f, 0.1875f, 0.0f, 0.75f);
        t.addVertexWithUV(0.09375f, -0.09375f, 0.1875f, 0.0f, 0.84375f);
        t.addVertexWithUV(0.09375f, -0.09375f, 0.0625f, 0.0625f, 0.84375f);
        t.addVertexWithUV(0.09375f, 0.09375f, 0.0625f, 0.0625f, 0.75f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.09375f, 0.09375f, 0.1875f, 0.21875f, 0.75f);
        t.addVertexWithUV(-0.09375f, -0.09375f, 0.1875f, 0.21875f, 0.84375f);
        t.addVertexWithUV(0.09375f, -0.09375f, 0.1875f, 0.3125f, 0.84375f);
        t.addVertexWithUV(0.09375f, 0.09375f, 0.1875f, 0.3125f, 0.75f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.09375f, 0.09375f, 0.0625f, 0.15625f, 0.75f);
        t.addVertexWithUV(-0.09375f, -0.09375f, 0.0625f, 0.15625f, 0.84375f);
        t.addVertexWithUV(-0.09375f, -0.09375f, 0.1875f, 0.21875f, 0.84375f);
        t.addVertexWithUV(-0.09375f, 0.09375f, 0.1875f, 0.21875f, 0.75f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.09375f, 0.09375f, 0.0625f, 0.15625f, 0.75f);
        t.addVertexWithUV(-0.09375f, 0.09375f, 0.1875f, 0.15625f, 0.6875f);
        t.addVertexWithUV(0.09375f, 0.09375f, 0.1875f, 0.0625f, 0.6875f);
        t.addVertexWithUV(0.09375f, 0.09375f, 0.0625f, 0.0625f, 0.75f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.09375f, -0.09375f, 0.1875f, 0.25f, 0.6875f);
        t.addVertexWithUV(-0.09375f, -0.09375f, 0.0625f, 0.25f, 0.75f);
        t.addVertexWithUV(0.09375f, -0.09375f, 0.0625f, 0.15625f, 0.75f);
        t.addVertexWithUV(0.09375f, -0.09375f, 0.1875f, 0.15625f, 0.6875f);
        t.draw();

        // Element: tail stabilizer
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0625f, 0.4375f);
        GL11.glRotatef(22.5f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.0f, -0.0625f, -0.4375f);
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.03125f, 0.25f, 0.375f, 0.0625f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.0f, 0.375f, 0.0625f, 0.1875f);
        t.addVertexWithUV(-0.03125f, 0.0f, 0.375f, 0.09375f, 0.1875f);
        t.addVertexWithUV(-0.03125f, 0.25f, 0.375f, 0.09375f, 0.0625f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.03125f, 0.25f, 0.5f, 0.0f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.0f, 0.5f, 0.0f, 0.1875f);
        t.addVertexWithUV(0.03125f, 0.0f, 0.375f, 0.0625f, 0.1875f);
        t.addVertexWithUV(0.03125f, 0.25f, 0.375f, 0.0625f, 0.0625f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.03125f, 0.25f, 0.5f, 0.15625f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.0f, 0.5f, 0.15625f, 0.1875f);
        t.addVertexWithUV(0.03125f, 0.0f, 0.5f, 0.1875f, 0.1875f);
        t.addVertexWithUV(0.03125f, 0.25f, 0.5f, 0.1875f, 0.0625f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.03125f, 0.25f, 0.375f, 0.09375f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.0f, 0.375f, 0.09375f, 0.1875f);
        t.addVertexWithUV(-0.03125f, 0.0f, 0.5f, 0.15625f, 0.1875f);
        t.addVertexWithUV(-0.03125f, 0.25f, 0.5f, 0.15625f, 0.0625f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.03125f, 0.25f, 0.375f, 0.09375f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.25f, 0.5f, 0.09375f, 0.0f);
        t.addVertexWithUV(0.03125f, 0.25f, 0.5f, 0.0625f, 0.0f);
        t.addVertexWithUV(0.03125f, 0.25f, 0.375f, 0.0625f, 0.0625f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.03125f, 0.0f, 0.5f, 0.125f, 0.0f);
        t.addVertexWithUV(-0.03125f, 0.0f, 0.375f, 0.125f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.0f, 0.375f, 0.09375f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.0f, 0.5f, 0.09375f, 0.0f);
        t.draw();
        GL11.glPopMatrix();

        // Element: left pectoral
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.125f, -0.0625f, -0.0625f);
        GL11.glRotatef(22.5f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.125f, 0.0625f, 0.0625f);
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.125f, 0.46875f, 0.53125f);
        t.addVertexWithUV(-0.125f, -0.1875f, -0.125f, 0.46875f, 0.59375f);
        t.addVertexWithUV(-0.1875f, -0.1875f, -0.125f, 0.5f, 0.59375f);
        t.addVertexWithUV(-0.1875f, -0.0625f, -0.125f, 0.5f, 0.53125f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(-0.125f, -0.0625f, 0.125f, 0.34375f, 0.53125f);
        t.addVertexWithUV(-0.125f, -0.1875f, 0.125f, 0.34375f, 0.59375f);
        t.addVertexWithUV(-0.125f, -0.1875f, -0.125f, 0.46875f, 0.59375f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.125f, 0.46875f, 0.53125f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.1875f, -0.0625f, 0.125f, 0.625f, 0.53125f);
        t.addVertexWithUV(-0.1875f, -0.1875f, 0.125f, 0.625f, 0.59375f);
        t.addVertexWithUV(-0.125f, -0.1875f, 0.125f, 0.65625f, 0.59375f);
        t.addVertexWithUV(-0.125f, -0.0625f, 0.125f, 0.65625f, 0.53125f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.1875f, -0.0625f, -0.125f, 0.5f, 0.53125f);
        t.addVertexWithUV(-0.1875f, -0.1875f, -0.125f, 0.5f, 0.59375f);
        t.addVertexWithUV(-0.1875f, -0.1875f, 0.125f, 0.625f, 0.59375f);
        t.addVertexWithUV(-0.1875f, -0.0625f, 0.125f, 0.625f, 0.53125f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.1875f, -0.0625f, -0.125f, 0.5f, 0.53125f);
        t.addVertexWithUV(-0.1875f, -0.0625f, 0.125f, 0.5f, 0.40625f);
        t.addVertexWithUV(-0.125f, -0.0625f, 0.125f, 0.46875f, 0.40625f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.125f, 0.46875f, 0.53125f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.1875f, -0.1875f, 0.125f, 0.53125f, 0.40625f);
        t.addVertexWithUV(-0.1875f, -0.1875f, -0.125f, 0.53125f, 0.53125f);
        t.addVertexWithUV(-0.125f, -0.1875f, -0.125f, 0.5f, 0.53125f);
        t.addVertexWithUV(-0.125f, -0.1875f, 0.125f, 0.5f, 0.40625f);
        t.draw();
        GL11.glPopMatrix();

        // Element: right pectoral
        GL11.glPushMatrix();
        GL11.glTranslatef(0.125f, -0.0625f, -0.0625f);
        GL11.glRotatef(22.5f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.125f, 0.0625f, 0.0625f);
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.1875f, -0.0625f, -0.125f, 0.5f, 0.53125f);
        t.addVertexWithUV(0.1875f, -0.1875f, -0.125f, 0.5f, 0.59375f);
        t.addVertexWithUV(0.125f, -0.1875f, -0.125f, 0.46875f, 0.59375f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.125f, 0.46875f, 0.53125f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.1875f, -0.0625f, 0.125f, 0.625f, 0.53125f);
        t.addVertexWithUV(0.1875f, -0.1875f, 0.125f, 0.625f, 0.59375f);
        t.addVertexWithUV(0.1875f, -0.1875f, -0.125f, 0.5f, 0.59375f);
        t.addVertexWithUV(0.1875f, -0.0625f, -0.125f, 0.5f, 0.53125f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(0.125f, -0.0625f, 0.125f, 0.65625f, 0.53125f);
        t.addVertexWithUV(0.125f, -0.1875f, 0.125f, 0.65625f, 0.59375f);
        t.addVertexWithUV(0.1875f, -0.1875f, 0.125f, 0.625f, 0.59375f);
        t.addVertexWithUV(0.1875f, -0.0625f, 0.125f, 0.625f, 0.53125f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.125f, 0.46875f, 0.53125f);
        t.addVertexWithUV(0.125f, -0.1875f, -0.125f, 0.46875f, 0.59375f);
        t.addVertexWithUV(0.125f, -0.1875f, 0.125f, 0.34375f, 0.59375f);
        t.addVertexWithUV(0.125f, -0.0625f, 0.125f, 0.34375f, 0.53125f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.125f, 0.46875f, 0.53125f);
        t.addVertexWithUV(0.125f, -0.0625f, 0.125f, 0.46875f, 0.40625f);
        t.addVertexWithUV(0.1875f, -0.0625f, 0.125f, 0.5f, 0.40625f);
        t.addVertexWithUV(0.1875f, -0.0625f, -0.125f, 0.5f, 0.53125f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(0.125f, -0.1875f, 0.125f, 0.5f, 0.40625f);
        t.addVertexWithUV(0.125f, -0.1875f, -0.125f, 0.5f, 0.53125f);
        t.addVertexWithUV(0.1875f, -0.1875f, -0.125f, 0.53125f, 0.53125f);
        t.addVertexWithUV(0.1875f, -0.1875f, 0.125f, 0.53125f, 0.40625f);
        t.draw();
        GL11.glPopMatrix();

        // Element: dorsal fin
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.125f, -0.0625f);
        GL11.glRotatef(22.5f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.0f, -0.125f, 0.0625f);
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.0625f, 0.375f, -0.1875f, 0.625f, 0.09375f);
        t.addVertexWithUV(0.0625f, 0.0625f, -0.1875f, 0.625f, 0.25f);
        t.addVertexWithUV(-0.0625f, 0.0625f, -0.1875f, 0.6875f, 0.25f);
        t.addVertexWithUV(-0.0625f, 0.375f, -0.1875f, 0.6875f, 0.09375f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.0625f, 0.375f, 0.0f, 0.53125f, 0.09375f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.0f, 0.53125f, 0.25f);
        t.addVertexWithUV(0.0625f, 0.0625f, -0.1875f, 0.625f, 0.25f);
        t.addVertexWithUV(0.0625f, 0.375f, -0.1875f, 0.625f, 0.09375f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.0625f, 0.375f, 0.0f, 0.78125f, 0.09375f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.0f, 0.78125f, 0.25f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.0f, 0.84375f, 0.25f);
        t.addVertexWithUV(0.0625f, 0.375f, 0.0f, 0.84375f, 0.09375f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.0625f, 0.375f, -0.1875f, 0.6875f, 0.09375f);
        t.addVertexWithUV(-0.0625f, 0.0625f, -0.1875f, 0.6875f, 0.25f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.0f, 0.78125f, 0.25f);
        t.addVertexWithUV(-0.0625f, 0.375f, 0.0f, 0.78125f, 0.09375f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.0625f, 0.375f, -0.1875f, 0.6875f, 0.09375f);
        t.addVertexWithUV(-0.0625f, 0.375f, 0.0f, 0.6875f, 0.0f);
        t.addVertexWithUV(0.0625f, 0.375f, 0.0f, 0.625f, 0.0f);
        t.addVertexWithUV(0.0625f, 0.375f, -0.1875f, 0.625f, 0.09375f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.0625f, 0.0625f, 0.0f, 0.75f, 0.0f);
        t.addVertexWithUV(-0.0625f, 0.0625f, -0.1875f, 0.75f, 0.09375f);
        t.addVertexWithUV(0.0625f, 0.0625f, -0.1875f, 0.6875f, 0.09375f);
        t.addVertexWithUV(0.0625f, 0.0625f, 0.0f, 0.6875f, 0.0f);
        t.draw();
        GL11.glPopMatrix();

        // Element: back dorsal
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0625f, 0.28125f);
        GL11.glRotatef(-22.5f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.0f, -0.0625f, -0.28125f);
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.03125f, 0.09375f, 0.25f, 0.15625f, 0.03125f);
        t.addVertexWithUV(0.03125f, 0.03125f, 0.25f, 0.15625f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.03125f, 0.25f, 0.1875f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.09375f, 0.25f, 0.1875f, 0.03125f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.03125f, 0.09375f, 0.3125f, 0.125f, 0.03125f);
        t.addVertexWithUV(0.03125f, 0.03125f, 0.3125f, 0.125f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.03125f, 0.25f, 0.15625f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.09375f, 0.25f, 0.15625f, 0.03125f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.03125f, 0.09375f, 0.3125f, 0.21875f, 0.03125f);
        t.addVertexWithUV(-0.03125f, 0.03125f, 0.3125f, 0.21875f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.03125f, 0.3125f, 0.25f, 0.0625f);
        t.addVertexWithUV(0.03125f, 0.09375f, 0.3125f, 0.25f, 0.03125f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.03125f, 0.09375f, 0.25f, 0.1875f, 0.03125f);
        t.addVertexWithUV(-0.03125f, 0.03125f, 0.25f, 0.1875f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.03125f, 0.3125f, 0.21875f, 0.0625f);
        t.addVertexWithUV(-0.03125f, 0.09375f, 0.3125f, 0.21875f, 0.03125f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.03125f, 0.09375f, 0.25f, 0.1875f, 0.03125f);
        t.addVertexWithUV(-0.03125f, 0.09375f, 0.3125f, 0.1875f, 0.0f);
        t.addVertexWithUV(0.03125f, 0.09375f, 0.3125f, 0.15625f, 0.0f);
        t.addVertexWithUV(0.03125f, 0.09375f, 0.25f, 0.15625f, 0.03125f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.03125f, 0.03125f, 0.3125f, 0.21875f, 0.0f);
        t.addVertexWithUV(-0.03125f, 0.03125f, 0.25f, 0.21875f, 0.03125f);
        t.addVertexWithUV(0.03125f, 0.03125f, 0.25f, 0.1875f, 0.03125f);
        t.addVertexWithUV(0.03125f, 0.03125f, 0.3125f, 0.1875f, 0.0f);
        t.draw();
        GL11.glPopMatrix();

        // Element: mouf back
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.125f, -0.4375f, 0.0f, 0.25f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.4375f, 0.125f, 0.25f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.125f, 0.21875f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.125f, -0.4375f, 0.0f, 0.25f);
        t.addVertexWithUV(0.125f, -0.125f, -0.4375f, 0.0f, 0.25f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.125f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.4375f, 0.125f, 0.25f);
        t.addVertexWithUV(0.125f, -0.125f, -0.4375f, 0.25f, 0.25f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.25f, 0.21875f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.125f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.4375f, 0.125f, 0.25f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.4375f, 0.125f, 0.25f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.125f, 0.21875f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.125f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.125f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.4375f, 0.25f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.125f, -0.4375f, 0.25f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.125f, -0.4375f, 0.125f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.125f, -0.4375f, 0.125f, 0.21875f);
        t.draw();

        // Element: mouf top
        t.startDrawingQuads();
        t.setNormal(0f, 0f, -1f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.5f, 0.03125f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.5f, 0.03125f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.5f, 0.15625f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.5f, 0.15625f, 0.21875f);
        t.setNormal(1f, 0f, 0f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.0f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.5f, 0.03125f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.5f, 0.03125f, 0.21875f);
        t.setNormal(0f, 0f, 1f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.1875f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.1875f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.3125f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.3125f, 0.21875f);
        t.setNormal(-1f, 0f, 0f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.5f, 0.15625f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.5f, 0.15625f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.1875f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.1875f, 0.21875f);
        t.setNormal(0f, 1f, 0f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.5f, 0.15625f, 0.21875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.15625f, 0.1875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.03125f, 0.1875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.5f, 0.03125f, 0.21875f);
        t.setNormal(0f, -1f, 0f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.4375f, 0.28125f, 0.1875f);
        t.addVertexWithUV(-0.125f, -0.0625f, -0.5f, 0.28125f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.5f, 0.15625f, 0.21875f);
        t.addVertexWithUV(0.125f, -0.0625f, -0.4375f, 0.15625f, 0.1875f);
        t.draw();

    }
}
