package statics;

import mock.StaticTestClass;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.hyperskill.hstest.dev.statics.StaticFieldsManager.resetStaticFields;
import static org.hyperskill.hstest.dev.statics.StaticFieldsManager.saveStaticFields;
import static org.junit.Assert.*;

public class StaticFieldsTest {

    @BeforeClass
    public static void saveFields() throws Exception {
        saveStaticFields(StaticTestClass.class.getPackageName());
    }

    @After
    public void resetFields() throws Exception {
        resetStaticFields();
    }

    @Test
    public void TestChangingObjects() {
        try {
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
            for (int i = 5; i <= 8; i++) {
                assertEquals(0, StaticTestClass.getList(i).size());
                StaticTestClass.getList(i).add("1");
                assertEquals(1, StaticTestClass.getList(i).size());

                StaticTestClass.setList(i, new ArrayList<>());
                assertEquals(0, StaticTestClass.getList(i).size());
                StaticTestClass.getList(i).add("1");
                assertEquals(1, StaticTestClass.getList(i).size());

                resetStaticFields();
                assertEquals(0, StaticTestClass.getList(i).size());
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void TestChangingPrimitives() {
        try {
            for (int i = 5; i <= 8; i++) {
                assertEquals(0, StaticTestClass.getInt(i));
                StaticTestClass.setInt(i, 1);
                assertEquals(1, StaticTestClass.getInt(i));
                resetStaticFields();
                assertEquals(0, StaticTestClass.getInt(i));
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void TestSavingNulls() {
        try {
            for (int i = 1; i <= 8; i++) {
                assertNull(StaticTestClass.getNull(i));
                resetStaticFields();
                assertNull(StaticTestClass.getNull(i));
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void TestReplacingNulls() {
        try {
            for (int i = 5; i <= 8; i++) {
                assertNull(StaticTestClass.getNull(i));
                StaticTestClass.setNull(i, new ArrayList<>());
                assertNotNull(StaticTestClass.getNull(i));
                resetStaticFields();
                assertNull(StaticTestClass.getNull(i));
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Ignore
    @Test
    public void TestReferences() {
        try {

            assertEquals(0, StaticTestClass.ref1.size());
            assertEquals(0, StaticTestClass.ref2.size());

            StaticTestClass.ref1.add("1");
            assertEquals(1, StaticTestClass.ref1.size());
            assertEquals(1, StaticTestClass.ref2.size());

            StaticTestClass.ref2.add("2");
            assertEquals(2, StaticTestClass.ref1.size());
            assertEquals(2, StaticTestClass.ref2.size());

            resetStaticFields();

            assertEquals(0, StaticTestClass.ref1.size());
            assertEquals(0, StaticTestClass.ref2.size());

            StaticTestClass.ref1.add("1");
            assertEquals(1, StaticTestClass.ref1.size());
            assertEquals(1, StaticTestClass.ref2.size());

            StaticTestClass.ref2.add("2");
            assertEquals(2, StaticTestClass.ref1.size());
            assertEquals(2, StaticTestClass.ref2.size());

        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void TestScanner() {
        try {
            Scanner before = StaticTestClass.scanner;
            resetStaticFields();
            Scanner after = StaticTestClass.scanner;
            assertSame(before, after);
        } catch (Exception ex) {
            fail();
        }
    }
}
