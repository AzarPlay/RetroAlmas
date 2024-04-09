package dev.azarplay_polar.retroalmas.events;

import dev.azarplay_polar.retroalmas.RetroAlmas;
import dev.azarplay_polar.retroalmas.utils.CustomFileConfiguration;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class onInventoryInteract implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException, InvalidConfigurationException {
        CustomFileConfiguration config = new CustomFileConfiguration("menus.yml", RetroAlmas.getPlugin(RetroAlmas.class));
        Player p = (Player) event.getWhoClicked();
        if (event.getClickedInventory().getTitle().equals(config.getString("shop.title")) || event.getClickedInventory().getTitle().equals(config.getString("effects.title"))) {
            event.setCancelled(true);
            Material material = Material.getMaterial(config.getInt("shop.itens.voltar.id"));
            if (event.getCurrentItem().getType() == material) {
                p.closeInventory();
                p.performCommand("souls");
            }
        }
        if (event.getInventory().getTitle().equals(config.getString("home.title"))) {
            event.setCancelled(true);
            if (event.isRightClick() && event.getCurrentItem() != null || event.isLeftClick() && event.getCurrentItem() != null) {
                config.getConfigurationSection("home.itens").getKeys(false).forEach(key -> {
                    System.out.println(event.getSlot() + "/" + config.getInt("home.itens." + key + ".slot"));
                    if (event.getSlot() == config.getInt("home.itens." + key + ".slot") - 1) {
                        System.out.println(config.getString("home.itens." + key + ".command"));
                        if (config.getString("home.itens." + key + ".command") != null) {
                            p.performCommand(config.getString("home.itens." + key + ".command") );
                        }
                    }
                });
            }
        }
    }
}
