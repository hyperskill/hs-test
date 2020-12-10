package org.hyperskill.hstest.dynamic.security;

import java.security.AccessControlException;

public class TestingSecurityManager extends SecurityManagerWrapper {
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
            prevGroup = currGroup;
            try {
                currGroup = currGroup.getParent();
            } catch (AccessControlException ex) {
                break;
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

    @Override
    public void checkExit(int status) {
        throw new ExitException(status);
    }
}
