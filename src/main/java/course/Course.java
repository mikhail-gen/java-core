package course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course implements DbDataAccess {
    private String subjectName;
    private Teacher teacher;
    private Journal journal = new Journal();
    private List<LocalDate> schedule = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public void addDate(LocalDate date) {
        if (!schedule.contains(date)) {
            schedule.add(date);
            rebuildJournal();
        }
    }

    public void removeDate(LocalDate date) {
        schedule.remove(date);
        rebuildJournal();
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            rebuildJournal();
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
        rebuildJournal();
    }


    /**
     * Updates journal entries for all (student, date) combinations.
     * Cleans from HashMap if a combination does not exist anymore.
     * Adds combinations if they do not exist yet.
     */
    public void rebuildJournal() {
        journal.getRecords().keySet().removeIf(date -> !schedule.contains(date));

        for (Map.Entry<LocalDate, Map<Student, JournalEntry>> dateEntry : journal.getRecords().entrySet()) {
            dateEntry.getValue().keySet().removeIf(student -> !students.contains(student));
        }

        for (LocalDate date : schedule) {
            Map<Student, JournalEntry> studentEntries = journal.getRecords()
                .computeIfAbsent(date, d -> new HashMap<>());

            for (Student student : students) {
                studentEntries.putIfAbsent(student, new JournalEntry());
            }
        }
    }

    @Override
    public void getDataFromDb() {
        //TBD
    }

    @Override
    public void storeDataToDb() {
        //TBD
    }
}