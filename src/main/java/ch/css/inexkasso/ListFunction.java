package ch.css.inexkasso;

import java.sql.*;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.SQL_LIST;
import static ch.css.inexkasso.Constant.URL;

public class ListFunction {



    static void listlabelsfuction() {

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_LIST)) {

            System.out.printf("| %-40s | %-20s | %-40s | %-20s |\n", "Label", "Passwort", "ApplicationWebsite", "Username");

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                String label = rs.getString("Label");
                String password = rs.getString("Password");
                String applicationWebsite = rs.getString("ApplicationWebsite");
                String name = rs.getString("NameUser");
                System.out.printf("| %-40s | %-20s | %-40s | %-20s |\n", label, cryptoPassword.decrypt(cryptoPassword.encrypt(password)), applicationWebsite, name);
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