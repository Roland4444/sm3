package util.crypto;

import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;

public class FNS2001Test {

    @Test
    public void getPrivate() {
    }

    @Test
    public void getCert() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException, UnrecoverableEntryException {
        FNS2001 fns2001 = new FNS2001();
        assertNotEquals(null, fns2001.getCert());
        assertNotEquals(null, fns2001.getPrivate());
        System.out.println(fns2001.getPrivate().getAlgorithm());
    }
}