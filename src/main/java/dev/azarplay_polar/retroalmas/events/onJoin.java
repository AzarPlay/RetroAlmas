package dev.azarplay_polar.retroalmas.events;

import dev.azarplay_polar.retroalmas.caches.Maps;
import dev.azarplay_polar.retroalmas.managers.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class onJoin implements Listener {
    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        CompletableFuture.runAsync(() -> {
            try {
                Maps.players.put(p, new PlayerManager(p).loadPlayerDataFromDatabase());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
