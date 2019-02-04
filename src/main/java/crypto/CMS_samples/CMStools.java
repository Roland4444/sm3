/**
 * $RCSfile$
 * version $Revision$
 * created 21.04.2009 16:51:19 by kunina
 * last modified $Date$ by $Author$
 * (C) ООО Крипто-Про 2004-2009.
 *
 * Программный код, содержащийся в этом файле, предназначен
 * для целей обучения. Может быть скопирован или модифицирован 
 * при условии сохранения абзацев с указанием авторства и прав.
 *
 * Данный код не может быть непосредственно использован
 * для защиты информации. Компания Крипто-Про не несет никакой
 * ответственности за функционирование этого кода.
 */
package crypto.CMS_samples;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.tools.Array;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Logger;

public class CMStools {

public static final String CERT_EXT = ".cer";

public static final String CMS_EXT = ".p7b";

public static final String SEPAR = File.separator;

public static String TEST_PATH = System.getProperty("user.dir") + SEPAR + "temp";

public static final String SIGN_KEY_NAME = "gost_dup";
public static final String SIGN_KEY_NAME_CONT = "gostrdup.000";
public static final char[] SIGN_KEY_PASSWORD = "Pass1234".toCharArray();
public static String SIGN_CERT_PATH = TEST_PATH + SEPAR + SIGN_KEY_NAME + CERT_EXT;

public static final String SIGN_KEY_NAME_2012_256 = "client_key_2012_256";
public static final String SIGN_KEY_NAME_CONT_2012_256 = "clientrk.000";
public static final char[] SIGN_KEY_PASSWORD_2012_256 = "pass1".toCharArray();
public static String SIGN_CERT_PATH_2012_256 = TEST_PATH + SEPAR + SIGN_KEY_NAME_2012_256 + CERT_EXT;

public static final String SIGN_KEY_NAME_2012_512 = "client_key_2012_512";
public static final String SIGN_KEY_NAME_CONT_2012_512 = "clientrk.001";
public static final char[] SIGN_KEY_PASSWORD_2012_512 = "pass3".toCharArray();
public static String SIGN_CERT_PATH_2012_512 = TEST_PATH + SEPAR + SIGN_KEY_NAME_2012_512 + CERT_EXT;

public static final String RECIP_KEY_NAME = "afevma_dup";
public static final String RECIP_KEY_NAME_CONT = "afevmard.000";
public static final char[] RECIP_KEY_PASSWORD = "security".toCharArray();
public static String RECIP_CERT_PATH = TEST_PATH + SEPAR + RECIP_KEY_NAME + CERT_EXT;

public static final String RECIP_KEY_NAME_2012_256 = "server_key_2012_256";
public static final String RECIP_KEY_NAME_CONT_2012_256 = "serverrk.000";
public static final char[] RECIP_KEY_PASSWORD_2012_256 = "pass2".toCharArray();
public static String RECIP_CERT_PATH_2012_256 = TEST_PATH + SEPAR + RECIP_KEY_NAME_2012_256 + CERT_EXT;

public static final String RECIP_KEY_NAME_2012_512 = "server_key_2012_512";
public static final String RECIP_KEY_NAME_CONT_2012_512 = "serverrk.001";
public static final char[] RECIP_KEY_PASSWORD_2012_512 = "pass4".toCharArray();
public static String RECIP_CERT_PATH_2012_512 = TEST_PATH + SEPAR + RECIP_KEY_NAME_2012_512 + CERT_EXT;


public static final String STORE_TYPE = JCP.HD_STORE_NAME;

// ГОСТ Р 34.10-2001
public static final String KEY_ALG_NAME = JCP.GOST_EL_DH_NAME;
public static final String DIGEST_ALG_NAME = JCP.GOST_DIGEST_NAME;

// ГОСТ Р 34.10-2012 (256)
public static final String KEY_ALG_NAME_2012_256 = JCP.GOST_DH_2012_256_NAME;
public static final String DIGEST_ALG_NAME_2012_256 = JCP.GOST_DIGEST_2012_256_NAME;

// ГОСТ Р 34.10-2012 (512)
public static final String KEY_ALG_NAME_2012_512 = JCP.GOST_DH_2012_512_NAME;
public static final String DIGEST_ALG_NAME_2012_512 = JCP.GOST_DIGEST_2012_512_NAME;

public static final String SEC_KEY_ALG_NAME = "GOST28147";

/**
 * OIDs для CMS
 */
public static final String STR_CMS_OID_DATA = "1.2.840.113549.1.7.1";
public static final String STR_CMS_OID_SIGNED = "1.2.840.113549.1.7.2";
public static final String STR_CMS_OID_ENVELOPED = "1.2.840.113549.1.7.3";

public static final String STR_CMS_OID_CONT_TYP_ATTR = "1.2.840.113549.1.9.3";
public static final String STR_CMS_OID_DIGEST_ATTR = "1.2.840.113549.1.9.4";
public static final String STR_CMS_OID_SIGN_TYM_ATTR = "1.2.840.113549.1.9.5";

public static final String STR_CMS_OID_TS = "1.2.840.113549.1.9.16.1.4";

// ГОСТ Р 34.10-2001
public static final String DIGEST_OID = JCP.GOST_DIGEST_OID;
public static final String SIGN_OID = JCP.GOST_EL_KEY_OID;

// ГОСТ Р 34.10-2012 (256)
public static final String DIGEST_OID_2012_256 = JCP.GOST_DIGEST_2012_256_OID;
public static final String SIGN_OID_2012_256 = JCP.GOST_PARAMS_SIG_2012_256_KEY_OID;

// ГОСТ Р 34.10-2012 (512)
public static final String DIGEST_OID_2012_512 = JCP.GOST_DIGEST_2012_512_OID;
public static final String SIGN_OID_2012_512 = JCP.GOST_PARAMS_SIG_2012_512_KEY_OID;

public static final String DATA = "12345";
public static final String DATA_FILE = "data.txt";
public static String DATA_FILE_PATH = TEST_PATH + SEPAR + DATA_FILE;

/**
 * logger
 */
public static Logger logger = Logger.getLogger("LOG");

private static CertificateFactory cf = null;
private static Certificate rootCert = null;

/**
 * @param args *
 * @throws Exception /
 */
public static void main(String[] args) throws Exception {


}

public static void prepareCertsAndData() throws Exception {

    expCert(RECIP_KEY_NAME, RECIP_CERT_PATH);
    expCert(SIGN_KEY_NAME, SIGN_CERT_PATH);

    expCert(RECIP_KEY_NAME_2012_256, RECIP_CERT_PATH_2012_256);
    expCert(SIGN_KEY_NAME_2012_256, SIGN_CERT_PATH_2012_256);

    expCert(RECIP_KEY_NAME_2012_512, RECIP_CERT_PATH_2012_512);
    expCert(SIGN_KEY_NAME_2012_512, SIGN_CERT_PATH_2012_512);

    Array.writeFile(DATA_FILE_PATH, DATA.getBytes());

}

private static void expCert(String name, String pathh) throws KeyStoreException,
        NoSuchAlgorithmException, IOException, CertificateException {
    final KeyStore ks = KeyStore.getInstance(STORE_TYPE);
    ks.load(null, null);
    final Certificate cert = ks.getCertificate(name);
    Array.writeFile(pathh, cert.getEncoded());
}

public static PrivateKey loadKey(String name, char[] password)
        throws Exception {
    final KeyStore hdImageStore = KeyStore.getInstance(CMStools.STORE_TYPE);
    hdImageStore.load(null, null);
    return (PrivateKey) hdImageStore.getKey(name, password);
}

public static Certificate loadCertificate(String name)
        throws Exception {
    final KeyStore hdImageStore = KeyStore.getInstance(CMStools.STORE_TYPE);
    hdImageStore.load(null, null);
    return hdImageStore.getCertificate(name);
}

public static Certificate readCertificate(String fileName) throws IOException,
        CertificateException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    final Certificate cert;
    try {
        fis = new FileInputStream(fileName);
        bis = new BufferedInputStream(fis);
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        cert = cf.generateCertificate(bis);
        return cert;
    } finally {
        if (bis != null) bis.close();
        if (fis != null) fis.close();
    }
}

public static byte[] digestm(byte[] bytes, String digestAlgorithmName)
        throws Exception {
    return digestm(bytes, digestAlgorithmName, JCP.PROVIDER_NAME);
}

public static byte[] digestm(byte[] bytes, String digestAlgorithmName,
     String providerName) throws Exception {

    //calculation messageDigest
    final ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
    final MessageDigest digest = MessageDigest.getInstance(digestAlgorithmName, providerName);
    final DigestInputStream digestStream = new DigestInputStream(stream, digest);

    while (digestStream.available() != 0) digestStream.read();

    return digest.digest();
}
}
