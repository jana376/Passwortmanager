package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.*;
import static javax.accessibility.AccessibleRole.TABLE;

public class Masterpassword {
    public static void createTableIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            DatabaseMetaData dbMeta = conn.getMetaData();

            // Tabelle mit exaktem Namen "MasterPassword" prüfen (case-sensitive!)
            try (ResultSet rs = dbMeta.getTables(null, null, "MasterPassword", null)) {
                if (!rs.next()) {
                    String createTableSQL = "CREATE TABLE \"MasterPassword\" (" +
                            "MasterpasswordId INT PRIMARY KEY, " +
                            "Username VARCHAR(50), " +
                            "Masterpassword VARCHAR(100))";
                    try (Statement stmt = conn.createStatement()) {
                        stmt.executeUpdate(createTableSQL);
                        System.out.println("Tabelle MasterPassword wurde erstellt.");
                    }
                } else {
                    System.out.println("Tabelle MasterPassword existiert bereits.");
                }
            }
        }
    }



    public int getNextMasterpasswordId() throws SQLException {
        String sql = "SELECT MAX(MasterpasswordId) FROM \"MasterPassword\"";
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
        String username;
        String masterPassword;
        String choice;

        if (!isMasterPasswordStored()) {
            System.out.println("Noch kein Master-Passwort gespeichert. Bitte registrieren.");
        }

        while (true) {
            System.out.print("Möchtest du dich registrieren? (y/n): ");
            choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y") || choice.equals("n")) {
                break;
            }
            System.out.println("Ungültige Eingabe! Bitte gib 'y' oder 'n' ein.");
        }

        if (choice.equals("y")) {
            while (true) {
                String[] credentials = promptCredentialsSignin(scanner);
                username = credentials[0];
                String pass1 = credentials[1];
                String pass2 = credentials[2];

                if (pass1.equals(pass2)) {
                    insertMasterPassword(username, pass1);
                    System.out.println("Du hast dich erfolgreich registriert.");
                    break;
                } else {
                    System.out.println("Die erste und zweite Eingabe des Passworts stimmt nicht über ein, bitte versuche es noch einmal.");
                }
            }

        } else {
            while (true) {
                String[] credentials = promptCredentialsSignUp(scanner);
                username = credentials[0];
                masterPassword = credentials[1];

                if (checkCredentials(username, masterPassword)) {
                    System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
                    break;
                } else {
                    System.out.println("Benutzer nicht gefunden.");
                    while (true) {
                        System.out.print("Möchtest du dich neu registrieren? (y/n): ");
                        String antwort = scanner.nextLine().trim().toLowerCase();

                        if (antwort.equals("y")) {
                            insertMasterPassword(username, masterPassword);
                            System.out.println("Neuer Benutzer registriert.");
                            return;
                        } else if (antwort.equals("n")) {
                            break;
                        } else {
                            System.out.println("Ungültige Eingabe. Bitte 'y' oder 'n' eingeben.");
                        }
                    }
                }
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
        int nextId = getNextMasterpasswordId();
        String sql = "INSERT INTO \"MasterPassword\" (MasterpasswordId, Username, Masterpassword) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nextId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
    }

    private String[] promptCredentialsSignin(Scanner scanner) {
        System.out.print("Bitte gib deinen Benutzernamen ein: ");
        String user = scanner.nextLine().trim();

        System.out.print("Bitte gib ein Masterpasswort ein: ");
        String pass1 = scanner.nextLine().trim();

        System.out.print("Bitte bestätige das Masterpasswort: ");
        String pass2 = scanner.nextLine().trim();
        return new String[]{user, pass1, pass2};
    }

    private String[] promptCredentialsSignUp(Scanner scanner) {
        System.out.print("Bitte gib deinen Benutzernamen ein: ");
        String user = scanner.nextLine().trim();

        System.out.print("Bitte gib ein Masterpasswort ein: ");
        String pass1 = scanner.nextLine().trim();

        return new String[]{user, pass1};
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 *
 * Username: tobias123
 * Passwort: asdf
 */
