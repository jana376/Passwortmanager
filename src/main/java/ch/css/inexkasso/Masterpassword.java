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
        String sql = "SELECT 1 FROM " + TABLE + " WHERE Username = ? AND Masterpassword = ?";
        try (Connection conn = DriverManager.getConnection(URL);

             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inputUsername);
            stmt.setString(2, inputPassword);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }


    void handleMasterPassword(Scanner scanner) throws SQLException {
        String username = "";
        String masterPassword = "";
        String choice = " ";

        if (!isMasterPasswordStored()) {
            System.out.println("Noch kein Master-Passwort gespeichert. Bitte registrieren.");
        }

        System.out.print("Möchtest du dich registrieren? (y/n): ");
        choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("y")) {
            String[] credentials = promptCredentials(scanner);
            username = credentials[0];
            masterPassword = credentials[1];

            insertMasterPassword(username, masterPassword);
            System.out.println("Du hast dich erfolgreich registriert.");

        } else if (choice.equals("n")) {
            String[] credentials = promptCredentials(scanner);
            username = credentials[0];
            masterPassword = credentials[1];

            if (checkCredentials(username, masterPassword)) {
                System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
            } else {
                System.out.println("Ungültige Eingabe");
                    System.out.println("Möchtest du dich neu registrieren? (y/n)");
                credentials = promptCredentials(scanner);
                username = credentials[0];
                masterPassword = credentials[1];
                while (!checkCredentials(username, masterPassword)) {
                    String antwort = scanner.nextLine().trim();

                    if (antwort.equalsIgnoreCase("y")) {
                        insertMasterPassword(username, masterPassword);
                        System.out.println("Neuer Benutzer registriert.");
                        return;
                    } else if (antwort.equalsIgnoreCase("n")) {
                        credentials = promptCredentials(scanner);
                        username = credentials[0];
                        masterPassword = credentials[1];
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

    private String[] promptCredentials(Scanner scanner) {
        System.out.print("Bitte gib deinen Benutzernamen ein: ");
        String user = scanner.nextLine().trim();

        System.out.print("Bitte gib ein Masterpasswort ein: ");
        String pass = scanner.nextLine().trim();

        return new String[]{user, pass};
    }

}
/*
 * Username: jana123
 * Passwort: ooo.oreo
 *
 * Username: tobias123
 * Passwort: asdf
 */