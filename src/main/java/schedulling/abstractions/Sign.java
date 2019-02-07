package schedulling.abstractions;

import Message.abstractions.BinaryMessage;
import com.objsys.asn1j.runtime.*;


import crypto.CMS_samples.CMS;
import crypto.CMS_samples.CMSSign;
import crypto.CMS_samples.CMStools;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.*;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.CertificateSerialNumber;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Name;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.params.OID;


import java.io.*;
import java.security.*;
import java.security.Signature;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;

public abstract class Sign implements Serializable {
    public KeyStore keyStore;
    public final String STR_CMS_OID_SIGNED = "1.2.840.113549.1.7.2";
    public final String STR_CMS_OID_DATA = "1.2.840.113549.1.7.1";

    public String SIGNATURE_LINK;
    public String DIGEST_LINK;

    abstract public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException;

    abstract public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException;

    public byte[] SMEV3PKSC7(byte[] input) throws Exception {
        return (byte[]) CMSSign.createHashCMS(input, new PrivateKey[]{getPrivate()},
                new Certificate[]{getCert()}, null , true);
    }

}






