package ch.css.inexkasso;

import java.sql.*;

import static ch.css.inexkasso.Main.URL;

public class Masterpassword {

    private static final String TABLE = "MasterPassword";


    public static void createTableIfNotExists() {
        String sql = "CREATE TABLE " + TABLE + " (" +
                "MasterpasswordId INT PRIMARY KEY, " +
                "Username VARCHAR(255), " +
                "Masterpassword VARCHAR(255))";
        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
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

}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */