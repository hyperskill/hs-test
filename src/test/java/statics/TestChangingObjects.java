package statics;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


class TestChangingObjectsMain {
    public    static final List<String> list1 = new ArrayList<>();
    protected static final List<String> list2 = new ArrayList<>();
    private   static final List<String> list3 = new ArrayList<>();
              static final List<String> list4 = new ArrayList<>();
    public    static       List<String> list5 = new ArrayList<>();
    protected static       List<String> list6 = new ArrayList<>();
    private   static       List<String> list7 = new ArrayList<>();
              static       List<String> list8 = new ArrayList<>();


    public    static final List noTypeList1 = new ArrayList<>();
    protected static final List noTypeList2 = new ArrayList<>();
    private   static final List noTypeList3 = new ArrayList<>();
              static final List noTypeList4 = new ArrayList<>();
    public    static       List noTypeList5 = new ArrayList<>();
    protected static       List noTypeList6 = new ArrayList<>();
    private   static       List noTypeList7 = new ArrayList<>();
              static       List noTypeList8 = new ArrayList<>();


    public    static final ArrayList<String> implicitList1 = new ArrayList<>();
    protected static final ArrayList<String> implicitList2 = new ArrayList<>();
    private   static final ArrayList<String> implicitList3 = new ArrayList<>();
              static final ArrayList<String> implicitList4 = new ArrayList<>();
    public    static       ArrayList<String> implicitList5 = new ArrayList<>();
    protected static       ArrayList<String> implicitList6 = new ArrayList<>();
    private   static       ArrayList<String> implicitList7 = new ArrayList<>();
              static       ArrayList<String> implicitList8 = new ArrayList<>();


    public    static final CopyOnWriteArrayList<String> concurrentList1 = new CopyOnWriteArrayList<>();
    protected static final CopyOnWriteArrayList<String> concurrentList2 = new CopyOnWriteArrayList<>();
    private   static final CopyOnWriteArrayList<String> concurrentList3 = new CopyOnWriteArrayList<>();
              static final CopyOnWriteArrayList<String> concurrentList4 = new CopyOnWriteArrayList<>();
    public    static       CopyOnWriteArrayList<String> concurrentList5 = new CopyOnWriteArrayList<>();
    protected static       CopyOnWriteArrayList<String> concurrentList6 = new CopyOnWriteArrayList<>();
    private   static       CopyOnWriteArrayList<String> concurrentList7 = new CopyOnWriteArrayList<>();
              static       CopyOnWriteArrayList<String> concurrentList8 = new CopyOnWriteArrayList<>();


    public    static final List noTypeconcurrentList1 = new CopyOnWriteArrayList<>();
    protected static final List noTypeConcurrentList2 = new CopyOnWriteArrayList<>();
    private   static final List noTypeConcurrentList3 = new CopyOnWriteArrayList<>();
              static final List noTypeConcurrentList4 = new CopyOnWriteArrayList<>();
    public    static       List noTypeConcurrentList5 = new CopyOnWriteArrayList<>();
    protected static       List noTypeConcurrentList6 = new CopyOnWriteArrayList<>();
    private   static       List noTypeConcurrentList7 = new CopyOnWriteArrayList<>();
              static       List noTypeConcurrentList8 = new CopyOnWriteArrayList<>();


    public    static final List<Integer> homoList1 = Arrays.asList(1, 2, 3);
    protected static final List<Integer> homoList2 = Arrays.asList(1, 2, 3);
    private   static final List<Integer> homoList3 = Arrays.asList(1, 2, 3);
              static final List<Integer> homoList4 = Arrays.asList(1, 2, 3);
    public    static       List<Integer> homoList5 = Arrays.asList(1, 2, 3);
    protected static       List<Integer> homoList6 = Arrays.asList(1, 2, 3);
    private   static       List<Integer> homoList7 = Arrays.asList(1, 2, 3);
              static       List<Integer> homoList8 = Arrays.asList(1, 2, 3);


    public    static final List homoNoTypeList1 = Arrays.asList(1, 2, 3);
    protected static final List homoNoTypeList2 = Arrays.asList(1, 2, 3);
    private   static final List homoNoTypeList3 = Arrays.asList(1, 2, 3);
              static final List homoNoTypeList4 = Arrays.asList(1, 2, 3);
    public    static       List homoNoTypeList5 = Arrays.asList(1, 2, 3);
    protected static       List homoNoTypeList6 = Arrays.asList(1, 2, 3);
    private   static       List homoNoTypeList7 = Arrays.asList(1, 2, 3);
              static       List homoNoTypeList8 = Arrays.asList(1, 2, 3);


