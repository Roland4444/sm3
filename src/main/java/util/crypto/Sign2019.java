package util.crypto;


import schedulling.abstractions.Sign;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class Sign2019 extends Sign {
    @Override
    public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        char[] keyPassword = "vkaj2019".toCharArray();
        // char[] keyPassword = "1234567890".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("VKAJ2019", keyPassword);
        //  PrivateKey key = (PrivateKey)keyStore.getKey("3a693e6f-2b86-4244-8ff7-e9c35a692210", keyPassword);
        return key;
    }

    @Override
    public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        Certificate cert = (Certificate) keyStore.getCertificate("VKAJ2019");
        //  Certificate cert = (Certificate) keyStore.getCertificate("3a693e6f-2b86-4244-8ff7-e9c35a692210");
        return cert;
    }
}
