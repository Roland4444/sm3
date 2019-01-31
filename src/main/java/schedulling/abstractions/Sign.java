package schedulling.abstractions;

import com.objsys.asn1j.runtime.*;


import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.*;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.CertificateSerialNumber;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Name;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.params.AlgIdSpec;
import ru.CryptoPro.JCP.params.OID;
import ru.CryptoPro.JCP.params.ParamsInterface;
import scala.Enumeration;
import sun.misc.BASE64Encoder;

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









}

/*char[] keyPassword = "vca2018".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey("VCAJ2018", keyPassword);;*/