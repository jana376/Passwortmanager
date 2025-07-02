package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.GetPasswordFunction.getPasswordfunction;
import static ch.css.inexkasso.ListFunction.listlabelsfuction;
import static ch.css.inexkasso.Masterpassword.*;


public class Main {
     public static final String URL = "jdbc:derby:testDB;create=true";
    public static final String SAFE_BEFEHL = "-s..";
    public static final String LIST_LABEL_BEFEHL = "-list.l";
    public static final String GET_PASSWORDWITHLABEL_BEFEHL = "-get.g";
    public static final String HELP_USER_BEFEHL = "help";
    public static final String DELETE_PASSWORD_BEFEHL = "-delete.d";
    public static final String EXIT_PROGRAMM_BEFEHL = "exit";

    public static void main(String[] args) throws SQLException {

        Masterpassword masterpassword = new Masterpassword();
        createTableIfNotExists();

        Scanner scanner = new Scanner(System.in);
        handleMasterPassword(scanner, masterpassword);
        String userInput;


        while (true) {
            System.out.print("Was möchtest du machen? ");
            userInput = scanner.nextLine();
            if (userInput.equals(SAFE_BEFEHL)) {
                handleSavePassword(scanner);
            } else if (userInput.equals(LIST_LABEL_BEFEHL)) {
                listlabelsfuction();
            } else if (userInput.equals(GET_PASSWORDWITHLABEL_BEFEHL)) {
                System.out.print("Welches ist das Label, dessen Passwort du ausgeben willst?: ");
                String label = scanner.nextLine();
                getPasswordfunction(label);
            } else if (userInput.equals(HELP_USER_BEFEHL)) {
                HelpFunction.help();
            } else if (userInput.equals(DELETE_PASSWORD_BEFEHL)) {
                DeleteFunction.deletePassword(scanner);
            } else if (userInput.equals(EXIT_PROGRAMM_BEFEHL)) {
                System.out.println("Programm wurde erfolgreich beendet.");
                break;
            } else {
                System.out.println("Unbekannter Befehl. Mit dem Befehl help kannst du alle Befehle sehen und was ihre Funktion ist.");
            }
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
        listlabelsfuction();
    }

}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */