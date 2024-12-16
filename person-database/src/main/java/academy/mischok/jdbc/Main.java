package academy.mischok.jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;



public class Main {
    // Verbindung zur Datenbank
    public final static String SERVER = "ep-billowing-sea-a2j0bcas.eu-central-1.aws.neon.tech/DanVikDatabase?";
    public final static String DATABASE = "DanVikDatabase";
    public final static String USER = "DanVikDatabase_owner";
    public final static String PASSWORD = "Ml56LASTBPHc";
    // Farben
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        // Herstellen einer Datenbankverbindung zur Verwendung in allen nachfolgenden Aufrufen
        // try-catch Block um Fehler abzufangen, z.B. Zugriff auf die Datenbank
        try (Connection connection = ConnectionHelper.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            // Endlosschleife für die Menüanzeige
            while (true) {
                System.out.println("L: Alle Personen anzeigen");
                System.out.println("A: Person hinzufügen");
                System.out.println("E: Person bearbeiten");
                System.out.println("D: Person löschen");
                System.out.println("F: Nach Nachname filtern");
                System.out.println("P: Nach beliebigen Feldern filtern");
                System.out.println("S: Personen sortieren");
                System.out.println("Q: Beenden");
                System.out.println("Bitte wählen!");
                String wahl = scanner.next();
                // Verarbeiten der Auswahl
                switch (wahl.toUpperCase()) {
                    case "L":
                        allePersonenAnzeigen(connection);
                        break;
                    case "A":
                        personHinzufuegen(connection, scanner);
                        break;
                    case "E":
                        personBearbeiten(connection, scanner);
                        break;
                    case "D":
                        personLoeschen(connection, scanner);
                        break;
                    case "F":
                        nachnameFiltern(connection, scanner);
                        break;
                    case "P":
                        beliebigeFelderFiltern(connection, scanner);
                        break;
                    case "S":
                        personenSortieren(connection, scanner);
                        break;
                    case "Q":
                        System.out.println("Programm beendet.");
                        return;
                    // Fehlermeldung
                    default:
                        System.out.println(YELLOW + "Ungültige Wahl. Bitte versuchen Sie es erneut." + RESET);
                }
            }
            // SQL Fehler abfangen
        } catch (SQLException e) {
            // Ausgabe Details SQL Fehler
            e.printStackTrace();
        }
    }

    // Methode komplette Tabelle ausgeben
    private static void allePersonenAnzeigen(Connection connection) throws SQLException {
        // SQL Abfrage um die Personendatenbank-Tabelle anzuzeigen
        String query = "SELECT * FROM person ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Ausgabe in einer Tabelle
            zeichneHeader();
            // Schleife durch die Ergebnisse der SQL Abfrage
            while (rs.next()) {
                // Alle Werte der Personen ausgeben
                zeichneZeile(rs);
            }
            zeichneFooter();
        }
    }

    // Methode Person hinzufügen
