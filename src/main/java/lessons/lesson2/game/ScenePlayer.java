package lessons.lesson2.game;

import lessons.lesson2.game.data.GameData;
import lessons.lesson2.game.data.GameDataReader;
import lessons.lesson2.game.models.Choice;
import lessons.lesson2.game.models.Scene;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ScenePlayer {
    public static GameData gameData = GameDataReader.loadGameData(new GameDataReader.ResourceLoader());
    private Scene currentScene;
    private static final String START_SCENE = "start";
    private static final String END_SCENE = "end";

    public void playGame() {
        for (Scene scene : gameData.getScene().values()) {
            scene.validate();
        }

        try (Scanner scanner = new Scanner(System.in)) {
            currentScene = getStartScene();
            while (true) {
                System.out.println(currentScene.getDescription());
                if (Objects.equals(currentScene.getIdentifier(), gameData.getScene().get(END_SCENE).getIdentifier())) {
                    break;
                }
                if (currentScene.getEnding() != null) {
                    playEnding();
                    currentScene = endGame();
                }
                if (currentScene.getChoice() != null && !currentScene.getChoice().isEmpty()) {
                    printChoices();
                    currentScene = followPlayersChoice(scanner);
                } else {
                    System.out.println("Nothing to do here. Ending game.");
                    break;
                }
            }
        }
    }

    private Scene getStartScene() {
        return gameData.getScene().get(START_SCENE);
    }

    private void playEnding() {
        System.out.println("The end " + currentScene.getEnding().getName());
        System.out.println(currentScene.getEnding().getResult());
    }

    private void printChoices() {
        List<Choice> choices = currentScene.getChoice();
        if (choices == null || choices.isEmpty()) {
            System.out.println("No choices available.");
            return;
        }

        int index = 1;
        for (Choice choice : choices) {
            System.out.println(index + ". " + choice.getDescription());
            index++;
        }
    }

    private Scene followPlayersChoice(Scanner scanner) {
        var sceneIdentifier = acquirePlayersChoice(scanner);
        System.out.println(sceneIdentifier);
        return gameData.getScene().get(sceneIdentifier);
    }

    private String acquirePlayersChoice(Scanner scanner) {
        int choiceCount = currentScene.getChoice().size();
        System.out.println("Make your choice!");
        int userChoice = -1;
        int choiceIndex = -1;

        while (true) {
            System.out.print("Choose path (from 1 to " + choiceCount + "): ");
            if (scanner.hasNextInt()) {
                userChoice = scanner.nextInt();
                if (userChoice > 0 && userChoice <= choiceCount) {
                    choiceIndex = userChoice - 1;
                    break;
                } else {
                    System.out.println("Nonexistent path");
                }
            } else {
                System.out.println("Invalid input: number expected.");
                scanner.next();
            }
        }

        System.out.println("Path chosen: " + userChoice);
        return currentScene.getChoice().get(choiceIndex).getIdentifier();
    }

    private Scene endGame() {
        return gameData.getScene().get(END_SCENE);
    }
}