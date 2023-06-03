package me.rixve.doorlock.commands;

import me.rixve.doorlock.ItemManager.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class keyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.getInventory().contains(ItemManager.basicKey))
                player.getInventory().addItem(ItemManager.basicKey);
        } else {
            sender.sendMessage("You have to be a player to use this command!");
        }

        return false;
    }
}
