package schedulling.abstractions;

import crypto.Gost3411Hash;
import org.bouncycastle.cms.CMSException;
import org.junit.Test;
import util.crypto.TestSign2001;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;

public class SignTest {
    TestSign2001 ts = new TestSign2001();

    public SignTest() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
    }

    @Test
    public void sign2() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, NoSuchProviderException, SignatureException, KeyStoreException, CMSException {
        byte[] data = new byte[]{0x00};
        assertNotEquals(null, ts.sign2(data));
    }
}