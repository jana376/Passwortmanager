CREATE Database Passwrotmanagerdb;

CREATE TABLE Password(
    PasswordID int PRIMARY KEY Not null,
    Label varchar(50) not null,
    Password varchar(100) not null,
    ApplicationWebsite varchar(50),
    NameUser varchar(50)
);

CREATE TABLE MasterPassword(
    MasterpasswordId int PRIMARY KEY not null,
    Username varchar(50),
    Masterpassword varchar(100)
);

CREATE TABLE cmd(
    cmdID int primary key not null,
    possiblecommands varchar(15),
    Behaviour varchar(50)
);