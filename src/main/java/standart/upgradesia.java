package standart;

import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
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
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class upgradesia extends Standart {
    public final String root ="<tns:ESIARegisterBySimplifiedRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register_by_simplified/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
            "    <tns:RoutingCode>DEV</tns:RoutingCode>\n" +
            "\t<tns:SnilsOperator></tns:SnilsOperator>\n" +
            "    <tns:oid></tns:oid>\n" +
            "    <tns:ra></tns:ra>\n" +
            "    <tns:snils></tns:snils>\n" +
            "    <tns:lastName></tns:lastName>\n" +
            "    <tns:firstName></tns:firstName>\n" +
            "    <tns:middleName></tns:middleName>\n" +
            "    <tns:gender></tns:gender>\n" +
            "    <tns:birthDate></tns:birthDate>\n" +
            "    <tns:birthPlace></tns:birthPlace>\n" +
            "    <tns:citizenship></tns:citizenship>\n" +
            "    <tns:doc>\n" +
            "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
            "        <ns2:series></ns2:series>\n" +
            "        <ns2:number></ns2:number>\n" +
            "        <ns2:issueId></ns2:issueId>\n" +
            "        <ns2:issueDate></ns2:issueDate>\n" +
            "        <ns2:issuedBy></ns2:issuedBy>\n" +
            "    </tns:doc>\n" +
            "    <tns:addressRegistration>\n" +
            "        <ns2:type>PLV</ns2:type>\n" +
            "        <ns2:region>23</ns2:region>\n" +
            "        <ns2:fiasCode>720b25da-f43e-4204-9013-3cb06be3e9e4</ns2:fiasCode>\n" +
            "        <ns2:addressStr>Кемеровская Область, Таштагольский Район, Шерегеш Поселок городского типа</ns2:addressStr>\n" +
            "        <ns2:countryId>RUS</ns2:countryId>\n" +
            "        <ns2:zipCode>394000</ns2:zipCode>\n" +
            "        <ns2:street>Советская Улица</ns2:street>\n" +
            "        <ns2:house>86/1</ns2:house>\n" +
            "        <ns2:flat>пом.419</ns2:flat>\n" +
            "        <ns2:frame>204у</ns2:frame>\n" +
            "        <ns2:building>e</ns2:building>\n" +
            "    </tns:addressRegistration>\n" +
            "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
            "    <tns:mode>mobile</tns:mode>\n" +
            "</tns:ESIARegisterBySimplifiedRequest>";
    public String rawxml = inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", root);

    public upgradesia(StreamResult sr, SignerXML sihner, util.Sign personal, Sign Full){
        this.out = sr;
        this.signer =sihner;
        this.personal=personal;
        this.MainSign =Full;
    }


    public void setLink(TempDataContainer temp){
        this.temp=temp;
    }
    public  byte[] GetSoap() throws IOException {
        return InfoToRequest;
    };
    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, XMLSecurityException,
            IOException, CertificateException, NoSuchAlgorithmException, TransformerException,
            ParserConfigurationException, UnrecoverableEntryException,
            NoSuchProviderException, SAXException, KeyStoreException {
        return signer.signconsumerns4(MainSign, GetSoap());
    };

    public byte[] GetResponseRequest() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(MainSign, GetSoap()));
        StreamSource input=new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] GetResponseRequestwoFilter() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(MainSign, GetSoap()));
        StreamSource input=new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] ack() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(MainSign, GetSoap()));
        StreamSource input=new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] GetResponceRequestCompiled() throws Exception {
        String prepared = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:GetResponseRequest>\n" +
                "      <ns2:MessageTypeSelector xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\">\n" +
                "        <ns2:NamespaceURI>urn://mincomsvyaz/esia/reg_service/find_account/1.4.1</ns2:NamespaceURI>" +
                "        <ns2:RootElementLocalName>request</ns2:RootElementLocalName>\n" +
                "        <ns2:Timestamp>2018-07-24T16:06:49.623</ns2:Timestamp>\n" +
                "      </ns2:MessageTypeSelector>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:GetResponseRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        setinput(prepared.getBytes());
        return GetResponseRequestwoFilter();

    }

    public byte[] GetResponceFilteredCompiled() throws Exception{
        return GetResponceRequestCompiled();
    }

    public byte[] Ack(String id) throws Exception {
        String prepared = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:AckRequest>\n" +
                "      <ns2:AckTargetMessage xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\" accepted=\"true\">e1a4c9f0-7eba-11e8-83c9-fa163e24a723</ns2:AckTargetMessage>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:AckRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        this.setinput(inj.injectTagDirect(prepared, "ns2:AckTargetMessage", id).getBytes());
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(MainSign, GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    @Override
    public byte[] generateUnsSOAP(byte[] input) throws IOException {
        ESIAFindMessageInitial restored = (ESIAFindMessageInitial) BinaryMessage.restored(input);
        String dwithId0 = inj.injectTag(rawxml, ":MessageID>", gen.generate());
        String dwithId = inj.flushTagData(dwithId0, "CallerInformationSystemSignature");
        String dwithId2 = inj.flushTagData(dwithId, "ns:PersonalSignature");
        return injectdatainXML(restored, dwithId2).getBytes();
    }


    public  String getName(){
        return "UPGRADEESIA";
    }

    public  boolean check(byte[] input) throws IOException {
        if (input==null)
            return false;

        String input2 = new String(input);
        if (input2.indexOf("Id=\"SIGNED_BY_CONSUMER\"")<0)
            return false;
        if (input2.indexOf("URI=\"#SIGNED_BY_CONSUMER\"")<0)
            return false;
        System.out.println("Extracted!"+this.ext.parseTagFromByte(input2.getBytes(), "ns4:CallerInformationSystemSignature"));
        System.out.println("STOP!");
        System.out.println("MESSAGE\n\n"+input2);
        if (this.ext.parseTagFromByte(input2.getBytes(), "ns4:CallerInformationSystemSignature")==null ||
                (this.ext.parseTagFromByte(input2.getBytes(), "ns4:CallerInformationSystemSignature").length()==0))
            return false;
        System.out.println("Extracted===>>"+this.ext.extractAttribute(input, "Id"));
        if (this.ext.extractAttribute(input, "Id")==null) return false;
        if (!this.ext.extractAttribute(input, "Id").equals("SIGNED_BY_CONSUMER"))
            return false;
        return true;
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


    public String injectdatainXML(ESIAFindMessageInitial msg, String whereInject) {
        String[] massive = new String[9];
        printMSG(msg);
        massive[0] = inj.injectTag(whereInject, "tns:SnilsOperator>", msg.OperatorSnils);
        massive[1] = inj.injectTag(massive[0], "tns:ra>", msg.Ra);
        massive[2] = inj.injectTag(massive[1], "tns:lastName>", msg.Surname);
        massive[3] = inj.injectTag(massive[2], "tns:firstName>", msg.Name);
        massive[4] = inj.injectTag(massive[3], "tns:middleName>", msg.MiddleName);
        massive[5] = inj.injectTag(massive[4], "ns2:series>", msg.Passseria);
        massive[6] = inj.injectTag(massive[5], "ns2:number>", msg.Passnumber);
        massive[7] = inj.injectTag(massive[6], "tns:mobile>", msg.MobileNumber);
        massive[8] = inj.injectTag(massive[7], "tns:snils>", msg.SNILS);
        return massive[8];
    }


    public void printMSG(ESIAFindMessageInitial msg){
        System.out.println("##############\n"+msg.OperatorSnils);
        System.out.println(msg.Ra);
        System.out.println(msg.Surname);
        System.out.println(msg.Name);
        System.out.println(msg.MiddleName);
        System.out.println(msg.Passseria);
        System.out.println(msg.Passnumber);
        System.out.println(msg.MobileNumber);
        System.out.println(msg.SNILS);
    }

}
