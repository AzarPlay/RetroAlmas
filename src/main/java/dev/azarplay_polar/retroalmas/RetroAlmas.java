package dev.azarplay_polar.retroalmas;

import dev.azarplay_polar.retroalmas.caches.Maps;
import dev.azarplay_polar.retroalmas.commands.Souls;
import dev.azarplay_polar.retroalmas.database.DatabaseManager;
import dev.azarplay_polar.retroalmas.database.TableManager;
import dev.azarplay_polar.retroalmas.events.*;
import dev.azarplay_polar.retroalmas.utils.CustomFileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.IOException;

public final class RetroAlmas extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            DatabaseManager.getConnection();
            TableManager.createTable();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[ RetroAlmas ] Conectado ao banco de dados");
        }
        catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ RetroAlmas ] Não foi possivel conectar ao banco de dados");
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[RetroAlmas] Plugin iniciado");
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new onInventoryInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new onQuit(), this);
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        try {
            verifyMenuInventory();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        getCommand("souls").setExecutor(new Souls());
        if (!(Maps.players.isEmpty())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new TaskForUpdate().task();
                }
            }.runTaskTimerAsynchronously(this, 0, 10 * 20);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void verifyMenuInventory() throws IOException, InvalidConfigurationException {
        CustomFileConfiguration config = new CustomFileConfiguration("menus.yml", this);
        if (config.getConfigurationSection("menu") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Você não configurou o menu corretamente. verifique em menus.yml");
        }
        else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Menu configurado corretamente");
        }
    }
}
