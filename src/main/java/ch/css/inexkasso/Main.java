package ch.css.inexkasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        String urlDatenbank = "jdbc:derby:testDB;create=true";
        String user = "root";
        String password = "";


        System.out.println("Bitte gebe ein Master Password ein, dass gibst du bei jeder neuen Anmeldung an. Es kann nicht mehr geändert werden.");

        System.out.print("Bitte gib einen Username ein: ");
        Scanner eingabeusername = new Scanner(System.in);
        String username = eingabeusername.nextLine();

        System.out.print("Bitte gib dein Passwort ein: ");
        Scanner eingabepasswort = new Scanner(System.in);
        String masterpasswort = eingabepasswort.nextLine();

        System.out.println(username + masterpasswort);

        try (Connection connection = DriverManager.getConnection(urlDatenbank, user, password)) {

            String sqlInsertStatement = "insert into Passwrotmanagerdb.MasterPassword(MasterpasswordId, Username, Masterpassword) values(1,username,masterpasswort)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertStatement)) {
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, masterpasswort);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Daten erfolgreich eingefügt.");
                } else {
                    System.out.println("Fehler beim Einfügen der Daten.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Herstellen der Datenbankverbindung: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
