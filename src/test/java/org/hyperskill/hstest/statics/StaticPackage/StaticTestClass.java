package org.hyperskill.hstest.statics.StaticPackage;

import java.util.ArrayList;
import java.util.List;

public class StaticTestClass {

    public    static final List<String> list1 = new ArrayList<>();
    protected static final List<String> list2 = new ArrayList<>();
    private   static final List<String> list3 = new ArrayList<>();
              static final List<String> list4 = new ArrayList<>();

    public    static       List<String> list5 = new ArrayList<>();
    protected static       List<String> list6 = new ArrayList<>();
    private   static       List<String> list7 = new ArrayList<>();
              static       List<String> list8 = new ArrayList<>();

    public    static final int int1 = 0;
    protected static final int int2 = 0;
    private   static final int int3 = 0;
              static final int int4 = 0;

    public    static       int int5 = 0;
    protected static       int int6 = 0;
    private   static       int int7 = 0;
              static       int int8 = 0;

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

    public static void setList(int num, List<String> list) {
        switch (num) {
            case 5: list5 = list; break;
            case 6: list6 = list; break;
            case 7: list7 = list; break;
            case 8: list8 = list; break;
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
