package util.crypto;

import crypto.Gost3411Hash;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;

import java.io.*;
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
    public void calculatePKSC7() throws Exception {
        byte[] arr = new byte[]{0x00, 0x00};

        assertNotEquals(null, ts.CMSSign(arr, true));
        Gost3411Hash hash = new Gost3411Hash();
        System.out.println(hash.base64(ts.CMSSign(arr, true)));
    }

    @Test
    public void calculatePKSC7Advanced() throws Exception {
        assertNotEquals(null, ts.AdvancedPKSC7("temp.wav"));
        Gost3411Hash hash = new Gost3411Hash();
        System.out.println(ts.AdvancedPKSC7("temp.wav").length);
        System.out.println(hash.base64(ts.AdvancedPKSC7("temp.wav")));
    }


}