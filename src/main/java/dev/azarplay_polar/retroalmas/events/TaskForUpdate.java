package dev.azarplay_polar.retroalmas.events;

import dev.azarplay_polar.retroalmas.caches.Maps;
import dev.azarplay_polar.retroalmas.database.DatabaseManager;
import dev.azarplay_polar.retroalmas.managers.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskForUpdate {
    Connection connection;
    public void task(){
        if (Maps.players.isEmpty()){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Não ha dados para salvar.");
            return;
        }
        Maps.players.forEach((player, playerData) -> {
            if (!(player.isOnline())) {
                Maps.players.remove(player);
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"Player não esta on");
                return;
            }

            try {
                connection = DatabaseManager.getConnection().getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String query = "UPDATE Players SET souls = ? WHERE uuid = ?";
            try {
                Bukkit.getConsoleSender().sendMessage("Update do player " + player.getName());
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Que agora esta com " + playerData.getSouls());
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDouble(1, playerData.getSouls());
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
                Bukkit.getConsoleSender().sendMessage("O player " + player.getName() + " foi atualizado");
                connection.close();
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage("Erro ocorreu ao atualizar o player " + player.getName());
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
