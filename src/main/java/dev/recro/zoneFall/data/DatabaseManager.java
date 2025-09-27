package dev.recro.zoneFall.data;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    private final String url;
    private final String user;
    private final String password;

    public DatabaseManager(String host, int port, String database, String user, String password) {
        this.url = "jdbc:mariadb://" + host + ":" + port + "/" + database;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }

    // Load player data from DB
    public PlayerData loadPlayerData(UUID uuid) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT sellmultiplier, name, kills, mobkills, deaths, balance, firstjoin, lastjoin FROM player_stats WHERE uuid = ?"
            );
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PlayerData data = new PlayerData(uuid);
                data.setName(rs.getString("name"));
                data.setBalance(rs.getDouble("balance"));
                data.setSellMultiplier(rs.getDouble("sellmultiplier"));
                data.setFirstJoin(rs.getLong("firstjoin"));
                data.setLastJoin(System.currentTimeMillis());
                for (int i = 0; i < rs.getInt("kills"); i++) data.addKill();
                for (int i = 0; i < rs.getInt("mobkills"); i++) data.addMobKill();
                for (int i = 0; i < rs.getInt("deaths"); i++) data.addDeath();
                return data;
            } else {
                // Player not in DB yet, create default row
                PlayerData data = new PlayerData(uuid);
                data.setFirstJoin(System.currentTimeMillis());
                savePlayerData(data);
                return data;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new PlayerData(uuid);
        }
    }

    // Save player data to DB
    public void savePlayerData(PlayerData data) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "REPLACE INTO player_stats (uuid, name, sellmultiplier, kills, mobkills, deaths, balance, lastjoin, firstjoin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, data.getUuid().toString());
            ps.setString(2, data.getName());
            ps.setDouble(3, data.getSellMultiplier());
            ps.setInt(4, data.getKills());
            ps.setInt(5, data.getMobKills());
            ps.setInt(6, data.getDeaths());
            ps.setDouble(7, data.getBalance());
            ps.setLong(8, data.getLastJoin());
            ps.setLong(9, data.getFirstJoin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setupTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_stats (" +
                    "uuid CHAR(36) PRIMARY KEY," +
                    "kills INT NOT NULL DEFAULT 0," +
                    "mob_kills INT NOT NULL DEFAULT 0," +
                    "deaths INT NOT NULL DEFAULT 0," +
                    "balance DOUBLE NOT NULL DEFAULT 0" +
                    ")");

            ensureColumnExists(connection, "player_stats", "lastjoin", "LONG NOT NULL DEFAULT 0.0");
            ensureColumnExists(connection, "player_stats", "firstjoin", "LONG NOT NULL DEFAULT 0.0");
            ensureColumnExists(connection, "player_stats", "mobkills", "INT NOT NULL DEFAULT 0");
            ensureColumnExists(connection, "player_stats", "sellmultiplier", "DOUBLE NOT NULL DEFAULT 1");
            ensureColumnExists(connection, "player_stats", "name", "CHAR(17)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ensureColumnExists(Connection conn, String table, String column, String definition) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getColumns(null, null, table, column)) {
            if (!rs.next()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD COLUMN " + column + " " + definition);
                    System.out.println("[Database] Added missing column: " + column);
                }
            }
        }
    }
}