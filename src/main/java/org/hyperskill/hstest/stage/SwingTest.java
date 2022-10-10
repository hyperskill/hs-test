package org.hyperskill.hstest.stage;

import lombok.Getter;
import lombok.Setter;
import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.fixture.*;
import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testcase.attach.SwingSettings;
import org.hyperskill.hstest.testing.Settings;
import org.hyperskill.hstest.testing.runner.SwingApplicationRunner;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class SwingTest extends StageTest<SwingSettings> {

    public static void main(String[] args) { }

    protected JFrame frame;
    @Getter @Setter protected FrameFixture window;

    <T extends AbstractComponentFixture<?, ?, ?>>
    void require(T[] elems, Consumer<T> checkFunc, String requirement) {
        for (T elem : elems) {
            try {
                checkFunc.accept(elem);
            } catch (AssertionError ex) {
                String name = ((SwingApplicationRunner) runner).fixtureToName(elem);
                throw new WrongAnswer("Component \"" + name + "\" " + requirement);
            }
        }
    }

    public SwingTest(JFrame frame) {
        InfiniteLoopDetector.setWorking(false);
        Settings.doResetOutput = false;
        runner = new SwingApplicationRunner();
        attach = new SwingSettings(this, frame);
        this.frame = frame;
    }

    public void requireEnabled(AbstractComponentFixture<?, ?, ?>... elements) {
        require(elements, AbstractComponentFixture::requireEnabled, "should be enabled");
    }

    public void requireDisabled(AbstractComponentFixture<?, ?, ?>... elements) {
        require(elements, AbstractComponentFixture::requireDisabled, "should be disabled");
    }

    public void requireVisible(AbstractComponentFixture<?, ?, ?>... elements) {
        require(elements, AbstractComponentFixture::requireVisible, "should be visible");
    }

    public void requireNotVisible(AbstractComponentFixture<?, ?, ?>... elements) {
        require(elements, AbstractComponentFixture::requireNotVisible, "should not be visible");
    }

    public void requireFocused(AbstractComponentFixture<?, ?, ?>... elements) {
        require(elements, AbstractComponentFixture::requireFocused, "should be in focus");
    }

    public <T extends AbstractComponentFixture<T, ?, ?> & EditableComponentFixture<T>>
    void requireEditable(T... elements) {
        require(elements, e -> e.requireEditable(), "should be editable");
    }

    public <T extends AbstractComponentFixture<T, ?, ?> & EditableComponentFixture<T>>
    void requireNotEditable(T... elements) {
        require(elements, e -> e.requireNotEditable(), "should not be editable");
    }

    public void requireEmpty(JTextComponentFixture... elements) {
        require(elements, JTextComponentFixture::requireEmpty, "should be empty");
    }

    public <T extends AbstractButtonFixture<T, ?>> void click(T button) {
        try {
            button.click();
        } catch (ActionFailedException ex) {
            String name = ((SwingApplicationRunner) runner).fixtureToName(button);
            throw new WrongAnswer("The test was unable to click the specific button component. Button text is \"" + name + "\".\n" +
                    "To mitigate this error, try the following:-\n" +
                    "1. Do not use the computer while the test is being executed. This maintains focus on the components the test wants to manipulate.\n" +
                    "2. Make sure the component is present and is within the boundaries of the program screen and can be clicked.\n" +
                    "3. There is something that is blocking the test from manipulating components in the screen. It should be removed.");
        } catch (NullPointerException ex) {
            String name = ((SwingApplicationRunner) runner).fixtureToName(button);
            throw new WrongAnswer("Null pointer exception occurred due to component \"" + name + "\".");
        } catch (IllegalStateException ex) {
            String name = ((SwingApplicationRunner) runner).fixtureToName(button);
            throw new WrongAnswer("The component \"" + name + "\" should be enabled and showing on the screen.");
        }
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
}
