package lessons.lesson2.game.data;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GameDataReader {
    private static final String GAME_DATA_FILE = "game-data.yml";

    /**
     * Загружает данные игры из YAML файла.
     *
     * @return {@link GameData} класс с данными игры.
     * @throws RuntimeException если файл не найден.
     */

    public static GameData loadGameData(ResourceLoader loader) {
        try (InputStream inputStream = loader.loadResource(GAME_DATA_FILE)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Файл %s не найден. Проверьте его наличие."
                    .formatted(GAME_DATA_FILE));
            }
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return new Yaml().loadAs(reader, GameData.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке тестовых данных из файла %s.".formatted(GAME_DATA_FILE), e);
        }
    }

    public static class ResourceLoader {
        public InputStream loadResource(String name) {
            return getClass().getClassLoader().getResourceAsStream(name);
        }
    }
}