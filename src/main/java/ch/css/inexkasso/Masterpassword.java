package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.*;
import static javax.accessibility.AccessibleRole.TABLE;

public class Masterpassword {




    public static void createTableIfNotExists() {
        String SQL_CREATE_TABLE_MASTERPASSWORD = "CREATE TABLE " + TABLE + " (" +
                "MasterpasswordId INT PRIMARY KEY, " +
                "Username VARCHAR(255), " +
                "Masterpassword VARCHAR(255))";
        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL_CREATE_TABLE_MASTERPASSWORD);
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) {
                System.err.println("Fehler beim Erstellen der Tabelle");
            }
        }
    }

    public boolean isMasterPasswordStored() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLE + " WHERE MasterpasswordId = 1";
        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void insertMasterPassword(String username, String password) throws SQLException {
        String sql = "INSERT INTO " + TABLE + " (MasterpasswordId, Username, Masterpassword) VALUES (1, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }

    public boolean checkCredentials(String inputUsername, String inputPassword) throws SQLException {
        String sql = "SELECT Username, Masterpassword FROM " + TABLE + " WHERE MasterpasswordId = 1";
        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String dbUsername = rs.getString("Username");
                String dbPassword = rs.getString("Masterpassword");
                return inputUsername.equals(dbUsername) && inputPassword.equals(dbPassword);
            }
        }
        return false;
    }
    static void handleMasterPassword(Scanner scanner, Masterpassword masterpassword) throws SQLException {
        System.out.print("Bitte gib einen Username ein: ");
        String username = scanner.nextLine().trim();

        System.out.print("Bitte gib dein Master-Passwort ein: ");
        String masterPassword = scanner.nextLine().trim();

        if (!masterpassword.isMasterPasswordStored()) {
            masterpassword.insertMasterPassword(username, masterPassword);
            System.out.println("Datensatz erfolgreich eingefügt.");
        } else {
            while (!masterpassword.checkCredentials(username, masterPassword)) {
                System.out.println("Zugangsdaten stimmen nicht überein.");
                System.out.print("Bitte gib einen Username ein: ");
                username = scanner.nextLine().trim();
                System.out.print("Bitte gib dein Master-Passwort ein: ");
                masterPassword = scanner.nextLine().trim();
            }
            System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
        }
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */