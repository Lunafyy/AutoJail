package me.luanfy.autojail;

import me.luanfy.autojail.commands.JailWand;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class AutoJail extends JavaPlugin implements Listener {

    List<String> jailed = new ArrayList<String>();

    @Override
    public void onEnable() {
        this.getCommand("jailwand").setExecutor(new JailWand());
        getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getLogger().info(ChatColor.GREEN + "AutoJail has successfully loaded :D");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "AutoJail has successfully disabled :D");
    }

    @EventHandler
    public void onJail(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            e.setCancelled(true);
            World world = this.getServer().getWorlds().get(0);
            Location jail = world.getSpawnLocation();
            Player criminal = (Player) e.getEntity();
            Player attacker = (Player) e.getDamager();
            if(Objects.requireNonNull(attacker.getInventory().getItemInHand().getItemMeta()).getDisplayName().equals(ChatColor.RED + "Fearful Jail Wand")) {
                if(attacker.hasPermission("jailwand.use")) {
                    criminal.teleport(jail);
                    criminal.sendMessage(ChatColor.RED + "You've been jailed!");
                    attacker.sendMessage(ChatColor.GREEN + "You've jailed " + criminal.getDisplayName());
                    if(!jailed.contains(criminal.getDisplayName())){
                        jailed.add(criminal.getDisplayName());
                    }

                }
            }

        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("revive")){
            if(sender instanceof Player){
                if(sender.hasPermission("jailwand.revive")){
                    if(!(jailed.size() == 0)){
                        Random rand = new Random();
                        Player admin = (Player) sender;
                        Player revived = Bukkit.getPlayer(jailed.get(rand.nextInt(jailed.size())));
                        Bukkit.broadcastMessage(ChatColor.GREEN + revived.getDisplayName() + " has been revived!!");
                        revived.teleport(admin.getLocation());
                        jailed.remove(revived.getDisplayName());
                    } else {
                        sender.sendMessage(ChatColor.RED + "There are no players in the jail!");
                    }
                }

            }
            return true;
        }
        return true;
    }
}