// Methode Person hinzufügen
    private static void personHinzufuegen(Connection connection, Scanner scanner) throws SQLException {
        // Eingabe Vorname
// Überschreiben Vorname
        System.out.println("Vorname:");
// Hier füge den zusätzlichen scanner.nextLine() Aufruf ein, um das verbleibende Zeilenende zu konsumieren
        scanner.nextLine();
        String first_name = scanner.nextLine();

// Namensüberprüfung
        while (!Testen.pruefenName(first_name)) {
            System.out.println(YELLOW + "Zahlen sind nicht erlaubt. " + "\n" +
                    "Bitte geben Sie einen gültigen Vornamen ein: " + RESET);
            first_name = scanner.nextLine();
        }

        // Eingabe Nachname
        System.out.println("Nachname:");
        String last_name = scanner.nextLine();
        // Namensüberprüfung
        while (!Testen.pruefenName(last_name)) {
            System.out.println(YELLOW + "Zahlen und Satzzeichen sind nicht erlaubt. " + "\n" +
                    "Bitte geben Sie einen gültigen Nachnamen ein: " + RESET);
            last_name = scanner.nextLine();
        }
        // Eingabe E-Mail
        System.out.println("E-Mail:");
        String email = scanner.nextLine();
        // E-Mailüberprüfung
        while (!Testen.pruefenEmail(email)) {
            System.out.println(YELLOW + "Sonderzeichen, Zahlen und Buchstaben, sowie Groß- und Kleinschreibung" +
                    " sind erlaubt." + "\n" +
                    "Bitte geben Sie eine gültige E-Mail-Adresse mit einem @ ein: " + RESET);
            email = scanner.nextLine();
        }
        // Eingabe Land
        System.out.println("Land, in Englisch:");
        String country = scanner.nextLine();
        // Namensüberprüfung
        while (!Testen.pruefenLaenderName(country)) {
            System.out.println(YELLOW + "Zahlen sind nicht erlaubt. " + "\n" +
                    "Bitte geben Sie einen gültigen Ländernamen ein in Englisch: " + RESET);
            country = scanner.nextLine();
        }
        // Eingabe Geburtstag
        System.out.println("Geburtstag (yyyy-mm-dd):");
        String birthday = scanner.nextLine();
        // Geburtstagsüberprüfung
        while (!Testen.pruefenDatum(birthday)) {
            System.out.println(YELLOW + "Ungültiges Datum. Bitte das Datum im Format yyyy-mm-dd eingeben." + RESET);
            birthday = scanner.nextLine();
        }
        // Eingabe Gehalt
        System.out.println("Gehalt in Ganzzahlen:");
        int salary = scanner.nextInt();
        // Gehaltsüberprüfung
        while (!Testen.pruefenGehalt(salary)) {
            System.out.println(YELLOW + "Ungültiges Gehalt. Bitte geben Sie eine ganze, positive Zahl ein: " + RESET);
            salary = scanner.nextInt();
        }
        // Eingabe Bonus
        System.out.println("Bonus:");
        int bonus = scanner.nextInt();
        // Bonusüberprüfung
        while (!Testen.pruefenBonus(bonus)) {
            System.out.println(YELLOW + "Ungültiges Gehalt. Bitte geben Sie eine positive Zahl ein: " + RESET);
            bonus = scanner.nextInt();
        }

        // Befehl zum Füllen der Spalten für die Methode Person hinzufügen
        String query = "INSERT INTO person (first_name, last_name, email, country, birthday, salary, bonus) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, country);
            stmt.setObject(5, LocalDate.parse(birthday));
            stmt.setInt(6, salary);
            stmt.setInt(7, bonus);
            stmt.executeUpdate();
            System.out.println(GREEN + "Person hinzugefügt." + RESET);
        }
    }


    // Methode Person bearbeiten
    private static void personBearbeiten(Connection connection, Scanner scanner) throws SQLException {
        // Abfrage nach der ID
        System.out.println("Nenne ID der Person, die bearbeitet werden soll: ");
        String idInput = scanner.next();
        // Überprüfung der ID auf Zahlen und keine Buchstaben oder Sonderzeichen
        while (!Testen.pruefenID(idInput)) {
            System.out.println(YELLOW + "Ungültige ID. Bitte geben Sie eine gültige numerische ID ein: " + RESET);
            idInput = scanner.nextLine();
        }

        // Konvertierung eines Strings in eine Ganzzahl
        int id = Integer.parseInt(idInput);
        // Überschreiben Vorname
        System.out.println("Neuer Vorname:");
        scanner.nextLine();
        String first_name = scanner.nextLine();

// Namensüberprüfung
        while (!Testen.pruefenName(first_name)) {
            System.out.println(YELLOW + "Zahlen sind nicht erlaubt. " + "\n" +
                    "Bitte geben Sie einen gültigen Vornamen ein: " + RESET);
            first_name = scanner.nextLine();
        }
        // Überschreiben Nachname
        System.out.println("Neuer Nachname:");
        String last_name = scanner.nextLine();
        // Namensüberprüfung
        while (!Testen.pruefenName(last_name)) {
            System.out.println(YELLOW + "Zahlen und Satzzeichen sind nicht erlaubt. " + "\n" +
                    "Bitte geben Sie einen gültigen Nachnamen ein: " + RESET);
            last_name = scanner.nextLine();
        }
        // Überschreiben E-Mail
        System.out.println("Neue E-Mail:");
        String email = scanner.next();
        // E-Mailüberprüfung
        while (!Testen.pruefenEmail(email)) {
            System.out.println(YELLOW + "Sonderzeichen, Zahlen und Buchstaben, sowie Groß- und Kleinschreibung" +
                    " sind erlaubt." + "\n" +
                    "Bitte geben Sie eine gültige E-Mail-Adresse mit einem @ ein: " + RESET);
            email = scanner.nextLine();
        }
        // Überschreiben Land
        System.out.println("Neues Land in Englisch:");
        String country = scanner.next();
        // Namensüberprüfung
        while (!Testen.pruefenLaenderName(country)) {
            System.out.println(YELLOW + "Sonderzeichen und Zahlen sind nicht erlaubt. " + "\n" +
                    "Bitte geben Sie einen gültigen Ländernamen ein in Englisch: " + RESET);
            country = scanner.nextLine();
        }
        // Überschreiben Geburtstag
        System.out.println("Neues Geburtstag (yyyy-mm-dd):");
        String birthday = scanner.next();
        // Geburtstagsüberprüfung
        if (!Testen.pruefenDatum(birthday)) {
            System.out.println(YELLOW + "Ungültiges Datum. Bitte das Datum im Format yyyy-mm-dd eingeben." + RESET);
            return;
        }
        // Überschreiben Gehalt
        System.out.println("Neues Gehalt:");
        int salary = scanner.nextInt();
        // Gehaltsüberprüfung
        while (!Testen.pruefenGehalt(salary)) {
            System.out.println(YELLOW + "Ungültiges Gehalt. Bitte geben Sie eine ganze, positive Zahl ein: " + RESET);
            salary = scanner.nextInt();
        }
        // Überschreiben Bonus
        System.out.println("Neues Bonus:");
        int bonus = scanner.nextInt();
        // Bonusüberprüfung
        while (!Testen.pruefenBonus(bonus)) {
            System.out.println(YELLOW + "Ungültiges Gehalt. Bitte geben Sie eine positive Zahl ein: " + RESET);
            bonus = scanner.nextInt();
        }
        // Definiert eine SQL Abfrage zum Überschreiben der Datensätze in der Tabelle Personendatenbank
        String query = "UPDATE person SET first_name = ?, last_name = ?, email = ?, country = ?, birthday = ?, salary = ?, bonus = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, country);
            stmt.setObject(5, LocalDate.parse(birthday));
            stmt.setInt(6, salary);
            stmt.setInt(7, bonus);
            stmt.setInt(8, id);
            int rowsUpdated = stmt.executeUpdate();
            // Überschreiben bestätigt
            if (rowsUpdated > 0) {
                System.out.println(GREEN + "Person bearbeitet." + RESET);
                // Fehlermeldung Zeile nicht vorhanden
            } else {
                System.out.println(YELLOW + "Person mit dieser ID nicht gefunden." + RESET);
            }
        }
    }

    // Methode Zeile löschen
    private static void personLoeschen(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("ID der Person, die gelöscht werden soll:");
        int id = scanner.nextInt();
        // Lenkungsfrage
        System.out.println(RED + "Soll die Person wirklich gelöscht werden? (y/n)" + RESET);
        String answer = scanner.nextLine();
        if (!answer.equals("y")) {
            System.out.println("Abgebrochen.");
            return;
        }
        // SQl Abfrage zum Löschen
        String query = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            // Löschvorgang bestätigt
            if (rowsDeleted > 0) {
                System.out.println(GREEN + "Person gelöscht." + RESET);
                // Fehlermeldung Person nicht vorhanden
            } else {
                System.out.println(YELLOW + "Person mit dieser ID nicht gefunden." + RESET);
            }
        }
    }

    // Methode Filter für Nachname
    private static void nachnameFiltern(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Nachname:");
        String lastName = scanner.next();
        // SQL Abfrage filtern nach Nachname
        String query = "SELECT * FROM person WHERE LOWER(last_name) LIKE LOWER(?) ORDER BY id";
        // Versucht, ein PreparedStatement für die SQL-Abfrage zu erstellen.
        // Die Abfrage verwendet ein Platzhalterzeichen für den Nachnamen.
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Parameter Nachname + ermöglicht eine Teilstringsuche durch den LIKE-Operator
            stmt.setString(1, lastName.toLowerCase() + "%");
            // Ergebnisse aus der SQL Abfrage in einem Result
            try (ResultSet rs = stmt.executeQuery()) {
                zeichneHeader();
                boolean gefunden = false;
                // Schleife durch die Ergebnisse der SQL Abfrage
                while (rs.next()) {
                    // Alle Werte der Personen deren Nachnamen mit der SQL Abfrage übereinstimmen ausgeben
                    zeichneZeile(rs);
                    gefunden = true;
                }
                if (!gefunden) {
                    System.out.println(RED + "Keine Person mit diesem Nachnamen gefunden." + RESET);
                }
            }zeichneFooter();
        }
    }

    //Methode Filter für SpalteV
    private static void beliebigeFelderFiltern(Connection connection, Scanner scanner) throws SQLException {

        LocalDate dateValue;

        System.out.println("Filtern nach:");
        System.out.println("1. ID");
        System.out.println("2. Vorname");
        System.out.println("3. Nachname");
        System.out.println("4. E-Mail");
        System.out.println("5. Land");
        System.out.println("6. Geburtstag");
        System.out.println("7. Gehalt");
        System.out.println("8. Bonus");
        int auswahl = scanner.nextInt();
        scanner.nextLine();

        if (auswahl < 1 || auswahl > 8) {
            System.out.println(RED + "Ungültige Eingabe. Bitte eine Zahl zwischen 1 und 8 eingeben." + RESET);
            return;
        }

        String feld;
        boolean isStringBereich = false;
        boolean isDateBereich = false;
        boolean isIntBereich = false;

        switch (auswahl) {
            case 1:
                feld = "id";
                isIntBereich = true;
                break;
            case 2:
                feld = "first_name";
                isStringBereich = true;
                break;
            case 3:
                feld = "last_name";
                isStringBereich = true;
                break;
            case 4:
                feld = "email";
                isStringBereich = true;
                break;
            case 5:
                feld = "country";
                isStringBereich = true;
                break;
            case 6:
                feld = "birthday";
                isDateBereich = true;
                break;
            case 7:
                feld = "salary";
                isIntBereich = true;
                break;
            case 8:
                feld = "bonus";
                isIntBereich = true;
                break;
            default:
                System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen 1 und 8 eingeben.");
                return;
        }

        System.out.println("Geben Sie den Wert ein, nach dem gefiltert werden soll:");
        String wert = scanner.nextLine();

        String query = "";

        if (isStringBereich) {
            query = "SELECT * FROM person WHERE LOWER(" + feld + ") LIKE LOWER(?) ORDER BY id";
            wert = wert.toLowerCase() + "%";
        } else if (isDateBereich) {
            query = "SELECT * FROM person WHERE " + feld + " = ? ORDER BY id";
        } else if (isIntBereich) {
            query = "SELECT * FROM person WHERE " + feld + " = ? ORDER BY id";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (isStringBereich) {
                stmt.setString(1, wert);
            } else if (isDateBereich) {
                try {

//                    dateValue = LocalDate.parse(wert, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    stmt.setString(1, wert);
                    // Datum verarbeiten und überprüfen
                    /*LocalDate dateValue = LocalDate.parse(wert);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(dateValue);
                    stmt.setDate(1, sqlDate);
                    */

                } catch (DateTimeParseException e) {
                    System.out.println(RED + "Ungültiges Datum. Bitte das Datum im Format yyyy-MM-dd eingeben." + RESET);
                    return;
                }
            } else {
                try {
                    int intValue = Integer.parseInt(wert);
                    stmt.setInt(1, intValue);
                } catch (NumberFormatException e) {
                    System.out.println(RED + "Ungültige Eingabe. Bitte eine gültige Zahl eingeben." + RESET);
                    return;
                }
            }
            //string con
            //local date ändern
            try (ResultSet rs = stmt.executeQuery()) {
                zeichneHeader();
                boolean gefunden = false;
                while (rs.next()) {
                    zeichneZeile(rs);
                    gefunden = true;
                }
                if (!gefunden) {
                    System.out.println(RED + "Keine Daten gefunden." + RESET);
                }
                zeichneFooter();
            }
        }
    }






    // Methode Sortieren
    private static void personenSortieren(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Sortieren nach:");
        System.out.println("1. ID");
        System.out.println("2. Vorname");
        System.out.println("3. Nachname");
        System.out.println("4. E-Mail");
        System.out.println("5. Land");
        System.out.println("6. Geburtstag");
        System.out.println("7. Gehalt");
        System.out.println("8. Bonus");
        int choice = scanner.nextInt();
        System.out.println("Sortierrichtung (ASC/DESC):");
        String direction = scanner.next().toUpperCase();

        String feld;
        switch (choice) {
            case 1:
                feld = "id";
                break;
            case 2:
                feld = "first_name";
                break;
            case 3:
                feld = "last_name";
                break;
            case 4:
                feld = "email";
                break;
            case 5:
                feld = "country";
                break;
            case 6:
                feld = "birthday";
                break;
            case 7:
                feld = "salary";
                break;
            case 8:
                feld = "bonus";
                break;
            default:
                System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen 1 und 8 eingeben.");
                return;
        }

        if (!direction.equals("ASC") && !direction.equals("DESC")) {
            System.out.println("Ungültige Sortierrichtung. Bitte ASC oder DESC eingeben.");
            return;
        }

        // SQL Abfrage sortieren nach Gruppe XY
        String query = "SELECT * FROM person ORDER BY " + feld + " " + direction;

        // Ergebnisse aus der SQL Abfrage in einem Result
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            // Tabelle zeichnen
            zeichneHeader();
            // Schleife durch die Ergebnisse der SQL Abfrage
            while (rs.next()) {
                // Alle Werte die mit der SQL Abfrage übereinstimmen ausgeben
                zeichneZeile(rs);

            }
            zeichneFooter();
        }
    }







    // Zeichnet den Header der Tabelle
    private static void zeichneHeader() {
        System.out.println("+-----+---------------------+---------------------------+-------------------------------+-------------------+------------+-----------+-----------+");
        System.out.println("| ID  | Vorname             | Nachname                  | E-Mail                        | Land              | Geburtstag | Gehalt    | Bonus     |");
        System.out.println("+-----+---------------------+---------------------------+-------------------------------+-------------------+------------+-----------+-----------+");
    }

    // Zeichnet eine Zeile der Tabelle basierend auf einem ResultSet
    private static void zeichneZeile(ResultSet rs) throws SQLException {
        System.out.printf("| %-4d| %-20s| %-26s| %-30s| %-18s| %-11s| %-10d| %-10d|%n",
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("country"),
                rs.getString("birthday"),
                rs.getInt("salary"),
                rs.getInt("bonus"));
    }

    // Zeichnet den Footer der Tabelle
    private static void zeichneFooter() {
        System.out.println("+-----+---------------------+---------------------------+-------------------------------+-------------------+------------+-----------+-----------+");
    }
}


    // Zeichnet den Header der Tabelle

