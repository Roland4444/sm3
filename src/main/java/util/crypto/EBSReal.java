package util.crypto;

import ru.CryptoPro.JCPxml.Consts;
import schedulling.abstractions.Sign;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class EBSReal extends Sign {
    public EBSReal() throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        this.DIGEST_LINK= Consts.URI_GOST_DIGEST;
        this.SIGNATURE_LINK=Consts.URI_GOST_SIGN;
        this.keyStore=   KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);

    }
        @Override
        public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
            char[] keyPassword = "sdv2019fns".toCharArray();
            return (PrivateKey)keyStore.getKey("SDV2019FNS", keyPassword);
        }

        @Override
        public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException {
            return keyStore.getCertificate("SDV2019FNS");
        }
}


