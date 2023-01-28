package com.connorcode.mastodonlink;


import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;

public class Database {
    public Connection connection;

    public Database(String path) {
        // Init database
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + MastodonLink.plugin.getDataFolder() + File.separator + path);

            // Init tables
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("PRAGMA synchronous = NORMAL");
            stmt.executeUpdate("PRAGMA journal_mode = WAL");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (uuid TEXT NOT NULL UNIQUE, mastadon TEXT NOT NULL, mastodonId TEXT NOT NULL UNIQUE, created INTEGER NOT NULL)");
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS pending (uuid TEXT NOT NULL UNIQUE, name TEXT NOT NULL, code TEXT NOT NULL, created INTEGER NOT NULL)");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<String> isPlayerPending(Player player) {
        return run(() -> {
            var stmt = connection.prepareStatement("SELECT code FROM pending WHERE uuid = ?");
            stmt.setString(1, player.getUniqueId().toString());
            var resultSet = stmt.executeQuery();

            if (resultSet.isClosed()) return Optional.empty();
            return Optional.of(resultSet.getString(1));
        });
    }

    public boolean isPlayerLinked(Player player) {
        return Boolean.TRUE.equals(run(() -> {
            var stmt = connection.prepareStatement("SELECT * FROM users WHERE uuid = ?");
            stmt.setString(1, player.getUniqueId().toString());
            var res = stmt.executeQuery();
            return !res.isClosed();
        }));
    }

    public Optional<String> confirmAccount(String code, String mastodon, String mastodonId) {
        return run(() -> {
            var stmt = connection.prepareStatement("SELECT uuid, name FROM pending WHERE code = ?");
            stmt.setString(1, code);
            var res = stmt.executeQuery();
            if (res.isClosed()) return Optional.empty();
            var uuid = res.getString(1);
            var name = res.getString(2);

            var stmt2 = connection.prepareStatement("DELETE FROM pending WHERE code = ?");
            stmt2.setString(1, code);
            stmt2.executeUpdate();

            var stmt3 = connection.prepareStatement(
                    "INSERT INTO  users (uuid, mastadon, mastodonId, created) VALUES (?, ?, ?, ?)");
            stmt3.setString(1, uuid);
            stmt3.setString(2, mastodon);
            stmt3.setString(3, mastodonId);
            stmt3.setLong(4, System.currentTimeMillis());
            stmt3.executeUpdate();
            return Optional.of(name);
        });
    }

    public String createAuthCode(Player player) {
        var code = Misc.randomAscii(MastodonLink.config.verifyCodeLength);
        var epoch = System.currentTimeMillis();

        MastodonLink.logger.log(Level.INFO, String.format("Created verification code for %s", player.getName()));
        run(() -> {
            var stmt =
                    connection.prepareStatement("INSERT INTO pending (uuid, name, code, created) VALUES (?, ?, ?, ?)");
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, player.getName());
            stmt.setString(3, code);
            stmt.setLong(4, epoch);
            stmt.execute();
        });

        return code;
    }

    public Optional<String> getMastodon(Player player) {
        return run(() -> {
            var stmt = connection.prepareStatement("SELECT mastadon FROM users WHERE uuid = ?");
            stmt.setString(1, player.getUniqueId().toString());
            var res = stmt.executeQuery();
            if (res.isClosed()) return Optional.empty();
            return Optional.of(res.getString(1));
        });
    }

    public void close() {
        run(() -> connection.close());
    }

    private <T> void run(VoidDatabaseRunnable<T> runnable) {
        try {
            runnable.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> T run(DatabaseRunnable<T> runnable) {
        try {
            return runnable.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    interface VoidDatabaseRunnable<T> {
        void run() throws SQLException;
    }

    interface DatabaseRunnable<T> {
        T run() throws SQLException;
    }
}
