package projects.encryptdecrypt.src.cryptoalg;

public class Shift implements Crypto {
    private static final int a = 'a';
    private static final int z = 'z';
    private static final int A = 'A';
    private static final int Z = 'Z';
    private static final int size = 26;

    @Override
    public String getDecryption(String data, String key) {
        char[] chars = data.toCharArray();
        char[] shiftChars = new char[chars.length];
        int shift = Integer.parseInt(key);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= a && chars[i] <= z) {
                int temp = chars[i] - a - shift;
                char shiftItem = (char) ((temp < 0 ? temp + size : temp) + a);
                shiftChars[i] = shiftItem;
            } else if (chars[i] >= A && chars[i] <= Z) {
                int temp = chars[i] - A - shift;
                char shiftItem = (char) ((temp < 0 ? temp + size : temp) + A);
                shiftChars[i] = shiftItem;
            } else {
                shiftChars[i] = chars[i];
            }
        }
        return String.valueOf(shiftChars);
    }

    @Override
    public String getEncryption(String data, String key) {
        char[] chars = data.toCharArray();
        char[] shiftChars = new char[chars.length];
        int shift = Integer.parseInt(key);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= a && chars[i] <= z) {
                char shiftItem = (char) (((chars[i] - a + shift) % size) + a);
                shiftChars[i] = shiftItem;
            } else if (chars[i] >= A && chars[i] <= Z) {
                char shiftItem = (char) (((chars[i] - A + shift) % size) + A);
                shiftChars[i] = shiftItem;
            } else {
                shiftChars[i] = chars[i];
            }
        }
        return String.valueOf(shiftChars);
    }
}
