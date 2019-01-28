package standart;
import Message.abstractions.FileInBinary;
import Message.toSMEV.EBS.EBSMessage;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;
import schedulling.abstractions.Sign;
import schedulling.abstractions.TempDataContainer;
import transport.Transport;
import util.Injector;
import util.SignatureProcessorException;
import util.SignerXML;
import util.ftpClient;

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


public class ebs extends Standart {
    public String Matrix = "<bm:BioMetadata><bm:Key></bm:Key><bm:Value>00.000</bm:Value></bm:BioMetadata>";
    public String[] AudioDict = {"voice_1_start" , "",  "voice_1_end","", "voice_1_desc", "digits_asc",
            "voice_2_start" , "",  "voice_2_end","", "voice_2_desc", "digits_desc",
            "voice_3_start" , "",  "voice_3_end","", "voice_3_desc", "digits_random"};
    public String Soundguuid, Photoguuid;
    public ebs(StreamResult sr, SignerXML sihner, Injector inj, Transport transport, TempDataContainer temp){
        super(sr, sihner, inj, transport, temp);
        rawxml = inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", root);
    }
    public final String root ="<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
            "    <bm:RegistrarMnemonic></bm:RegistrarMnemonic>\n" +
            "    <bm:EmployeeId></bm:EmployeeId>\n" +
            "    <bm:BiometricData>\n" +
            "        <bm:Id>ID-1</bm:Id>\n" +
            "        <bm:Date></bm:Date>\n" +
            "        <bm:RaId></bm:RaId>\n" +
            "        <bm:PersonId></bm:PersonId>\n" +
            "        <bm:IdpMnemonic></bm:IdpMnemonic>      \n" +
            "        <bm:Data>\n" +
            "            <bm:Modality>SOUND</bm:Modality>\n" +
            "            <bm:AttachmentRef attachmentId=\"\"/>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_1_start</bm:Key>\n" +
            "                <bm:Value></bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_1_end</bm:Key>\n" +
            "                <bm:Value></bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_1_desc</bm:Key>\n" +
            "                <bm:Value>digits_asc</bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_2_start</bm:Key>\n" +
            "                <bm:Value></bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_2_end</bm:Key>\n" +
            "                <bm:Value></bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_2_desc</bm:Key>\n" +
            "                <bm:Value>digits_desc</bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_3_start</bm:Key>\n" +
            "                <bm:Value></bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_3_end</bm:Key>\n" +
            "                <bm:Value></bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "            <bm:BioMetadata>\n" +
            "                <bm:Key>voice_3_desc</bm:Key>\n" +
            "                <bm:Value>digits_random</bm:Value>\n" +
            "            </bm:BioMetadata>\n" +
            "        </bm:Data>\n" +
            "        <bm:Data>\n" +
            "            <bm:Modality>PHOTO</bm:Modality>\n" +
            "            <bm:AttachmentRef attachmentId=\"\"/>\n" +
            "        </bm:Data>\n" +
            "    </bm:BiometricData>\n" +
            "</bm:RegisterBiometricDataRequest>";
    public byte[] GetSoap() {
        System.out.println("GET SOAP===>");
        System.out.println(new String(InfoToRequest));
        return InfoToRequest;
    }


    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, XMLSecurityException,
            IOException, CertificateException, NoSuchAlgorithmException, TransformerException,
            ParserConfigurationException, UnrecoverableEntryException,
            NoSuchProviderException, SAXException, KeyStoreException {
        return signer.signconsumerns4(signer.getmainSign(), GetSoap());
    }

    ;

    public byte[] GetResponseRequest() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] GetResponseRequestwoFilter() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] ack() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }
//

    public byte[] GetResponceRequestCompiled() throws Exception {
        String prepared = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:GetResponseRequest>\n" +
                "      <ns2:MessageTypeSelector xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\">\n" +
        //<=need register!        "        <ns2:NamespaceURI>urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0</ns2:NamespaceURI>" +
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

    public byte[] GetResponceFilteredCompiled() throws Exception {
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
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    @Override
    public byte[] generateUnsSOAP(byte[] input) throws IOException {
        return null;
    }


    public int uploadWav(EBSMessage msg) throws IOException {
        String uuid=gen.generate();
        String smev3addr = "smev3-n0.test.gosuslugi.ru";
        util.ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);

        if (ftpcl.open()!=0) {
            System.out.println("error opening connection ");
        }
        if ( ftpcl.mkdir(uuid)!=0){
            System.out.println("error creatimg folder");

        }

        FileInBinary.suspendToDisk(msg.SoundBLOB);
        String dirtoupload = "/"+uuid+"/"+msg.SoundBLOB.filename;
        int res = ftpcl.uploadfile(msg.SoundBLOB.filename, dirtoupload);
        if (res!=0){
            System.out.println("error uploading file folder");

        }
        Soundguuid=uuid;
        return res;
    };

    public String generateSoundBlob(EBSMessage msg) throws IOException {
        StringBuffer sb = new StringBuffer();
        if (uploadWav(msg)!=0)
            return null;
        printMsg(msg);
        return null;

    }


    public String SoundBioMethadata(EBSMessage msg){
        return null;
    }




    public void printMsg(EBSMessage msg){
        System.out.println(msg.SoundBLOB.begin09);
        System.out.println(msg.SoundBLOB.begin90);
        System.out.println(msg.SoundBLOB.begin090);
        System.out.println(msg.SoundBLOB.end09);
        System.out.println(msg.SoundBLOB.end90);
        System.out.println(msg.SoundBLOB.end090);

    }


    public String getName() {
        return "EBS";
    }

    public boolean check(byte[] input) throws IOException {
        if (input == null)
            return false;

        String input2 = new String(input);
        if (input2.indexOf("Id=\"SIGNED_BY_CONSUMER\"") < 0)
            return false;
        if (input2.indexOf("URI=\"#SIGNED_BY_CONSUMER\"") < 0)
            return false;
        System.out.println("Extracted!" + this.ext.parseTagFromByte(input2.getBytes(), "ns4:CallerInformationSystemSignature"));
        System.out.println("STOP!");
        System.out.println("MESSAGE\n\n" + input2);
        if (this.ext.parseTagFromByte(input2.getBytes(), "ns4:CallerInformationSystemSignature") == null ||
                (this.ext.parseTagFromByte(input2.getBytes(), "ns4:CallerInformationSystemSignature").length() == 0))
            return false;
        System.out.println("Extracted===>>" + this.ext.extractAttribute(input, "Id"));
        if (this.ext.extractAttribute(input, "Id") == null) return false;
        if (!this.ext.extractAttribute(input, "Id").equals("SIGNED_BY_CONSUMER"))
            return false;
        return true;
    }

    }