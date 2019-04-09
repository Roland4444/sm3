package standart;
import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import org.xml.sax.SAXException;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;
import util.crypto.TestSign2001;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
public class findesiaTest {
    String initial = "<?xml version=\"1.0\" encoding=\"utf-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>659e8860-521c-11e9-8745-1c6f6549be6b</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\"><tns:RoutingCode>TESIA</tns:RoutingCode><tns:SnilsOperator>000-000-600 06</tns:SnilsOperator><tns:ra>1000300890</tns:ra><tns:lastName>ТЩипакина</tns:lastName><tns:firstName>ТАнастасия</tns:firstName><tns:middleName>ТАндреевна</tns:middleName><tns:doc><ns2:type>RF_PASSPORT</ns2:type><ns2:series>1213</ns2:series><ns2:number>525129</ns2:number></tns:doc><tns:mobile>+7(964)8827042</tns:mobile></tns:ESIAFindAccountRequest></ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" /><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" /><Reference URI=\"#SIGNED_BY_CONSUMER\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /><Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" /><DigestValue>iZWFNJfr+VNSIE7u2fWmYnrZlf9Uo6OYWU2/u4BzEGk=</DigestValue></Reference></SignedInfo><SignatureValue>ErXmaqSOL6AqfED0gEZJuxHIfI1NtebZXI0++Ng/5tGiPyxlFpKHDTu9B+hmvdvfnE9BdKa/OG0PhrMbCBx7Ug==</SignatureValue><KeyInfo><X509Data><X509Certificate>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</X509Certificate></X509Data></KeyInfo></Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";


