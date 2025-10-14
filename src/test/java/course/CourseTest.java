package course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    private Course course;
    private Student student1;
    private Student student2;
    private LocalDate date1;
    private LocalDate date2;

    @BeforeEach
    void setUp() {
        course = new Course();
        student1 = new Student(1, "Alice");
        student2 = new Student(2, "Bob");
        date1 = LocalDate.of(2025, 10, 1);
        date2 = LocalDate.of(2025, 10, 2);

        course.addStudent(student1);
        course.addStudent(student2);
        course.addDate(date1);
        course.addDate(date2);
    }

    @Test
    void testRebuildJournalCreatesEntriesForAllStudentsAndDates() {
        course.rebuildJournal();

        for (LocalDate date : List.of(date1, date2)) {
            for (Student student : List.of(student1, student2)) {
                JournalEntry entry = course.getJournal().getEntry(student, date);
                assertNotNull(entry, "Expected journal entry for " + student + " on " + date);
                assertFalse(entry.isAbsent(), "Default absence should be false");
                assertNull(entry.getGrade(), "Default grade should be null");
            }
        }
    }

    @Test
    void testRebuildJournalRemovesStudentsAndDates() {
        // Initial build
        course.rebuildJournal();

        // Remove one student and one date
        course.removeStudent(student2);
        course.removeDate(date2);


        // Check that removed student/date is gone
        assertNull(course.getJournal().getEntry(student2, date1), "Removed student should not have an entry");
        assertNull(course.getJournal().getEntry(student1, date2), "Removed date should not have an entry");

        // Check that valid entries remain
        assertNotNull(course.getJournal().getEntry(student1, date1), "Remaining entry should still exist");
    }

    @Test
    void testRebuildJournalDoesNotOverwriteExistingEntries() {
        course.getJournal().recordStudent(student1, date1, false); // Mark as absent
        course.getJournal().recordStudent(student1, date1, 95);   // Assign grade

        course.rebuildJournal();

        JournalEntry entry = course.getJournal().getEntry(student1, date1);
        assertFalse(entry.isAbsent(), "Should preserve existing absence status");
        assertEquals(95, entry.getGrade(), "Should preserve existing grade");
    }
}
