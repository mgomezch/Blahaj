package ch.mgomez.blahaj.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import ch.mgomez.blahaj.BlahajConfig;

/**
 * Floating, non-pausing live configuration dialog for adjusting Bl\u00e5haj parameters in real time.
 */
public class GuiBlahajLiveAdjuster extends GuiScreen {

    public enum Tab {

        FIRST_PERSON("gui.blahaj.tab.first_person", "1st"),
        THIRD_PERSON("gui.blahaj.tab.third_person", "3rd"),
        ANIMATION("gui.blahaj.tab.animation", "Spin"),
        SIZES("gui.blahaj.tab.sizes", "Sizes");

        public final String langKey;
        public final String fallback;

        Tab(String langKey, String fallback) {
            this.langKey = langKey;
            this.fallback = fallback;
        }

        public String getLabel() {
            return StatCollector.canTranslate(langKey) ? StatCollector.translateToLocal(langKey) : fallback;
        }
    }

    private static int savedGuiLeft = -1;
    private static int savedGuiTop = -1;
    private static Tab savedTab = Tab.FIRST_PERSON;

    private int guiLeft = 20;
    private int guiTop = 20;
    private final int guiWidth = 194;
    private final int guiHeight = 205;

    private boolean isDragging = false;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    private Tab currentTab = Tab.FIRST_PERSON;
    private String statusMessage = "";
    private long statusMessageTime = 0;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();
        if (savedGuiLeft >= 0 && savedGuiTop >= 0) {
            guiLeft = Math.max(0, Math.min(width - guiWidth, savedGuiLeft));
            guiTop = Math.max(0, Math.min(height - guiHeight, savedGuiTop));
        } else {
            guiLeft = 20;
            guiTop = Math.max(10, (height - guiHeight) / 2);
        }
        currentTab = savedTab;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        savedGuiLeft = guiLeft;
        savedGuiTop = guiTop;
        savedTab = currentTab;
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int dWheel = Mouse.getEventDWheel();
        if (dWheel != 0) {
            int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            int dir = dWheel > 0 ? 1 : -1;
            onMouseWheel(mouseX, mouseY, dir);
        }
    }

    private void onMouseWheel(int mouseX, int mouseY, int direction) {
        if (mouseX < guiLeft || mouseX > guiLeft + guiWidth
            || mouseY < guiTop + 40
            || mouseY > guiTop + guiHeight - 38) {
            return;
        }

        int rowIdx = (mouseY - (guiTop + 42)) / 17;
        boolean fine = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
        boolean coarse = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

        adjustParameter(currentTab, rowIdx, direction, fine, coarse);
    }

    private void adjustParameter(Tab tab, int rowIdx, int dir, boolean fine, boolean coarse) {
        switch (tab) {
            case FIRST_PERSON:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.firstPersonPosX += dir * (fine ? 0.005 : (coarse ? 0.10 : 0.02));
                        break;
                    case 1:
                        BlahajConfig.firstPersonPosY += dir * (fine ? 0.005 : (coarse ? 0.10 : 0.02));
                        break;
                    case 2:
                        BlahajConfig.firstPersonPosZ += dir * (fine ? 0.005 : (coarse ? 0.10 : 0.02));
                        break;
                    case 3:
                        BlahajConfig.firstPersonRotX += dir * (fine ? 0.5 : (coarse ? 15.0 : 2.0));
                        break;
                    case 4:
                        BlahajConfig.firstPersonRotY += dir * (fine ? 0.5 : (coarse ? 15.0 : 2.0));
                        break;
                    case 5:
                        BlahajConfig.firstPersonRotZ += dir * (fine ? 0.5 : (coarse ? 15.0 : 2.0));
                        break;
                }
                break;

            case THIRD_PERSON:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.thirdPersonPosX += dir * (fine ? 0.005 : (coarse ? 0.10 : 0.02));
                        break;
                    case 1:
                        BlahajConfig.thirdPersonPosY += dir * (fine ? 0.005 : (coarse ? 0.10 : 0.02));
                        break;
                    case 2:
                        BlahajConfig.thirdPersonPosZ += dir * (fine ? 0.005 : (coarse ? 0.10 : 0.02));
                        break;
                    case 3:
                        BlahajConfig.thirdPersonRotX += dir * (fine ? 0.5 : (coarse ? 15.0 : 2.0));
                        break;
                    case 4:
                        BlahajConfig.thirdPersonRotY += dir * (fine ? 0.5 : (coarse ? 15.0 : 2.0));
                        break;
                    case 5:
                        BlahajConfig.thirdPersonRotZ += dir * (fine ? 0.5 : (coarse ? 15.0 : 2.0));
                        break;
                }
                break;

            case ANIMATION:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.enableSpin = !BlahajConfig.enableSpin;
                        break;
                    case 1:
                        BlahajConfig.spinSpeedPitch += dir * (fine ? 1.0 : (coarse ? 25.0 : 5.0));
                        break;
                    case 2:
                        BlahajConfig.spinSpeedYaw += dir * (fine ? 1.0 : (coarse ? 25.0 : 5.0));
                        break;
                    case 3:
                        BlahajConfig.spinSpeedRoll += dir * (fine ? 1.0 : (coarse ? 25.0 : 5.0));
                        break;
                }
                break;

            case SIZES:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.smallScaleMultiplier = Math
                            .max(0.1, BlahajConfig.smallScaleMultiplier + dir * (fine ? 0.01 : (coarse ? 0.20 : 0.05)));
                        break;
                    case 1:
                        BlahajConfig.chonkerScaleMultiplier = Math.max(
                            0.1,
                            BlahajConfig.chonkerScaleMultiplier + dir * (fine ? 0.01 : (coarse ? 0.20 : 0.05)));
                        break;
                }
                break;
        }
    }

    private void resetRow(Tab tab, int rowIdx) {
        switch (tab) {
            case FIRST_PERSON:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.firstPersonPosX = BlahajConfig.DEFAULT_FP_POS_X;
                        break;
                    case 1:
                        BlahajConfig.firstPersonPosY = BlahajConfig.DEFAULT_FP_POS_Y;
                        break;
                    case 2:
                        BlahajConfig.firstPersonPosZ = BlahajConfig.DEFAULT_FP_POS_Z;
                        break;
                    case 3:
                        BlahajConfig.firstPersonRotX = BlahajConfig.DEFAULT_FP_ROT_X;
                        break;
                    case 4:
                        BlahajConfig.firstPersonRotY = BlahajConfig.DEFAULT_FP_ROT_Y;
                        break;
                    case 5:
                        BlahajConfig.firstPersonRotZ = BlahajConfig.DEFAULT_FP_ROT_Z;
                        break;
                }
                break;

            case THIRD_PERSON:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.thirdPersonPosX = BlahajConfig.DEFAULT_TP_POS_X;
                        break;
                    case 1:
                        BlahajConfig.thirdPersonPosY = BlahajConfig.DEFAULT_TP_POS_Y;
                        break;
                    case 2:
                        BlahajConfig.thirdPersonPosZ = BlahajConfig.DEFAULT_TP_POS_Z;
                        break;
                    case 3:
                        BlahajConfig.thirdPersonRotX = BlahajConfig.DEFAULT_TP_ROT_X;
                        break;
                    case 4:
                        BlahajConfig.thirdPersonRotY = BlahajConfig.DEFAULT_TP_ROT_Y;
                        break;
                    case 5:
                        BlahajConfig.thirdPersonRotZ = BlahajConfig.DEFAULT_TP_ROT_Z;
                        break;
                }
                break;

            case ANIMATION:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.enableSpin = BlahajConfig.DEFAULT_ENABLE_SPIN;
                        break;
                    case 1:
                        BlahajConfig.spinSpeedPitch = BlahajConfig.DEFAULT_SPIN_PITCH;
                        break;
                    case 2:
                        BlahajConfig.spinSpeedYaw = BlahajConfig.DEFAULT_SPIN_YAW;
                        break;
                    case 3:
                        BlahajConfig.spinSpeedRoll = BlahajConfig.DEFAULT_SPIN_ROLL;
                        break;
                }
                break;

            case SIZES:
                switch (rowIdx) {
                    case 0:
                        BlahajConfig.smallScaleMultiplier = BlahajConfig.DEFAULT_SMALL_SCALE;
                        break;
                    case 1:
                        BlahajConfig.chonkerScaleMultiplier = BlahajConfig.DEFAULT_CHONKER_SCALE;
                        break;
                }
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Dragging window logic
        if (isDragging) {
            if (Mouse.isButtonDown(0)) {
                guiLeft = Math.max(0, Math.min(width - guiWidth, mouseX - dragOffsetX));
                guiTop = Math.max(0, Math.min(height - guiHeight, mouseY - dragOffsetY));
            } else {
                isDragging = false;
            }
        }

        // Draw dialog body background in Blåhaj blue (#6996AD)
        drawRect(guiLeft, guiTop, guiLeft + guiWidth, guiTop + guiHeight, 0xF06996AD);
        // Outer border in darker oceanic slate
        drawRect(guiLeft - 1, guiTop - 1, guiLeft + guiWidth + 1, guiTop, 0xFF3D5F70);
        drawRect(guiLeft - 1, guiTop + guiHeight, guiLeft + guiWidth + 1, guiTop + guiHeight + 1, 0xFF3D5F70);
        drawRect(guiLeft - 1, guiTop, guiLeft, guiTop + guiHeight, 0xFF3D5F70);
        drawRect(guiLeft + guiWidth, guiTop, guiLeft + guiWidth + 1, guiTop + guiHeight, 0xFF3D5F70);

        // Title bar
        drawGradientRect(guiLeft, guiTop, guiLeft + guiWidth, guiTop + 18, 0xFF4D7589, 0xFF3D5F70);
        String title = StatCollector.canTranslate("gui.blahaj.live_adjuster.title")
            ? StatCollector.translateToLocal("gui.blahaj.live_adjuster.title")
            : "Bl\u00e5haj Live Adjuster";
        drawString(fontRendererObj, "\u00a7f\u00a7l" + title, guiLeft + 6, guiTop + 5, 0xFFFFFF);

        // Close button
        boolean closeHover = mouseX >= guiLeft + guiWidth - 16 && mouseX <= guiLeft + guiWidth - 3
            && mouseY >= guiTop + 3
            && mouseY <= guiTop + 15;
        drawRect(
            guiLeft + guiWidth - 16,
            guiTop + 3,
            guiLeft + guiWidth - 3,
            guiTop + 15,
            closeHover ? 0xFFC95E70 : 0x44000000);
        drawCenteredString(fontRendererObj, "\u00d7", guiLeft + guiWidth - 10, guiTop + 3, 0xFFFFFF);

        // Tab bar
        Tab[] tabs = Tab.values();
        int tabW = (guiWidth - 8) / tabs.length;
        for (int i = 0; i < tabs.length; i++) {
            Tab t = tabs[i];
            int tx1 = guiLeft + 4 + i * tabW;
            int tx2 = tx1 + tabW - 2;
            int ty1 = guiTop + 21;
            int ty2 = ty1 + 14;
            boolean active = (t == currentTab);
            boolean tabHover = mouseX >= tx1 && mouseX <= tx2 && mouseY >= ty1 && mouseY <= ty2;

            int tabColor = active ? 0xFF3D5F70 : (tabHover ? 0xFF5A8398 : 0xFF4C7387);
            drawRect(tx1, ty1, tx2, ty2, tabColor);
            if (active) {
                // Subtle white indicator line under active tab
                drawRect(tx1, ty2 - 1, tx2, ty2, 0xFFFFFFFF);
            }
            drawCenteredString(
                fontRendererObj,
                (active ? "\u00a7f\u00a7l" : "\u00a7f") + t.getLabel(),
                (tx1 + tx2) / 2,
                ty1 + 3,
                0xFFFFFF);
        }

        // Draw parameters for active tab
        drawTabContent(mouseX, mouseY);

        // Bottom action buttons
        int btnY = guiTop + guiHeight - 34;

        // Save button
        boolean saveHover = mouseX >= guiLeft + 6 && mouseX <= guiLeft + 54 && mouseY >= btnY && mouseY <= btnY + 15;
        drawRect(guiLeft + 6, btnY, guiLeft + 54, btnY + 15, saveHover ? 0xFF358E62 : 0xFF28724E);
        String saveText = StatCollector.canTranslate("gui.blahaj.button.save")
            ? StatCollector.translateToLocal("gui.blahaj.button.save")
            : "Save";
        drawCenteredString(fontRendererObj, "\u00a7f" + saveText, guiLeft + 30, btnY + 4, 0xFFFFFF);

        // Reset tab button
        boolean resetTabHover = mouseX >= guiLeft + 58 && mouseX <= guiLeft + 124
            && mouseY >= btnY
            && mouseY <= btnY + 15;
        drawRect(guiLeft + 58, btnY, guiLeft + 124, btnY + 15, resetTabHover ? 0xFFA14D5C : 0xFF7E3B47);
        String resetTabText = StatCollector.canTranslate("gui.blahaj.button.reset_tab")
            ? StatCollector.translateToLocal("gui.blahaj.button.reset_tab")
            : "Reset tab";
        drawCenteredString(fontRendererObj, "\u00a7f" + resetTabText, guiLeft + 91, btnY + 4, 0xFFFFFF);

        // Camera toggle button
        boolean camHover = mouseX >= guiLeft + 128 && mouseX <= guiLeft + 188 && mouseY >= btnY && mouseY <= btnY + 15;
        drawRect(guiLeft + 128, btnY, guiLeft + 188, btnY + 15, camHover ? 0xFF588196 : 0xFF456B7E);
        String camKey = (mc.gameSettings.thirdPersonView == 0) ? "gui.blahaj.button.cam_1st"
            : "gui.blahaj.button.cam_3rd";
        String camDefault = (mc.gameSettings.thirdPersonView == 0) ? "Cam: 1st" : "Cam: 3rd";
        String camText = StatCollector.canTranslate(camKey) ? StatCollector.translateToLocal(camKey) : camDefault;
        drawCenteredString(fontRendererObj, "\u00a7f" + camText, guiLeft + 158, btnY + 4, 0xFFFFFF);

        // Status message or footer hint
        if (System.currentTimeMillis() - statusMessageTime < 2500) {
            drawCenteredString(
                fontRendererObj,
                "\u00a7f\u00a7l" + statusMessage,
                guiLeft + guiWidth / 2,
                guiTop + guiHeight - 14,
                0xFFFFFF);
        } else {
            String hint = StatCollector.canTranslate("gui.blahaj.hint.scroll")
                ? StatCollector.translateToLocal("gui.blahaj.hint.scroll")
                : "Scroll: \u00b1 | Shift: fine | Ctrl: fast";
            drawCenteredString(
                fontRendererObj,
                "\u00a7f" + hint,
                guiLeft + guiWidth / 2,
                guiTop + guiHeight - 14,
                0xFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawTabContent(int mouseX, int mouseY) {
        switch (currentTab) {
            case FIRST_PERSON:
                drawRow(
                    0,
                    "gui.blahaj.param.x_pos",
                    "X Pos",
                    String.format("%+.2f", BlahajConfig.firstPersonPosX),
                    mouseX,
                    mouseY);
                drawRow(
                    1,
                    "gui.blahaj.param.y_pos",
                    "Y Pos",
                    String.format("%+.2f", BlahajConfig.firstPersonPosY),
                    mouseX,
                    mouseY);
                drawRow(
                    2,
                    "gui.blahaj.param.z_pos",
                    "Z Pos",
                    String.format("%+.2f", BlahajConfig.firstPersonPosZ),
                    mouseX,
                    mouseY);
                drawRow(
                    3,
                    "gui.blahaj.param.x_rot",
                    "X Rot",
                    String.format("%+.1f\u00b0", BlahajConfig.firstPersonRotX),
                    mouseX,
                    mouseY);
                drawRow(
                    4,
                    "gui.blahaj.param.y_rot",
                    "Y Rot",
                    String.format("%+.1f\u00b0", BlahajConfig.firstPersonRotY),
                    mouseX,
                    mouseY);
                drawRow(
                    5,
                    "gui.blahaj.param.z_rot",
                    "Z Rot",
                    String.format("%+.1f\u00b0", BlahajConfig.firstPersonRotZ),
                    mouseX,
                    mouseY);
                break;

            case THIRD_PERSON:
                drawRow(
                    0,
                    "gui.blahaj.param.x_pos",
                    "X Pos",
                    String.format("%+.2f", BlahajConfig.thirdPersonPosX),
                    mouseX,
                    mouseY);
                drawRow(
                    1,
                    "gui.blahaj.param.y_pos",
                    "Y Pos",
                    String.format("%+.2f", BlahajConfig.thirdPersonPosY),
                    mouseX,
                    mouseY);
                drawRow(
                    2,
                    "gui.blahaj.param.z_pos",
                    "Z Pos",
                    String.format("%+.2f", BlahajConfig.thirdPersonPosZ),
                    mouseX,
                    mouseY);
                drawRow(
                    3,
                    "gui.blahaj.param.x_rot",
                    "X Rot",
                    String.format("%+.1f\u00b0", BlahajConfig.thirdPersonRotX),
                    mouseX,
                    mouseY);
                drawRow(
                    4,
                    "gui.blahaj.param.y_rot",
                    "Y Rot",
                    String.format("%+.1f\u00b0", BlahajConfig.thirdPersonRotY),
                    mouseX,
                    mouseY);
                drawRow(
                    5,
                    "gui.blahaj.param.z_rot",
                    "Z Rot",
                    String.format("%+.1f\u00b0", BlahajConfig.thirdPersonRotZ),
                    mouseX,
                    mouseY);
                break;

            case ANIMATION:
                drawSpinToggleRow(0, "gui.blahaj.param.spin", "Spin", BlahajConfig.enableSpin, mouseX, mouseY);
                drawRow(
                    1,
                    "gui.blahaj.param.pitch",
                    "Pitch",
                    String.format("%.0f\u00b0/s", BlahajConfig.spinSpeedPitch),
                    mouseX,
                    mouseY);
                drawRow(
                    2,
                    "gui.blahaj.param.yaw",
                    "Yaw",
                    String.format("%.0f\u00b0/s", BlahajConfig.spinSpeedYaw),
                    mouseX,
                    mouseY);
                drawRow(
                    3,
                    "gui.blahaj.param.roll",
                    "Roll",
                    String.format("%.0f\u00b0/s", BlahajConfig.spinSpeedRoll),
                    mouseX,
                    mouseY);
                break;

            case SIZES:
                drawRow(
                    0,
                    "gui.blahaj.param.smol",
                    "Sm\u00f6l",
                    String.format("%.2fx", BlahajConfig.smallScaleMultiplier),
                    mouseX,
                    mouseY);
                drawRow(
                    1,
                    "gui.blahaj.param.chonker",
                    "Chonker",
                    String.format("%.2fx", BlahajConfig.chonkerScaleMultiplier),
                    mouseX,
                    mouseY);
                break;
        }
    }

    private void drawRow(int rowIdx, String langKey, String defaultLabel, String valueText, int mouseX, int mouseY) {
        int ry = guiTop + 42 + rowIdx * 17;
        boolean rowHover = mouseX >= guiLeft + 4 && mouseX <= guiLeft + guiWidth - 4
            && mouseY >= ry
            && mouseY < ry + 16;

        if (rowHover) {
            drawRect(guiLeft + 4, ry, guiLeft + guiWidth - 4, ry + 16, 0x25FFFFFF);
        }

        // Label in white text
        String label = StatCollector.canTranslate(langKey) ? StatCollector.translateToLocal(langKey) : defaultLabel;
        drawString(fontRendererObj, label, guiLeft + 8, ry + 4, 0xFFFFFF);

        // [-] button
        int bx1 = guiLeft + 68;
        int by = ry + 1;
        boolean minusHover = mouseX >= bx1 && mouseX <= bx1 + 14 && mouseY >= by && mouseY <= by + 14;
        drawRect(bx1, by, bx1 + 14, by + 14, minusHover ? 0xFF588196 : 0xFF456B7E);
        drawCenteredString(fontRendererObj, "-", bx1 + 7, by + 3, 0xFFFFFF);

        // Value box in dark slate contrast
        int vx1 = guiLeft + 84;
        drawRect(vx1, by, vx1 + 54, by + 14, 0xFF335060);
        drawCenteredString(fontRendererObj, "\u00a7f" + valueText, vx1 + 27, by + 3, 0xFFFFFF);

        // [+] button
        int bx2 = guiLeft + 140;
        boolean plusHover = mouseX >= bx2 && mouseX <= bx2 + 14 && mouseY >= by && mouseY <= by + 14;
        drawRect(bx2, by, bx2 + 14, by + 14, plusHover ? 0xFF588196 : 0xFF456B7E);
        drawCenteredString(fontRendererObj, "+", bx2 + 7, by + 3, 0xFFFFFF);

        // [R] button with soft mouth pink/coral accent
        int bx3 = guiLeft + 158;
        boolean rHover = mouseX >= bx3 && mouseX <= bx3 + 14 && mouseY >= by && mouseY <= by + 14;
        drawRect(bx3, by, bx3 + 14, by + 14, rHover ? 0xFFB86675 : 0xFF964E5D);
        drawCenteredString(fontRendererObj, "\u00a7fR", bx3 + 7, by + 3, 0xFFFFFF);
    }

    private void drawSpinToggleRow(int rowIdx, String langKey, String defaultLabel, boolean enabled, int mouseX,
        int mouseY) {
        int ry = guiTop + 42 + rowIdx * 17;
        String label = StatCollector.canTranslate(langKey) ? StatCollector.translateToLocal(langKey) : defaultLabel;
        drawString(fontRendererObj, label, guiLeft + 8, ry + 4, 0xFFFFFF);

        int bx = guiLeft + 68;
        int by = ry + 1;
        int bw = 104;
        boolean hover = mouseX >= bx && mouseX <= bx + bw && mouseY >= by && mouseY <= by + 14;
        int color = enabled ? (hover ? 0xFF358E62 : 0xFF28724E) : (hover ? 0xFFA14D5C : 0xFF7E3B47);
        drawRect(bx, by, bx + bw, by + 14, color);
        String toggleKey = enabled ? "gui.blahaj.toggle.enabled" : "gui.blahaj.toggle.disabled";
        String toggleDefault = enabled ? "Enabled" : "Disabled";
        String toggleText = StatCollector.canTranslate(toggleKey) ? StatCollector.translateToLocal(toggleKey)
            : toggleDefault;
        drawCenteredString(fontRendererObj, "\u00a7f" + toggleText, bx + bw / 2, by + 3, 0xFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        // Close [X] button
        if (mouseX >= guiLeft + guiWidth - 16 && mouseX <= guiLeft + guiWidth - 3
            && mouseY >= guiTop + 3
            && mouseY <= guiTop + 15) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
            return;
        }

        // Title bar drag
        if (mouseX >= guiLeft && mouseX <= guiLeft + guiWidth && mouseY >= guiTop && mouseY <= guiTop + 18) {
            isDragging = true;
            dragOffsetX = mouseX - guiLeft;
            dragOffsetY = mouseY - guiTop;
            return;
        }

        // Tab selection
        Tab[] tabs = Tab.values();
        int tabW = (guiWidth - 8) / tabs.length;
        for (int i = 0; i < tabs.length; i++) {
            int tx1 = guiLeft + 4 + i * tabW;
            int tx2 = tx1 + tabW - 2;
            int ty1 = guiTop + 21;
            int ty2 = ty1 + 14;
            if (mouseX >= tx1 && mouseX <= tx2 && mouseY >= ty1 && mouseY <= ty2) {
                currentTab = tabs[i];
                return;
            }
        }

        // Parameter rows
        int maxRows = (currentTab == Tab.FIRST_PERSON || currentTab == Tab.THIRD_PERSON) ? 6
            : (currentTab == Tab.ANIMATION ? 4 : 2);

        for (int row = 0; row < maxRows; row++) {
            int ry = guiTop + 42 + row * 17;
            int by = ry + 1;

            if (currentTab == Tab.ANIMATION && row == 0) {
                // Spin toggle button
                if (mouseX >= guiLeft + 68 && mouseX <= guiLeft + 172 && mouseY >= by && mouseY <= by + 14) {
                    BlahajConfig.enableSpin = !BlahajConfig.enableSpin;
                    return;
                }
                continue;
            }

            boolean fine = (mouseButton == 1) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
                || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            boolean coarse = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

            // [-] button
            int bx1 = guiLeft + 68;
            if (mouseX >= bx1 && mouseX <= bx1 + 14 && mouseY >= by && mouseY <= by + 14) {
                adjustParameter(currentTab, row, -1, fine, coarse);
                return;
            }

            // [+] button
            int bx2 = guiLeft + 140;
            if (mouseX >= bx2 && mouseX <= bx2 + 14 && mouseY >= by && mouseY <= by + 14) {
                adjustParameter(currentTab, row, 1, fine, coarse);
                return;
            }

            // [R] reset button
            int bx3 = guiLeft + 158;
            if (mouseX >= bx3 && mouseX <= bx3 + 14 && mouseY >= by && mouseY <= by + 14) {
                resetRow(currentTab, row);
                return;
            }
        }

        // Bottom action buttons
        int btnY = guiTop + guiHeight - 34;

        // Save
        if (mouseX >= guiLeft + 6 && mouseX <= guiLeft + 54 && mouseY >= btnY && mouseY <= btnY + 15) {
            BlahajConfig.save();
            String savedMsg = StatCollector.canTranslate("gui.blahaj.status.saved")
                ? StatCollector.translateToLocal("gui.blahaj.status.saved")
                : "Saved to config!";
            statusMessage = "\u2713 " + savedMsg;
            statusMessageTime = System.currentTimeMillis();
            return;
        }

        // Reset tab
        if (mouseX >= guiLeft + 58 && mouseX <= guiLeft + 124 && mouseY >= btnY && mouseY <= btnY + 15) {
            switch (currentTab) {
                case FIRST_PERSON:
                    BlahajConfig.resetDefaultsFirstPerson();
                    break;
                case THIRD_PERSON:
                    BlahajConfig.resetDefaultsThirdPerson();
                    break;
                case ANIMATION:
                    BlahajConfig.resetDefaultsAnimation();
                    break;
                case SIZES:
                    BlahajConfig.resetDefaultsSizes();
                    break;
            }
            statusMessage = StatCollector.canTranslate("gui.blahaj.status.reset")
                ? StatCollector.translateToLocal("gui.blahaj.status.reset")
                : "Reset tab defaults!";
            statusMessageTime = System.currentTimeMillis();
            return;
        }

        // Cam toggle
        if (mouseX >= guiLeft + 128 && mouseX <= guiLeft + 188 && mouseY >= btnY && mouseY <= btnY + 15) {
            if (mc.gameSettings.thirdPersonView == 0) {
                mc.gameSettings.thirdPersonView = 2; // Front view
            } else {
                mc.gameSettings.thirdPersonView = 0; // First person
            }
            return;
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
        super.mouseMovedOrUp(mouseX, mouseY, which);
        if (which == 0) {
            isDragging = false;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
}
