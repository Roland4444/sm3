package standart.ESIA;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.xml.sax.SAXException;
import schedulling.abstractions.TempDataContainer;
import standart.Standart;
import transport.Transport;
import util.Injector;
import util.SignatureProcessorException;
import util.SignerXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class simpleIden extends Standart {
    public final String root = "<tns:ESIADataVerifyRequest xmlns:tns=\"urn://mincomsvyaz/esia/uprid/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
            "    <tns:RoutingCode></tns:RoutingCode>\n" +
            "    <tns:passportSeries></tns:passportSeries>\n" +
            "    <tns:passportNumber></tns:passportNumber>\n" +
            "    <tns:lastName></tns:lastName>\n" +
            "    <tns:firstName></tns:firstName>\n" +
            "    <tns:middleName></tns:middleName>\n" +
            "    <tns:snils></tns:snils>\n" +
            "</tns:ESIADataVerifyRequest>";
    public simpleIden(StreamResult sr, SignerXML sihner, Injector inj, Transport transport, TempDataContainer temp){
        super(sr, sihner, inj, transport, temp);
        rawxml = inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", root);
    }
    @Override
    public boolean check(byte[] input) throws IOException {
        return false;
    }

    @Override
    public void setinput(byte[] input) throws IOException {
        String textInput = new String(input);
        System.out.println("INTO SETTING INPUT!!!!");
        String genned = gen.generate();
        this.temp.StringContainer = genned;
        System.out.println("generated" + this.temp.StringContainer);
        System.out.println(genned);
        String dwithId0 = inj.injectTag(textInput, ":MessageID>", genned);
        if (this.bypassID) {
            this.temp.StringContainer = ext.extractTagValue(input, ":MessageID");
            dwithId0 = textInput;
        }
        String dwithId = inj.flushTagData(dwithId0, "CallerInformationSystemSignature");
        String dwithId2 = inj.flushTagData(dwithId, "ns:PersonalSignature");
        this.InfoToRequest = dwithId2.getBytes();
    }

    @Override
    public byte[] GetSoap() throws IOException {
        return InfoToRequest;
    }

    @Override
    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, XMLSecurityException, IOException, CertificateException, NoSuchAlgorithmException, TransformerException, ParserConfigurationException, UnrecoverableEntryException, NoSuchProviderException, SAXException, KeyStoreException {
        return new byte[0];
    }

    @Override
    public byte[] GetResponceFilteredCompiled() throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] GetResponceRequestCompiled() throws Exception {
        return new byte[0];
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public byte[] generateUnsSOAP(byte[] input) throws IOException {
        return new byte[0];
    }
}
