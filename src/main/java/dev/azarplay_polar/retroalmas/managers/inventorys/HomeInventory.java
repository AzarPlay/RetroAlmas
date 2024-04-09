package dev.azarplay_polar.retroalmas.managers.inventorys;

import dev.azarplay_polar.retroalmas.RetroAlmas;
import dev.azarplay_polar.retroalmas.utils.CustomFileConfiguration;
import dev.azarplay_polar.retroalmas.utils.getItensInventoryFromConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class HomeInventory {
    CustomFileConfiguration config = new CustomFileConfiguration("menus.yml", RetroAlmas.getPlugin(RetroAlmas.class));
    Player p;
    public HomeInventory(Player player) throws IOException, InvalidConfigurationException {
        p = player;
        openInventory();
    }
    private void openInventory() throws IOException, InvalidConfigurationException {
        String title = config.getString("home.title");
        Inventory inv = Bukkit.createInventory(null, config.getInt("home.rows") * 9, title);
        Inventory inv2 = new getItensInventoryFromConfig(inv, p, config.getConfigurationSection("home.itens")).setItensInventory();
        p.openInventory(inv2);
    }
}
