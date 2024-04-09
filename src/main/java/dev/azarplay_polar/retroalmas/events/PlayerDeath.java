package dev.azarplay_polar.retroalmas.events;

import dev.azarplay_polar.retroalmas.RetroAlmas;
import dev.azarplay_polar.retroalmas.api.PrimeActionbar;
import dev.azarplay_polar.retroalmas.utils.CustomFileConfiguration;
import dev.azarplay_polar.retroalmas.utils.TranslateUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;
import java.text.DecimalFormat;

public class PlayerDeath implements Listener {
    double bonus = 1.0;
    double souls = 0.0;


    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event) throws IOException, InvalidConfigurationException {
        Player p = event.getEntity().getKiller().getPlayer();
        CustomFileConfiguration config = new CustomFileConfiguration("mobs.yml", RetroAlmas.getPlugin(RetroAlmas.class));
        Configuration config_two = RetroAlmas.getPlugin(RetroAlmas.class).getConfig();
        souls = config.getDouble("player.souls");
        config.getConfigurationSection("player").getKeys(false).forEach(perm -> {
            if (p.hasPermission(perm)) {
                bonus += config.getDouble("player.permissions." + perm + ".bonus");
                System.out.println(bonus);
            }
        });
        souls = (souls * bonus);

        DecimalFormat df = new DecimalFormat("#.##");

        if (config_two.getBoolean("utils.actionbar.use")) {
            String reformated = new TranslateUtils().translateColors(config_two.getString("utils.actionbar.message").replaceAll("@souls", String.valueOf(df.format(souls))).replaceAll("@bonus", String.valueOf(df.format(bonus))));
            PrimeActionbar.sendActionbar(p, reformated);
        }
    }
}
