package crypto.riseup;

import crypto.Gost3411Hash;
import org.junit.Test;
import util.crypto.TestSign2001;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.junit.Assert.*;

public class PKCS7ToolsTest {
    TestSign2001 ts = new TestSign2001();
    Gost3411Hash hash = new Gost3411Hash();

    public PKCS7ToolsTest() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
    }

    @Test
    public void signPKCS7SunSecurity() {
    }


}