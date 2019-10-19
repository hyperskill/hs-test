package projects.encryptdecrypt.src;


import projects.encryptdecrypt.src.cryptoalg.Crypto;

abstract class CryptoFactory {
    abstract Crypto createCrypto(String alg);
    Crypto factoryCrypto(String alg) {
        Crypto crypto = createCrypto(alg);
        if (crypto == null) {
            System.out.println("Sorry, we are not able to create this kind of algorithm");
            return null;
        }
        return crypto;
    }
}
