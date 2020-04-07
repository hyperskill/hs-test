package org.hyperskill.hstest.v7.dynamic;

import org.junit.contrib.java.lang.system.internal.NoExitSecurityManager;

import java.security.AccessControlException;

public class TestingSecurityManager extends NoExitSecurityManager {
    private final ThreadGroup rootGroup;

    public TestingSecurityManager(SecurityManager originalSecurityManager,
                                  ThreadGroup rootGroup) {
        super(originalSecurityManager);
        this.rootGroup = rootGroup;
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        ThreadGroup currGroup = Thread.currentThread().getThreadGroup();
        if (currGroup != rootGroup) {
            throw new AccessControlException("Cannot access or create ThreadGroup objects");
        }
    }
}
