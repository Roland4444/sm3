package crypto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class Gost3411Hash {
    public Gost3411Hash(){
        Security.addProvider(new BouncyCastleProvider());
    }

    public static final byte[] toByteArray( String hexString )
    {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];
        for ( int ii = 0; ii < arrLength; ii++ ){
            int index = ii << 1;
            String l_digit = hexString.substring( index, index + 2 );
            buf[ii] = ( byte ) Integer.parseInt( l_digit, 16 );
        }
        return buf;
    }

    public String swapString(String in){
        String res="";
        int i=0;
        while (i<=in.length()-1){
            res+=in.charAt(i+1);
            res+=in.charAt(i);
            i=i+2;
        }
        return res;
    }

    public String h(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        md.update( data.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        BigInteger out = new BigInteger( 1, digest );
        String hex = String.format( "%02x", new BigInteger( 1, digest ) );
        return out.toString(16);
    }

    public byte[] hash_byte(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        md.update( data.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        return digest;
    }

    public byte[] hash_byte(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        md.update( data );
        byte[] digest = md.digest();
        return digest;
    }

    public String base64(byte[] input){
        return new sun.misc.BASE64Encoder().encode(input);
    }

    public String h_Base64rfc2045(String data) throws NoSuchAlgorithmException {
        return base64(hash_byte(data));
    }

    public String h_Base64rfc2045(byte[] data) throws NoSuchAlgorithmException {
        return base64(hash_byte(data));
    }

    public String h_swapped(String data) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        md.update( data.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        BigInteger out = new BigInteger( 1, digest );
        String hex = String.format( "%02x", new BigInteger( 1, digest ) );
        System.out.println(hex);
        return swapString(out.toString(16));
    }

    public byte[] getBytesFromBase64(String input){
        return Base64.getDecoder().decode(input);
    }


    public byte[] swapBytes(byte[] in){
        byte[] res=new byte[in.length];
        System.out.println("len shifting bytes="+in.length);
        int i=0;
        int j=0;
        while (i<in.length){
            res[j++]=in[i+1];
            res[j++]=in[i];
            i+=2;
        }
        return res;
    }

    public X509Certificate getCert(String input) throws CertificateException {
        InputStream in = new ByteArrayInputStream(getBytesFromBase64(input));
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) factory.generateCertificate(in);
        return cert;//new X509Certificate(inputStream);
    }



    public String getOrg(String input) throws CertificateException {
        System.out.println(getCert(input).getBasicConstraints());
        System.out.println(getCert(input).getSubjectDN().toString());
        System.out.println(getCert(input).getIssuerDN().toString());

        return getCert(input).getSerialNumber().toString(); //new String(getCert(rawxml).getNotAfter());
    };

    public String getSerial(String input) throws CertificateException {
        return getCert(input).getSerialNumber().toString();
    };

    public String getExpires(String input) throws CertificateException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date1= getCert(input).getNotBefore();
        Date date2 = getCert(input).getNotAfter();
        return dateFormat.format(date1)+"/"+dateFormat.format(date2);
    };


    public void pfx12() throws NoSuchProviderException, KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
        String path = "certs/vkabank.pfx";
        char[] pass = "1".toCharArray();
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance("vkabank", provider.getName());
        ks.load(new FileInputStream(path), "1".toCharArray());
        String alias = (String) ks.aliases().nextElement(); /* alias='CCA India 2011\u0000'*/
        PrivateKey pk = (PrivateKey) ks.getKey("vkabank", "1".toCharArray());/* returns null */
        Certificate[] chain = (Certificate[]) ks.getCertificateChain("vkabank");/* returns null */
        X509Certificate last = (X509Certificate) chain[chain.length - 1];
        System.out.println(last.getNotBefore());
        System.out.println(last.getNotAfter());
    }
}


