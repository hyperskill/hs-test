package org.hyperskill.hstest.stage;

import lombok.Getter;
import lombok.Setter;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JComponentFixture;
import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.testcase.attach.SwingSettings;
import org.hyperskill.hstest.testing.Settings;
import org.hyperskill.hstest.testing.runner.SwingApplicationRunner;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class SwingTest extends StageTest<SwingSettings> {

    public static void main(String[] args) { }

    protected JFrame frame;
    @Getter @Setter protected FrameFixture window;

    public SwingTest(JFrame frame) {
        InfiniteLoopDetector.setWorking(false);
        Settings.doResetOutput = false;
        runner = new SwingApplicationRunner();
        attach = new SwingSettings(this, frame);
        this.frame = frame;
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
