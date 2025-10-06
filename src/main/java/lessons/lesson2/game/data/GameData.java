package lessons.lesson2.game.data;

import lessons.lesson2.game.models.Scene;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Класс данных со сценарием игры.
 */
@Getter
@Setter
public class GameData {
    private Map<String, Scene> scene;
}