package dev.azarplay_polar.retroalmas.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.azarplay_polar.retroalmas.RetroAlmas;
import org.bukkit.configuration.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

public class DatabaseManager {
    public DatabaseManager() {
    }
    static Configuration configuration = RetroAlmas.getPlugin(RetroAlmas.class).getConfig();

    static String senhaDecodificada;

    static {
        try {
            senhaDecodificada = URLDecoder.decode(configuration.getString("database.password"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    static String url = "jdbc:mysql://" + configuration.getString("database.host") + ":" + configuration.getInt("database.port") + "/" + configuration.getString("database.database");

    private static HikariConfig getConfigs() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(configuration.getString("database.user"));
        config.setPassword(configuration.getString("database.password")); // pegar meu celu
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setAutoCommit(true);
        return config;
    }
    public static HikariDataSource getConnection(){
        HikariDataSource dataSource = new HikariDataSource(getConfigs());
        return dataSource;
    }
}
