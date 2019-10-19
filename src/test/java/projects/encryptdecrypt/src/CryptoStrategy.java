package projects.encryptdecrypt.src;

import projects.encryptdecrypt.src.cryptoalg.Crypto;

class CryptoStrategy {
    private Crypto crypto;

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public String getCrypto(String mode, String data, String key) {
        return crypto.getCrypto(mode, data, key);
    }
}
