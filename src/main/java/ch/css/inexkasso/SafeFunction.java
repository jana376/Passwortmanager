package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.ListFunction.listlabelsfuction;
import static ch.css.inexkasso.Main.URL;


public class SafeFunction {


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
    static void handleSavePassword(Scanner scanner) {
        SafeFunction safeFunction = new SafeFunction();

        System.out.print("Speichere das Passwort unter einem Namen: ");
        String label = scanner.nextLine();

        System.out.print("Wie lautet dein Username dort?: ");
        String nameUser = scanner.nextLine();

        System.out.print("Wie lautet dein Passwort dort?: ");
        String password = scanner.nextLine();

        System.out.print("Wie heisst die Website oder Application?: ");
        String applicationwebsitee = scanner.nextLine();

        System.out.println("Danke! Die Daten wurden gespeichert.");

        safeFunction.createPasswordTableIfNotExists();
        safeFunction.savePassword(label, nameUser, password, applicationwebsitee);
        listlabelsfuction();
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */