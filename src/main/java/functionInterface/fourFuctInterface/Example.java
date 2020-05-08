package functionInterface.fourFuctInterface;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Example {

    @Test
    public void testConsumerExa() {
        var i = 123;
        consumerExa(i, v -> System.out.println(-v));
    }

    //Consumer接口，接受一个参数没有返回值
    public void consumerExa(Integer i, Consumer<Integer> consumer) {
        consumer.accept(i);
    }

    @Test
    public void testSupplier() {
        var count = 10;
        var list = supplierExa(count, () -> (int) (Math.random() * 100));
        list.forEach(v -> System.out.print(v + " "));
    }

    //Supplier接口，没用参数，返回一个对象
    public ArrayList<Integer> supplierExa(int count, Supplier<Integer> supplier) {
        var list = new ArrayList<Integer>();

        for (var i = 0; i < count; i++) {
            var e = supplier.get();
            list.add(e);
        }
        return list;
    }


    @Test
    public void testFunctionExa() {
        var s = "1234567890";
        var str = functonExa(s, v -> v + "dou");
        System.out.println(str);
    }

    //Function<T, R>接口，接受一个T类型的参数，返回一个R类型的参数
    public String functonExa(String s, Function<String, String> function) {
        return function.apply(s);
    }


    @Test
    public void testPredicateExa() {
        var list = new ArrayList<String>();
        list.add("1dfs");
        list.add("1cf");
        list.add("2df");
        list.add("4dse");
        list.add("4jde");
        list.add("3dfe");
        var list2 = predicateExa(list, v -> v.startsWith("1"));
        list2.forEach(v -> System.out.print(v + " "));
    }

    //Predicate接口，接受一个参数，返回一个布尔值
public ArrayList<String> predicateExa(List<String> list, Predicate<String> predic) {
        var list2 = new ArrayList<String>();

        list.forEach(v -> {
            if (predic.test(v)) {
                list2.add(v);
            }
        });
        return list2;
    }
}
