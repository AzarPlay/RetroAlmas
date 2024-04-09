package dev.azarplay_polar.retroalmas.events;

import dev.azarplay_polar.retroalmas.caches.Maps;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class onQuit implements Listener {
    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerJoinEvent event){
        if (Maps.players.containsKey(event.getPlayer())) {
            new TaskForUpdate().task();
            event.getPlayer().sendMessage(ChatColor.GREEN + "Seu dados foram atualizados");
        }
    }
}
