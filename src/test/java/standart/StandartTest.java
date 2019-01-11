package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.abstractions.Sign;
import schedulling.abstractions.TempDataContainer;
import se.roland.Extractor;
import transport.SAAJ;
import util.*;
import util.crypto.Sign2018;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class StandartTest {
    Extractor ext = new Extractor();
    Injector inj = new Injector();
    Sign s = new Sign2018();
    SignerXML x = new SignerXML(s,s);
    Sign2018 ps = new Sign2018();;
    OutputStream os = new ByteArrayOutputStream();
    StreamResult sr = new StreamResult(os);
    boolean supress= false;
    gis gis = new gis(sr,x, inj, new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl"), new TempDataContainer());
    egr inn = new egr(sr,x, inj, new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl"), new TempDataContainer());

    public StandartTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
    }

    @Test
    public void getResReq() throws Exception {
        egr egr = new egr(sr,x, inj, new SAAJ("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl"), new TempDataContainer());

        assertNotEquals(null, egr.GetResReq());

    }
}