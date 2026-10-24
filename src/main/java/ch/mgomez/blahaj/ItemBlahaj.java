package ch.mgomez.blahaj;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBlahaj extends Item {

    public ItemBlahaj() {
        setMaxStackSize(1);
        setUnlocalizedName("blahaj");
        setTextureName("blahaj:blahaj");
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        world.playSoundAtEntity(player, "step.cloth", 0.8F, 1.6F + (world.rand.nextFloat() * 0.4F));
        if (world.isRemote) {
            for (int i = 0; i < 3; i++) {
                double ox = (world.rand.nextDouble() - 0.5D) * 0.8D;
                double oy = 1.0D + world.rand.nextDouble() * 0.5D;
                double oz = (world.rand.nextDouble() - 0.5D) * 0.8D;
                world.spawnParticle("heart", player.posX + ox, player.posY + oy, player.posZ + oz, 0.0D, 0.05D, 0.0D);
            }
        }
        return stack;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("item.blahaj.tooltip.desc"));
        list.add(EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("item.blahaj.tooltip.cuddle"));
    }
}
