package projects.encryptdecrypt.src.cryptoalg;

public interface Crypto {
    String getDecryption(String data, String key);

    String getEncryption(String data, String key);

    default String getCrypto(String mode, String data, String key) {
        String msg = "";
        switch (mode) {
            case "enc":
                msg = getEncryption(data, key);
                break;
            case "dec":
                msg = getDecryption(data, key);
                break;
            default:
                System.out.println("Unknown operation");
                break;
        }
        return msg;
    }
}
