package me.rixve.doorlock.commands;

import me.rixve.doorlock.DoorLock;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CloseDoorsCommand implements CommandExecutor {

    private final DoorLock plugin;

    public CloseDoorsCommand(DoorLock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for(Block block : plugin.getUnlockedMap()) {
            Door door = (Door) block.getBlockData();
            door.setOpen(false);
            block.setBlockData(door);
        }
        plugin.resetUnlocked();
        return false;
    }
}
