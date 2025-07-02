package ch.css.inexkasso;

import java.sql.*;
import static ch.css.inexkasso.Main.URL;


public class SafeFunction {
    private static final String TABLE = "MasterPassword";


    public void createPasswordTableIfNotExists() {
        String sql = """
                CREATE TABLE Password(
                PasswordId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                Label VARCHAR(255),
                Password VARCHAR(255),
                ApplicationWebsite VARCHAR(255),
                NameUser VARCHAR(255)
                )""";

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void savePassword(String label, String nameUser, String password, String applicationwebsitee) {
        String sql = "INSERT INTO Password (Label, Password, ApplicationWebsite, NameUser) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, label);
            ps.setString(2, password);
            ps.setString(3, applicationwebsitee);
            ps.setString(4, nameUser);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Passwort wurde erfolgreich gespeichert.");
            } else {
                System.out.println("Fehler beim Speichern des Passworts.");
            }

        } catch (SQLException e) {
            System.err.println("Fehler bei Datenbankverbindung");

        }
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */