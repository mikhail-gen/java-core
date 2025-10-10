package lessons.lesson6.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH);

    public static LocalDateTime getParsedDateTime(String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            System.err.println("[WARN] flightDateTime is null or empty.");
            return null;
        }

        try {
            return LocalDateTime.parse(dateTime.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("[ERROR] Failed to parse flightDateTime: '" + dateTime + "'");
            System.err.println("[ERROR] Expected format: dd MMMM yyyy, HH:mm (e.g., 01 December 2025, 10:15)");
            System.err.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    public static String formatDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            System.err.println("[WARN] LocalDateTime is null, cannot format.");
            return "";
        }

        return dateTime.format(FORMATTER);
    }
}
