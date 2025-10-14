package course;

import java.time.LocalDate;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Journal {
    private Map<LocalDate, Map<Student, JournalEntry>> records = new HashMap<>();

    public void recordStudent(Student student, LocalDate date, boolean isAbsent) {
        records.putIfAbsent(date, new HashMap<>());
        records.get(date).putIfAbsent(student, new JournalEntry(isAbsent, null));
        records.get(date).get(student).setAbsent(isAbsent);
    }

    public void recordStudent(Student student, LocalDate date, int grade) {
        records.putIfAbsent(date, new HashMap<>());
        records.get(date).putIfAbsent(student, new JournalEntry(false, grade));
        records.get(date).get(student).setGrade(grade);
    }

    public JournalEntry getEntry(Student student, LocalDate date) {
        return records.getOrDefault(date, Collections.emptyMap()).get(student);
    }

    public void printJournal() {
        for (LocalDate date : records.keySet()) {
            System.out.println("Date: " + date);
            Map<Student, JournalEntry> entries = records.get(date);
            for (Map.Entry<Student, JournalEntry> entry : entries.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}