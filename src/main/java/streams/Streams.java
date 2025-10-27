package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {


//    Дан список чисел. Оставь только чётные и выведи их квадраты.
//        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        numbers.stream()
            .filter(i -> i % 2 == 0)
            .map(i -> i * i)
            .toList();


//    Подсчитай, сколько строк в списке длиннее 5 символов.
        List<String> words = List.of("apple", "banana", "pear", "pineapple");
        var count = words.stream()
            .filter(s -> s.length() > 5)
            .count();

//    Найди максимальное и минимальное число в списке с помощью Stream API.
        List<Integer> nums = List.of(10, 2, 33, 4, 25);
        var min = nums.stream()
            .min(Comparator.naturalOrder())
            .orElseThrow();

        var max = nums.stream()
            .max(Comparator.naturalOrder())
            .orElseThrow();


//    Посчитай среднюю длину строк в списке.
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");
        var avarage = names.stream()
            .mapToInt(s -> s.length())
            .average();


//    Удали дубликаты и отсортируй строки по длине.
        List<String> input = List.of("apple", "pear", "apple", "banana", "pear");
        var result = input.stream()
            .distinct()
            .sorted(Comparator.comparingInt(s -> s.length()))
            .toList();

//    Преобразуй список строк в Map: ключ — строка, значение — длина.
        List<String> fruits = List.of("apple", "banana", "kiwi");
        var newMap = fruits.stream()
            .collect(Collectors.toMap(s -> s, s -> s.length()));

//    Сгруппируй имена по первой букве.
        List<String> names2 = List.of("Alice", "Andrew", "Bob", "Charlie", "Catherine");

        var grouped = names2.stream()
            .collect(Collectors.groupingBy(s -> s.charAt(0)));

//    Собери список имён в одну строку через запятую.
        List<String> names3 = List.of("Tom", "Jerry", "Spike");
        var oneStringOfNames = names3.stream()
            .collect(Collectors.joining(", "));

//    Из списка предложений получить список всех слов.
        List<String> sentences = List.of("Java is cool", "Streams are powerful");
        var splitedToWords = sentences.stream()
            .map(s -> s.split(" ")) // each sentence → array of words
            .toList();

        var words2 = splitedToWords.stream()
            .flatMap(Arrays::stream) // flatten each array into stream
            .toList();

//    Найди самый дорогой продукт в каждой категории.
        record Product(String name, String category, double price) {}
    List<Product> products = List.of(
        new Product("Phone", "Electronics", 1200),
        new Product("TV", "Electronics", 1800),
        new Product("Apple", "Fruits", 2.5),
        new Product("Mango", "Fruits", 4.0));

        var mostExpensiveByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::category,
                Collectors.maxBy(Comparator.comparingDouble(Product::price))
            ));
    }
}