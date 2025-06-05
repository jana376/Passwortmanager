package ch.css.inexkasso;

import java.sql.Connection;
import java.sql.DriverManager;

public class DerbyTest {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:testDB;create=true")) {
            System.out.println("Verbindung erfolgreich!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
