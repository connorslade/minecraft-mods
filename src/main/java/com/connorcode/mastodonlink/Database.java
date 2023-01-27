package com.connorcode.mastodonlink;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Database {
    public Connection connection;

    public Database(String path) {
        // Init database
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + MastodonLink.plugin.getDataFolder() + File.separator + path);

            // Init tables
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("PRAGMA synchronous = NORMAL");
            stmt.executeUpdate("PRAGMA journal_mode = WAL");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (uuid TEXT NOT NULL UNIQUE, mastadon TEXT NOT NULL UNIQUE, created INTEGER NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pending (uuid TEXT NOT NULL UNIQUE, code TEXT NOT NULL, created INTEGER NOT NULL)");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    interface DatabaseRunnable<T> {
        T run() throws SQLException;
    }

    private <T> T run(DatabaseRunnable<T> runnable) {
        try {
            return runnable.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPlayerLinked(UUID uuid) {
        return false;
    }

    public boolean isPlayerPending(UUID uuid) {
        return false;
    }

    public void close() {
        run(() -> {
            connection.close();
            return null;
        });
    }
}
