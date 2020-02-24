package org.hyperskill.hstest.v7.stage;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public abstract class SwingTest<AttachType> extends StageTest<AttachType> {

    public static void main(String[] args) { }

    protected JFrame frame;
    protected FrameFixture window;

    public SwingTest(JFrame frame) {
        super(SwingTest.class, frame);
        this.frame = frame;
        needReloadClass = false;
    }

    @BeforeClass
    public static void setUpOnce() {
        // FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUpUI() {
        window = new FrameFixture(GuiActionRunner.execute(() -> frame));
        Rectangle savedFrameBounds = frame.getBounds();
        window.show();
        frame.setBounds(savedFrameBounds);
        frame.setAlwaysOnTop(true);
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    public static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }

    public static boolean checkExistence(final Supplier<JComponentFixture<?>> func) {
        try {
            JComponentFixture<?> component = func.get();
            return component != null;
        } catch (ComponentLookupException ex) {
            return false;
        }
    }
}
