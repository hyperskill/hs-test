package org.hyperskill.hstest.testing.runner;

import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.AbstractComponentFixture;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JCheckBoxFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JInternalFrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JListFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JProgressBarFixture;
import org.assertj.swing.fixture.JRadioButtonFixture;
import org.assertj.swing.fixture.JScrollBarFixture;
import org.assertj.swing.fixture.JScrollPaneFixture;
import org.assertj.swing.fixture.JSliderFixture;
import org.assertj.swing.fixture.JSpinnerFixture;
import org.assertj.swing.fixture.JSplitPaneFixture;
import org.assertj.swing.fixture.JTabbedPaneFixture;
import org.assertj.swing.fixture.JTableFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.fixture.JToggleButtonFixture;
import org.assertj.swing.fixture.JToolBarFixture;
import org.assertj.swing.fixture.JTreeFixture;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.PresentationError;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testcase.attach.SwingSettings;
import org.hyperskill.hstest.testing.TestRun;
import org.hyperskill.hstest.testing.swing.SwingComponent;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SwingApplicationRunner implements TestRunner {
    private static class FieldFixtureItem {
        final Field field;
        final String name;
        final Class<?> fixtureClass;
        AbstractComponentFixture<?, ?, ?> fixture = null;

        FieldFixtureItem(Field field, String name,
                         Class<?> fixtureClass) {
            this.field = field;
            this.name = name;
            this.fixtureClass = fixtureClass;
        }
    }

    private final List<FieldFixtureItem> stageComponents = new ArrayList<>();
    private SwingSettings settings;

    private SwingSettings checkAttach(TestCase<?> testCase) {
        Object attachObj = testCase.getAttach();
        if (!(attachObj instanceof SwingSettings)) {
            throw new UnexpectedError(
                "Attach is not of type SwingSettings, it's " + attachObj);
        }
        return (SwingSettings) attachObj;
    }

    private void setUpWindow() {
        settings.window = new FrameFixture(GuiActionRunner.execute(() -> settings.frame));
        settings.window.robot().settings().componentLookupScope(ComponentLookupScope.ALL);

        Rectangle savedFrameBounds = settings.frame.getBounds();
        settings.window.show();
        settings.stageTest.setWindow(settings.window);
        settings.frame.setBounds(savedFrameBounds);
    }

    private void stopWindow() {
        settings.window.cleanUp();
    }

    private void performBasicTests() {
        if (settings.frame.getTitle().length() == 0) {
            throw new PresentationError("Window title is empty.");
        }

        if (!settings.frame.isVisible()) {
            throw new PresentationError("Window is not visible.");
        }
    }

    private void detectFixtureFields() {
        SwingTest stageTest = settings.stageTest;

        for (Field field : ReflectionUtils.getAllFields(stageTest)) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(SwingComponent.class)) {
                continue;
            }

            SwingComponent comp = field.getAnnotation(SwingComponent.class);

            String name = comp.name();
            if (name.isEmpty()) {
                name = Utils.capitalize(field.getName());
            }

            Class<?> fieldClass = field.getType();
            Class<?> requiredClass = AbstractComponentFixture.class;

            if (!requiredClass.isAssignableFrom(fieldClass)) {
                throw new UnexpectedError("Field " + field.getName()
                    + " should be of type " + requiredClass + ", found " + fieldClass);
            }

            stageComponents.add(new FieldFixtureItem(
                field, name, fieldClass
            ));
        }
    }

    private static class FixtureMapItem {
        final Class<? extends AbstractComponentFixture<?, ?, ?>> fixtureClass;
        final Class<? extends Component> realClass;
        final Function<String, ? extends AbstractComponentFixture<?, ?, ?>> func;

        private FixtureMapItem(Class<? extends AbstractComponentFixture<?, ?, ?>> fixtureClass,
                               Class<? extends Component> realClass,
                               Function<String, ? extends AbstractComponentFixture<?, ?, ?>> func) {
            this.fixtureClass = fixtureClass;
            this.realClass = realClass;
            this.func = func;
        }
    }

    private final FixtureMapItem[] fixtureMap = {
        new FixtureMapItem(
            JButtonFixture.class,
            JButton.class,
            name -> settings.window.button(name)),

        new FixtureMapItem(
            JCheckBoxFixture.class,
            JCheckBox.class,
            name -> settings.window.checkBox(name)),

        new FixtureMapItem(
            JComboBoxFixture.class,
            JComboBox.class,
            name -> settings.window.comboBox(name)),

        new FixtureMapItem(
            DialogFixture.class,
            Dialog.class,
            name -> settings.window.dialog(name)),

        new FixtureMapItem(
            JFileChooserFixture.class,
            JFileChooser.class,
            name -> settings.window.fileChooser(name)),

        new FixtureMapItem(
            JInternalFrameFixture.class,
            JInternalFrame.class,
            name -> settings.window.internalFrame(name)),

        new FixtureMapItem(
            JLabelFixture.class,
            JLabel.class,
            name -> settings.window.label(name)),

        new FixtureMapItem(
            JListFixture.class,
            JList.class,
            name -> settings.window.list(name)),

        new FixtureMapItem(
            JMenuItemFixture.class,
            JMenuItem.class,
            name -> settings.window.menuItem(name)),

        //new FixtureMapItem(
        //    JOptionPaneFixture.class,
        //    JOptionPane.class,
        //    name -> settings.window.optionPane(name)),

        new FixtureMapItem(
            JPanelFixture.class,
            JPanel.class,
            name -> settings.window.panel(name)),

        new FixtureMapItem(
            JProgressBarFixture.class,
            JProgressBar.class,
            name -> settings.window.progressBar(name)),

        new FixtureMapItem(
            JRadioButtonFixture.class,
            JRadioButton.class,
            name -> settings.window.radioButton(name)),

        new FixtureMapItem(
            JScrollBarFixture.class,
            JScrollBar.class,
            name -> settings.window.scrollBar(name)),

        new FixtureMapItem(
            JScrollPaneFixture.class,
            JScrollPane.class,
            name -> settings.window.scrollPane(name)),

        new FixtureMapItem(
            JSliderFixture.class,
            JSlider.class,
            name -> settings.window.slider(name)),

        new FixtureMapItem(
            JSpinnerFixture.class,
            JSpinner.class,
            name -> settings.window.spinner(name)),

        new FixtureMapItem(
            JSplitPaneFixture.class,
            JSplitPane.class,
            name -> settings.window.splitPane(name)),

        new FixtureMapItem(
            JTabbedPaneFixture.class,
            JTabbedPane.class,
            name -> settings.window.tabbedPane(name)),

        new FixtureMapItem(
            JTableFixture.class,
            JTable.class,
            name -> settings.window.table(name)),

        new FixtureMapItem(
            JTextComponentFixture.class,
            JTextComponent.class,
            name -> settings.window.textBox(name)),

        new FixtureMapItem(
            JToggleButtonFixture.class,
            JToggleButton.class,
            name -> settings.window.toggleButton(name)),

        new FixtureMapItem(
            JToolBarFixture.class,
            JToolBar.class,
            name -> settings.window.toolBar(name)),

        new FixtureMapItem(
            JTreeFixture.class,
            JTree.class,
            name -> settings.window.tree(name)),
    };

    public String fixtureToName(AbstractComponentFixture<?, ?, ?> fixture) {
        for (FieldFixtureItem item : stageComponents) {
            if (item.fixture == fixture) {
                return item.name;
            }
        }
        throw new UnexpectedError("Cannot find fixture");
    }

    private void setFixtureFields() {
        for (FieldFixtureItem item : stageComponents) {
            for (FixtureMapItem mapItem : fixtureMap) {
                Class<?> neededClass = mapItem.fixtureClass;
                Class<?> foundClass = item.fixtureClass;

                if (neededClass.isAssignableFrom(foundClass)) {
                    try {
                        AbstractComponentFixture<?, ?, ?> fixture;
                        try {
                            fixture = mapItem.func.apply(item.name);
                            if (fixture == null) {
                                throw new ComponentLookupException("");
                            }
                            item.fixture = fixture;
                            item.field.set(settings.stageTest, fixture);
                        } catch (ComponentLookupException ex) {
                            if (item.fixture == null) {
                                throw new ErrorWithFeedback(
                                    "Cannot find a component of class "
                                        + mapItem.realClass.getSimpleName() + " with name " + item.name);
                            }
                        }
                    } catch (IllegalAccessException ex) {
                        throw new UnexpectedError("Cannot set a value to the field "
                            + item.field.getName());
                    }
                    break;
                }
            }
        }
    }

    private void launchSwingWindow() {
        setUpWindow();
        performBasicTests();
        detectFixtureFields();
    }

    @Override
    public void setUp(TestCase<?> testCase) {
        this.settings = checkAttach(testCase);
        launchSwingWindow();
    }

    @Override
    public void tearDown(TestCase<?> testCase) {
        stopWindow();
    }

    private void handleError(TestCase<?> testCase, Throwable cause) {
        // assertj-swing-junit throws AssertionError on .requireEnabled() / .requireEmpty() etc
        // ans we want to show such errors as "Wrong answer".
        // Feedback on the DynamicTest annotation is required.
        if (cause instanceof AssertionError) {
            if (testCase.getFeedback().isEmpty()) {
                throw new UnexpectedError("No feedback for the Swing test. " +
                    "Use \"feedback\" parameter in the \"DynamicTest\" annotation");
            } else {
                throw new WrongAnswer("");
            }

        // assertj-swing-junit throws ActionFailedException on some actions
        } else if (cause instanceof ActionFailedException) {
            if (cause.getMessage().contains(
                "The component to click is out of the boundaries of the screen")) {
                throw new ErrorWithFeedback(cause.getMessage() + "\n" +
                    "Please, make the component visible on the screen.");
            }


        } else if (cause instanceof IllegalStateException) {
            String message = cause.getMessage();

            if (message.contains("Expecting component") &&
                message.contains("to be showing on the screen")) {

                Matcher m = Pattern
                    .compile("name='([^']+)'")
                    .matcher(message);

                if (m.find()) {
                    String name = m.group(1);
                    throw new ErrorWithFeedback("Expecting component " +
                        "\"" + name + "\" to be showing on the screen, but it isn't.");
                }

                throw new ErrorWithFeedback(message);
            }
        }
    }

    @Override
    public CheckResult test(TestRun testRun) {
        setFixtureFields();

        TestCase<?> testCase = testRun.getTestCase();

        try {
            try {
                return testCase.getDynamicTesting().handle();
            } catch (UnexpectedError ex) {
                handleError(testCase, ex.getCause());
                throw ex;
            }
        } catch (Throwable ex) {
            testRun.setErrorInTest(ex);
        }

        Throwable error = testRun.getErrorInTest();

        if (error instanceof TestPassed) {
            return CheckResult.correct();
        } else if (error instanceof WrongAnswer) {
            return CheckResult.wrong(((WrongAnswer) error).getFeedbackText());
        } else {
            return null;
        }
    }
}
