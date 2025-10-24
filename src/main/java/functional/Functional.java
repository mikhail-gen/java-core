package functional;

import java.util.*;
import java.util.function.*;

public class Functional {

    public static void main(String[] args) {

        // 1. Predicate<String>: строка не пуста и длиннее 3 символов
        Predicate<String> validString = s -> s != null && s.length() > 3;

        // 2. Function<String, Integer>: возвращает длину строки
        Function<String, Integer> getStringLength = s -> s.length();
        Function<String, Integer> stringLength = String::length;


        // 3. Supplier<UUID>: возвращает новый UUID
        Supplier<UUID> randomUUID = () -> UUID.randomUUID();

        // 4. Consumer<String>: выводит строку в upper case
        Consumer<String> stringToUpperCase = s -> s.toUpperCase();

        // 5. BiFunction<Integer, Integer, Integer>: сумма двух чисел
        BiFunction<Integer, Integer, Integer> sumTwoIntegers = (i, j) -> Integer.sum(i, j);

        // 6. Объединение trim и toUpperCase
        Function<String, String> trim = String::trim;
        Function<String, String> toUpperCase = String::toUpperCase;
        Function<String, String> trimAndThenToUpperCase = trim.andThen(toUpperCase);


        Function<String, String> trimThenUpper = trim.andThen(toUpperCase);

        // 7. Объединение двух Consumer
        Consumer<String> printString = System.out::println;
        Consumer<String> printLength = s -> System.out.println(s.length());
        Consumer<String> printStringAndThenPintLength = printString.andThen(printLength);


        // 8. Predicate<Integer>: isEven и isPositive, потом "нечётное или отрицательное"
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> oddOrNegative = isEven.negate().or(isPositive.negate());


        // 9. BiFunction + Function через andThen()
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        Function<Integer, String> toStr = x -> x.toString();
        BiFunction<Integer, Integer, String> multiplyThenStr = multiply.andThen(toStr);

        // 10. UnaryOperator<String>: добавляет "!!!"
        UnaryOperator<String> adsExclaims = s -> s = s + "!!!";
    }

    // 11. Метод filter(List<T> list, Predicate<T> predicate)
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // 12. Метод map(List<T> list, Function<T, R> mapper)
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        List<R> resultList = new ArrayList<>();
        for (T item : list) {
            resultList.add(mapper.apply(item));
        }
        return resultList;
    }

    // преобразуй List<String> в List<Integer> (длины строк).
    public static List<Integer> mapStringLengthsToIntegers(List<String> list) {
        List<Integer> resultIntegerList = new ArrayList<>();
        Function<String, Integer> getStringLength = s -> s.length();
        resultIntegerList = map(list, getStringLength);
        return resultIntegerList;
    }

    // 13. Метод forEach(List<T> list, Consumer<T> consumer)
    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }


    // 14. Метод generate(Supplier<T> supplier, int n)
    public static <T> List<T> generate(Supplier<T> supplier, int n) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(supplier.get());
        }
        return result;
    }
}