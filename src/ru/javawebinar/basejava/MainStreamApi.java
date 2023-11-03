package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreamApi {
    public static void main(String[] args) {
        int minValue1 = minValue(new int[]{4, 1, 2, 3, 3, 2, 3});
        int minValue2 = minValue(new int[]{8, 9, 2});
        System.out.println(minValue1);
        System.out.println(minValue2);

        List<Integer> list1 = oddOrEven(new ArrayList<>(List.of(4, 1, 2, 3, 3, 2, 3)));
        List<Integer> list2 = oddOrEven(new ArrayList<>(List.of(8, 9, 2)));
        list1.forEach(System.out::print);
        System.out.println();
        list2.forEach(System.out::print);
    }

    static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce(0, (a, b) -> a * 10 + b);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        Integer sum = integers.stream().reduce(0, Integer::sum);
        return integers.stream().filter(x -> isOdd(sum) != isOdd(x)).collect(Collectors.toList());
    }

    private static boolean isOdd(Integer integer) {
        return integer % 2 == 0;
    }
}
