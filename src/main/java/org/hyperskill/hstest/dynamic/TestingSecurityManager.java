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
        ThreadGroup prevGroup = null;
        ThreadGroup currGroup = Thread.currentThread().getThreadGroup();
        while (currGroup != rootGroup) {
            try {
                prevGroup = currGroup;
                currGroup = currGroup.getParent();
            } catch (AccessControlException ex) {
                return currGroup;
            }
        }
        return prevGroup;
    }

    private static boolean isPrivilegedAccess() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().equals("java.security.AccessController")
                    && stackTraceElement.getMethodName().equals("doPrivileged")) {
               return true;
            }
        }
        return false;
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        ThreadGroup currGroup = Thread.currentThread().getThreadGroup();
        if (currGroup != rootGroup && g == rootGroup && !isPrivilegedAccess()) {
            throw new AccessControlException("Cannot access or create ThreadGroup objects");
        }
    }
}
