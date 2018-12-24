package util;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class Sign implements Serializable {
    public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        char[] keyPassword = "vca2018".toCharArray();
       // char[] keyPassword = "1234567890".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("VCAJ2018", keyPassword);
      //  PrivateKey key = (PrivateKey)keyStore.getKey("3a693e6f-2b86-4244-8ff7-e9c35a692210", keyPassword);
        return key;
    };

    public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        Certificate cert = (Certificate) keyStore.getCertificate("VCAJ2018");
      //  Certificate cert = (Certificate) keyStore.getCertificate("3a693e6f-2b86-4244-8ff7-e9c35a692210");
        return cert;
    };


}


/*char[] keyPassword = "vca2018".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("VCAJ2018", keyPassword);;*/