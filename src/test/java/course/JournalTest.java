package course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JournalTest {

    private Journal journal;
    private Student student1;
    private Student student2;
    private LocalDate date1;
    private LocalDate date2;

    @BeforeEach
    void setUp() {
        journal = new Journal();
        student1 = new Student(1, "Alice");
        student2 = new Student(2, "Bob");
        date1 = LocalDate.of(2025, 10, 10);
        date2 = LocalDate.of(2025, 10, 11);
    }

    @Test
    void testRecordAttendance() {
        journal.recordStudent(student1, date1, true); // absent

        JournalEntry entry = journal.getEntry(student1, date1);
        assertNotNull(entry);
        assertTrue(entry.isAbsent());
        assertNull(entry.getGrade());
    }

    @Test
    void testRecordGrade() {
        journal.recordStudent(student1, date1, 88); // grade only

        JournalEntry entry = journal.getEntry(student1, date1);
        assertNotNull(entry);
        assertFalse(entry.isAbsent());
        assertEquals(88, entry.getGrade());
    }

    @Test
    void testUpdateAttendanceAndGradeSeparately() {
        journal.recordStudent(student1, date1, true);
        journal.recordStudent(student1, date1, 90);

        JournalEntry entry = journal.getEntry(student1, date1);
        assertNotNull(entry);
        assertTrue(entry.isAbsent());
        assertEquals(90, entry.getGrade());
    }

    @Test
    void testMultipleStudentsAndDates() {
        journal.recordStudent(student1, date1, true);
        journal.recordStudent(student2, date2, 75);

        JournalEntry entry1 = journal.getEntry(student1, date1);
        JournalEntry entry2 = journal.getEntry(student2, date2);

        assertNotNull(entry1);
        assertTrue(entry1.isAbsent());

        assertNotNull(entry2);
        assertFalse(entry2.isAbsent());
        assertEquals(75, entry2.getGrade());
    }

    @Test
    void testGetEntryForNonexistentEntry() {
        assertNull(journal.getEntry(student1, date1));
    }
}