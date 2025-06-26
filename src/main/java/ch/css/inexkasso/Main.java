package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Masterpassword.*;

public class Main {
    private static final String URL = "jdbc:derby:testDB;create=true";
    private static final String TABLE = "MasterPassword";

    public static void main(String[] args) throws SQLException {
Masterpassword masterpassword = new Masterpassword();

        createTableIfNotExists();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Bitte gib einen Username ein: ");
        String username = scanner.nextLine().trim();

        System.out.print("Bitte gib dein Master-Passwort ein: ");
        String masterPassword = scanner.nextLine().trim();

        if (!isMasterPasswordStored()) {
            insertMasterPassword(username, masterPassword);
            System.out.println("Datensatz erfolgreich eingefügt.");
        } else {
            while (!credentialsMatch(username, masterPassword)) {
                System.out.println("Zugangsdaten stimmen nicht überein.");
                System.out.print("Bitte gib einen Username ein: ");
                username = scanner.nextLine().trim();
                System.out.print("Bitte gib dein Master-Passwort ein: ");
                masterPassword = scanner.nextLine().trim();
            }
            System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
        }

        System.out.print("Speichere das Passwort unter einem Namen: ");
        String label = scanner.nextLine();

        System.out.print("Wie lautet dein Username dort?: ");
        String nameUser = scanner.nextLine();

        System.out.print("Wie lautet dein Passwort dort?: ");
        String password = scanner.nextLine();

        System.out.print("Wie heisst die Website oder Application?: ");
        String applicationwebsitee = scanner.nextLine();

        System.out.println("Danke! Die Daten wurden gespeichert.");

        createPasswordTableIfNotExists();
        savePassword(label, nameUser, password, applicationwebsitee);

        scanner.close();
    }


    private static void createPasswordTableIfNotExists() {
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

    private static void savePassword(String label, String nameUser, String password, String applicationwebsitee) {
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