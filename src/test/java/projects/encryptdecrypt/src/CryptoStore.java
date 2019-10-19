package projects.encryptdecrypt.src;


import projects.encryptdecrypt.src.cryptoalg.Crypto;
import projects.encryptdecrypt.src.cryptoalg.Shift;
import projects.encryptdecrypt.src.cryptoalg.Unicode;

class CryptoStore extends CryptoFactory {

    @Override
    Crypto createCrypto(String alg) {
        Crypto crypto;
        switch (alg) {
            case "shift":
                crypto = new Shift();
                break;
            case "unicode":
                crypto = new Unicode();
                break;
            default:
                crypto = null;
                break;
        }
        return crypto;
    }
}
