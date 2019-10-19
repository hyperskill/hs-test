package statics;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.lang.reflect.Field;
import java.util.*;


class Static2 {

    public static Collection unmodCollection = Collections.unmodifiableCollection(new ArrayList<>());

    public static List unmodList = Collections.unmodifiableList(new ArrayList<>());

    public static Set unmodSet = Collections.unmodifiableSet(new TreeSet<>());
    public static Set unmodNavSet = Collections.unmodifiableNavigableSet(new TreeSet<>());
    public static Set unmodSortedSet = Collections.unmodifiableSortedSet(new TreeSet<>());

    public static Map unmodMap = Collections.unmodifiableMap(new TreeMap<>());
    public static Map unmodNavMap = Collections.unmodifiableNavigableMap(new TreeMap<>());
    public static Map unmodSortedMap = Collections.unmodifiableSortedMap(new TreeMap<>());


    static void print() throws Exception {
        List<Collection> lists = new ArrayList<>();
        List<Map> maps = new ArrayList<>();

        for (Field f : Static2.class.getDeclaredFields()) {
            Object obj = f.get(null);
            if (obj instanceof Collection) {
                lists.add((Collection) obj);
            } else {
                maps.add((Map) obj);
            }
        }

        for (Collection coll : lists) {
            System.out.println(coll == null);

            System.out.println(coll.getClass());
            System.out.println(coll.size());
            for (Object obj : coll) {
                System.out.println(obj.getClass());
                System.out.println(obj);
            }

            try {
                coll.add("123");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println(coll.size());
        }

        for (Map map : maps) {
            System.out.println(map == null);

            System.out.println(map.getClass());
            System.out.println(map.size());

            for (Object obj : map.keySet()) {
                System.out.println(obj.getClass());
                System.out.println(obj);
                Object value = map.get(obj);
                System.out.println(value.getClass());
                System.out.println(value);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        print();
    }
}

public class TestUnmodifiableObjects extends BaseStageTest {

    public TestUnmodifiableObjects() {
        super(Static2.class);
    }

    private String rightOutput =
        "false\n" +
        "class java.util.Collections$UnmodifiableCollection\n" +
        "0\n" +
        "null\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableRandomAccessList\n" +
        "0\n" +
        "null\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableSet\n" +
        "0\n" +
        "null\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableNavigableSet\n" +
        "0\n" +
        "null\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableSortedSet\n" +
        "0\n" +
        "null\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableMap\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableNavigableMap\n" +
        "0\n" +
        "false\n" +
        "class java.util.Collections$UnmodifiableSortedMap\n" +
        "0\n";

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals(rightOutput));
    }
}