    String preparedwithoutTest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>eae76b19-1955-11e9-b5f4-5d9abb5f304e</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
            "   \t<tns:RoutingCode>DEV</tns:RoutingCode>\n" +
            "  \t<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
            "    <tns:ra>1000321282</tns:ra>\n" +
            "    <tns:lastName>Тестов</tns:lastName>\n" +
            "    <tns:firstName>Тест</tns:firstName>\n" +
            "    <tns:middleName>Тестович</tns:middleName>\n" +
            "    <tns:doc>\n" +
            "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
            "        <ns2:series>1111</ns2:series>\n" +
            "        <ns2:number>111111</ns2:number>\n" +
            "    </tns:doc>\n" +
            "<tns:mobile>+7(920)4021351</tns:mobile>\n" +
            "<tns:snils>229-785-346 20</tns:snils>\n" +
            "</tns:ESIAFindAccountRequest>\n" +
            "</ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>zCvp3v3nyMlr88Df//vgxtCfC53Hcgnf5TX9WgGZMhM=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>Yntz620M/TR8WXTNTAuVVJs4cSGUiRhZGpiBpzeF5Jbd8Rjlox9dfd3eSIJdI5YOxtPQTqo9woxcnYQcfidTnw==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
    String testSend = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>655939d2-14ab-11e9-86ab-c964c8d784c4</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
            "   \t<tns:RoutingCode>DEV</tns:RoutingCode>\n" +
            "  \t<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
            "    <tns:ra>1000321282</tns:ra>\n" +
            "    <tns:lastName>Тестов</tns:lastName>\n" +
            "    <tns:firstName>Тест</tns:firstName>\n" +
            "    <tns:middleName>Тестович</tns:middleName>\n" +
            "    <tns:doc>\n" +
            "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
            "        <ns2:series>1111</ns2:series>\n" +
            "        <ns2:number>111111</ns2:number>\n" +
            "    </tns:doc>\n" +
            "<tns:mobile>+7(920)4021351</tns:mobile>\n" +
            "<tns:snils>229-785-346 20</tns:snils>\n" +
            "</tns:ESIAFindAccountRequest>\n" +
            "</ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>akIKkkpYkl2zGDxXg5okdOPS/bYxgo5zPC+hOhNAgzs=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>GrtqFffEnOi2ZIuIaRexTJFEa8HTzKP5J5JQJZwZhpABYkusvb+u/B1iKRQuMNaPgpfXnZI9kV9YRQMvIVgpWQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
    DependencyContainer deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()));
    public findesiaTest() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException {
        msg.ID="";
        msg.Ra="1000321282";
        msg.Surname="Тестов";
        msg.Name="Тест";
        msg.MiddleName="Тестович";
        msg.OperatorSnils="135-419-238 52";
        msg.Passseria="1111";
        msg.Passnumber="111111";
        msg.MobileNumber="+7(920)4021351";
        msg.SNILS="229-785-346 20";


        msg2.ID="";
        msg2.Ra="1000321282";
        msg2.Surname="Тестовчук";
        msg2.Name="Тестово";
        msg2.MiddleName="Тестовиччевич";
        msg2.OperatorSnils="135-419-238 52";
        msg2.Passseria="2222";
        msg2.Passnumber="888888";
        msg2.MobileNumber="+7(920)7898887";
        msg2.SNILS="229-785-777 20";

    }
    ESIAFindMessageInitial msg = new ESIAFindMessageInitial();
    ESIAFindMessageInitial msg2 = new ESIAFindMessageInitial();

    @Test
    public void generateSOAP() {

        //deps.findesia.set
        assertNotEquals(null, deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
        System.out.println(deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
    }

    @Test
    public void sendPreapred() throws Exception {
        deps.findesia.setinput(testSend.getBytes());

        deps.findesia.SendSoapSigned();
    }

    @Test
    public void sendPreparedWithoutTest() throws Exception {
        deps.findesia.setinput(preparedwithoutTest.getBytes());
        deps.findesia.SendSoapSigned();
    }

    @Test
    public void GetresponcerequestwoFilter() throws Exception {//2014-02-11T17:10:03.616+04:00
        String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2018-07-18T09:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        String originalid = deps.ext.extractTagValue(response,":OriginalMessageId");
        String messageId = deps.ext.extractTagValue(response,":MessageId");
        System.out.println("\n@\n"+ originalid);
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
        if (originalid!=null)
            deps.gis.Ack(messageId);



    }

    @Test
    public void sendSimple() throws Exception {

        System.out.println("RAW=>"+deps.findesia.rawxml);
        deps.findesia.setinput(deps.findesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg)));
        assertNotEquals(null, deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
        System.out.println(deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
        deps.findesia.SendSoapSigned();

    }


    @Test
    public void sendSimple2() throws Exception {

        deps.findesia.setinput(deps.findesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg2)));
        assertNotEquals(null, deps.findesia.injectdatainXML(msg2, deps.findesia.rawxml));
        System.out.println(deps.findesia.injectdatainXML(msg2, deps.findesia.rawxml));
        deps.findesia.SendSoapSigned();

    }

    @Test
    public void generateSOAP1() throws IOException {
        String etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "   \t<tns:RoutingCode></tns:RoutingCode>\n" +
                "  \t<tns:SnilsOperator></tns:SnilsOperator>\n" +
                "    <tns:ra></tns:ra>\n" +
                "    <tns:lastName></tns:lastName>\n" +
                "    <tns:firstName></tns:firstName>\n" +
                "    <tns:middleName></tns:middleName>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series></ns2:series>\n" +
                "        <ns2:number></ns2:number>\n" +
                "    </tns:doc>\n" +
                "<tns:mobile></tns:mobile>\n" +
                "<tns:snils></tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n</ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println(deps.findesia.emptySOAP);
        System.out.println(deps.findesia.root);
        System.out.println(deps.findesia.rawxml);

        assertEquals(etalon, deps.findesia.rawxml);
    //    assertNotEquals(etalon, deps.findesia.buildSOAPunsigned(msg));
     //   System.out.println(deps.findesia.buildSOAPunsigned(msg));

    }

    @Test
    public void generateUnsSOAP() throws IOException {

        ESIAFindMessageInitial msg0 = new ESIAFindMessageInitial();
        msg0.OperatorSnils="135-419-238 52";
        msg0.MobileNumber="+7(920)4021351";
        msg0.Ra="1000321282";
        msg0.Name="Тест";
        msg0.MiddleName="Тестович";
        msg0.Surname="Тестов";
        msg0.Passseria="1111";
        msg0.Passnumber="111111";
        msg0.SNILS="229-785-346 20";
        String etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "   \t<tns:RoutingCode>DEV</tns:RoutingCode>\n" +
                "  \t<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
                "    <tns:ra>1000321282</tns:ra>\n" +
                "    <tns:lastName>Тестов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Тестович</tns:middleName>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>1111</ns2:series>\n" +
                "        <ns2:number>111111</ns2:number>\n" +
                "    </tns:doc>\n" +
                "<tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "<tns:snils>229-785-346 20</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n</ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println(deps.findesia.emptySOAP);
        System.out.println(deps.findesia.root);
        System.out.println(deps.findesia.rawxml);
        String result = new String(deps.findesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg0)));
        String flushed = deps.inj.flushTagData(result, "ns:MessageID");
        assertEquals(etalon, flushed);
        System.out.println(flushed);
    }

    @Test
    public void getSoap() {
        //String EtalonRAWEmptrySoapwoTestMessageTeag ="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent></ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        String EtalonRAWEmptrySoapwoTestMessageTeag ="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";

        assertEquals(EtalonRAWEmptrySoapwoTestMessageTeag, deps.findesia.emptySOAP);
    }

    @Test
    public void findSpecified() throws Exception {
        String init="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>zGz</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "            <tns:RoutingCode>TESIA</tns:RoutingCode>\n" +
                "            <tns:SnilsOperator>000-000-600 06</tns:SnilsOperator>\n" +
                "            <tns:ra>1000300890</tns:ra>\n" +
                "            <tns:lastName>Èñìàèëîâ</tns:lastName>\n" +
                "            <tns:firstName>Òåñò</tns:firstName>\n" +
                "            <tns:middleName>Áàíêîâè÷</tns:middleName>\n" +
                "            <tns:doc>\n" +
                "              <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "              <ns2:series>0009</ns2:series>\n" +
                "              <ns2:number>123123</ns2:number>\n" +
                "            </tns:doc>\n" +
                "            <tns:mobile>+7(988)0693468</tns:mobile>\n" +
                "            <tns:snils>000-303-303 61</tns:snils>\n" +
                "          </tns:ESIAFindAccountRequest>\n" +
                "        </ns2:MessagePrimaryContent>\n" +
                "      </ns:SenderProvidedRequestData>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" ></ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";
        deps.findesia.setinput(init.getBytes());
        assertNotEquals(null, deps.findesia.injectdatainXML(msg2, deps.findesia.rawxml));
        System.out.println(deps.findesia.injectdatainXML(msg2, deps.findesia.rawxml));
        deps.findesia.SendSoapSigned();
    }

    @Test
    public void findismailov() throws Exception {
        String blob ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>d8161c55-1ecc-11e9-90eb-1dec74fcc959</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "   \t<tns:RoutingCode>TESIA</tns:RoutingCode>\n" +
                "  \t<tns:SnilsOperator>000-000-600 06</tns:SnilsOperator>\n" +
                "    <tns:ra>1000300890</tns:ra>\n" +
                "    <tns:lastName>Исмаилов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Банкович</tns:middleName>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>0009</ns2:series>\n" +
                "        <ns2:number>123123</ns2:number>\n" +
                "    </tns:doc>\n" +
                "<tns:mobile>+7(988)0693468</tns:mobile>\n" +
                "<tns:snils>000-303-303 61</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n" +
                "</ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>HUVEqoOE3Rkngx6/ic6xBFtRm1C4YxRBIroEBr0+D6o=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>skAUTOLg7zoPMX/04w35xbCEFF2rok+Cs5mbnzj0075Ouy4oQ3jriElEod4n6opc/1vqgkK3CGEnbEL9ROCP/g==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIHvDCCB2ugAwIBAgIRAXILAVZQABCz6BEejDlOj3AwCAYGKoUDAgIDMIIBRjEYMBYGBSqFA2QBEg0xMjM0NTY3ODkwMTIzMRowGAYIKoUDA4EDAQESDDAwMTIzNDU2Nzg5MDEpMCcGA1UECQwg0KHRg9GJ0LXQstGB0LrQuNC5INCy0LDQuyDQtC4gMjYxFzAVBgkqhkiG9w0BCQEWCGNhQHJ0LnJ1MQswCQYDVQQGEwJSVTEYMBYGA1UECAwPNzcg0JzQvtGB0LrQstCwMRUwEwYDVQQHDAzQnNC+0YHQutCy0LAxJDAiBgNVBAoMG9Ce0JDQniDQoNC+0YHRgtC10LvQtdC60L7QvDEwMC4GA1UECwwn0KPQtNC+0YHRgtC+0LLQtdGA0Y/RjtGJ0LjQuSDRhtC10L3RgtGAMTQwMgYDVQQDDCvQotC10YHRgtC+0LLRi9C5INCj0KYg0KDQotCaICjQoNCi0JvQsNCx0YEpMB4XDTE4MDcyMDEzMDE0MVoXDTE5MDcyMDEzMTE0MVowgfAxHTAbBgkqhkiG9w0BCQIMDtCS0JrQkNCR0JDQndCaMRowGAYIKoUDA4EDAQESDDAwMzAxNTAxMTc1NTEYMBYGBSqFA2QBEg0xMDIzMDAwMDAwMjEwMRwwGgYDVQQKDBPQkNCeINCS0JrQkNCR0JDQndCaMRswGQYDVQQHDBLQkNGB0YLRgNCw0YXQsNC90YwxMzAxBgNVBAgMKjMwINCQ0YHRgtGA0LDRhdCw0L3RgdC60LDRjyDQvtCx0LvQsNGB0YLRjDELMAkGA1UEBhMCUlUxHDAaBgNVBAMME9CQ0J4g0JLQmtCQ0JHQkNCd0JowYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARAqjtC1dM6zvtwmhJbUMVVOiC+8kbOOgufkJJFKHy5rMaFG6jWxUiGKvI8AAcEE7rP93ui2TMVzaDecGOrspIW6KOCBIMwggR/MA4GA1UdDwEB/wQEAwIE8DAdBgNVHQ4EFgQU541ASZ2wBv/db7s8wxlcnshsQxAwggGIBgNVHSMEggF/MIIBe4AUPu8ZPw+5ebDx5ikho+S5lbml7pChggFOpIIBSjCCAUYxGDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1Njc4OTAxKTAnBgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZIhvcNAQkBFghjYUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEVMBMGA1UEBwwM0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+0LwxMDAuBgNVBAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIGA1UEAwwr0KLQtdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKYIRAXILAVZQALmz5xHPOr40d6AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMCcGCSsGAQQBgjcVCgQaMBgwCgYIKwYBBQUHAwIwCgYIKwYBBQUHAwQwHQYDVR0gBBYwFDAIBgYqhQNkcQEwCAYGKoUDZHECMCsGA1UdEAQkMCKADzIwMTgwNzIwMTMwMTQxWoEPMjAxOTA3MjAxMzAxNDFaMIIBNAYFKoUDZHAEggEpMIIBJQwrItCa0YDQuNC/0YLQvtCf0YDQviBDU1AiICjQstC10YDRgdC40Y8gMy45KQwsItCa0YDQuNC/0YLQvtCf0YDQviDQo9CmIiAo0LLQtdGA0YHQuNC4IDIuMCkMY9Ch0LXRgNGC0LjRhNC40LrQsNGCINGB0L7QvtGC0LLQtdGC0YHRgtCy0LjRjyDQpNCh0JEg0KDQvtGB0YHQuNC4IOKEliDQodCkLzEyNC0yNTM5INC+0YIgMTUuMDEuMjAxNQxj0KHQtdGA0YLQuNGE0LjQutCw0YIg0YHQvtC+0YLQstC10YLRgdGC0LLQuNGPINCk0KHQkSDQoNC+0YHRgdC40Lgg4oSWINCh0KQvMTI4LTI4ODEg0L7RgiAxMi4wNC4yMDE2MDYGBSqFA2RvBC0MKyLQmtGA0LjQv9GC0L7Qn9GA0L4gQ1NQIiAo0LLQtdGA0YHQuNGPIDMuOSkwZQYDVR0fBF4wXDBaoFigVoZUaHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2kucnUvY2RwLzNlZWYxOTNmMGZiOTc5YjBmMWU2MjkyMWEzZTRiOTk1YjlhNWVlOTAuY3JsMFcGCCsGAQUFBwEBBEswSTBHBggrBgEFBQcwAoY7aHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2kucnUvY2RwL3Rlc3RfY2FfcnRsYWJzMi5jZXIwCAYGKoUDAgIDA0EAWIKbobPiDap0i63WV/XyVw9IeSeOGvQAgsverXl1IdpLqXAvHX1prvCUumTiu+aYvhGJIvcxjDyLuGhb3OQjGg==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        deps.findesia.setinput(blob.getBytes());
        deps.findesia.SendSoapSigned();
    }


    String getrespreq() throws Exception {
        String prepared = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2014-02-11T17:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;
    };

    public void findMessagebyID(String msgId) throws Exception {
        String result = getrespreq();
        while (true) {
            String id = deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid = deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("\nOriginal id=" + originalid);
            if (id != null) {
                deps.gis.Ack(id);

            }
            if ((originalid != null) && originalid.equals(msgId))
                return;
            result = getrespreq();
            //   Thread.sleep(0);
        }
    }

    @Test
    public void findAcc() throws Exception {
        deps.findesia.setinput(initial.getBytes());
        assertNotEquals(null, deps.findesia.GetSoap());
        assertNotEquals(null, deps.findesia.SignedSoap());
        String response = new String(deps.findesia.SendSoapSigned());
        String messageId = deps.ext.extractTagValue(response, ":MessageId");
        findMessagebyID(messageId);
    }

    @Test
    public void checkEmptySoap(){
        assertNotEquals(null, deps.findesia.emptySOAP);
        System.out.println(deps.findesia.emptySOAP);
    }

    @Test
    public void buildontheFlyAndSend() throws Exception {
        ESIAFindMessageInitial msg = new ESIAFindMessageInitial();
        deps.findesia.ProdModeRoutingEnabled=false;
        msg.OperatorSnils="000-000-600 06";
        msg.MobileNumber="+7(964)8827042";
        msg.MiddleName="ТАндреевна";
        msg.Name="ТАнастасия";
        msg.Surname="ТЩипакина";
        msg.Passnumber="525129";
        msg.Passseria="1213";
        msg.Ra="1000300890";
        msg.ID="12";
        deps.findesia.setinput(deps.findesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg)));
        assertNotEquals(null, deps.findesia.GetSoap());
        assertNotEquals(null, deps.findesia.SignedSoap());
        String response = new String(deps.findesia.SendSoapSigned());
        String messageId = deps.ext.extractTagValue(response, ":MessageId");
        findMessagebyID(messageId);
    }

    @Test
    public void findanswer() throws Exception {
       findMessagebyID("Z7557975-5ac9-11e9-aadf-351d468f44fe");
    }
}

