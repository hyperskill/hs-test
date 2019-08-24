package org.hyperskill.hstest.dev.dynamic;

import org.hyperskill.hstest.dev.dynamic.input.InputStreamHandler;
import org.hyperskill.hstest.dev.dynamic.input.SystemInMock;

import java.io.StringReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        StringReader currentReader = new StringReader("1");
        System.out.println(currentReader.read());

    }
}
