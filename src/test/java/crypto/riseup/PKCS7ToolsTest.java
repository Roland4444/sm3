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
    PKCS7Tools pktool = new PKCS7Tools();
    TestSign2001 ts = new TestSign2001();
    Gost3411Hash hash = new Gost3411Hash();

    public PKCS7ToolsTest() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
    }

    @Test
    public void signPKCS7SunSecurity() {
    }

    @Test
    public void signPKCS7SunSecurity1() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException, UnrecoverableEntryException {
        byte[] toSign = "90".getBytes();

        assertNotEquals(null, (X509Certificate) ts.getCert());
        assertNotEquals(null, pktool.signPKCS7SunSecurity(toSign, ts.getPrivate(), (X509Certificate) ts.getCert()));
    }

    @Test
    public void gettingPKSC7andHashes() throws IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException {
        byte[] wav = Files.readAllBytes(new File("temp.wav").toPath());
        assertNotEquals(null, wav);
        assertNotEquals(null, hash.base64(pktool.signPKCS7SunSecurity(wav, ts.getPrivate(), (X509Certificate) ts.getCert())));
        System.out.println(pktool.signPKCS7SunSecurity(wav, ts.getPrivate(), (X509Certificate) ts.getCert()).length);
    //    System.out.print(hash.base64(pktool.signPKCS7SunSecurity(wav, ts.getPrivate(), (X509Certificate) ts.getCert())));
        FileOutputStream fos = new FileOutputStream("pksc.bin");
        fos.write(hash.base64(pktool.signPKCS7SunSecurity(wav, ts.getPrivate(), (X509Certificate) ts.getCert())).getBytes());
        fos.close();

    }
}