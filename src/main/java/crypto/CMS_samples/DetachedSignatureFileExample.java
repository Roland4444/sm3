/**
 * Copyright 2004-2012 Crypto-Pro. All rights reserved.
 * Программный код, содержащийся в этом файле, предназначен
 * для целей обучения. Может быть скопирован или модифицирован
 * при условии сохранения абзацев с указанием авторства и прав.
 *
 * Данный код не может быть непосредственно использован
 * для защиты информации. Компания Крипто-Про не несет никакой
 * ответственности за функционирование этого кода.
 */
package crypto.CMS_samples;

import com.objsys.asn1j.runtime.Asn1BerDecodeBuffer;
import com.objsys.asn1j.runtime.Asn1BerEncodeBuffer;
import crypto.Gost3411Hash;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.ContentInfo;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.DigestAlgorithmIdentifier;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignedData;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignerInfo;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.params.OID;
import ru.CryptoPro.JCP.tools.Array;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * Пример создания отсоединенной подписи
 * большого файла.
 *
 * 27/06/2012
 *
 */
public class DetachedSignatureFileExample {

    /**
     * Файл приличного размера.
     */
    public static final String fileName = "temp.wav";

    /**
     * Алиас ключа для подписи.
     */
    public static final String alias = "gost_exch";

    /**
     * Пароль к контейнеру с ключом.
     */
    public static final char[] password = "Pass1234".toCharArray();

    /**
     * Пример.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Gost3411Hash hash = new Gost3411Hash();
        KeyStore keyStore =  KeyStore.getInstance("HDImageStore", "JCP");
        keyStore.load(null, null);

        // Получаем ключ и сертификат.
        char[] keyPassword = "1234567890".toCharArray();
        PrivateKey privateKey = (PrivateKey)keyStore.getKey("3a693e6f-2b86-4244-8ff7-e9c35a692210", keyPassword);
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("3a693e6f-2b86-4244-8ff7-e9c35a692210");

        // Создаем подпись, при этом сначала получаем хеш с файла.
        Signature signature = Signature.getInstance(JCP.GOST_DHEL_SIGN_NAME);
        signature.initSign(privateKey);
        readAndHash(signature, fileName);

        // Создаем подпись.
        byte[] cms = CMS.createCMS(null, signature.sign(), certificate, true);

        // Пишем ее в файл - потом можно проверить в csptest:
        /*
            csptest -sfsign -verify -in "C:\TESTS\CMS\data.exe" -signature
            "C:\jcp_sig_64_data_exe.dat" -detached -cades_disable
         */
        System.out.println("DIGEST==>\n"+hash.h_Base64rfc2045(Files.readAllBytes(new File(fileName).toPath())));
        System.out.println("\nSIGN==>\n"+hash.base64(cms));


        Array.writeFile("binData/out.bin", cms);

        // Проверяем подпись, снова сначала получаем хеш с файла.
        signature = Signature.getInstance(JCP.GOST_DHEL_SIGN_NAME);
        signature.initVerify(certificate.getPublicKey());
        readAndHash(signature, fileName);

        verify(cms, certificate, signature);
    }

    /**
     * Читаем файл и по кусочкам хешируем.
     *
     * @param signature Проинициализированная подпись.
     * @param fileName Имя файла для хеширования.
     * @return подпись с хешем.
     * @throws Exception
     */
    public static Signature readAndHash(Signature signature, String fileName) throws Exception {

        File file = new File(fileName);
        FileInputStream fData = new FileInputStream(file);

        // Не очень удобный способ чтения, но ведь это пример.
        int read;
        while ( (read = fData.read()) != -1) {
            signature.update((byte)read);
        }

        fData.close();

        return signature;
    }

    /**
     * Проверка подписи.
     *
     * @param buffer Отделенная подпись.
     * @param cert Сертификат для проверки подписи.
     * @param signature Проверяемая подпись.
     * @throws Exception
     */
    public static void verify(byte[] buffer, X509Certificate cert, Signature signature)
            throws Exception {

        int i;
        final Asn1BerDecodeBuffer asnBuf = new Asn1BerDecodeBuffer(buffer);
        final ContentInfo all = new ContentInfo();

        all.decode(asnBuf);

        if (!new OID(CMStools.STR_CMS_OID_SIGNED).eq(all.contentType.value)) {
            throw new Exception("Not supported");
        }

        final SignedData cms = (SignedData) all.content;

        if (cms.version.value != 1) {
            throw new Exception("Incorrect version");
        }

        if (!new OID(CMStools.STR_CMS_OID_DATA).eq(cms.encapContentInfo.eContentType.value)) {
            throw new Exception("Nested not supported");
        }

        OID digestOid = null;

        DigestAlgorithmIdentifier a = new DigestAlgorithmIdentifier(new OID(CMStools.DIGEST_OID).value);

        for (i = 0; i < cms.digestAlgorithms.elements.length; i++) {
            if (cms.digestAlgorithms.elements[i].algorithm.equals(a.algorithm)) {
                digestOid =
                        new OID(cms.digestAlgorithms.elements[i].algorithm.value);
                break;
            }
        }

        if (digestOid == null) {
            throw new Exception("Unknown digest");
        }

        int pos = -1;

        for (i = 0; i < cms.certificates.elements.length; i++) {

            final Asn1BerEncodeBuffer encBuf = new Asn1BerEncodeBuffer();
            cms.certificates.elements[i].encode(encBuf);

            final byte[] in = encBuf.getMsgCopy();

            X509Certificate tmp = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(in));

            System.out.println(tmp.getSubjectDN());
            System.out.println(cert.getSubjectDN());

            if (Arrays.equals(in, cert.getEncoded())) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            throw new Exception("Not signed on certificate.");
        }

        final SignerInfo info = cms.signerInfos.elements[pos];

        if (info.version.value != 1) {
            throw new Exception("Incorrect version");
        }

        if (!digestOid.equals(new OID(info.digestAlgorithm.algorithm.value))) {
            throw new Exception("Not signed on certificate.");
        }

        final byte[] sign = info.signature.value;

        // check
        final boolean checkResult = signature.verify(sign);

        if (checkResult) {
            if (CMStools.logger != null) {
                CMStools.logger.info("Valid signature");
            }
        }
        else {
            throw new Exception("Invalid signature.");
        }
    }

}
