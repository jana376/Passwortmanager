CREATE TABLE Password(
    PasswordID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
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
    Behaviour varchar(500)
);

INSERT INTO cmd(cmdID, possiblecommands, behaviour) VALUES (1,'-s..', 'Speichert das Passwort und die dazugehörigen Angeben z.B. Label, etc.');
INSERT INTO cmd(cmdid, possiblecommands, behaviour) VALUES (2,'-list.l', 'Listet alle Labels mit den dazugehörigen Passworts auf.');
INSERT INTO cmd(cmdid, possiblecommands, behaviour) VALUES (3,'-get.g', 'Das zugehörige Label hinschreiben, und das Passwort wird mir angezeigt.');
INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (4,'help', 'Gebe alle Befehle aus, die es gibt und bekomme eine Erklärung.');
INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (5,'-delete.d', 'Lösche ein Passwort');
INSERT INTO cmd(cmdID, possiblecommands, Behaviour) VALUES (6,'exit', 'Verlasse das Programm.');
INSERT into cmd(cmdID, possiblecommands, Behaviour) VALUES (7, 'b.z', 'Zurück zum Auswahlprogramm');



SELECT * FROM MasterPassword;
