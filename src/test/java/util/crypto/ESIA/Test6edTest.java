package util.crypto.ESIA;

import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;

public class Test6edTest {
    Test6ed test = new Test6ed();

    @Test
    public void getPrivate() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        assertNotEquals(null, test.getCert());
        assertNotEquals(null, test.getPrivate());
    }
}