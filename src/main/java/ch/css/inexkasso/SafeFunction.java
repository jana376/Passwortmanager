package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.*;
import static ch.css.inexkasso.ListFunction.listlabelsfuction;
import static ch.css.inexkasso.SQLStrings.SQL_NON_EXIST_TABLE;
import static ch.css.inexkasso.SQLStrings.SQL_SAFE;


public class SafeFunction {
    public void createPasswordTableIfNotExists() {

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL_NON_EXIST_TABLE);
        } catch (SQLException e) {
            e.getSQLState();
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
        savePassword(label, nameUser, password, applicationwebsitee);
        listlabelsfuction();
    }

    public static void savePassword(String label, String nameUser, String password, String applicationwebsitee) {
        try (Connection conn = DriverManager.getConnection(URL); PreparedStatement ps = conn.prepareStatement(SQL_SAFE)) {

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