package me.rixve.doorlock.commands;

import me.rixve.doorlock.DoorLock;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetKeyCommand implements CommandExecutor {

    private final DoorLock plugin;

    public GetKeyCommand(DoorLock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, 1);
            if(DoorLock.types.contains(block.getType())) {
                if(plugin.isLocked(block) && !player.getInventory().contains(plugin.getkey(block)))
                    player.getInventory().addItem(plugin.getkey(block));
            }
        }
        return false;
    }
}
