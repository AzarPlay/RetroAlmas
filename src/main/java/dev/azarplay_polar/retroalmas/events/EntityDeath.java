package dev.azarplay_polar.retroalmas.events;

import dev.azarplay_polar.retroalmas.RetroAlmas;
import dev.azarplay_polar.retroalmas.api.PrimeActionbar;
import dev.azarplay_polar.retroalmas.caches.Maps;
import dev.azarplay_polar.retroalmas.managers.player.PlayerManager;
import dev.azarplay_polar.retroalmas.utils.CustomFileConfiguration;
import dev.azarplay_polar.retroalmas.utils.TranslateUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class EntityDeath implements Listener {
    double bonus = 1.0;

    @EventHandler
    public void EntityDeath(EntityDeathEvent event) throws IOException, InvalidConfigurationException, SQLException {
        Player p = event.getEntity().getKiller();
        if (!(p instanceof Player)) {
            return;
        }
        CustomFileConfiguration config = new CustomFileConfiguration("mobs.yml", RetroAlmas.getPlugin(RetroAlmas.class));
        Configuration config_two = RetroAlmas.getPlugin(RetroAlmas.class).getConfig();
        PlayerManager.PlayerData data = new PlayerManager(p).existPlayer();
        if (data == null) {
            Maps.players.put(p, new PlayerManager.PlayerData(p, 1.0));
        }
        Maps.players.forEach((player, playerData) -> {
            System.out.println("Jogador: " + player.getName());
            System.out.println("Almas: " + playerData.getSouls());
        });
        config.getConfigurationSection("mobs").getKeys(false).forEach(mob -> {
            ConfigurationSection section = config.getConfigurationSection("mobs." + mob);
            if (mob.equals(event.getEntity().getType().toString())) {
                double souls = section.getDouble("souls");
                section.getConfigurationSection("permissions").getKeys(false).forEach(perm -> {
                    if (p.hasPermission(perm)) {
                        bonus += section.getDouble("permissions." + perm + ".bonus");
                    }
                });
                souls *= bonus;
                Double souls2 = data.getSouls();
                souls2 += souls;
                data.setSouls(souls2);

                DecimalFormat df = new DecimalFormat("#.##");

                if (config_two.getBoolean("utils.actionbar.use")) {
                    String reformated = new TranslateUtils().translateColors(config_two.getString("utils.actionbar.message").replaceAll("@souls", String.valueOf(df.format(data.getSouls()))).replaceAll("@bonus", String.valueOf(df.format(bonus))));
                    PrimeActionbar.sendActionbar(p, reformated);
                }

            }
        });
    }
}
