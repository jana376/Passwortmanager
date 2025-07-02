package ch.css.inexkasso;

import java.sql.*;

public class GetPasswordFunction {
    private static final String URL = "jdbc:derby:testDB;create=true";

    static void getPasswordfunction(String userInput) throws SQLException {
        String sql = "SELECT Password FROM Password WHERE Label = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userInput);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String password = rs.getString("Password");
                System.out.println("Das Passwort für Label \"" + userInput + "\" ist: " + password);
            } else {
                System.out.println("Kein Passwort für das Label \"" + userInput + "\" gefunden.");
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen des Passworts: " + e.getMessage());
        }
    }
}
