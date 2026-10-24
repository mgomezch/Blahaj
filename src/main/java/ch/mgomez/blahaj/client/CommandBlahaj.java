package ch.mgomez.blahaj.client;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import ch.mgomez.blahaj.client.gui.GuiBlahajLiveAdjuster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CommandBlahaj extends CommandBase {

    @Override
    public String getCommandName() {
        return "blahaj";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/blahaj [reload|gui]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            ch.mgomez.blahaj.ClientProxy.registerSpecialItemRenderers();
            int count = CustomImageManager.reloadCustomImages();
            sender.addChatMessage(
                new ChatComponentText(
                    EnumChatFormatting.AQUA + "[Bl\u00e5haj] "
                        + EnumChatFormatting.GREEN
                        + "Reloaded "
                        + EnumChatFormatting.YELLOW
                        + count
                        + EnumChatFormatting.GREEN
                        + " custom flat image(s) from "
                        + EnumChatFormatting.GRAY
                        + CustomImageManager.getCustomDir()
                            .getPath()));
            return;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("gui")) {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new GuiBlahajLiveAdjuster());
            }
            return;
        }

        sender.addChatMessage(
            new ChatComponentText(
                EnumChatFormatting.AQUA + "[Bl\u00e5haj] "
                    + EnumChatFormatting.WHITE
                    + "Commands: "
                    + EnumChatFormatting.YELLOW
                    + "/blahaj reload "
                    + EnumChatFormatting.GRAY
                    + "(refresh config/blahaj/custom/ images), "
                    + EnumChatFormatting.YELLOW
                    + "/blahaj gui "
                    + EnumChatFormatting.GRAY
                    + "(open live adjuster)"));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "reload", "gui");
        }
        return null;
    }
}
