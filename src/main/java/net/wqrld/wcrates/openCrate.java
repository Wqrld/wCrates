package net.wqrld.wcrates;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class openCrate implements Listener {
    Random r = new Random();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.ENDER_CHEST) && e.getClickedBlock().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.DIAMOND_BLOCK) {
                if (e.getPlayer().getItemInHand().getType() == Material.TRIPWIRE_HOOK) {
                    e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
                    Location chestloc = e.getClickedBlock().getLocation();
                    for (int y = chestloc.getBlockY() - 1; y > 0; y--) {
                        Block b = chestloc.getWorld().getBlockAt(chestloc.getBlockX(), y, chestloc.getBlockZ());
                        if (b.getType() == Material.CHEST) {
                            Chest chest = (Chest) b.getState();
                            int size = chest.getBlockInventory().getStorageContents().length;
                            ItemStack reward = chest.getBlockInventory().getStorageContents()[r.nextInt(size)];
                            e.getPlayer().getInventory().addItem(reward);
                            e.getPlayer().sendMessage(ChatColor.BLUE + "YAY " + reward.getAmount() + " " + reward.getType().name().substring(0, 1).toUpperCase() + reward.getType().name().substring(1).toLowerCase().replaceAll("_", ""));
                            break;
                        }
                    }

                    e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.DRAGON_BREATH, 2003);
                } else {
                    e.getPlayer().sendMessage(ChatColor.RED + "PLS GIB KEY");
                }
                e.setCancelled(true);
            } else if (e.getPlayer().getItemInHand().getType() == Material.TRIPWIRE_HOOK) {
                e.getPlayer().sendMessage(ChatColor.RED + "no placing keys dumbo");
                e.setCancelled(true);
            }
        }
    }

}
