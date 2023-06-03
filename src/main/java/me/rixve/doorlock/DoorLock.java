package me.rixve.doorlock;


import com.sun.tools.javac.util.List;
import me.rixve.doorlock.ItemManager.ItemManager;
import me.rixve.doorlock.commands.*;

import me.rixve.doorlock.listeners.PlayerClickListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


import javax.sound.sampled.Line;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public final class DoorLock extends JavaPlugin {

    private HashMap<Block, ItemStack> doors;
    private ArrayList <Block> unlocked;
    public static ArrayList<Material> types;


    @Override
    public void onEnable() {
        doors = new HashMap<>();
        types = new ArrayList<>();
        unlocked = new ArrayList<>();
        ItemManager.init();
        getServer().getLogger().log(Level.INFO, "Plugin enabled!");
        getCommand("key").setExecutor(new keyCommand());
        getCommand("getkey").setExecutor(new GetKeyCommand(this));
        getCommand("closeDoors").setExecutor(new CloseDoorsCommand(this));
        getCommand("removeLock").setExecutor(new removeLockCommand(this));
        getCommand("renamekey").setExecutor(new RenameKeyCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerClickListener(this), this);

        saveDefaultConfig();

        if(getConfig().contains("data")) {
            restoreFiles();
        }




        types.add(Material.OAK_DOOR);
        types.add(Material.DARK_OAK_DOOR);
        types.add(Material.ACACIA_DOOR);
        types.add(Material.BAMBOO_DOOR);
        types.add(Material.BIRCH_DOOR);
        types.add(Material.CRIMSON_DOOR);
        types.add(Material.JUNGLE_DOOR);
        types.add(Material.MANGROVE_DOOR);
        types.add(Material.SPRUCE_DOOR);
        types.add(Material.WARPED_DOOR);
    }


    private void savefiles() {
        getConfig().set("data", null);
        for(HashMap.Entry<Block, ItemStack> entry: doors.entrySet()) {
            Block bl = entry.getKey();
            Location loc = bl.getLocation();
            String str = (int) loc.getX() + "(" + (int) loc.getY() + "(" + (int) loc.getZ();
            getConfig().set("data." + str, entry.getValue());
        }
        getConfig().set("data." + "keycounter", ItemManager.keyCounter);
        saveConfig();
    }

    private void restoreFiles() {
        getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
            if(Objects.equals(key, "keycounter")) {
                ItemManager.keyCounter = this.getConfig().getInt("data." + key);
            } else {
                ItemStack content = (ItemStack) this.getConfig().getItemStack("data." + key);
                String str = key;

                int first = str.indexOf("(");
                int second = str.indexOf("(", first + 1);

                Double x = Double.parseDouble(str.substring(0, first));

                Double y = Double.parseDouble(str.substring(first + 1, second));
                Double z = Double.parseDouble(str.substring(second + 1));
                Location loc = new Location(getServer().getWorld("world"), x, y, z);
                doors.put(loc.getBlock(), content);
            }
        });
    }


    @Override
    public void onDisable() {
        savefiles();
        for(Block block : getUnlockedMap()) {
            Door door = (Door) block.getBlockData();
            door.setOpen(false);
            block.setBlockData(door);
        }
        resetUnlocked();
    }

    public void addDoor(Block block, ItemStack key) {
        if(this.doors.containsKey(block)) {
            this.doors.replace(block, key);
        }
        else {
            this.doors.put(block, key);
        }
    }

    public void removeDoor (Block block) {
        if(this.doors.containsKey(block)) {
            this.doors.remove(block);
        }
    }

    public ItemStack getkey(Block block) {
        return this.doors.get(block);
    }

    public boolean isLocked(Block block){
        return this.doors.containsKey(block);
    }


    public void addUnlocked(Block block) {
        if(!this.unlocked.contains(block)) {
            this.unlocked.add(block);
        }
    }

    public void removeUnlocked (Block block) {
        if(this.unlocked.contains(block)) {
            this.unlocked.remove(block);
        }
    }


    public boolean isUnlocked(Block block){
        return this.unlocked.contains(block);
    }

    public ArrayList<Block> getUnlockedMap() {
        return unlocked;
    }
    public void resetUnlocked() {
        unlocked = new ArrayList<>();
    }

}
