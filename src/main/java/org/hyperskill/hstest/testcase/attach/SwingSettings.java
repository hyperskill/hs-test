package org.hyperskill.hstest.testcase.attach;

import lombok.Data;
import org.assertj.swing.fixture.FrameFixture;
import org.hyperskill.hstest.stage.SwingTest;

import javax.swing.*;

@Data
public class SwingSettings {
    public final SwingTest stageTest;
    public final JFrame frame;
    public FrameFixture window;
}
