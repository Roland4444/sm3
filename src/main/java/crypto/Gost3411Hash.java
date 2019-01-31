package crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
import java.util.Arrays;
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

    public byte[] decryptKey(String encryptedBase64Key, String secretString) throws Exception{
        byte[] secretBytes = HexStringToByteArray(secretString);
        SecretKey secret = new SecretKeySpec(secretBytes, 0, secretBytes.length, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(HexStringToByteArray("00000000000000000000000000000000"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, ivspec);
        return cipher.doFinal(Base64.getDecoder().decode(encryptedBase64Key));
    }

    public String onlyKey(String encryptedBase64Key, String secretString) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] secretBytes = HexStringToByteArray(secretString);
        SecretKey secret = new SecretKeySpec(secretBytes, 0, secretBytes.length, "AES");
        IvParameterSpec ivspec = new IvParameterSpec(HexStringToByteArray("00000000000000000000000000000000"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, ivspec);
        byte[] full = cipher.doFinal(Base64.getDecoder().decode(encryptedBase64Key));
        byte[] decrypted_arr = new byte[64];
        for (int i=0;i<=63;i++)
            decrypted_arr[i]=full[i];
        return ByteArrayToHexString(decrypted_arr);
    }




    public String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }
    /**
     * Utility method to convert a hexadecimal string to a byte string.
     *
     * <p>Behavior with input strings containing non-hexadecimal characters is undefined.
     *
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws java.lang.IllegalArgumentException if input length is incorrect
     */
    public byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    /**
     * Utility method to concatenate two byte arrays.
     * @param first First array
     * @param rest Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }





}


