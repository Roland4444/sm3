package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import util.Sign;
import util.SignatureProcessorException;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class esiaTest {
    DependencyContainer deps = new DependencyContainer();
    Scheduller sch = new Scheduller(deps);
    Sign signer = new Sign();
    public boolean supress=false;

    public esiaTest() throws ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, IOException {
    }

    @Test
    public void signedSoap() throws IOException {
        String data = "S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIARegisterRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register/1.4.0\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.0\">\n" +
                "    <tns:RoutingCode>DEV</tns:RoutingCode>\n" +
                "<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
                "    <tns:ra>1000321282</tns:ra>\n" +
                "    <tns:snils>229-785-346 20</tns:snils>\n" +
                "    <tns:lastName>Тестов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Тестович</tns:middleName>\n" +
                "    <tns:gender>M</tns:gender>\n" +
                "    <tns:birthDate>11.11.1988</tns:birthDate>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>1111</ns2:series>\n" +
                "        <ns2:number>111111</ns2:number>\n" +
                "        <ns2:issueId>111111</ns2:issueId>\n" +
                "        <ns2:issueDate>01.10.2017</ns2:issueDate>\n" +
                "        <ns2:issuedBy>выдан</ns2:issuedBy>\n" +
                "    </tns:doc>\n" +
                "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "    <tns:citizenship>RUS</tns:citizenship>\n" +
                "    <tns:mode>mobile</tns:mode>\n" +
                "    <tns:address>\n" +
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
                "    </tns:address>\n" +
                "    <tns:birthPlace>воронеж</tns:birthPlace>\n" +
                "</tns:ESIARegisterRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns4:CallerInformationSystemSignature");
        String etalonFlushed = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";

        deps.esia.setinput(prepared.getBytes());
        assertNotEquals(null, deps.esia.GetSoap());
    }





    @Test
    public void getSoap() {
    }

    @Test
    public void sendInitialFindESIA() throws Exception {
        String testData = "<tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "\t<tns:RoutingCode>DEV</tns:RoutingCode>\n" +
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
                "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "    <tns:snils>229-785-346 20</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n";
        String input = deps.inj.injectTagDirect(deps.esia.emptySOAP, "MessagePrimaryContent", testData);
        deps.esia.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);

        assertNotEquals(null, deps.esia.GetSoap());
        assertNotEquals(null, deps.esia.SignedSoap());
        String response = new String(deps.esia.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }



    @Test
    public void sendInitialShortFindESIA() throws Exception {
        String testData = "<tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "\t<tns:RoutingCode>DEV</tns:RoutingCode>\n" +
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
                //        "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
                //        "    <tns:snils>229-785-346 20</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n";
        String input = deps.inj.injectTagDirect(deps.esia.emptySOAP, "MessagePrimaryContent", testData);
        deps.esia.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);

        assertNotEquals(null, deps.esia.GetSoap());
        assertNotEquals(null, deps.esia.SignedSoap());
        String response = new String(deps.esia.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void getResponceRequestCompiled() throws Exception {
        deps.esia.GetResponceRequestCompiled();
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
        System.out.println("@\n"+ originalid);
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
        deps.gis.Ack(messageId);

    }

    @Test
    public void sendRegisterESIA() throws Exception {
        String testData = "<tns:ESIARegisterRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "    <tns:RoutingCode>DEV</tns:RoutingCode>\n" +
                "\t<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
                "    <tns:ra>1000321282</tns:ra>\n" +
                "    <tns:snils>229-785-346 20</tns:snils>\n" +
                "    <tns:lastName>Тестов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Тестович</tns:middleName>\n" +
                "    <tns:gender>M</tns:gender>\n" +
                "    <tns:birthDate>11.11.1988</tns:birthDate>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>1111</ns2:series>\n" +
                "        <ns2:number>111111</ns2:number>\n" +
                "        <ns2:issueId>111111</ns2:issueId>\n" +
                "        <ns2:issueDate>01.10.2017</ns2:issueDate>\n" +
                "        <ns2:issuedBy>выдан</ns2:issuedBy>\n" +
                "    </tns:doc>\n" +
                "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "    <tns:citizenship>RUS</tns:citizenship>\n" +
                "    <tns:mode>mobile</tns:mode>\n" +
                "    <tns:address>\n" +
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
                "    </tns:address>\n" +
                "    <tns:birthPlace>воронеж</tns:birthPlace>\n" +
                "</tns:ESIARegisterRequest>";
        String input = deps.inj.injectTagDirect(deps.esia.emptySOAP, "MessagePrimaryContent", testData);
        deps.esia.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);

        assertNotEquals(null, deps.esia.GetSoap());
        assertNotEquals(null, deps.esia.SignedSoap());
        String response = new String(deps.esia.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }




    @Test
    public void signedSoapUpgrade() throws IOException {
        String data = "S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIARegisterRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register/1.4.0\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.0\">\n" +
                "    <tns:RoutingCode>DEV</tns:RoutingCode>\n" +
                "<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
                "    <tns:ra>1000321282</tns:ra>\n" +
                "    <tns:snils>229-785-346 20</tns:snils>\n" +
                "    <tns:lastName>Тестов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Тестович</tns:middleName>\n" +
                "    <tns:gender>M</tns:gender>\n" +
                "    <tns:birthDate>11.11.1988</tns:birthDate>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>1111</ns2:series>\n" +
                "        <ns2:number>111111</ns2:number>\n" +
                "        <ns2:issueId>111111</ns2:issueId>\n" +
                "        <ns2:issueDate>01.10.2017</ns2:issueDate>\n" +
                "        <ns2:issuedBy>выдан</ns2:issuedBy>\n" +
                "    </tns:doc>\n" +
                "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "    <tns:citizenship>RUS</tns:citizenship>\n" +
                "    <tns:mode>mobile</tns:mode>\n" +
                "    <tns:address>\n" +
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
                "    </tns:address>\n" +
                "    <tns:birthPlace>воронеж</tns:birthPlace>\n" +
                "</tns:ESIARegisterRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns4:CallerInformationSystemSignature");
        String etalonFlushed = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";

        deps.esia.setinput(prepared.getBytes());
        assertNotEquals(null, deps.esia.GetSoap());
    }


}