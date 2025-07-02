package ch.css.inexkasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static ch.css.inexkasso.Constant.URL;

public class DeleteFunction {
    public static void deletePassword(Scanner scanner) {
        System.out.print("Welches Passwort möchtest du löschen? ");
        String passwordToDelete = scanner.nextLine();
        try {
            if (deletePasswordInDatabase(passwordToDelete)) {
                System.out.println("Passwort erfolgreich gelöscht.");
            } else {
                System.out.println("Kein Eintrag mit diesem Passwort gefunden.");
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des Passworts: " + e.getMessage());
        }
    }

    public static boolean deletePasswordInDatabase(String passwordLabel) throws SQLException {
        String sql = "DELETE FROM Password WHERE Password = ?";
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, passwordLabel);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }
}
