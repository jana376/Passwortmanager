package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Masterpassword.createTableIfNotExists;


public class ListFunction {
    private static final String URL = "jdbc:derby:testDB;create=true";
    private static final String TABLE = "MasterPassword";

    static void listlabelsfuction() {

        String sql = """
                SELECT Label,Password, ApplicationWebsite,NameUser From Password""";

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("| %-40s | %-20s | %-40s | %-20s |\n", "Label", "Passwort", "ApplicationWebsite", "Username");

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                String label = rs.getString("Label");
                String password = rs.getString("Password");
                String applicationWebsite = rs.getString("ApplicationWebsite");
                String name = rs.getString("NameUser");
                System.out.printf("| %-40s | %-20s | %-40s | %-20s |\n", label, password, applicationWebsite, name);
            }

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Passwörter");
        }
    }

}

/*
 * Username: jana123
 * Passwort: ooo.oreo
 */