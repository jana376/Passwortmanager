package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:derby:testDB;create=true";
    private static final String TABLE = "MasterPassword";

    public static void main(String[] args) throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Bitte gib einen Username ein: ");
            String username = scanner.nextLine().trim();

            System.out.print("Bitte gib dein Master-Passwort ein: ");
            String masterPassword = scanner.nextLine().trim();

            createTableIfNotExists();
            insertMasterPassword(username, masterPassword);
        }
    }

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
                System.err.println("Fehler beim Erstellen der Tabelle");
            }
        }
    }

    private static void insertMasterPassword(String username, String masterPassword) throws SQLException {

        try (Connection conn = DriverManager.getConnection(URL)) {
            String checkSql = "SELECT COUNT(*) FROM MasterPassword WHERE MasterpasswordId = 1";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                 ResultSet rs = checkStmt.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    String insertSql = "INSERT INTO MasterPassword (MasterpasswordId, Username, Masterpassword) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, 1);
                        insertStmt.setString(2, username);
                        insertStmt.setString(3, masterPassword);
                        insertStmt.executeUpdate();
                        System.out.println("Datensatz erfolgreich eingefügt.");
                        String selectSql = "SELECT * FROM MasterPassword WHERE MasterpasswordId = 1";
                        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                            ResultSet rs2 = selectStmt.executeQuery();
                            System.out.println(rs2.getString("Username"));
                            System.out.println(rs2.getString("Masterpassword"));

                        }
                    }
                } else {
                    System.out.println("Das Masterpasswort existiert bereits. Änderungen sind nicht erlaubt.");
                }
            }
        }
    }
}