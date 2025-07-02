package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.DeleteFunction.deletePassword;
import static ch.css.inexkasso.GetPasswordFunction.getPasswordfunction;
import static ch.css.inexkasso.HelpFunction.help;
import static ch.css.inexkasso.ListFunction.listlabelsfuction;
import static ch.css.inexkasso.Masterpassword.*;
import static ch.css.inexkasso.SafeFunction.*;

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
        wasMoechtestDuMachen(scanner);
        scanner.close();
    }

    private static void wasMoechtestDuMachen(Scanner scanner) {
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
                help();
            } else if (userInput.equals(DELETE_PASSWORD_BEFEHL)) {
                deletePassword(scanner);
            } else if (userInput.equals(EXIT_PROGRAMM_BEFEHL)) {
                System.out.println("Programm wurde erfolgreich beendet.");
                break;
            } else {
                System.out.println("Unbekannter Befehl. Mit dem Befehl help kannst du alle Befehle sehen und was ihre Funktion ist.");
            }
        }
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */