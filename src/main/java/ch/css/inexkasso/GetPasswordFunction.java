package ch.css.inexkasso;

import java.sql.*;

import static ch.css.inexkasso.Constant.URL;
public class GetPasswordFunction {


    static void getPasswordfunction(String userInput) {
        String sql = "SELECT Password FROM Password WHERE Label = ?";

        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userInput);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String password = rs.getString("Password");
                String encrypt = cryptoPassword.encrypt(password);
                String decrypt = cryptoPassword.decrypt(encrypt);
                System.out.println("Das Passwort für Label \"" + userInput + "\" ist: " + decrypt);
            } else {
                System.out.println("Kein Passwort für das Label \"" + userInput + "\" gefunden.");
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen des Passworts: " + e.getMessage());
        }
    }
}
