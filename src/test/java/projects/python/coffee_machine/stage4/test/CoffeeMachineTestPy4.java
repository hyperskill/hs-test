package projects.python.coffee_machine.stage4.test;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.ArrayList;
import java.util.List;


class TestClue {
    String string;
    TestClue(String s) {
        string = s;
    }
}

public class CoffeeMachineTestPy4 extends StageTest<TestClue> {

    @Override
    public List<TestCase<TestClue>> generate() {
        return List.of(
            new TestCase<TestClue>()
                .setAttach(new TestClue("take\n"))
                .setInput("take\n"),

            new TestCase<TestClue>()
                .setAttach(new TestClue("buy\n1\n"))
                .setInput("buy\n1\n"),

            new TestCase<TestClue>()
                .setAttach(new TestClue("buy\n2\n"))
                .setInput("buy\n2\n"),

            new TestCase<TestClue>()
                .setAttach(new TestClue("buy\n3\n"))
                .setInput("buy\n3\n"),

            new TestCase<TestClue>()
                .setAttach(new TestClue("fill\n2001\n510\n101\n21\n"))
                .setInput("fill\n2001\n510\n101\n21\n")
        );
    }

    @Override
    public CheckResult check(String reply, TestClue clue) {
        String[] lines = reply.trim().split("\\n");

        if (lines.length <= 1) {
            return CheckResult.wrong("Looks like you didn't print anything!");
        }

        String[] clueLines = clue.string.trim().split("\\n");
        String action = clueLines[0].trim();

        List<Integer> milk = new ArrayList<>();
        List<Integer> water = new ArrayList<>();
        List<Integer> beans = new ArrayList<>();
        List<Integer> cups = new ArrayList<>();
        List<Integer> money = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.split("\\s+");
            if (words.length == 0) {
                continue;
            }
            String firstWord = words[0].replace("$", "");
            int amount;
            try {
                amount = Integer.parseInt(firstWord);
            }
            catch (Exception e) {
                continue;
            }
            if (line.contains("milk")) {
                milk.add(amount);
            }
            else if (line.contains("water")) {
                water.add(amount);
            }
            else if (line.contains("beans")) {
                beans.add(amount);
            }
            else if (line.contains("cups")) {
                cups.add(amount);
            }
            else if (line.contains("money")) {
                money.add(amount);
            }
        }

        if (milk.size() != 2) {
            return new CheckResult(false,
                "There should be two lines with \"milk\", " +
                    "found: " + milk.size());
        }

        if (water.size() != 2) {
            return new CheckResult(false,
                "There should be two lines with \"water\", " +
                    "found: " + water.size());
        }

        if (beans.size() != 2) {
            return new CheckResult(false,
                "There should be two lines with \"beans\", " +
                    "found: " + beans.size());
        }

        if (cups.size() != 2) {
            return new CheckResult(false,
                "There should be two lines with \"cups\", " +
                    "found: " + cups.size());
        }

        if (money.size() != 2) {
            return new CheckResult(false,
                "There should be two lines with \"money\", " +
                    "found: " + money.size());
        }


        int milk0 = milk.get(0);
        int milk1 = milk.get(milk.size() - 1);

        int water0 = water.get(0);
        int water1 = water.get(water.size() - 1);

        int beans0 = beans.get(0);
        int beans1 = beans.get(beans.size() - 1);

        int cups0 = cups.get(0);
        int cups1 = cups.get(cups.size() - 1);

        int money0 = money.get(0);
        int money1 = money.get(money.size() - 1);

        if (water0 != 400 || milk0 != 540 || beans0 != 120
            || cups0 != 9 || money0 != 550) {
            return new CheckResult(false,
                "Initial setup is wrong: " +
                    "coffee machine should fe filled like " +
                    "stated in the description");
        }

        int diffWater = water1 - water0;
        int diffMilk = milk1 - milk0;
        int diffBeans = beans1 - beans0;
        int diffCups = cups1 - cups0;
        int diffMoney = money1 - money0;

