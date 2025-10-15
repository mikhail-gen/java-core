package course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JournalEntry {
    private boolean isAbsent;
    private Integer grade;

    public boolean isAbsent() {
        return isAbsent;
    }

    @Override
    public String toString() {
        return (isAbsent ? "Absent" : "Present") + (grade != null ? ", Grade: " + grade : "");
    }
}