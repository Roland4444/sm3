package util.crypto;

import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;

public class Sign2019Test {

    @Test
    public void getCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        Sign2019 s2019 = new Sign2019();
        assertNotEquals(null, s2019.getCert());
        assertNotEquals(null, s2019.getPrivate());
        System.out.println(s2019.getPrivate().getAlgorithm());

    }
}