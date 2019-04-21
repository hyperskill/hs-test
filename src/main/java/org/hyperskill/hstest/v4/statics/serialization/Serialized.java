package org.hyperskill.hstest.v4.statics.serialization;

public class Serialized {
    public String gson;
    public String jackson;
    public String jsonio;

    public boolean isCircular = false;

    public Class sourceClass;
    public Object objectToSerialize;

    public Exception gsonSerialized;
    public Exception jacksonSerialized;
    public Exception jsonioSerialized;

    public Exception jacksonDeserialized;
    public Exception jsonioDeserialized;

    public boolean cantSerialize() {
        return gson == null && jackson == null && jsonio == null;
    }

    @Override
    public String toString() {
        return
            "GSON    : " + gson + "\n" +
            "Jackson : " + jackson + "\n" +
            "JsonIo  : " + jsonio + "\n" +
            "Circular: " + isCircular;
    }
}
