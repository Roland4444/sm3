package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.xml.sax.SAXException;
import schedulling.abstractions.TempDataContainer;
import util.Sign;
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

public class createsia extends Standart {
    public createsia(StreamResult sr, SignerXML sihner, util.Sign personal, Sign Full){
        this.out = sr;
        this.signer =sihner;
        this.personal=personal;
        this.MainSign =Full;
    }

    public final String root ="<tns:ESIARegisterRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
            "    <tns:RoutingCode>DEV</tns:RoutingCode>\n" +
            "\t<tns:SnilsOperator></tns:SnilsOperator>\n" +
            "    <tns:ra></tns:ra>\n" +
            "    <tns:snils></tns:snils>\n" +
            "    <tns:lastName></tns:lastName>\n" +
            "    <tns:firstName></tns:firstName>\n" +
            "    <tns:middleName></tns:middleName>\n" +
            "    <tns:gender></tns:gender>\n" +
            "    <tns:birthDate></tns:birthDate>\n" +
            "    <tns:doc>\n" +
            "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
            "        <ns2:series></ns2:series>\n" +
            "        <ns2:number></ns2:number>\n" +
            "        <ns2:issueId></ns2:issueId>\n" +
            "        <ns2:issueDate></ns2:issueDate>\n" +
            "        <ns2:issuedBy></ns2:issuedBy>\n" +
            "    </tns:doc>\n" +
            "    <tns:mobile></tns:mobile>\n" +
            "    <tns:citizenship>RUS</tns:citizenship>\n" +
            "    <tns:mode>mobile</tns:mode>\n" +
            "    <tns:address>\n" +
            "        <ns2:type>PLV</ns2:type>\n" +
            "        <ns2:region></ns2:region>\n" +
            "        <ns2:fiasCode></ns2:fiasCode>\n" +
            "        <ns2:addressStr></ns2:addressStr>\n" +
            "        <ns2:countryId>RUS</ns2:countryId>\n" +
            "        <ns2:zipCode></ns2:zipCode>\n" +
            "        <ns2:street></ns2:street>\n" +
            "        <ns2:house></ns2:house>\n" +
            "        <ns2:flat></ns2:flat>\n" +
            "        <ns2:frame></ns2:frame>\n" +
            "        <ns2:building></ns2:building>\n" +
            "    </tns:address>\n" +
            "    <tns:birthPlace></tns:birthPlace>\n" +
            "</tns:ESIARegisterRequest>";
    public String rawxml = inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", root);
    public void setLink(TempDataContainer temp){
        this.temp=temp;
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
        return signer.signconsumerns4(MainSign, GetSoap());
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
