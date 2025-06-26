package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Masterpassword.*;


public class Main {
    private static final String URL = "jdbc:derby:testDB;create=true";
    private static final String TABLE = "MasterPassword";

    public static void main(String[] args) throws SQLException {

        String safeBefehl = "-s..";
        String listLabel =  "-list.l";
        String getPasswordandLabel = "-get.g";

Masterpassword masterpassword = new Masterpassword();
        createTableIfNotExists();


        Scanner scanner = new Scanner(System.in);
        handleMasterPassword(scanner, masterpassword);
        System.out.println("Was möchtest du machen?");
        String userInput = scanner.nextLine();

        if (userInput.equals(safeBefehl)){
            handleSavePassword(scanner);
        }else if (userInput.equals(listLabel)){
            listlabelsfuction();
        }
        scanner.close();
    }

    private static void handleSavePassword(Scanner scanner) {
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
    }

    private static void handleMasterPassword(Scanner scanner, Masterpassword masterpassword) throws SQLException {
        System.out.print("Bitte gib einen Username ein: ");
        String username = scanner.nextLine().trim();

        System.out.print("Bitte gib dein Master-Passwort ein: ");
        String masterPassword = scanner.nextLine().trim();

        if (!masterpassword.isMasterPasswordStored()) {
            masterpassword.insertMasterPassword(username, masterPassword);
            System.out.println("Datensatz erfolgreich eingefügt.");
        } else {
            while (!masterpassword.checkCredentials(username, masterPassword)) {
                System.out.println("Zugangsdaten stimmen nicht überein.");
                System.out.print("Bitte gib einen Username ein: ");
                username = scanner.nextLine().trim();
                System.out.print("Bitte gib dein Master-Passwort ein: ");
                masterPassword = scanner.nextLine().trim();
            }
            System.out.println("Hallo " + username + ", du bist erfolgreich angemeldet worden. :)");
        }
    }
    private static void listlabelsfuction() throws SQLException {
        String sql = """
                SELECT Label,Password From Password""";

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("| %-40s | %-20s |\n", "Label", "Passwort");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                String label = rs.getString("Label");
                String password = rs.getString("Password");
                System.out.printf("| %-40s | %-20s |\n", label, password);
            }

            System.out.println("-------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Passwörter: " + e.getMessage());
        }
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */