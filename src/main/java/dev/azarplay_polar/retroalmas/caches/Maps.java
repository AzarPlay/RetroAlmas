package dev.azarplay_polar.retroalmas.caches;

import dev.azarplay_polar.retroalmas.managers.player.PlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Maps {
    public static HashMap<Player, PlayerManager.PlayerData> players = new HashMap<>();
}
