package projects.encryptdecrypt.test;

import projects.encryptdecrypt.src.Main;

import org.hyperskill.hstest.v7.common.FileUtils;
import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class EncryptDecryptTest extends BaseStageTest<String> {
    public EncryptDecryptTest() {
        super(Main.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addArguments(
                    "-alg", "unicode",
                    "-mode", "enc",
                    "-key", "5",
                    "-in", "in.txt",
                    "-out", "output.txt"
                )
                .setAttach("\\jqhtrj%yt%m~ujwxpnqq&")
                .addFile("in.txt", "Welcome to hyperskill!"),

            new TestCase<String>()
                .addArguments(
                    "-key", "0",
                    "-mode", "enc",
                    "-alg", "unicode",
                    "-in", "input.txt",
                    "-out", "output.txt"
                )
                .setAttach("Hello")
                .addFile("input.txt", "Hello"),

            new TestCase<String>()
                .addArguments(
                    "-key", "1",
                    "-in", "i.txt",
                    "-alg", "unicode",
                    "-out", "output.txt",
                    "-mode", "enc"
                )
                .setAttach("123456789")
                .addFile("i.txt", "012345678"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "dec",
                    "-in", "input.txt",
                    "-out", "output.txt",
                    "-alg", "unicode",
                    "-key", "5"
                )
                .setAttach("Welcome to hyperskill!")
                .addFile("input.txt", "\\jqhtrj%yt%m~ujwxpnqq&"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "dec",
                    "-alg", "unicode",
                    "-key", "0",
                    "-in", "input.txt",
                    "-out", "output.txt"
                )
                .setAttach("Hi")
                .addFile("input.txt", "Hi"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "dec",
                    "-key", "1",
                    "-in", "input.txt",
                    "-alg", "unicode",
                    "-out", "output.txt"
                )
                .setAttach("111122223333")
                .addFile("input.txt", "222233334444"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "enc",
                    "-key", "5",
                    "-in", "input.txt",
                    "-alg", "shift",
                    "-out", "output.txt"
                )
                .setAttach("bjqhtrj yt mdujwxpnqq")
                .addFile("input.txt", "welcome to hyperskill"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "dec",
                    "-key", "10",
                    "-in", "input.txt",
                    "-alg", "shift",
                    "-out", "output.txt"
                )
                .setAttach("treasure")
                .addFile("input.txt", "dbokcebo"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "enc",
                    "-key", "12",
                    "-in", "input.txt",
                    "-alg", "shift",
                    "-out", "output.txt"
                )
                .setAttach("cphpchdjcijdjibhdjebhjucspucmdmudbnuccup")
                .addFile("input.txt", "qdvdqvrxqwxrxwpvrxspvxiqgdiqarairpbiqqid"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "dec",
                    "-key", "10",
                    "-in", "input.txt",
                    "-alg", "shift",
                    "-out", "output.txt"
                )
                .setAttach("y")
                .addFile("input.txt", "i"),

            new TestCase<String>()
                .addArguments(
                    "-mode", "enc",
                    "-key", "10",
                    "-in", "input.txt",
                    "-alg", "shift",
                    "-out", "output.txt"
                )
                .setAttach("i")
                .addFile("input.txt", "y")
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        String output = FileUtils.readFile("output.txt");
        return new CheckResult(output.trim().equals(clue.trim()));
    }

}
