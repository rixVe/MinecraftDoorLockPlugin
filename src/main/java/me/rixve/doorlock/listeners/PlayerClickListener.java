package me.rixve.doorlock.listeners;

import me.rixve.doorlock.DoorLock;
import me.rixve.doorlock.ItemManager.ItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerClickListener implements Listener {

    private final DoorLock plugin;

    public PlayerClickListener(DoorLock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if(DoorLock.types.contains(block.getType())) {
                if(!plugin.isLocked(block)) {
                    if(Objects.equals(player.getInventory().getItemInMainHand(), ItemManager.basicKey)) {
                        ItemStack key = ItemManager.createUniqueKey();
                        player.getInventory().setItemInMainHand(key);
                        plugin.addDoor(block, key);
                        Door door = (Door) block.getBlockData();
                        Location loc2 = block.getLocation();
                        if(door.getHalf() == Bisected.Half.TOP) {
                            loc2.setY(block.getLocation().getY() - 1);
                        } else {

                            loc2.setY(block.getLocation().getY() + 1);
                        }

                        plugin.addDoor(loc2.getBlock(), key);
                        event.setCancelled(true);
                    }
                } else {
                    if(player.getInventory().contains(plugin.getkey(block)) && Objects.equals(player.getInventory().getItemInMainHand(), ItemManager.basicKey)) {
                        player.getInventory().setItemInMainHand(plugin.getkey(block));
                        event.setCancelled(true);
                        return;
                    }
                    if(!Objects.equals(player.getInventory().getItemInMainHand(), plugin.getkey(block)) && !plugin.isUnlocked(block)) {
                        player.sendMessage("This door is locked!");
                        event.setCancelled(true);
                        return;
                    }
                    if(Objects.equals(player.getInventory().getItemInMainHand(), plugin.getkey(block)) && !plugin.isUnlocked(block)) {
                        plugin.addUnlocked(block);
                        Door door = (Door) block.getBlockData();
                        Location loc2 = block.getLocation();
                        if(door.getHalf() == Bisected.Half.TOP) {
                            loc2.setY(block.getLocation().getY() - 1);
                        } else {

                            loc2.setY(block.getLocation().getY() + 1);
                        }
                        plugin.addUnlocked(loc2.getBlock());

                    }
                }
            }
        }
    }

}
