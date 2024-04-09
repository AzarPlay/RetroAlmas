package dev.azarplay_polar.retroalmas.utils;

import dev.azarplay_polar.retroalmas.RetroAlmas;
import javafx.collections.transformation.TransformationList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;

public class getItensInventoryFromConfig {
    Inventory inv;
    Player p;
    ConfigurationSection section;
    public getItensInventoryFromConfig(Inventory inv, Player p, ConfigurationSection section) throws IOException, InvalidConfigurationException {
        this.inv = inv;
        this.p = p;
        this.section = section;
    }
    public Inventory setItensInventory() {
        section.getKeys(false).forEach(key -> {
            ConfigurationSection section = this.section.getConfigurationSection(key);
            if (section.getBoolean("head")) {
                ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setDisplayName(p.getDisplayName());
                meta.setOwner(p.getName());
                meta.setLore(new TranslateUtils().translateAllColors(section.getStringList("lore")));
                meta.addEnchant(Enchantment.DURABILITY,1,section.getBoolean("glow"));
                item.setItemMeta(meta);
                inv.setItem(section.getInt("slot") - 1, item);
            }
            else {
                Material material = Material.getMaterial(section.getInt("id"));

                if (material == null) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Houve um erro ao criar esse item. verifique ce o id esta certo. Id: " + section.getString("id"));
                    return;
                }
                ItemStack item = new ItemStack(material);

                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(new TranslateUtils().translateColors(section.getString("name")));
                meta.setLore(new TranslateUtils().translateAllColors(section.getStringList("lore")));
                if (section.getBoolean("glow")) {
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            item.setItemMeta(meta);
            inv.setItem(section.getInt("slot") - 1, item);
            };
        });
        return inv;
    }
}
