package crypto.riseup;


import sun.security.pkcs.PKCS7;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;

import java.io.ByteArrayOutputStream;

import java.security.*;
import java.security.cert.X509Certificate;


@SuppressWarnings("unchecked")
public class PKCS7Tools {

    public byte[] signPKCS7SunSecurity(byte[] digestedContent, PrivateKey privateKey, X509Certificate certificate)  {
        try {

            // Данные для подписи.
            PKCS9Attribute[] authenticatedAttributeList = {
                    new PKCS9Attribute(PKCS9Attribute.CONTENT_TYPE_OID, sun.security.pkcs.ContentInfo.DATA_OID),
                    new PKCS9Attribute(PKCS9Attribute.SIGNING_TIME_OID, new java.util.Date()),
                    new PKCS9Attribute(PKCS9Attribute.MESSAGE_DIGEST_OID, digestedContent)
            };
            PKCS9Attributes authenticatedAttributes = new PKCS9Attributes(authenticatedAttributeList);
            // Подписываем.
            byte[] signedAttributes = sign(privateKey, authenticatedAttributes.getDerEncoding());

            System.out.println("LENGTH=>"+signedAttributes.length);

            // SignerInfo.
            java.math.BigInteger serial = certificate.getSerialNumber();
            sun.security.pkcs.SignerInfo si = new sun.security.pkcs.SignerInfo(
                    new X500Name(certificate.getIssuerDN().getName()), // X500Name issuerName,
                    serial, //x509.getSerialNumber(), // BigInteger serial,
                    AlgorithmId.get(JCPCMSTools.DIGEST_OID), //JCPCMSTools.GOST_DIGEST_NAME), // AlgorithmId digestAlgorithmId,
                    authenticatedAttributes, // PKCS9Attributes authenticatedAttributes,
                    new AlgorithmId(new ObjectIdentifier(JCPCMSTools.SIGN_OID)), // AlgorithmId digestEncryptionAlgorithmId,
                    signedAttributes, // byte[] encryptedDigest,
                    null); // PKCS9Attributes unauthenticatedAttributes) {
            sun.security.pkcs.SignerInfo[] signerInfos = {si};

            // Сертификат.
            X509Certificate[] certificates = {certificate};

            // Алгоритм подписи.
            AlgorithmId[] digestAlgorithmIds = {AlgorithmId.get(JCPCMSTools.DIGEST_OID)};

            sun.security.pkcs.ContentInfo contentInfo = new sun.security.pkcs.ContentInfo(sun.security.pkcs.ContentInfo.DATA_OID, null);

            // Собираем все вместе и пишем в стрим.
            PKCS7 p7 = new PKCS7(digestAlgorithmIds, contentInfo, certificates, signerInfos);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            p7.encodeSignedData(bos);

            return bos.toByteArray();

        } catch (Exception e) {

        }
        return null;
    }

    private byte[] sign(PrivateKey key, byte[] data) {
        try {
            Signature signer = Signature.getInstance(JCPCMSTools.GOST_EL_SIGN_NAME);
            signer.initSign(key);
            signer.update(data);
            return signer.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {

        }

        return null;
    }

}
