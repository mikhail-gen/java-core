package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Fors {
    public static void main(String[] args) {
//    Дан список чисел. Оставь только чётные и выведи их квадраты.
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> result = new ArrayList<>();
        for (int n : numbers) {
            if (n % 2 == 0) {
                result.add(n * n);
            }
        }

//    Подсчитай, сколько строк в списке длиннее 5 символов.
        List<String> words = List.of("apple", "banana", "pear", "pineapple");
        int count = 0;
        for (String s : words) {
            if (s.length() > 5) {
                count++;
            }
        }

//    Найди максимальное и минимальное число в списке с помощью Stream API.
        List<Integer> nums = List.of(10, 2, 33, 4, 25);
        int min = nums.get(0);
        int max = nums.get(0);
        for (int n : nums) {
            if (n < min) min = n;
            if (n > max) max = n;
        }

//    Посчитай среднюю длину строк в списке.
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");
        int totalLength = 0;
        for (String s : names) {
            totalLength += s.length();
        }
        var average = totalLength / names.size();


//    Удали дубликаты и отсортируй строки по длине.
        List<String> input = List.of("apple", "pear", "apple", "banana", "pear");
        Set<String> unique = new HashSet<>(input);
        List<String> result2 = new ArrayList<>(unique);
        result2.sort(Comparator.comparingInt(String::length));

//    Преобразуй список строк в Map: ключ — строка, значение — длина.
        List<String> fruits = List.of("apple", "banana", "kiwi");
        var newMap = new HashMap<>();
        for (String s : fruits) {
            newMap.put(s, s.length());
        }

//    Сгруппируй имена по первой букве.
        List<String> names2 = List.of("Alice", "Andrew", "Bob", "Charlie", "Catherine");
        Map<Character, List<String>> grouped = new HashMap<>();
        for (String s : names2) {
            char first = s.charAt(0);
            grouped.computeIfAbsent(first, k -> new ArrayList<>()).add(s);
        }

//    Собери список имён в одну строку через запятую.
        List<String> names3 = List.of("Tom", "Jerry", "Spike");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < names3.size(); i++) {
            stringBuilder.append(names3.get(i));
            if (i < names3.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        String oneStringOfNames = stringBuilder.toString();

//    Из списка предложений получить список всех слов.
        List<String> sentences = List.of("Java is cool", "Streams are powerful");
        List<String> words2 = new ArrayList<>();
        for (String sentence : sentences) {
            String[] parts = sentence.split(" ");
            for (String word : parts) {
                words2.add(word);
            }
        }

//    Найди самый дорогой продукт в каждой категории.
        record Product(String name, String category, double price) {}
        List<Product> products = List.of(
            new Product("Phone", "Electronics", 1200),
            new Product("TV", "Electronics", 1800),
            new Product("Apple", "Fruits", 2.5),
            new Product("Mango", "Fruits", 4.0));

    }
}