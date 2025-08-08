package ch.css.inexkasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Setup {
    public static void main(String[] args) {
        String sql = "INSERT INTO COMMANDS (cmdID, possiblecommands, behaviour) VALUES (?, ?, ?)";

        String[][] data = {
                {"1", "safe", "Speichert das Passwort und die dazugehörigen Angaben z.B. Label, etc."},
                {"2", "list", "Listet alle Labels mit den dazugehörigen Passwörtern auf."},
                {"3", "get", "Das zugehörige Label hinschreiben, und das Passwort wird angezeigt."},
                {"4", "help", "Gibt alle Befehle aus und erklärt sie."},
                {"5", "delete", "Löscht ein Passwort."},
                {"6", "exit", "Verlässt das Programm."},
                {"7", "b.z", "Zurück zum Auswahlprogramm."}
        };

        try (Connection conn = DriverManager.getConnection("jdbc:derby:C:/temp/passwortmanagerdb;create=true");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (String[] row : data) {
                stmt.setInt(1, Integer.parseInt(row[0]));
                stmt.setString(2, row[1]);
                stmt.setString(3, row[2]);
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("Tabelle COMMANDS erfolgreich befüllt.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

