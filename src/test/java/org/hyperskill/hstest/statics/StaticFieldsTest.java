package org.hyperskill.hstest.statics;


import org.hyperskill.hstest.statics.StaticPackage.StaticTestClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.hyperskill.hstest.dev.statics.StaticFieldsManager.resetStaticFields;
import static org.hyperskill.hstest.dev.statics.StaticFieldsManager.saveStaticFields;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StaticFieldsTest {

    @Test
    public void TestChangingObjects() {
        try {
            saveStaticFields(StaticTestClass.class.getPackageName());
            for (int i = 1; i <= 8; i++) {
                assertEquals(StaticTestClass.getList(i).size(), 0);
                StaticTestClass.getList(i).add("1");
                assertEquals(StaticTestClass.getList(i).size(), 1);
                resetStaticFields();
                assertEquals(StaticTestClass.getList(i).size(), 0);
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void TestReplacingObjects() {
        try {
            saveStaticFields(StaticTestClass.class.getPackageName());
            for (int i = 5; i <= 8; i++) {
                assertEquals(StaticTestClass.getList(i).size(), 0);
                StaticTestClass.getList(i).add("1");
                assertEquals(StaticTestClass.getList(i).size(), 1);

                StaticTestClass.setList(i, new ArrayList<>());
                assertEquals(StaticTestClass.getList(i).size(), 0);
                StaticTestClass.getList(i).add("1");
                assertEquals(StaticTestClass.getList(i).size(), 1);

                resetStaticFields();
                assertEquals(StaticTestClass.getList(i).size(), 0);
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void TestChangingPrimitives() {
        try {
            saveStaticFields(StaticTestClass.class.getPackageName());
            for (int i = 5; i <= 8; i++) {
                assertEquals(StaticTestClass.getInt(i), 0);
                StaticTestClass.setInt(i, 1);
                assertEquals(StaticTestClass.getInt(i), 1);
                resetStaticFields();
                assertEquals(StaticTestClass.getInt(i), 0);
            }
        } catch (Exception ex) {
            fail();
        }
    }
}
