package dev.azarplay_polar.retroalmas.commands;

import dev.azarplay_polar.retroalmas.managers.inventorys.EffectInventory;
import dev.azarplay_polar.retroalmas.managers.inventorys.HomeInventory;
import dev.azarplay_polar.retroalmas.managers.inventorys.ShopInventory;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import java.io.IOException;


public class Souls implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender.hasPermission("retroalmas.souls")) {
            try {
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.VILLAGER_IDLE, 1, 1);
                if (args.length == 0) {
                    new HomeInventory((Player) sender);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("shop")) {
                    new ShopInventory((Player) sender).openInventory();
                    return true;
                }
                else if (args[0].equalsIgnoreCase("effects")) {
                    new EffectInventory((Player) sender).openInventory();
                    return true;
                }
                else {
                    sender.sendMessage("§cUse: /souls");
                    return true;
                }
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
