package me.luanfy.autojail.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JailWand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("jailwand.get")){
                p.sendMessage(ChatColor.GREEN + "Take a fine Jail Wand :D");
                ItemStack wand = new ItemStack(Material.BLAZE_ROD);
                ItemMeta wandMeta = wand.getItemMeta();

                wandMeta.setDisplayName(ChatColor.RED + "Fearful Jail Wand");
                wand.addUnsafeEnchantment(Enchantment.DURABILITY, 14);

                wand.setItemMeta(wandMeta);

                p.getInventory().addItem(wand);
            }

        }
        return true;
    }


}
