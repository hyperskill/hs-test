package org.hyperskill.hstest.android.testcase;

public interface Process extends Runnable {
    void start();
    void stop();
    boolean isStarted();
    boolean isStopped();
}
