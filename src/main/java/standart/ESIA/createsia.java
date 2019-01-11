
package standart.ESIA;
import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIACreate.ESIACreateInit;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.xml.sax.SAXException;
import schedulling.abstractions.Sign;
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

public class createsia extends Standart {
    public createsia(StreamResult sr, SignerXML sihner, Injector inj, Transport transport, TempDataContainer temp){
        super(sr, sihner, inj, transport, temp);
        rawxml = inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", root);
    }

    public final String root ="<tns:ESIARegisterRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
            "    <tns:RoutingCode></tns:RoutingCode>\n" +
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
        return signer.signconsumerns4(signer.getmainSign(), GetSoap());
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
        ESIACreateInit restored = (ESIACreateInit) BinaryMessage.restored(input);
        String dwithId0 = inj.injectTag(rawxml, ":MessageID>", gen.generate());
        String dwithId = inj.flushTagData(dwithId0, "CallerInformationSystemSignature");
        String dwithId2 = inj.flushTagData(dwithId, "ns:PersonalSignature");
        return injectdatainXML(restored, dwithId2).getBytes();
    }

    public String injectdatainXML(ESIACreateInit msg, String whereInject) {
        String[] massive = new String[25];
        massive[0] = inj.injectTag(whereInject, "tns:SnilsOperator>", msg.SNILSOper);
        massive[1] = inj.injectTag(massive[0], "tns:ra>", msg.RA);
        massive[2] = inj.injectTag(massive[1], "tns:lastName>", msg.Surname);
        massive[3] = inj.injectTag(massive[2], "tns:firstName>", msg.Name);
        massive[4] = inj.injectTag(massive[3], "tns:middleName>", msg.MiddleName);
        massive[5] = inj.injectTag(massive[4], "ns2:series>", msg.PassSeria);
        massive[6] = inj.injectTag(massive[5], "ns2:number>", msg.PassNumber);
        massive[7] = inj.injectTag(massive[6], "tns:mobile>", msg.Mobile);
        massive[8] = inj.injectTag(massive[7], "tns:snils>", msg.SNILS);
        massive[9] = inj.injectTag(massive[8], "tns:gender>", msg.Gender.toString());
        massive[10] = inj.injectTag(massive[9], "tns:birthDate>", msg.Birthdate);
        massive[11] = inj.injectTag(massive[10], "ns2:issueId>", msg.IssuedPassID);
        massive[12] = inj.injectTag(massive[11], "ns2:issueDate>", msg.IssuedDatePass);
        massive[13] = inj.injectTag(massive[12], "ns2:issuedBy>", msg.IssuedBy);
        massive[14] = inj.injectTag(massive[13], "ns2:region>", msg.Region);
        massive[15] = inj.injectTag(massive[14], "ns2:fiasCode>", msg.FIAS);
        massive[16] = inj.injectTag(massive[15], "ns2:addressStr>", msg.AddressStr);
        massive[17] = inj.injectTag(massive[16], "ns2:zipCode>", msg.ZIP);
        massive[18] = inj.injectTag(massive[17], "ns2:street>", msg.Street);
        massive[19] = inj.injectTag(massive[18], "ns2:house>", msg.House);
        massive[20] = inj.injectTag(massive[19], "ns2:flat>", msg.Flat);
        massive[21] = inj.injectTag(massive[20], "ns2:frame>", msg.Frame);
        massive[22] = inj.injectTag(massive[21], "ns2:building>", msg.Building);
        massive[23] = inj.injectTag(massive[22], "tns:birthPlace>", msg.BirthPlace);
        if (this.ProdModeRoutingEnabled)
            massive[24] = inj.injectTag(massive[23], "tns:RoutingCode>", "PROD");
        else
            massive[24] = inj.injectTag(massive[23], "tns:RoutingCode>", "DEV");
        return massive[24];
    }
}
