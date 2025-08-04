package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.*;
import static ch.css.inexkasso.DeleteFunction.deletePassword;
import static ch.css.inexkasso.GetPasswordFunction.getPasswordfunction;
import static ch.css.inexkasso.HelpFunction.help;
import static ch.css.inexkasso.ListFunction.listlabelsfuction;
import static ch.css.inexkasso.Masterpassword.*;
import static ch.css.inexkasso.SafeFunction.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        Masterpassword masterpassword = new Masterpassword();
        createTableIfNotExists();

        Scanner scanner = new Scanner(System.in);
        masterpassword.handleMasterPassword(scanner);
        help();
        askWhatUserWantsToDo(scanner);
        scanner.close();
    }

    private static void askWhatUserWantsToDo(Scanner scanner) {
        String userInput;
        label:
        while (true) {
            System.out.print("Was möchtest du machen? ");
            userInput = scanner.nextLine().trim();
            switch (userInput) {
                case SAFE_COMMAND:
                    handleSavePassword(scanner);
                    break;
                case LIST_LABEL_COMMAND:
                    listlabelsfuction();
                    break;
                case GET_PASSWORD_WITH_LABEL_COMMAND:
                    System.out.print("Welches ist das Label, dessen Passwort du ausgeben willst?: ");
                    String label = scanner.nextLine().trim();
                    getPasswordfunction(label);
                    break;
                case HELP_USER_COMMAND:
                    help();
                    break;
                case DELETE_PASSWORD_COMMAND:
                    deletePassword(scanner);
                    break;
                case EXIT_PROGRAMM_COMMAND:
                    System.out.println("Programm wurde erfolgreich beendet.");
                    break label;
                default:
                    System.out.println("Unbekannter Befehl. Mit dem Befehl help kannst du alle Befehle sehen und was ihre Funktion ist.");
                    break;
            }
        }
    }
}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 *
 * Username: tobias123
 * Passwort: asdf
 */
