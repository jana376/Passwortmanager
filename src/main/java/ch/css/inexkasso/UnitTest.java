package ch.css.inexkasso;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.testng.AssertJUnit.assertTrue;

public class UnitTest {

    @Test
    public void testHelpPrintsExpectedOutput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HelpFunction.help();

        String output = outContent.toString();
        System.setOut(System.out);
        assertTrue(output.contains("| list                                | Listet alle Labels mit den dazugehörigen Passwörtern auf."));
        assertTrue(output.contains("| get                                 | Zeigt das Passwort zu einem gegebenen Label an."));
        assertTrue(output.contains("| delete                              | Löscht ein Passwort."));
    }

    @Test
    public void testListFunction() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ListFunction.listlabelsfuction();
        String output = outContent.toString();
        System.setOut(System.out);

        assertTrue(output.contains("| Label"));
        assertTrue(output.contains("| laptop passwort                          | ola_die_Waldfee187"));
        assertTrue(output.contains("| Passwortmanager Masterpasswort           | ooo.oreo"));
        assertTrue(output.contains("| test1                                    | test2"));
        assertTrue(output.contains("--------------------------------------------------------------------------------------------------------------------------------------"));
    }
}
