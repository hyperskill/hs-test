package org.hyperskill.hstest.dynamic;

import org.junit.contrib.java.lang.system.internal.NoExitSecurityManager;

import java.security.AccessControlException;

public class TestingSecurityManager extends NoExitSecurityManager {
    private static ThreadGroup rootGroup;

    public TestingSecurityManager(SecurityManager originalSecurityManager,
                                  ThreadGroup rootGroup) {
        super(originalSecurityManager);
        TestingSecurityManager.rootGroup = rootGroup;
    }

    public static ThreadGroup getTestingGroup() {
        ThreadGroup currGroup = Thread.currentThread().getThreadGroup();
        while (currGroup != rootGroup) {
            try {
                currGroup = currGroup.getParent();
            } catch (AccessControlException ex) {
                return currGroup;
            }
        }
        return null;
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        ThreadGroup currGroup = Thread.currentThread().getThreadGroup();
        if (currGroup != rootGroup && g == rootGroup) {
            throw new AccessControlException("Cannot access or create ThreadGroup objects");
        }
    }
}
