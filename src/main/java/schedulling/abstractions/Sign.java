package schedulling.abstractions;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public abstract class Sign implements Serializable {
    abstract public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException ;
    abstract public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException ;


}


/*char[] keyPassword = "vca2018".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("VCAJ2018", keyPassword);;*/