package util.crypto;

import org.junit.Test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.junit.Assert.*;
import ru.CryptoPro.JCPxml.Consts;
public class Sign2018Test {

    @Test
    public void getCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException {
        Sign2018 s2018 = new Sign2018();
        assertNotEquals(null, s2018.getCert());
        assertNotEquals(null, s2018.getPrivate());
        System.out.println(s2018.getPrivate().getAlgorithm());
    }

    @Test
    public void consts(){
        Consts const_= new Consts() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
        const_.
    }

}