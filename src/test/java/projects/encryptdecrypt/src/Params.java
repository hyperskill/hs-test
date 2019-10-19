package projects.encryptdecrypt.src;

public class Params {
    private String mode;
    private String key;
    private String data;
    private String in;
    private String out;
    private String alg;

    public Params(String mode, String key, String data, String in, String out, String alg) {
        this.mode = mode;
        this.key = key;
        this.data = data;
        this.in = in;
        this.out = out;
        this.alg = alg;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