    public    static final Object objectList1 = Arrays.asList(1, 2, 3);
    protected static final Object objectList2 = Arrays.asList(1, 2, 3);
    private   static final Object objectList3 = Arrays.asList(1, 2, 3);
              static final Object objectList4 = Arrays.asList(1, 2, 3);
    public    static       Object objectList5 = Arrays.asList(1, 2, 3);
    protected static       Object objectList6 = Arrays.asList(1, 2, 3);
    private   static       Object objectList7 = Arrays.asList(1, 2, 3);
              static       Object objectList8 = Arrays.asList(1, 2, 3);


    public    static final List<Integer> homoImplicitList1 = Arrays.asList(1, 2, 3);
    protected static final List<Integer> homoImplicitList2 = Arrays.asList(1, 2, 3);
    private   static final List<Integer> homoImplicitList3 = Arrays.asList(1, 2, 3);
              static final List<Integer> homoImplicitList4 = Arrays.asList(1, 2, 3);
    public    static       List<Integer> homoImplicitList5 = Arrays.asList(1, 2, 3);
    protected static       List<Integer> homoImplicitList6 = Arrays.asList(1, 2, 3);
    private   static       List<Integer> homoImplicitList7 = Arrays.asList(1, 2, 3);
              static       List<Integer> homoImplicitList8 = Arrays.asList(1, 2, 3);


    public    static final List<Object> heteroList1 = Arrays.asList(10, "123", new ArrayList<>());
    protected static final List<Object> heteroList2 = Arrays.asList(10, "123", new ArrayList<>());
    private   static final List<Object> heteroList3 = Arrays.asList(10, "123", new ArrayList<>());
              static final List<Object> heteroList4 = Arrays.asList(10, "123", new ArrayList<>());
    public    static       List<Object> heteroList5 = Arrays.asList(10, "123", new ArrayList<>());
    protected static       List<Object> heteroList6 = Arrays.asList(10, "123", new ArrayList<>());
    private   static       List<Object> heteroList7 = Arrays.asList(10, "123", new ArrayList<>());
              static       List<Object> heteroList8 = Arrays.asList(10, "123", new ArrayList<>());


    public    static final List heteroNoTypeList1 = Arrays.asList(10, "123", new ArrayList<>());
    protected static final List heteroNoTypeList2 = Arrays.asList(10, "123", new ArrayList<>());
    private   static final List heteroNoTypeList3 = Arrays.asList(10, "123", new ArrayList<>());
              static final List heteroNoTypeList4 = Arrays.asList(10, "123", new ArrayList<>());
    public    static       List heteroNoTypeList5 = Arrays.asList(10, "123", new ArrayList<>());
    protected static       List heteroNoTypeList6 = Arrays.asList(10, "123", new ArrayList<>());
    private   static       List heteroNoTypeList7 = Arrays.asList(10, "123", new ArrayList<>());
              static       List heteroNoTypeList8 = Arrays.asList(10, "123", new ArrayList<>());


    public    static final List listOfLists1 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
    protected static final List listOfLists2 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
    private   static final List listOfLists3 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
              static final List listOfLists4 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
    public    static       List listOfLists5 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
    protected static       List listOfLists6 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
    private   static       List listOfLists7 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());
              static       List listOfLists8 = Arrays.asList(Arrays.asList(), Arrays.asList(), Arrays.asList());


    public    static final List<String> null1 = null;
    protected static final List<String> null2 = null;
    private   static final List<String> null3 = null;
              static final List<String> null4 = null;
    public    static       List<String> null5 = null;
    protected static       List<String> null6 = null;
    private   static       List<String> null7 = null;
              static       List<String> null8 = null;

    static void print() throws Exception {
        List<List> lists = new ArrayList<>();

        for (Field f : TestChangingObjectsMain.class.getDeclaredFields()) {
            lists.add((List) f.get(null));
        }

        for (List list : lists) {
            System.out.print(list == null);
            if (list == null) {
                continue;
            }

            System.out.print(list.getClass());
            System.out.print(list.size());
            for (Object obj : list) {
                System.out.print(obj.getClass());
                System.out.print(obj);
            }

            try {
                list.add("123");
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }

            System.out.println(list.size());
        }
    }

    public static void main(String[] args) throws Exception {
        print();
        null5 = new ArrayList<>();
        null6 = new ArrayList<>();
        null7 = new ArrayList<>();
        null8 = new ArrayList<>();
    }
}

public class TestChangingObjects extends StageTest {

    public TestChangingObjects() {
        super(TestChangingObjectsMain.class);
    }

    private String rightOutput =
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.ArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.concurrent.CopyOnWriteArrayList01\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer1class java.lang.Integer2class java.lang.Integer3null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.lang.Integer10class java.lang.String123class java.util.ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "falseclass java.util.Arrays$ArrayList3class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]class java.util.Arrays$ArrayList[]null3\n" +
        "truetruetruetruetruetruetruetrue";

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
        return new CheckResult(reply.equals(rightOutput), "");
    }
}
