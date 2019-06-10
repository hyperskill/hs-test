package statics;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

enum StaticEnum {
    ONE, TWO, THREE
}

public class StaticTestClass {

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

    public    static final int int1 = 0;
    protected static final int int2 = 0;
    private   static final int int3 = 0;
              static final int int4 = 0;
    public    static       int int5 = 0;
    protected static       int int6 = 0;
    private   static       int int7 = 0;
              static       int int8 = 0;

    public static Collection unmodCollection = Collections.unmodifiableCollection(new ArrayList<>());

    public static List unmodList = Collections.unmodifiableList(new ArrayList<>());

    public static Set unmodSet = Collections.unmodifiableSet(new TreeSet<>());
    public static Set unmodNavSet = Collections.unmodifiableNavigableSet(new TreeSet<>());
    public static Set unmodSortedSet = Collections.unmodifiableSortedSet(new TreeSet<>());

    public static Map unmodMap = Collections.unmodifiableMap(new TreeMap<>());
    public static Map unmodNavMap = Collections.unmodifiableNavigableMap(new TreeMap<>());
    public static Map unmodSortedMap = Collections.unmodifiableSortedMap(new TreeMap<>());

    public static class Node {
        public Node next;
    }

    public static class Node2 {
        public String s = "qwerty";
        public Integer q = 123;
        public Object[] o = new Object[] {"123", 123};
    }

    public static Node node1 = new Node();
    public static Node node2 = new Node();
    public static Node node3 = new Node();

    public static Node2 node4 = new Node2();

    static {
        node1.next = node2;
        node2.next = node1;
        node3.next = node3;
    }

    public static class Parent {
        String name;
        Parent(String name) {
            this.name = name;
        }
    }

    public static class Child extends Parent {
        String name;
        Child(String name) {
            super(name + name);
            this.name = name;
        }
    }

    public static Child inheritanceTest = new Child("Child");


    public static List<String> ref1 = new ArrayList<>();
    public static List<String> ref2 = ref1;

    public static Scanner scanner = new Scanner(System.in);

    public static List<String> getList(int num) {
        switch (num) {
            case 1: return list1;
            case 2: return list2;
            case 3: return list3;
            case 4: return list4;
            case 5: return list5;
            case 6: return list6;
            case 7: return list7;
            case 8: return list8;
        }
        throw new IllegalArgumentException();
    }

    public static List<String> getNull(int num) {
        switch (num) {
            case 1: return null1;
            case 2: return null2;
            case 3: return null3;
            case 4: return null4;
            case 5: return null5;
            case 6: return null6;
            case 7: return null7;
            case 8: return null8;
        }
        throw new IllegalArgumentException();
    }

    public static void setList(int num, List<String> list) {
        switch (num) {
            case 5: list5 = list; break;
            case 6: list6 = list; break;
            case 7: list7 = list; break;
            case 8: list8 = list; break;
        }
    }

    public static void setNull(int num, List<String> list) {
        switch (num) {
            case 5: null5 = list; break;
            case 6: null6 = list; break;
            case 7: null7 = list; break;
            case 8: null8 = list; break;
        }
    }

    public static int getInt(int num) {
        switch (num) {
            case 1: return int1;
            case 2: return int2;
            case 3: return int3;
            case 4: return int4;
            case 5: return int5;
            case 6: return int6;
            case 7: return int7;
            case 8: return int8;
        }
        throw new IllegalArgumentException();
    }

    public static void setInt(int num, int newInt) {
        switch (num) {
            case 5: int5 = newInt; break;
            case 6: int6 = newInt; break;
            case 7: int7 = newInt; break;
            case 8: int8 = newInt; break;
        }
    }
}
