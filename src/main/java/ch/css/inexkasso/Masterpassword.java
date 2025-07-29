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

    public void insertMasterPassword(String username, String password) throws SQLException {
        int nextId = getNextMasterpasswordId();
        String sql = "INSERT INTO Masterpassword (MasterpasswordId, Username, Masterpassword) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nextId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
    }

    public int getNextMasterpasswordId() throws SQLException {
        String sql = "SELECT MAX(MasterpasswordId) FROM Masterpassword";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1; // Erste ID
            }
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

    void handleMasterPassword(Scanner scanner) throws SQLException {
        System.out.print("Bitte gib einen Username ein: ");
        String username = scanner.nextLine().trim();

        System.out.print("Bitte gib dein Master-Passwort ein: ");
        String masterPassword = scanner.nextLine().trim();

        if (!isMasterPasswordStored()) {
            insertMasterPassword(username, masterPassword);
            System.out.println("Datensatz erfolgreich eingefügt.");
        } else {
            if (checkCredentials(username, masterPassword)) {
                System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
            } else {
                while (!checkCredentials(username, masterPassword)) {
                    System.out.println("Unbekannter Benutzer – möchtest du dich neu registrieren? (Ja/Nein)");
                    String antwort = scanner.nextLine().trim();

                    if (antwort.equalsIgnoreCase("Ja")) {
                        insertMasterPassword(username, masterPassword);
                        System.out.println("Neuer Benutzer registriert.");
                        return;
                    } else if (antwort.equalsIgnoreCase("Nein")) {
                        System.out.print("Bitte gib einen Username ein: ");
                        username = scanner.nextLine().trim();

                        System.out.print("Bitte gib dein Master-Passwort ein: ");
                        masterPassword = scanner.nextLine().trim();
                    }
                }

                System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
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

    void deleteAllPasswords() throws SQLException {
        String sql = "DELETE FROM Password";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

}
/*
 * Username: jana123
 * Passwort: ooo.oreo
 */