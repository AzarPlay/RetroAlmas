package dev.azarplay_polar.retroalmas.managers.player;

import dev.azarplay_polar.retroalmas.caches.Maps;
import dev.azarplay_polar.retroalmas.database.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class PlayerManager {
    Player p;
    static HashMap<Player, PlayerData> players = Maps.players;

    public PlayerManager(Player p) {
        this.p = p;
    }

    public PlayerData existPlayer() throws SQLException {
        if (!(players.containsKey(p))) {
            PlayerData playerData = loadPlayerDataFromDatabase();
            if (playerData == null) {
                PlayerData data = players.put(p, new PlayerData(p, 1.0));
                return data;
            }
            players.put(p, playerData);
            return playerData;
        }
        else {
            return players.get(p);
        }
    }
    public PlayerData loadPlayerDataFromDatabase() throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (connection == null) {
            p.kickPlayer("Falha ao conectar ao banco de dados.");
            return null;
        }

        PreparedStatement statement = null;
        statement = connection.prepareStatement("SELECT souls FROM Players WHERE uuid = ?");
        statement.setString(1, p.getUniqueId().toString());


        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                Bukkit.getConsoleSender().sendMessage("O player " + p.getName() + " existe no banco de dados.");
                PlayerData dat_two = new PlayerData(p, resultSet.getDouble("souls"));
                connection.close();
                return dat_two;
            }
            else {
                Bukkit.getConsoleSender().sendMessage("O player " + p.getName() + " não existe no banco de dados.");
                statement = connection.prepareStatement("INSERT INTO Players (uuid, souls) VALUES (?, ?)");
                statement.setString(1, p.getUniqueId().toString());
                statement.setDouble(2, 1.0);
                statement.executeUpdate();
                PlayerData data = new PlayerData(p, 1.0);
                connection.close();
                Maps.players.put(p, data);
                return data;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static class PlayerData {
        Player p;
        Double souls;
        public PlayerData(Player p, Double souls) {
            this.p = p;
            this.souls = souls;
        }
        public void setSouls(Double souls) {
            this.souls = souls;
        }

        public void removeSouls(Double souls) {
            this.souls -= souls;
        }

        public Double getSouls() {
            return souls;
        }

        public Player getPlayer() {
            return p;
        }
    }

}
