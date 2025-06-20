package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL   = "jdbc:derby:testDB;create=true";
    private static final String TABLE = "MasterPassword";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Bitte gib einen Username ein: ");
            String username = scanner.nextLine().trim();

            System.out.print("Bitte gib dein Master-Passwort ein: ");
            String masterPassword = scanner.nextLine().trim();

            createTableIfNotExists();
            insertMasterPassword(username, masterPassword);

        }
    }

    // Methode zum Erstellen der Tabelle, falls sie nicht existiert
    private static void createTableIfNotExists() {
        String sql = "CREATE TABLE " + TABLE + " (" +
                "MasterpasswordId INT PRIMARY KEY, " +
                "Username VARCHAR(255), " +
                "Masterpassword VARCHAR(255))";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            // Fehlercode X0Y32 = "Table already exists" – ignoriere diesen
            if (!"X0Y32".equals(e.getSQLState())) {
                System.err.println("Fehler beim Erstellen der Tabelle: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void insertMasterPassword(String username, String masterPassword) {
        String sql = "INSERT INTO " + TABLE + " (MasterpasswordId, Username, Masterpassword) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, 1);
            ps.setString(2, username);
            ps.setString(3, masterPassword);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Daten erfolgreich eingefügt.");
            } else {
                System.out.println("Fehler beim Einfügen der Daten.");
            }

        } catch (SQLException e) {
            System.err.println("Datenbank-Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}