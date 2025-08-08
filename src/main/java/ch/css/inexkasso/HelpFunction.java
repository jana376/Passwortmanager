package ch.css.inexkasso;

import java.sql.*;

import static ch.css.inexkasso.Constant.URL;

public class HelpFunction {
    public static void help() {
        String tableName = "COMMANDS";
        String sql = "SELECT POSSIBLECOMMANDS, BEHAVIOUR FROM " + tableName;

        try (Connection conn = DriverManager.getConnection(URL)) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                System.out.printf("| %-35s | %-70s |\n", "possiblecommands", "behaviour");
                System.out.println("----------------------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    String command = rs.getString("POSSIBLECOMMANDS");
                    String behaviour = rs.getString("BEHAVIOUR");
                    System.out.printf("| %-35s | %-70s |\n", command, behaviour);
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der Hilfeseite: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
