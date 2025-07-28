package ch.css.inexkasso;

import java.sql.*;

import static ch.css.inexkasso.Constant.URL;
import static ch.css.inexkasso.SQLStrings.SQL_LIST;

public class ListFunction {
    static void listlabelsfuction() {

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_LIST)) {
            passwortOutputTable(rs);
        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Labels");
        }
    }

    private static void passwortOutputTable(ResultSet rs) throws SQLException {
        System.out.printf("| %-40s | %-20s | %-40s | %-20s |\n", "Label", "ApplicationWebsite", "Username", "Password");

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        while (rs.next()) {
            String label = rs.getString("Label");
            String applicationWebsite = rs.getString("ApplicationWebsite");
            String name = rs.getString("NameUser");
            String password = rs.getString("Password");
            System.out.printf("| %-40s | %-20s | %-40s | %-20s |\n", label, applicationWebsite, name, password);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }
}
