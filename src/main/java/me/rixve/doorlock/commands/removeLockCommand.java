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
import org.jetbrains.annotations.NotNull;

public class removeLockCommand implements CommandExecutor {

    private final DoorLock plugin;

    public removeLockCommand(DoorLock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, 1);
            if(DoorLock.types.contains(block.getType())) {
                Door door = (Door) block.getBlockData();
                plugin.removeDoor(block);
                plugin.removeUnlocked(block);
                Location loc2 = block.getLocation();
                if(door.getHalf() == Bisected.Half.TOP) {
                    loc2.setY(block.getLocation().getY() - 1);
                } else {

                    loc2.setY(block.getLocation().getY() + 1);
                }
                plugin.removeDoor(loc2.getBlock());
                plugin.removeUnlocked(loc2.getBlock());
                ItemManager.keyCounter -=1;
            }
        }
        return false;
    }
}
