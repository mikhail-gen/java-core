package quiz;

import lombok.Getter;

import java.util.*;

@Getter
public class QuestionPool {
    private Map<String, String> questions = new LinkedHashMap<>();

    public int getQuestionQuantity() {
        return questions.size();
    }

    public void prepare(int quantityOfTeams) {
        if (quantityOfTeams <= 0) {
            System.out.println("Invalid number of teams.");
            return;
        }

        fillQuestions(); // Add 24 hardcoded questions

        Scanner scanner = new Scanner(System.in);
        int totalQuestions = 0;


        while (true) {
            System.out.print("Enter number of questions for each team, but keep in mind, that quantity of teams is "
                + quantityOfTeams + ", and max total number of questiobs is 24): ");
            String input = scanner.nextLine();

            try {
                totalQuestions = Integer.parseInt(input) * quantityOfTeams;

                if (totalQuestions <= 0 || totalQuestions > 24) {
                    System.out.println("Number must be between 1 and 24.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        List<Map.Entry<String, String>> entries = new ArrayList<>(questions.entrySet());
        Collections.shuffle(entries);

        questions = new LinkedHashMap<>();
        for (int i = 0; i < totalQuestions; i++) {
            Map.Entry<String, String> entry = entries.get(i);
            questions.put(entry.getKey(), entry.getValue());
        }

        System.out.println("\nSelected Questions:");
        for (int i = 0; i < totalQuestions; i++) {
            Map.Entry<String, String> entry = entries.get(i);
            System.out.println((i + 1) + ". " + entry.getKey() + " (Answer: ************* ");
        }
    }

    private void fillQuestions() {
        questions.put("What is the capital of France?", "Paris");
        questions.put("What is 2 + 2?", "4");
        questions.put("What color do you get by mixing red and white?", "Pink");
        questions.put("How many days are there in a week?", "7");
        questions.put("Which planet is known as the Red Planet?", "Mars");
        questions.put("Who wrote 'Romeo and Juliet'?", "Shakespeare");
        questions.put("How many legs does a spider have?", "8");
        questions.put("What is the opposite of 'cold'?", "Hot");
        questions.put("What is the largest mammal?", "Blue Whale");
        questions.put("Which ocean is the largest?", "Pacific Ocean");
        questions.put("How many hours are there in a day?", "24");
        questions.put("What gas do plants absorb from the air?", "Carbon Dioxide");
        questions.put("What is H2O commonly known as?", "Water");
        questions.put("What is the main language spoken in Spain?", "Spanish");
        questions.put("What currency is used in the USA?", "Dollar");
        questions.put("What fruit is yellow and curved?", "Banana");
        questions.put("What do bees produce?", "Honey");
        questions.put("What shape has three sides?", "Triangle");
        questions.put("How many continents are there?", "7");
        questions.put("Which sport uses a bat and ball?", "Baseball");
        questions.put("What is the tallest animal?", "Giraffe");
        questions.put("What is the boiling point of water (Celsius)?", "100");
        questions.put("Who painted the Mona Lisa?", "Leonardo da Vinci");
        questions.put("What is the opposite of 'up'?", "Down");
    }
}