package util;

import org.junit.Test;
import transport.SAAJ;
import transport.Transport;

import static org.junit.Assert.assertNotEquals;

public class SAAJTest {

    @Test
    public void send() throws Exception {
        String file = "xml4test/1withoutcert.xml";
        Transport saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(file, "results.xml"));
    }

    @Test
    public void sendFormed() throws Exception {
        String withIdHashSigCert4 = "xml4test/withIdHashSigCert4.xml";
        Transport saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert4, "responce.xml"));
    }

    @Test
    public void normal() throws Exception {
        String withIdHashSigCert4 = "xml4test/sendReqMod.xml";
        Transport saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert4, "responce.xml"));
    }

    @Test
    public void normalSignedHash() throws Exception {
        String withIdHashSigCert = "xml4test/result2.xml";

        Transport saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert, "responce.xml"));
      //  assertNotEquals(null, saa.send(base64HashSign, "responce.xml"));

    }

    @Test
    public void normalSignedH22ash() throws Exception {
        String withIdHashSigCert = "xml4test/result.xml";

        Transport saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert, "responce.xml"));
        //  assertNotEquals(null, saa.send(base64HashSign, "responce.xml"));

    }

    @Test
    public void sendvipiop() throws Exception {
        String withIdHashSigCert = "xml4test/resultvipip.xml";
        Transport saa= new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl");
        assertNotEquals(null, saa.send(withIdHashSigCert, "responce.xml"));
        //  assertNotEquals(null, saa.send(base64HashSign, "responce.xml"));

    }



}

