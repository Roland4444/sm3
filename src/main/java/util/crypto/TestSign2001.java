package util.crypto;

import ru.CryptoPro.JCPxml.Consts;
import schedulling.abstractions.Sign;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class TestSign2001 extends Sign {
    public TestSign2001() throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        this.DIGEST_LINK= Consts.URI_GOST_DIGEST;
        this.SIGNATURE_LINK=Consts.URI_GOST_SIGN;
        this.keyStore=   KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);

    }
    @Override
    public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        char[] keyPassword = "1234567890".toCharArray();
        return (PrivateKey)keyStore.getKey("3a693e6f-2b86-4244-8ff7-e9c35a692210", keyPassword);
    }

    @Override
    public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
        return keyStore.getCertificate("3a693e6f-2b86-4244-8ff7-e9c35a692210");
    }
}
