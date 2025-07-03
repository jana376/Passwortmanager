package ch.css.inexkasso;

public class SQLStrings {
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
