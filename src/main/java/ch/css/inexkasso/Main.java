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
        handleMasterPassword(scanner, masterpassword);
        help();
        askWhatUserWantsToDo(scanner);
        scanner.close();
    }

    private static void askWhatUserWantsToDo(Scanner scanner) {
        String userInput;
        while (true) {
            System.out.print("Was möchtest du machen? ");
            userInput = scanner.nextLine();
            if (userInput.equals(SAFE_COMMAND)) {
                handleSavePassword(scanner);
            } else if (userInput.equals(LIST_LABEL_COMMAND)) {
                listlabelsfuction();
            } else if (userInput.equals(GET_PASSWORD_WITH_LABEL_COMMAND)) {
                System.out.print("Welches ist das Label, dessen Passwort du ausgeben willst?: ");
                String label = scanner.nextLine();
                getPasswordfunction(label);
            } else if (userInput.equals(HELP_USER_COMMAND)) {
                help();
            } else if (userInput.equals(DELETE_PASSWORD_COMMAND)) {
                deletePassword(scanner);
            } else if (userInput.equals(EXIT_PROGRAMM_COMMAND)) {
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