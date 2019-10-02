package org.hyperskill.hstest.v6.testcase;

public interface Process extends Runnable {
    void start();
    void stop();
    boolean isStarted();
    boolean isStopped();
}
