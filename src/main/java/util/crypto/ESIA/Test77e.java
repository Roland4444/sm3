package util.crypto.ESIA;

import ru.CryptoPro.JCPxml.Consts;
import schedulling.abstractions.Sign;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class Test77e extends Sign {
    public Test77e(){
        this.DIGEST_LINK= Consts.URI_GOST_DIGEST;
        this.SIGNATURE_LINK=Consts.URI_GOST_SIGN;
    }
    @Override
    public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        char[] keyPassword = "1234567890".toCharArray();
        // char[] keyPassword = "1234567890".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("77ea7d1e-5f5d-479c-88d1-9d3e5eadcca6", keyPassword);
        //  PrivateKey key = (PrivateKey)keyStore.getKey("3a693e6f-2b86-4244-8ff7-e9c35a692210", keyPassword);
        return key;
    }

    @Override
    public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);
        Certificate cert = (Certificate) keyStore.getCertificate("77ea7d1e-5f5d-479c-88d1-9d3e5eadcca6");
        //  Certificate cert = (Certificate) keyStore.getCertificate("3a693e6f-2b86-4244-8ff7-e9c35a692210");
        return cert;
    }
}
