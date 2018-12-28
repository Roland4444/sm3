package standart;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Sign;
import util.Injector;

import util.SignatureProcessorException;

import util.SignerXML;
import util.crypto.Sign2018;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class egrTest {
    DependencyContainer deps = new DependencyContainer(new SignerXML(new Sign2018(), new Sign2018()));    Scheduller sch = new Scheduller(deps);
    Sign signer = new Sign2018();
    public boolean supress=false;
    Injector inj = new Injector();

    public egrTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
    }

    @Test
    public void InitialTest1() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns4:CallerInformationSystemSignature");
        String etalonFlushed = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(etalonFlushed,prepared );
        deps.egr.setinput(prepared.getBytes());
        assertNotEquals(null, deps.egr.GetSoap());
    }


    @Test
    public void testCheck() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(true,deps.egr.check(data.getBytes()) );

        String error1="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUME0R\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(false,deps.egr.check(error1.getBytes()) );

        String error2="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns3:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(false,deps.egr.check(error2.getBytes()) );

        assertEquals(false,deps.egr.check(null) );
    }

    @Test
    public void SignedTest1() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns4:CallerInformationSystemSignature");
        String etalonFlushed = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(etalonFlushed,prepared );
        deps.egr.setinput(prepared.getBytes());
        assertNotEquals(null, deps.egr.GetSoap());
        assertNotEquals(null, deps.egr.SignedSoap());
        System.out.println(new String(deps.egr.SignedSoap()));
    }


    @Test
    public void sendSignedTest1() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns4:CallerInformationSystemSignature");
        String etalonFlushed = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(etalonFlushed,prepared );
        deps.egr.setinput(prepared.getBytes());
        assertNotEquals(null, deps.egr.GetSoap());
        assertNotEquals(null, deps.egr.SignedSoap());
        System.out.println(new String(deps.egr.SignedSoap()));
        assertNotEquals(null,deps.egr.SendSoapSigned());

    }

    @Test
    public void sendInitialRequestRequestEGR() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns4:CallerInformationSystemSignature");
        String etalonFlushed = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"EC6418C9-D64C-4C6A-971F-00E1308C57FB\" НомерДела=\"0EA5C356-DB3C-4E33-8E5B-C312F4874909\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(etalonFlushed,prepared );

        deps.egr.setinput(prepared.getBytes());
        assertNotEquals(null, deps.egr.GetSoap());
        assertNotEquals(null, deps.egr.SignedSoap());
        String response = new String(deps.egr.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }


    @Test
    public void GetresponcerequestwoFilter() throws Exception {
        String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2014-02-11T17:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.egr.setinput(prepared.getBytes());
        assertNotEquals(null, deps.egr.GetSoap());
        String response = new String(deps.egr.GetResponseRequestwoFilter());
        String originalid = deps.ext.extractTagValue(response,":OriginalMessageId");
        System.out.println("@\n"+ originalid);
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }



    }

    @Test
    public void sendEgr() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipul-tosmv-ru/311-14/4.0.6\" ИдДок=\"C693C0F8-E825-438B-9D53-50BBF2EEAF44\" НомерДела=\"CA5615D8-54EE-4308-930F-0CA4107E79B0\"><ns1:ЗапросЮЛ><ns1:ИННЮЛ>5257045651</ns1:ИННЮЛ></ns1:ЗапросЮЛ></ns1:FNSVipULRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        OutputStream os = new ByteArrayOutputStream();
        StreamResult sr = new StreamResult(os);


        deps.egr.setinput(data.getBytes());
      //  assertNotEquals(null, inn.SendSoapSigned());
        System.out.print(String.valueOf(deps.egr.SendSoapSigned()));
    }

  //  @Test
    public void signedSoap() throws Exception {
        deps.dataImporter.loadDataToInputFlow(false);
        assertEquals(1, deps.inputDataFlow.pool.size());
        assertEquals("3577%egr", deps.inputDataFlow.pool.get(0).Id);
        assertEquals("egr", deps.inputDataFlow.pool.get(0).operator);
        assertNotEquals(null, deps.inputDataFlow.pool.get(0).DataToWork);
        deps.egr.setinput(new String(deps.inputDataFlow.pool.get(0).DataToWork).getBytes());
        String response = new String(deps.egr.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
        sch.processor.sendAll();


        sch.processor.getAllResponses(10);
        assertNotEquals(null, sch.deps.dbReqs.pool.get(0).ResponsedXML);

         //   sch.deps.performReceiveddata.ProcessResultsTable();

    }


    @Test
    public void extract() throws IOException {
        String filename="xml4test/res.xml";
        String etalon = "<ns1:FNSVipIPResponse xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.5\" ИдДок=\"33F0F745-FF7B-40B6-87B6-C9B4080E201A\"><ns1:СвИП xmlns:fnst=\"urn://x-artefacts-fns/vipip-types/4.0.5\" ДатаВып=\"2015-10-26\" ДатаОГРНИП=\"2005-04-19\" КодВидИП=\"1\" НаимВидИП=\"ИНДИВИДУАЛЬНЫЙ ПРЕДПРИНИМАТЕЛЬ\" ОГРНИП=\"305500910900012\"><ns1:СвФЛ Пол=\"1\"><ns1:ФИОРус Имя=\"ИМЯ 55009109000004\" Отчество=\"ОТЧЕСТВО 55009109000004\" Фамилия=\"ФАМИЛИЯ 55009109000004\"/><ns1:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвФЛ><ns1:СвГражд ВидГражд=\"1\" НаимСтран=\"РОССИЯ\" ОКСМ=\"643\"><ns1:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвГражд><ns1:СвАдрМЖ><ns1:АдресРФ КодРегион=\"50\"><fnst:Регион НаимРегион=\"МОСКОВСКАЯ\" ТипРегион=\"ОБЛАСТЬ\"/></ns1:АдресРФ><ns1:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвАдрМЖ><ns1:СвРегИП ДатаОГРНИП=\"2005-04-19\" НаимРО=\"ИНСПЕКЦИЯ ФЕДЕРАЛЬНОЙ НАЛОГОВОЙ СЛУЖБЫ ПО Г. ДОМОДЕДОВО МОСКОВСКОЙ ОБЛАСТИ.\" ОГРНИП=\"305500910900012\"/><ns1:СвРегОрг АдрРО=\",142000,МОСКОВСКАЯ ОБЛ,,ДОМОДЕДОВО Г,,КРАСНОАРМЕЙСКАЯ УЛ,42А,,\" КодНО=\"5009\" НаимНО=\"ИНСПЕКЦИЯ ФЕДЕРАЛЬНОЙ НАЛОГОВОЙ СЛУЖБЫ ПО Г. ДОМОДЕДОВО МОСКОВСКОЙ ОБЛАСТИ.\"><ns1:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвРегОрг><ns1:СвУчетНО ДатаПостУч=\"2005-04-19\" ИННФЛ=\"500907235960\"><ns1:СвНО КодНО=\"5009\" НаимНО=\"ИНСПЕКЦИЯ ФЕДЕРАЛЬНОЙ НАЛОГОВОЙ СЛУЖБЫ ПО Г.ДОМОДЕДОВО МОСКОВСКОЙ ОБЛАСТИ\"/><ns1:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвУчетНО><ns1:СвОКВЭД><ns1:СвОКВЭДОсн КодОКВЭД=\"52.62\" НаимОКВЭД=\"РОЗНИЧНАЯ ТОРГОВЛЯ В ПАЛАТКАХ И НА РЫНКАХ\"><fnst:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвОКВЭДОсн><ns1:СвОКВЭДДоп КодОКВЭД=\"52.61\" НаимОКВЭД=\"РОЗНИЧНАЯ ТОРГОВЛЯ ПО ЗАКАЗАМ\"><fnst:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвОКВЭДДоп><ns1:СвОКВЭДДоп КодОКВЭД=\"52.63\" НаимОКВЭД=\"ПРОЧАЯ РОЗНИЧНАЯ ТОРГОВЛЯ ВНЕ МАГАЗИНОВ\"><fnst:ГРНИПДата ГРНИП=\"305500910900012\" ДатаЗаписи=\"2005-04-19\"/></ns1:СвОКВЭДДоп></ns1:СвОКВЭД><ns1:СвЗапЕГРИП ГРНИП=\"305500910900012\" ДатаЗап=\"2005-04-19\" ИдЗап=\"305500910900012\"><ns1:ВидЗап КодСПВЗ=\"21211\" НаимВидЗап=\"(Р21001) РЕГИСТРАЦИЯ ФЛ В КАЧЕСТВЕ ИП\"/><ns1:СвРегОрг КодНО=\"5009\" НаимНО=\"ИНСПЕКЦИЯ ФЕДЕРАЛЬНОЙ НАЛОГОВОЙ СЛУЖБЫ ПО Г. ДОМОДЕДОВО МОСКОВСКОЙ ОБЛАСТИ.\"/><ns1:СведПредДок><ns1:НаимДок>ДОКУМЕНТ, УДОСТОВЕРЯЮЩИЙ ЛИЧНОСТЬ ГРАЖДАНИНА РОССИИ</ns1:НаимДок></ns1:СведПредДок><ns1:СведПредДок><ns1:НаимДок>ДОКУМЕНТ ОБ УПЛАТЕ ГОСУДАРСТВЕННОЙ ПОШЛИНЫ</ns1:НаимДок></ns1:СведПредДок><ns1:СведПредДок><ns1:НаимДок>Р21001 ЗАЯВЛЕНИЕ О РЕГИСТРАЦИИ ФЛ В КАЧЕСТВЕ ИП</ns1:НаимДок></ns1:СведПредДок><ns1:СвСвид ДатаВыдСвид=\"2005-04-19\" Номер=\"000285309\" Серия=\"50\"/></ns1:СвЗапЕГРИП><ns1:СвЗапЕГРИП ГРНИП=\"305500910900012\" ДатаЗап=\"2005-04-19\" ИдЗап=\"405500935601018\"><ns1:ВидЗап КодСПВЗ=\"23200\" НаимВидЗап=\"ВНЕСЕНИЕ СВЕДЕНИЙ ОБ УЧЕТЕ В НАЛОГОВОМ ОРГАНЕ\"/><ns1:СвРегОрг КодНО=\"5009\" НаимНО=\"ИНСПЕКЦИЯ ФЕДЕРАЛЬНОЙ НАЛОГОВОЙ СЛУЖБЫ ПО Г. ДОМОДЕДОВО МОСКОВСКОЙ ОБЛАСТИ.\"/></ns1:СвЗапЕГРИП></ns1:СвИП></ns1:FNSVipIPResponse>";

        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
        String xml = new String(arr);
        assertNotEquals(null, xml);
        System.out.println(xml);
    }

    @Test
    public void testcheck() throws IOException {
        String input="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipIPRequest xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.5\" ИдДок=\"754E9D4C-FF7A-4EBC-B047-944D7039E193\" НомерДела=\"47E6FD69-255E-4119-8E5B-929FD065ACF5\"><ns1:ЗапросИП><ns1:ИНН>772770224286</ns1:ИНН></ns1:ЗапросИП></ns1:FNSVipIPRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        assertEquals(true, deps.egr.check(input.getBytes()));
        String wrong="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGN22ED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipIPRequest xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.5\" ИдДок=\"754E9D4C-FF7A-4EBC-B047-944D7039E193\" НомерДела=\"47E6FD69-255E-4119-8E5B-929FD065ACF5\"><ns1:ЗапросИП><ns1:ИНН>772770224286</ns1:ИНН></ns1:ЗапросИП></ns1:FNSVipIPRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        assertEquals(false, deps.egr.check(wrong.getBytes()));
        String wrong2="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><ns1:FNSVipIPRequest xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.5\" ИдДок=\"754E9D4C-FF7A-4EBC-B047-944D7039E193\" НомерДела=\"47E6FD69-255E-4119-8E5B-929FD065ACF5\"><ns1:ЗапросИП><ns1:ИНН>772770224286</ns1:ИНН></ns1:ЗапросИП></ns1:FNSVipIPRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUME_R\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(false, deps.egr.check(wrong2.getBytes()));


    }



}

