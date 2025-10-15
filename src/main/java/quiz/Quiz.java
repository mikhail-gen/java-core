package quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Quiz {
    private QuestionPool questionPool = new QuestionPool();
    List<Team> teams;

    public void runQuiz() {
        prepareQuiz();
        launchQuiz();
        printResults();
    }

    private void prepareQuiz() {
        prepareTeams();
        questionPool.prepare(teams.size());

    }

    private void prepareTeams() {
        Scanner scanner = new Scanner(System.in);
        int numberOfTeams = 0;

        while (true) {
            System.out.print("Enter number of teams (2 to 5): ");
            String input = scanner.nextLine();
            try {
                numberOfTeams = Integer.parseInt(input);
                if (numberOfTeams >= 2 && numberOfTeams <= 5) {
                    break;
                } else {
                    System.out.println("Please enter a number between 2 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }

        teams = new ArrayList<>();

        for (int i = 1; i <= numberOfTeams; i++) {
            System.out.print("Enter name for team #" + i + ": ");
            String name = scanner.nextLine().trim();
            while (name.isEmpty()) {
                System.out.print("Name cannot be empty. Enter name for team #" + i + ": ");
                name = scanner.nextLine().trim();
            }
            teams.add(new Team(name, 0));
        }

        System.out.println("\nTeams ready:");
        for (Team team : teams) {
            System.out.println(" - " + team.getName());
        }
    }

    private void launchQuiz() {
        Scanner scanner = new Scanner(System.in);
        List<String> questions = new ArrayList<>(questionPool.getQuestions().keySet());
        Map<String, String> allQuestions = questionPool.getQuestions();

        int questionCount = questionPool.getQuestionQuantity();

        System.out.println("\n🎯 Starting the quiz!");

        for (int i = 0; i < questionCount; i++) {
            Team currentTeam = teams.get(i % teams.size());
            String question = questions.get(i);
            String correctAnswer = allQuestions.get(question);

            System.out.println("\nQuestion for team " + currentTeam.getName() + ":");
            System.out.println("🔹 " + question);
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase(correctAnswer)) {
                System.out.println("✅ Correct!");
                currentTeam.addScore();
            } else {
                System.out.println("❌ Incorrect! Correct answer: " + correctAnswer);
            }
        }

        System.out.println("\n🏁 That's all, folks!");
    }

    private void printResults() {
        System.out.println("\n📊 Quiz Results:");

        for (Team team : teams) {
            System.out.println("Team " + team.getName() + " - Score: " + team.getScore());
        }
    }
}