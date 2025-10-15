package quiz;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Team {
    private String name;
    private int score;

    public void addScore() {
        this.score += 1;
    }
}