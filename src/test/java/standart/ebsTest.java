package standart;

import Message.abstractions.BinaryMessage;
import Message.toSMEV.EBS.EBSMessage;
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
import util.crypto.TestSign2001;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class ebsTest {
    EBSMessage msg;

    DependencyContainer deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()));
    Scheduller sch = new Scheduller(deps);
    Sign signer = new Sign2018();
    public boolean supress=false;
    Injector inj = new Injector();
    String photofile = "biophoto.jpg";
    String soundfile = "biosound.wav";
    public String filename__ = "EBSMessageFUll.bin";
    public ebsTest() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException {
        msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));

    }

    @Test
    public void sendInitialRequestRequestGis() throws Exception {

        assertNotEquals(null, deps.ebs.signer);
        assertNotEquals(null, deps.ebs.signer.getmainSign());
        assertNotEquals(null, deps.ebs.signer.getPersonalSign());
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
        "   <S:Body>\n" +
        "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" + "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>6fefa07c-5e7f-11e4-a9ff-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest Id=\"PERSONAL_SIGNATURE\" xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\" xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns2:AttachmentHeaderList>\t<ns2:AttachmentHeader>\t<ns2:contentId>attach5MB.jpg</ns2:contentId>\t<ns2:MimeType>image/jpeg</ns2:MimeType>\t<ns2:SignaturePKCS7>MIICyAYJKoZIhvcNAQcCoIICuTCCArUCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggGLMIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHsxggEEMIIBAAIBATA2MC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUCBQDBXZBUMAoGBiqFAwICCQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTQxMDI4MDg1MDE2WjAvBgkqhkiG9w0BCQQxIgQgmZUE4Hn2Dtr05pue921ZxU60Ia3toLVEQfIs24PTdT8wCgYGKoUDAgITBQAEQBwEN+RenIpvL6lZHzzsPj5H4xgqZjs330i1JjAhjcACqQcCyt3vorTEX9/gfs16s9Lt9XxNj9Y88NvvLIrfFaw=</ns2:SignaturePKCS7>\t</ns2:AttachmentHeader>\t</ns2:AttachmentHeaderList></ns:SenderProvidedRequestData>\t<AttachmentContentList>\t<AttachmentContent>\t<Id>attach5MB.jpg</Id>\t<Content><xop:Include href=\"cid:attach5MB.jpg\" xmlns:xop=\"http://www.w3.org/2004/08/xop/include\"/></Content>\t</AttachmentContent>\t</AttachmentContentList>\n" +
        "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>jTP0hndqPRXO9O+5euAtKsysn83a9+gVkvUpojGTL84=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>i3PzufK7XEmCtElirdXqtYh7/CdZCIIgowfj+TbBHPw/Mk+1YA+/LSdIM3MO2tcH8ZeFA5e04VSz0deGcnVAaA==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
        "      </ns2:SendRequestRequest>\n" +
        "   </S:Body>\n" +
        "</S:Envelope>";
        deps.ebs.setinput(data.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0)
            System.out.println("FAULT");


    }

    @Test
    public void setinput() throws Exception {
        String testData = "<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>TEST01</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>123-456-789 00</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:54:52+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>240631324</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"ef37b493-e94f-4f27-9e86-f4cd80f1057f\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>10.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>12.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>20.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>22.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>30.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"397af8d0-d456-4dc1-9353-1d6822a02200\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-2</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:50:16+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>215979546</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>FINGERPRINT</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"acf4bf60-af0f-4479-a338-d3410e532bf5\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>";
        String input = deps.inj.injectTagDirect(deps.ebs.emptySOAP, "MessagePrimaryContent", testData);
        deps.ebs.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void notNull(){

    }


    @Test
    public void sendEBSWithoutFinferprint() throws Exception {
        String testData = "<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>TEST01</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>123-456-789 00</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:54:52+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>240631324</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"ef37b493-e94f-4f27-9e86-f4cd80f1057f\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>10.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>12.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>20.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>22.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>30.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"397af8d0-d456-4dc1-9353-1d6822a02200\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-2</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:50:16+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>215979546</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>";
        String input = deps.inj.injectTagDirect(deps.ebs.emptySOAP, "MessagePrimaryContent", testData);
        deps.ebs.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void getResponceRequestCompiled() throws Exception {
        deps.ebs.GetResponceRequestCompiled();
    }

    @Test
    public void printEtalon(){
        String testData = "<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>TEST01</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>123-456-789 00</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:54:52+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>240631324</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"ef37b493-e94f-4f27-9e86-f4cd80f1057f\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>10.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>12.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>20.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>22.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>30.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"397af8d0-d456-4dc1-9353-1d6822a02200\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-2</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:50:16+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>215979546</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>";
        System.out.println(testData);

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
                "</ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>HUVEqoOE3Rkngx6/ic6xBFtRm1C4YxRBIroEBr0+D6o=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>skAUTOLg7zoPMX/04w35xbCEFF2rok+Cs5mbnzj0075Ouy4oQ3jriElEod4n6opc/1vqgkK3CGEnbEL9ROCP/g==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println(blob);


    }


    @Test
    public void sendEBSSAFAR() throws Exception {
        String blob ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>d8161c55-1ecc-11e9-90eb-1dec74fcc959</ns:MessageID><ns2:MessagePrimaryContent><bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>981601_3T</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>000-000-600 06</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2019-01-23T13:14:52+03:00</bm:Date>\n" +
                "        <bm:RaId>1000300890</bm:RaId>\n" +
                "        <bm:PersonId>1000368305</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>TESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"e59a11cc-1f13-11e9-a54a-af41724ba888\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>1.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>2.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>3.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>4.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>5.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"f5cb6cae-1f13-11e9-8bb3-5706b6ad71d3\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>\n" +
                "</ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>HUVEqoOE3Rkngx6/ic6xBFtRm1C4YxRBIroEBr0+D6o=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>skAUTOLg7zoPMX/04w35xbCEFF2rok+Cs5mbnzj0075Ouy4oQ3jriElEod4n6opc/1vqgkK3CGEnbEL9ROCP/g==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";


        deps.ebs.setinput(blob.getBytes());
        assertNotEquals(null, blob);
        System.out.println(blob);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }


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
    public void attachment(){
        ArrayList<String> attaches = new ArrayList();
        attaches.add("biophoto");
    }


  //  @Test
    public void findMessageID() throws Exception {

            String result = getrespreq();
            while (true){
                String id=deps.ext.extractTagValue(result, ":MessageID");
                //   System.out.println("Extract id="+ id);
                String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
                System.out.println("Original id="+ originalid);
                if (id != null) {
                    deps.gis.Ack(id);

                }
                if (originalid.equals("22c78a30-1f14-11e9-af36-4b2d56ff4191"))
                    return;
                result = getrespreq();
                //   Thread.sleep(0);
            }

    }


    String getrespreq() throws Exception {
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
        //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;
    };


    @Test
    public void uploadtest() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.uploadfiletoftp(msg.SoundBLOB.filename));
    }

    @Test
    public void restoreMsg() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        byte[] rawmsg = Files.readAllBytes(new File(filename__).toPath());
        assertNotEquals(null, msg.otherinfo);
        assertNotEquals(null, msg.PhotoBLOB);
        assertNotEquals(null, msg.SoundBLOB);
        byte[] BinaryXML=deps.tableProcessor.OperatorMap.get("ebs").generateUnsSOAP(rawmsg);
        assertNotEquals(null, BinaryXML);
    }

    @Test
    public void generateSoundBlob() throws Exception {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.generateSoundBlock(msg) );
    }

    @Test
    public void soundBioMethadata() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.SoundBioMethadata(msg) );
        System.out.println(deps.ebs.SoundBioMethadata(msg));
    }

    @Test
    public void generateSoundHeader() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.generateSoundHeader("7878778"));
        System.out.println(deps.ebs.generateSoundHeader("7878778"));
    }

    @Test
    public void photoBlock() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.generatePhotoBlock(msg));
        System.out.println(deps.ebs.generatePhotoBlock(msg));
    }

    @Test
    public void processCryptoGraphy() throws Exception {
        deps.ebs.processCryptoGraphy(msg);
        assertNotEquals(null, deps.ebs.currentHashPhoto);
        assertNotEquals(null, deps.ebs.currentHashSound);
        System.out.println(deps.ebs.currentHashPhoto);
        System.out.println(deps.ebs.currentHashSound);

        assertNotEquals(null, deps.ebs.currentPKSC7Photo);
        assertNotEquals(null, deps.ebs.currentPKSC7Sound);
    }

    @Test
    public void attachSoundBlock() throws Exception {
        deps.ebs.generateSoundBlock(msg);
        deps.ebs.generatePhotoBlock(msg);

        assertNotEquals(null, deps.ebs.AttachPhotoBlock());
        assertNotEquals(null, deps.ebs.AttachSoundBlock());
        System.out.println( deps.ebs.AttachPhotoBlock());
        System.out.println( deps.ebs.AttachSoundBlock());

    }
}
