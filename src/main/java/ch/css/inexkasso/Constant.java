package ch.css.inexkasso;

public class Constant {
    public static final String URL = "jdbc:derby:testDB;create=true";
    public static final String SAFE_COMMAND = "safe";
    public static final String LIST_LABEL_COMMAND = "list";
    public static final String GET_PASSWORD_WITH_LABEL_COMMAND = "get";
    public static final String HELP_USER_COMMAND = "help";
    public static final String DELETE_PASSWORD_COMMAND = "delete";
    public static final String EXIT_PROGRAMM_COMMAND = "exit";
    public static final String SQL_GET = "SELECT Password FROM Password WHERE Label = ?";
    public static final String SQL_LIST = """ 
            SELECT Label,Password, ApplicationWebsite,NameUser From Password""";
    public static final String SQL_SAFE = "INSERT INTO Password (Label, Password, ApplicationWebsite, NameUser) VALUES (?, ?, ?, ?)";
    public static final String SQL_NON_EXIST_TABLE = """
            CREATE TABLE Password(
            PasswordId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
            Label VARCHAR(255),
            Password VARCHAR(255),
            ApplicationWebsite VARCHAR(255),
            NameUser VARCHAR(255)
            )""";
    public final String TABLE = "MasterPassword";

}
