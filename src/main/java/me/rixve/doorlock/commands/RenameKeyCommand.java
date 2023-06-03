package me.rixve.doorlock.commands;

import me.rixve.doorlock.DoorLock;
import me.rixve.doorlock.ItemManager.ItemManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RenameKeyCommand implements CommandExecutor {

    private final DoorLock plugin;

    public RenameKeyCommand(DoorLock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Block bl = player.getTargetBlock(null, 1);
            if(args.length <= 0) return false;
            if(plugin.isLocked(bl)){
                if(Objects.equals(player.getInventory().getItemInMainHand(), plugin.getkey(bl))) {
                    ItemStack customKey = ItemManager.createCustomKey(args[0]);
                    if(!player.getInventory().contains(customKey))
                        player.getInventory().setItemInMainHand(customKey);
                    else
                        player.getInventory().setItemInMainHand(null);
                    plugin.addDoor(bl, customKey);
                    Door door = (Door) bl.getBlockData();
                    Location loc2 = bl.getLocation();
                    if(door.getHalf() == Bisected.Half.TOP) {
                        loc2.setY(bl.getLocation().getY() - 1);
                    } else {

                        loc2.setY(bl.getLocation().getY() + 1);
                    }
                    plugin.addDoor(loc2.getBlock(), customKey);
                    ItemManager.keyCounter -=1;
                }
            }
        }
        return false;
    }
}