        if (action.equals("take")) {

            if (diffMilk != 0) {
                return new CheckResult(false,
                    "After \"take\" action milk " +
                        "amount shouldn't be changed");
            }

            if (diffWater != 0) {
                return new CheckResult(false,
                    "After \"take\" action water " +
                        "amount shouldn't be changed");
            }

            if (diffBeans != 0) {
                return new CheckResult(false,
                    "After \"take\" action beans " +
                        "amount shouldn't be changed");
            }

            if (diffCups != 0) {
                return new CheckResult(false,
                    "After \"take\" action cups " +
                        "amount shouldn't be changed");
            }

            if (money1 != 0) {
                return new CheckResult(false,
                    "After \"take\" action money " +
                        "amount should be zero");
            }

            return CheckResult.correct();
        }

        else if (action.equals("buy")) {

            String option = clueLines[1].trim();

            if (option.equals("1")) {

                if (diffWater != -250) {
                    return new CheckResult(false,
                        "After buying the first option " +
                            "water amount should be lowered by 250");
                }

                if (diffMilk != 0) {
                    return new CheckResult(false,
                        "After buying the first option " +
                            "milk amount should not be changed");
                }

                if (diffBeans != -16) {
                    return new CheckResult(false,
                        "After buying the first option " +
                            "beans amount should be lowered by 16");
                }

                if (diffCups != -1) {
                    return new CheckResult(false,
                        "After buying the first option " +
                            "cups amount should be lowered by 1");
                }

                if (diffMoney != 4) {
                    return new CheckResult(false,
                        "After buying the first option " +
                            "money amount should be increased by 4");
                }

                return CheckResult.correct();

            }

            else if (option.equals("2")) {

                if (diffWater != -350) {
                    return new CheckResult(false,
                        "After buying the second option " +
                            "water amount should be lowered by 350");
                }

                if (diffMilk != -75) {
                    return new CheckResult(false,
                        "After buying the second option " +
                            "milk amount should be lowered by 75");
                }

                if (diffBeans != -20) {
                    return new CheckResult(false,
                        "After buying the second option " +
                            "beans amount should be lowered by 20");
                }

                if (diffCups != -1) {
                    return new CheckResult(false,
                        "After buying the second option " +
                            "cups amount should be lowered by 1");
                }

                if (diffMoney != 7) {
                    return new CheckResult(false,
                        "After buying the second option " +
                            "money amount should be increased by 7");
                }

                return CheckResult.correct();
            }

            else if (option.equals("3")) {

                if (diffWater != -200) {
                    return new CheckResult(false,
                        "After buying the third option " +
                            "water amount should be lowered by 350");
                }

                if (diffMilk != -100) {
                    return new CheckResult(false,
                        "After buying the third option " +
                            "milk amount should be lowered by 75");
                }

                if (diffBeans != -12) {
                    return new CheckResult(false,
                        "After buying the third option " +
                            "beans amount should be lowered by 20");
                }

                if (diffCups != -1) {
                    return new CheckResult(false,
                        "After buying the third option " +
                            "cups amount should be lowered by 1");
                }

                if (diffMoney != 6) {
                    return new CheckResult(false,
                        "After buying the third option " +
                            "money amount should be increased by 7");
                }

                return CheckResult.correct();
            }
        }

        else if (action.equals("fill")) {

            int water_ = Integer.parseInt(clueLines[1]);
            int milk_ = Integer.parseInt(clueLines[2]);
            int beans_ = Integer.parseInt(clueLines[3]);
            int cups_ = Integer.parseInt(clueLines[4]);

            if (diffMoney != 0) {
                return new CheckResult(false,
                    "After \"fill\" action " +
                        "money amount should not be changed");
            }

            if (diffWater != water_) {
                return new CheckResult(false,
                    "After \"fill\" action " +
                        "water amount expected to be increased by " + water_ +
                        " but was increased by " + diffWater);
            }

            if (diffMilk != milk_) {
                return new CheckResult(false,
                    "After \"fill\" action " +
                        "milk amount expected to be increased by " + milk_ +
                        " but was increased by " + diffMilk);
            }

            if (diffBeans != beans_) {
                return new CheckResult(false,
                    "After \"fill\" action " +
                        "beans amount expected to be increased by " + beans_ +
                        " but was increased by " + diffBeans);
            }

            if (diffCups != cups_) {
                return new CheckResult(false,
                    "After \"fill\" action " +
                        "cups amount expected to be increased by " + cups_ +
                        " but was increased by " + diffCups);
            }


            return CheckResult.correct();

        }

        throw new RuntimeException("Can't check the answer");
    }
}
