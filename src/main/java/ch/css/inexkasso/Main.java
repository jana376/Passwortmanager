package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.GetPasswordFunction.getPasswordfunction;
import static ch.css.inexkasso.ListFunction.listlabelsfuction;
import static ch.css.inexkasso.Masterpassword.*;


public class Main {
    private static final String URL = "jdbc:derby:testDB;create=true";
    private static final String TABLE = "MasterPassword";

    public static void main(String[] args) throws SQLException {

        String safeBefehl = "-s..";
        String listLabel = "-list.l";
        String getPasswordwithlabel = "-get.g";
        String helpUser = "help";
        String deletePassword = "-delete.d";
        String exitProgramm = "exit";

        Masterpassword masterpassword = new Masterpassword();
        createTableIfNotExists();

        Scanner scanner = new Scanner(System.in);
        handleMasterPassword(scanner, masterpassword);
        System.out.print("Was möchtest du machen?");
        String userInput = scanner.nextLine();


         if (userInput.equals(safeBefehl)) {
                handleSavePassword(scanner);
            } else if (userInput.equals(listLabel)) {
                listlabelsfuction();
            } else if (userInput.equals(getPasswordwithlabel)) {
                System.out.print("Welches ist das Label, dessen Passwort du ausgeben willst?: ");
                userInput = scanner.nextLine();
                getPasswordfunction(userInput);
            } else if (userInput.equals(helpUser)) {
             help();
            } else if (userInput.equals(deletePassword)) {

            }else if(userInput.equals(exitProgramm)) {
             System.out.println("Programm wurde erfolgreich beendet.");
         }
        scanner.close();
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

    private static void handleSavePassword(Scanner scanner) throws SQLException {
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
    /*
    public static void createCMDTableIfNotExists() {
        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()) {

            String createTableSQL = """
        CREATE TABLE cmd(
            cmdID INT PRIMARY KEY NOT NULL,
            possiblecommands VARCHAR(15),
            Behaviour VARCHAR(500)
        )
        """;
            stmt.executeUpdate(createTableSQL);

            stmt.executeUpdate("INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (1,'-s..', 'Speichert das Passwort und die dazugehörigen Angaben z.B. Label, etc.')");
            stmt.executeUpdate("INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (2,'-list.l', 'Listet alle Labels mit den dazugehörigen Passwörtern auf.')");
            stmt.executeUpdate("INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (3,'-get.g', 'Das zugehörige Label hinschreiben, und das Passwort wird mir angezeigt.')");
            stmt.executeUpdate("INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (4,'help', 'Gebe alle Befehle aus, die es gibt und bekomme eine Erklärung.')");
            stmt.executeUpdate("INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (5,'-delete.d', 'Lösche ein Passwort')");
            stmt.executeUpdate("INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (6,'exit', 'Verlasse das Programm.')");

        } catch (SQLException e) {
            System.err.println("SQL-Fehler: " + e.getMessage());
        }
    }
*/

    private static void help() {
        String sql = "SELECT possiblecommands, behaviour FROM cmd";
        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("| %-40s | %-75s |\n", "possiblecommands", "behaviour");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                String possiblecommands = rs.getString("possiblecommands");
                String behaviour = rs.getString("behaviour");
                System.out.printf("| %-40s | %-75s |\n", possiblecommands, behaviour);
            }

            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Hilfeseite"+ e.getMessage());
        }

    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */