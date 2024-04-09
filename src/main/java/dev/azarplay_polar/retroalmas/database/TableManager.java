package dev.azarplay_polar.retroalmas.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableManager {
    static Connection connection_real;

    static {
        try {
            connection_real = DatabaseManager.getConnection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void createTable() throws SQLException {
        Connection connection = TableManager.connection_real;

        String createPlayersTableSQL = "CREATE TABLE IF NOT EXISTS Players ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "uuid VARCHAR(255) NOT NULL,"
            + "souls DOUBLE DEFAULT 0,"
            + "UNIQUE(uuid)"
            + ")";

        try (PreparedStatement createStatement = connection.prepareStatement(createPlayersTableSQL)) {
            createStatement.executeUpdate();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "As tabelas foram criadas.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection.close();
    }
}
