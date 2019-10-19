package projects.encryptdecrypt.src.cryptoalg;

public class Unicode implements Crypto {
    @Override
    public String getDecryption(String data, String key) {
        char[] chars = data.toCharArray();
        char[] shiftChars = new char[chars.length];
        int shift = Integer.parseInt(key);
        for (int i = 0; i < chars.length; i++) {
            char shiftItem = (char) (chars[i] - shift);
            shiftChars[i] = shiftItem;
        }
        return String.valueOf(shiftChars);
    }

    @Override
    public String getEncryption(String data, String key) {
        char[] chars = data.toCharArray();
        char[] shiftChars = new char[chars.length];
        int shift = Integer.parseInt(key);
        for (int i = 0; i < chars.length; i++) {
            char shiftItem = (char) (chars[i] + shift);
            shiftChars[i] = shiftItem;
        }
        return String.valueOf(shiftChars);
    }
}

