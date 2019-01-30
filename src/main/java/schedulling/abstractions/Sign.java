package schedulling.abstractions;

import com.objsys.asn1j.runtime.*;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.util.Store;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.*;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.CertificateSerialNumber;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Name;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.params.OID;

import java.io.*;
import java.security.*;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public abstract class Sign implements Serializable {
    public final String STR_CMS_OID_SIGNED = "1.2.840.113549.1.7.2";
    public final String STR_CMS_OID_DATA = "1.2.840.113549.1.7.1";

    public String SIGNATURE_LINK;
    public String DIGEST_LINK;
    abstract public PrivateKey getPrivate() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException ;
    abstract public Certificate getCert() throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, NoSuchProviderException, IOException, CertificateException ;







    public byte[] CMSSign(byte[] data, boolean detached) throws Exception {
        return CMSSignEx(data, getPrivate(), getCert(), detached, JCP.GOST_DIGEST_OID,
                JCP.GOST_EL_KEY_OID, JCP.GOST_DHEL_SIGN_NAME, JCP.PROVIDER_NAME);
    }

    public byte[] CMSSignEx(byte[] data, PrivateKey key,
                                   Certificate cert, boolean detached, String digestOid,
                                   String signOid, String signAlg, String providerName)
            throws Exception {

        // sign
        final Signature signature = Signature.getInstance(signAlg, providerName);
        signature.initSign(key);
        signature.update(data);

        final byte[] sign = signature.sign();

        // create cms format
        return createCMSEx(data, sign, cert, detached, digestOid, signOid);
    }


    public byte[] createCMSEx(byte[] buffer, byte[] sign,
                                     Certificate cert, boolean detached, String digestOid,
                                     String signOid) throws Exception {

        final ContentInfo all = new ContentInfo();
        all.contentType = new Asn1ObjectIdentifier(
                new OID(STR_CMS_OID_SIGNED).value);

        final SignedData cms = new SignedData();
        all.content = cms;
        cms.version = new CMSVersion(1);

        // digest
        cms.digestAlgorithms = new DigestAlgorithmIdentifiers(1);
        final DigestAlgorithmIdentifier a = new DigestAlgorithmIdentifier(
                new OID(digestOid).value);

        a.parameters = new Asn1Null();
        cms.digestAlgorithms.elements[0] = a;

        if (detached) {
            cms.encapContentInfo = new EncapsulatedContentInfo(
                    new Asn1ObjectIdentifier(
                            new OID(STR_CMS_OID_DATA).value), null);
        } // if
        else {
            cms.encapContentInfo =
                    new EncapsulatedContentInfo(new Asn1ObjectIdentifier(
                            new OID(STR_CMS_OID_DATA).value),
                            new Asn1OctetString(buffer));
        } // else

        // certificate
        cms.certificates = new CertificateSet(1);
        final ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Certificate certificate =
                new ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Certificate();
        final Asn1BerDecodeBuffer decodeBuffer =
                new Asn1BerDecodeBuffer(cert.getEncoded());
        certificate.decode(decodeBuffer);

        cms.certificates.elements = new CertificateChoices[1];
        cms.certificates.elements[0] = new CertificateChoices();
        cms.certificates.elements[0].set_certificate(certificate);

        // signer info
        cms.signerInfos = new SignerInfos(1);
        cms.signerInfos.elements[0] = new SignerInfo();
        cms.signerInfos.elements[0].version = new CMSVersion(1);
        cms.signerInfos.elements[0].sid = new SignerIdentifier();

        final byte[] encodedName = ((X509Certificate) cert)
                .getIssuerX500Principal().getEncoded();
        final Asn1BerDecodeBuffer nameBuf = new Asn1BerDecodeBuffer(encodedName);
        final Name name = new Name();
        name.decode(nameBuf);

        final CertificateSerialNumber num = new CertificateSerialNumber(
                ((X509Certificate) cert).getSerialNumber());
        cms.signerInfos.elements[0].sid.set_issuerAndSerialNumber(
                new IssuerAndSerialNumber(name, num));
        cms.signerInfos.elements[0].digestAlgorithm =
                new DigestAlgorithmIdentifier(new OID(digestOid).value);
        cms.signerInfos.elements[0].digestAlgorithm.parameters = new Asn1Null();
        cms.signerInfos.elements[0].signatureAlgorithm =
                new SignatureAlgorithmIdentifier(new OID(signOid).value);
        cms.signerInfos.elements[0].signatureAlgorithm.parameters = new Asn1Null();
        cms.signerInfos.elements[0].signature = new SignatureValue(sign);

        // encode
        final Asn1BerEncodeBuffer asnBuf = new Asn1BerEncodeBuffer();
        all.encode(asnBuf, true);
        return asnBuf.getMsgCopy();
    }


    public byte[] sign2(byte[] input) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException, CMSException {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        // Переопределяем алгоритмы на Крипто-Про.
        org.bouncycastle.cms.CMSConfig.setSigningDigestAlgorithmMapping(JCP.GOST_DIGEST_OID, JCP.GOST_DIGEST_NAME);
        org.bouncycastle.cms.CMSConfig.setSigningEncryptionAlgorithmMapping(JCP.GOST_EL_DH_OID, JCP.GOST_EL_DH_NAME);
        org.bouncycastle.cms.CMSConfig.setSigningEncryptionAlgorithmMapping(JCP.GOST_EL_KEY_OID, JCP.GOST_EL_DEGREE_NAME);

        // Грузим ключ и сертификат.
        KeyStore keyStore = KeyStore.getInstance(JCP.HD_STORE_NAME);
        keyStore.load(null, null);

        // Первый в списке сертификатов - сертификат ключа подписи.
        PrivateKey privKey = getPrivate();

        // II. Создание подписи.

        InputStream inStream = new FileInputStream("1_public.pem");
        BufferedInputStream bis = new BufferedInputStream( inStream );

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        List<Certificate> certList = new ArrayList<Certificate>();
        Certificate certificate = cf.generateCertificate(bis);
        certList.add(certificate);
        Store certs = new JcaCertStore(certList);

        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
        gen.addCertificates( certs );
        CMSProcessableInputStream msg = new CMSProcessableInputStream( new ByteArrayInputStream( "signedhash".getBytes() ) );

        CMSSignedData signedData = gen.generate(msg, false);
        byte[] pkcs7 = signedData.getEncoded() ) );
    }

}


/*char[] keyPassword = "vca2018".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("VCAJ2018", keyPassword);;*/