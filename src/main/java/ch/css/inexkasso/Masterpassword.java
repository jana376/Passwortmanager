package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.URL;

public class Masterpassword {
    private String currentUsername;

    public static void createTableIfNotExists() {
        String SQL_CREATE_TABLE_MASTERPASSWORD = "CREATE TABLE Masterpassword (" +
                "MasterpasswordId INT PRIMARY KEY, " +
                "Username VARCHAR(255), " +
                "Masterpassword VARCHAR(255))";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL_CREATE_TABLE_MASTERPASSWORD);
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) {
                System.err.println("Fehler beim Erstellen der Tabelle");
                e.printStackTrace();
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
                return 1;
            }
        }
    }

    public boolean checkCredentials(String inputUsername, String inputPassword) throws SQLException {
        String sql = "SELECT * FROM Masterpassword WHERE Username = ? AND Masterpassword = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inputUsername);
            stmt.setString(2, inputPassword);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void handleMasterPassword(Scanner scanner) throws SQLException {
        System.out.print("Bitte gib einen Username ein: ");
        String username = scanner.nextLine().trim();

        System.out.print("Bitte gib dein Master-Passwort ein: ");
        String masterPassword = scanner.nextLine().trim();

        if (checkCredentials(username, masterPassword)) {
            System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
            this.currentUsername = username;
        } else {
            System.out.println("Benutzer nicht gefunden. Möchtest du dich neu registrieren? (Ja/Nein)");
            String antwort = scanner.nextLine().trim();

            if (antwort.equalsIgnoreCase("Ja")) {
                insertMasterPassword(username, masterPassword);
                this.currentUsername = username;
                System.out.println("Neuer Benutzer registriert.");
            } else {
                System.out.println("Anmeldung abgebrochen.");
                this.currentUsername = null;
            }
        }
    }

    // Passwörter speichern
    public void savePassword(String label, String password, String website) throws SQLException {
        if (currentUsername == null) {
            System.out.println("Kein Benutzer angemeldet.");
            return;
        }

        String sql = "INSERT INTO Password (Label, Password, ApplicationWebsite, NameUser) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, label);
            stmt.setString(2, password);
            stmt.setString(3, website);
            stmt.setString(4, currentUsername);
            stmt.executeUpdate();
            System.out.println("Passwort gespeichert.");
        }
    }

    // Nur Passwörter des aktuell eingeloggten Benutzers anzeigen
    public void showPasswordsForUser() throws SQLException {
        if (currentUsername == null) {
            System.out.println("Kein Benutzer angemeldet.");
            return;
        }

        String sql = "SELECT Label, Password, ApplicationWebsite FROM Password WHERE NameUser = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, currentUsername);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nGespeicherte Passwörter für Benutzer: " + currentUsername);
            while (rs.next()) {
                System.out.println("Label: " + rs.getString("Label"));
                System.out.println("Passwort: " + rs.getString("Password"));
                System.out.println("Website/App: " + rs.getString("ApplicationWebsite"));
                System.out.println("-----");
            }
        }
    }

    public String getCurrentUsername() {
        return currentUsername;
    }
}


/*
 * Username: jana123
 * Passwort: ooo.oreo
 */