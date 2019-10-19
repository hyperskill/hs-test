package statics;


import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Static4 {
    public static class Node {
        public Node next;
    }

    public static class Node2 {
        public String s = "qwerty";
        public Integer q = 123;
        public Object[] o = new Object[] {"123", 123};
    }

    public static Node node1 = new Node();
    public static Node node2 = new Node();
    public static Node node3 = new Node();

    public static Node2 node4 = new Node2();

    static {
        node1.next = node2;
        node2.next = node1;
        node3.next = node3;
    }

    static void printNode4() {
        System.out.println(node4 != null);
        System.out.println(node4.getClass());
        System.out.println(node4.s.getClass());
        System.out.println(node4.s);
        System.out.println(node4.q.getClass());
        System.out.println(node4.q);
        System.out.println(node4.o.getClass());
        for (Object obj : node4.o) {
            System.out.println(obj.getClass());
            System.out.println(obj);
        }
    }

    static void printConnections() {
        System.out.println();

        Node[] nodes = new Node[] {node1, node2, node3, node1.next, node2.next, node3.next};

        for (Node node1 : nodes) {
            for (Node node2 : nodes) {
                System.out.println(node1 == node2);
            }
        }
    }

    public static void main(String[] args) {
        printNode4();
        node4.q = 234;
        node4.s = "657";
        node4.o[0] = new ArrayList();
        printNode4();

        printConnections();
        node1 = node2;
        printConnections();
    }
}

public class TestCircularReferences extends BaseStageTest {

    public TestCircularReferences() {
        super(Static4.class);
    }

    private String output;

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        if (output == null) {
            output = reply;
            return CheckResult.TRUE;
        }
        return new CheckResult(reply.equals(output));
    }
}
