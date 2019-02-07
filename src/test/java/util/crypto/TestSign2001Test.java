package util.crypto;

import crypto.Gost3411Hash;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSign2001Test {
    TestSign2001 ts = new TestSign2001();
    Gost3411Hash hasher = new Gost3411Hash();

    public TestSign2001Test() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
    }


    @Test
    public void getPrivate() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        assertNotEquals(null, ts.getPrivate());
    }

    @Test
    public void getCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        assertNotEquals(null, ts.getCert());
    }


    @Test
    public void generatesPhotoAudioCryptos() throws Exception {
        String photofile =  "/home/roland/IdeaProjects/sm3/photos/p1.jpg";
        String soundfile =  "/home/roland/IdeaProjects/sm3/etalon.wav";
        File f1 = new File(photofile);
        File f2 = new File(soundfile);
        assertTrue(f1.exists());
        assertTrue(f2.exists());
        byte[] imageData = Files.readAllBytes(f1.toPath());
        byte[] soundData = Files.readAllBytes(f2.toPath());

        System.out.println("\nSOUND HASH=>\n"+hasher.h_Base64rfc2045(soundData));

        System.out.println("\nPhoto HASH=>\n"+hasher.h_Base64rfc2045(imageData));

        String PKSC7Sound = hasher.base64(ts.SMEV3PKSC7(soundData));
        String PKSC7Photo = hasher.base64(ts.SMEV3PKSC7(imageData));

        System.out.println("\nPKSC7 sound sig=>\n"+PKSC7Sound);

        System.out.println("\nPKSC7 photo sig=>\n"+PKSC7Photo);
    }

    @Test
    public void generatesPhotoAudioCryptos2() throws Exception {

        String soundfile =  "/home/roland/IdeaProjects/sm3/result.wav";
        File f2 = new File(soundfile);
        assertTrue(f2.exists());
        byte[] soundData = Files.readAllBytes(f2.toPath());
        System.out.println("\nSOUND HASH=>\n"+hasher.h_Base64rfc2045(soundData));
        String PKSC7Sound = hasher.base64(ts.SMEV3PKSC7(soundData));
        System.out.println("\nPKSC7 sound sig=>\n"+PKSC7Sound);


    }



}