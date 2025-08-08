# Passwortmanager

Als Abschlussprojekt des Semesters darf ich ein Passwortmanager machen, der Passwörter speichern, ausgeben und löschen kann. Die Passwörter werden verschlüsselt und in einer Datenbank gespeichert.


## Getting Started

Mit dieser Anleitung, bekommen Sie gezielte Anweisungen, um den Passwortmanager lokal auf ihrem Rechner installieren zu können.

### Vorraussetzungen

Die Vorraussetzungen um den Passwortmanager benutzen zu können sind: 

- IntelliJ IDEA Ultimate, https://www.jetbrains.com/idea/download/?section=windows
- JDK 23: Windows, https://download.oracle.com/java/23/archive/jdk-23.0.2_windows-x64_bin.zip
- JDK 23: macOS, https://download.oracle.com/java/23/archive/jdk-23.0.2_macos-x64_bin.tar.gz
- Jakarta EE, https://jakarta.ee/release/10/
- MySQL, https://dev.mysql.com/downloads/installer/
- Maven installiert und im Path haben, https://maven.apache.org/download.cgi
  

### Installieren

#### Möglichkeit 1:

Um den Passwortmanager auf ihrem lokalen Gerät abszuspielen, müssen Sie nur die ZIP Datei extrahieren und dann können Sie los legen.

1. Öffnen Sie Ihr Terminal
2. Wechseln Sie zu der Datei wo der Passwortmanager drin gespeichert ist
3. z.B.
   
       cd C:\temp\Passwortmanager
5. dann geben Sie folgendes ein:
   
       mvn clean compile

6. und dann:

        mvn exec:java -Dexec.mainClass="ch.css.inexkasso.Main"
            


#### Möglichkeit 2:

Man kann auch einfach dieses Repository klonen und in Intellij ausführen.

1. Main starten

#### Registrieren
1. Wenn es das erste Mal ist, wo man den passwort manager benutzt, dann wird man gefragt ob man sich registrieren möchte
2. 'y' eingeben und die ENTER-Taste drücken.
4. Username eingeben ->  Enter drücken
5. Passwort gefragt -> Enter drücken
6. Wenn man fertig ist mit eingeben, einfach wieder ENTER-Taste drücken

#### Anmelden

Wenn man schon registriert ist und man sich anmelden möchte:
1. gibt man 'n' ein, wenn gefragt wird ob man sich registrieren möchte
2. Dann gibt man seine Anmeldedaten ein
3. Falls die Daten falsch eingegeben werden, dann geht es wieder von vorne los

Nachdem man sich registriert/angemeldet hat, wir eine Tabelle eingezeigt, in der stehen die Befehle die zur Verfügung stehen

WICHTIG: Das Passwort wird verschlüsselt und es ist nicht mehr änderbar. Wenn man es vergisst sind die Daten verloren.

----------------------------------------------------------------------------------------------------------------
  
### Befehle und Aktionen:

- safe   ->  Speichert das Passwort und die dazugehörigen Angaben z.B. Label, etc.  

- list   ->  Listet alle Labels mit den dazugehörigen Passwörtern auf.              

- get    ->  Zeigt das Passwort zu einem gegebenen Label an.                        

- help   ->  Gibt alle Befehle aus und erklärt sie.                                 

- delete ->  Löscht ein Passwort.                                                   

- exit   ->  Beendet das Programm.                                                  

Nach jeder fertigen Eingabe muss die Entertaste gedrückt werden.

ACTUNG: Leerzeichen werden bei eingegebenen Befehlen beachtet, das heisst wenn man "safe " eingibt, wird der Befehl nicht erkannt.


---
### Geld spenden

Bitte schick mir Geld, weil Lehrlingslohn.... Einfach auf das Bild klicken und Sie werden auf Paypal weiter geleitet

<a href="https://www.paypal.com/ch/digital-wallet/send-receive-money/send-money" target="_blank"><img src="https://i.ytimg.com/vi/KMdjT9RTgqw/hqdefault.jpg" alt="Schick mir bitte Geld." style="height: auto !important;width: auto !important;" ></a>

---
