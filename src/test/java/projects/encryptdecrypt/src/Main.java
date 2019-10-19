package projects.encryptdecrypt.src;

import projects.encryptdecrypt.src.cryptoalg.Crypto;

public class Main {

    public static void main(String[] args) {
        Params params = Helper.findArgs(args);

        String content = getContentToCrypto(
            params.getData(), params.getIn()
        );

        CryptoStore cryptoStore = new CryptoStore();
        Crypto cryptoAlg = cryptoStore.factoryCrypto(params.getAlg());

        CryptoStrategy cryptoStrategy = new CryptoStrategy();
        cryptoStrategy.setCrypto(cryptoAlg);
        String cryptoMsg = cryptoStrategy.getCrypto(
            params.getMode(), content, params.getKey()
        );

        printResult(params.getOut(), cryptoMsg);
    }

    public static String getContentToCrypto(String data, String in) {
        if (!data.isEmpty() && !in.isEmpty()) {
            return data;
        } else if (data.isEmpty() && in.isEmpty()) {
            return "";
        } else if (!in.isEmpty()) {
            return Helper.readFile(in);
        } else {
            return data;
        }
    }

    public static void printResult(String out, String result) {
        if (out.isEmpty()) {
            Helper.writeToTerminal(result);
        } else {
            Helper.writeToFile(out, result);
        }
    }
}
