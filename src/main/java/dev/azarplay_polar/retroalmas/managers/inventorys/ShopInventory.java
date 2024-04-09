package dev.azarplay_polar.retroalmas.managers.inventorys;

import dev.azarplay_polar.retroalmas.RetroAlmas;
import dev.azarplay_polar.retroalmas.utils.CustomFileConfiguration;
import dev.azarplay_polar.retroalmas.utils.getItensInventoryFromConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class ShopInventory {
    CustomFileConfiguration config = new CustomFileConfiguration("menus.yml", RetroAlmas.getPlugin(RetroAlmas.class));
    Player p;
    public ShopInventory(Player player) throws IOException, InvalidConfigurationException {
        p = player;
        openInventory();
    }


    public void openInventory() throws IOException, InvalidConfigurationException {
        String title = config.getString("shop.title");
        Inventory inv = Bukkit.createInventory(null, config.getInt("shop.rows") * 9, title);
        Inventory inv2 = new getItensInventoryFromConfig(inv, p, config.getConfigurationSection("shop.itens")).setItensInventory();
        p.openInventory(inv2);
    }
}
