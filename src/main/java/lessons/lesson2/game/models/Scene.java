package lessons.lesson2.game.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Scene {
    /**
     * Scene identifier = "-1" is reserved for the end scene.
     */
    private String identifier;
    private String name;
    private String description;
    private List<Choice> choice;
    private Ending ending;

    public void validate() {
        if ((choice != null && ending != null) || (choice == null && ending == null && !"-1".equals(identifier))) {
            throw new IllegalStateException("У сцены могут присутствовать либо Выборы либо Окончание");
        }
    }
}