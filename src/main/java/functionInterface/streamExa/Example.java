package functionInterface.streamExa;


import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Example {


    @Test
    public void testReduction() {
        var arr = new int[]{1, 2, 3};
        int sum = Arrays.stream(arr).reduce(0, (x, y) -> x + y);
        OptionalDouble i = Arrays.stream(arr).average();
        System.out.println(i + " " + sum);
    }

    @Test
    public void testPeek() {
        List<String> stream = Stream.of("abc", "ad", "vdds", "a", "dfadfa", "daf")
                .filter(x -> x.length() >= 3)
                .map(x -> x.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(stream);
    }

    @Test
    public void testSort() {
        var stream = IntStream.generate(() -> new Random().nextInt()).limit(10).toArray();
        System.out.println(Arrays.toString(stream));
        int[] ints = IntStream.of(stream).sorted().toArray();
        System.out.println(Arrays.toString(ints));
        System.out.println(Arrays.toString(stream));
    }

    @Test
    public void TestReduce() {
        String str = "hello world hello stream";
        Integer len = Arrays.stream(str.split(" ")).map(x -> x.length()).reduce(0, (x, y) -> x + y);
        System.out.println(len);
    }

    @Test
    public void testCollect() {
        int[] ints = {3, 4, 5, 6, 2, 6, 6452, 642, 624, 26};
        HashSet<Integer> set = Arrays.stream(ints).collect(HashSet<Integer>::new, HashSet::add, HashSet::addAll);
        System.out.println(set);
    }

    @Test
    public void testMapping() {
        String str = "hello hello java stream";
        List<Integer> intList = Arrays.stream(str.split(" ")).
                collect(Collectors.mapping(x -> x.length(), Collectors.toList()));
        System.out.println(intList);
    }


    @Test
    public void testGroupBy() {
        String str = "hello hello java stream";
        Map<String, List<String>> map = Arrays.stream(str.split(" ")).collect(Collectors.groupingBy(Function.identity(), Collectors.toList()));
        Map<String, List<Integer>> lenMap = Arrays.stream(str.split(" ")).
                collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.mapping(x -> x.length(),
                                        Collectors.toList())
                        )
                );
        Map<Integer, List<String>> mapLen = Arrays.stream(str.split(" ")).collect(Collectors.groupingBy(xx -> xx.length(), Collectors.toList()));
        System.out.println(map);
        System.out.println(lenMap);
        System.out.println(mapLen);
    }

    @Test
    public void testReduce() {
        String str = "hello hello java stream";
        Map<String, ? extends Serializable> collect = Arrays.stream(str.split(" ")).
                collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.mapping(
                                        x -> 1,
                                        Collectors.reducing(0, (x, y) -> x + y)
                                )
                        )
                );
        System.out.println(collect);
    }

    @Test
    public void testCountCharInWord0() {
        String str = "hello world, hello stream";
        HashMap<String, Integer> countMap = new HashMap<>();
        for (String s : str.split("")) {
            if (s.matches("[a-zA-Z0-9]")) {
                if (countMap.containsKey(s)) {
                    countMap.put(s, countMap.get(s) + 1);
                } else {
                    countMap.put(s, 1);
                }
            }
        }
        System.out.println(countMap);
    }

    @Test
    public void testCountCharInWord() {
        String str = "hello world, hello stream";
        Map<String, Long> countChar = Arrays.stream(str.split("")).
                filter(x -> x.matches("[a-zA-Z0-9]")).
                collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.counting())
                );

        System.out.println(countChar);
    }

    @Test
    public void testCountCharInWord2() {
        String str = "hello world, hello stream";
        Map<String, Integer> countChar = Arrays.stream(str.split("")).
                filter(x -> x.matches("[a-zA-Z0-9]")).
                collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.mapping(
                                        x -> 1,
                                        Collectors.reducing(0, (x, y) -> x + y)
                                )
                        )
                );

        System.out.println(countChar);
    }
}
