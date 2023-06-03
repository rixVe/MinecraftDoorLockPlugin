package me.rixve.doorlock.ItemManager;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack basicKey;
    public static int keyCounter = 1;

    public static void init() {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);

        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Unsigned Key"));
        meta.setUnbreakable(true);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Key that doesn't open anything yet"));
        meta.lore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        basicKey = item;
    }

    public static ItemStack createUniqueKey() {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);

        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Key number " + keyCounter));

        meta.setUnbreakable(true);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Key to door nr " + keyCounter));
        meta.lore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        keyCounter +=1;
        return item;
    }

    public static ItemStack createCustomKey(String str) {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);

        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Key to " + str));

        meta.setUnbreakable(true);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(str + " key"));
        meta.lore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        keyCounter +=1;
        return item;
    }
}
