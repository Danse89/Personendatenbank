package academy.mischok.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Testen {
    // Überprüft, ob die ID nur Zahlen enthält.
    public static boolean pruefenID(String id) {
        return id.matches("\\d+");
    }
    // Überprüft, ob der Name nur Buchstaben enthält und der erste Buchstabe ein Großbuchstabe ist.
    public static boolean pruefenName(String name) {
        return name.matches("[\\p{Lu}\\p{L}\\p{M} \\-\\.]*");
    }



    // Überprüft, ob der Name nur Buchstaben enthält
    // der erste Buchstabe ein Großbuchstabe ist.
    // Sowohl Groß- als auch Kleinbuchstaben sind erlaubt.
    public static boolean pruefenLaenderName(String name) {
        return name.matches("[\\p{Lu}\\p{L}\\p{M} \\-]*");
    }
    // Überprüft, ob die E-Mail-Adresse ein gültiges Format hat (einschließlich des @-Zeichens und eines Punktes vor dem Domainnamen).
    public static boolean pruefenEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    // Überprüft, ob das Geburtsdatum ein gültiges Format hat
    public static boolean pruefenDatum(String date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    // Überprüft, ob das Gehalt positiv ist.
    public static boolean pruefenGehalt(int number) {
        return number > 0;
    }
    // Überprüft, ob der Bonus positiv oder null ist (für Bonus).
    public static boolean pruefenBonus(int number) {
        return number >= 0;
    }
}

