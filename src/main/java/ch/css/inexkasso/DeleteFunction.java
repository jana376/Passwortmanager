package ch.css.inexkasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import static ch.css.inexkasso.Main.URL;
public class DeleteFunction {
    public static void deletePassword(Scanner scanner) {
        System.out.print("Welches Passwort möchtest du löschen? ");
        String passwordToDelete = scanner.nextLine();

        String sql = "DELETE FROM Password WHERE Password = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, passwordToDelete);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Passwort erfolgreich gelöscht.");
            } else {
                System.out.println("Kein Eintrag mit diesem Passwort gefunden.");
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des Passworts: " + e.getMessage());
        }
    }
}
