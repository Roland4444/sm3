package standart;
import Message.abstractions.BinaryMessage;
import Message.abstractions.FileInBinary;
import Message.toSMEV.EBS.EBSMessage;
import crypto.Gost3411Hash;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;


public class ebs extends Standart {
    public byte[] hooked=null;
    public Gost3411Hash Hasher;
    public String MatrixAttach = "<ns2:RefAttachmentHeader><ns2:uuid></ns2:uuid><ns2:Hash></ns2:Hash><ns2:MimeType>image/jpeg</ns2:MimeType><ns2:SignaturePKCS7></ns2:SignaturePKCS7></ns2:RefAttachmentHeader>";
    public String MatrixAudio = "<bm:BioMetadata><bm:Key></bm:Key><bm:Value>00.000</bm:Value></bm:BioMetadata>";
    public String MatrixPhoto = "<bm:Data><bm:Modality>PHOTO</bm:Modality><bm:AttachmentRef attachmentId=\"\"/></bm:Data>";
    public String emptySOAP = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
            "   <S:Body>\n" +
            "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
            "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>868a694d-5e80-11e4-a9ff-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent></ns2:MessagePrimaryContent>\t<ns2:RefAttachmentHeaderList>\t</ns2:RefAttachmentHeaderList></ns:SenderProvidedRequestData>\n" +
            "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>CFoY+9RvxHtLKwSWR7+D6sgYQ3tqtG9RpK6+rNp3/+4=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>9nhnHlNuBUwEZw+Gi/AtAFpmy/LUy0iAYkF2HdOFRIZNbmGbZmTSNRV8cPnsfu2Wg+b0POJwKK/pUINNpey+Yg==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
            "      </ns2:SendRequestRequest>\n" +
            "   </S:Body>\n" +
            "</S:Envelope>";
    public ArrayList<String> AudioDict = new ArrayList();
    public String currentHashSound  =   null;
    public String currentHashPhoto  =   null;
    public String currentPKSC7Sound =   null;
    public String currentPKSC7Photo =   null;
    public String Soundguuid, Photoguuid;
    public String AttachesHeaderList;
    public ebs(StreamResult sr, SignerXML sihner, Injector inj, Transport transport, TempDataContainer temp) throws IOException {
        super(sr, sihner, inj, transport, temp);
        Hasher = new Gost3411Hash();
    //    rawxml = inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", root);
        hooked=Files.readAllBytes(new File("openjdk-11.0.1_linux-x64_bin.tar.gz").toPath());
    }
    public final String startroot ="<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
            "    <bm:RegistrarMnemonic></bm:RegistrarMnemonic>\n" +
            "    <bm:EmployeeId></bm:EmployeeId>\n" +
            "    <bm:BiometricData>\n" +
            "        <bm:Id>ID-1</bm:Id>\n" +
            "        <bm:Date></bm:Date>\n" +
            "        <bm:RaId></bm:RaId>\n" +
            "        <bm:PersonId></bm:PersonId>\n" +
            "        <bm:IdpMnemonic></bm:IdpMnemonic>      \n" ;
    public final String endroot =
            "    </bm:BiometricData>\n" +
            "</bm:RegisterBiometricDataRequest>";
    public byte[] GetSoap() {
        System.out.println("GET SOAP===>");
        System.out.println(new String(InfoToRequest));
        return InfoToRequest;
    }


    public void processCryptoGraphy(EBSMessage msg) throws Exception {
        currentHashPhoto = Hasher.h_Base64rfc2045(msg.PhotoBLOB.fileContent);
        currentHashSound = Hasher.h_Base64rfc2045(msg.SoundBLOB.fileContent);
        currentPKSC7Photo = Hasher.base64(this.signer.getmainSign().CMSSign(msg.PhotoBLOB.fileContent, true));
        currentPKSC7Sound = Hasher.base64(this.signer.getmainSign().CMSSign(msg.SoundBLOB.fileContent, true));

    };


    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, XMLSecurityException,
            IOException, CertificateException, NoSuchAlgorithmException, TransformerException,
            ParserConfigurationException, UnrecoverableEntryException,
            NoSuchProviderException, SAXException, KeyStoreException {
        return signer.signcallerns2(signer.getmainSign(), GetSoap());
    };

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

    public String uploadfiletoftp(String filename) throws IOException {
        String uuid=gen.generate();
        String smev3addr = "smev3-n0.test.gosuslugi.ru";
        util.ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;
        System.out.println("port=>>"+ftpcl.port);
        if (ftpcl.open()!=0)
            System.out.println("error opening connection ");
        if ( ftpcl.mkdir(uuid)!=0)
            System.out.println("error creatimg folder");
        String dirtoupload = "/"+uuid+"/"+filename;
        int res = ftpcl.uploadfile(filename, dirtoupload);
        if (res!=0){
            System.out.println("error uploading file folder");
            return "";
        }
        return uuid;
    }

    public String AttachSoundBlock(){
        String stage1 = inj.injectTag(MatrixAttach,"ns2:uuid>", Soundguuid );
        String stage2 = inj.injectTag(stage1,"ns2:Hash>", currentHashSound );
        String stage3 = inj.injectTag(stage2, "ns2:SignaturePKCS7>", currentPKSC7Sound);
        String stage4 = inj.injectTag(stage3, "ns2:MimeType>", "audio/x-wav");
        return stage4;
    };



    public String AttachPhotoBlock(){
        String stage1 = inj.injectTag(MatrixAttach,"ns2:uuid>", Photoguuid );
        String stage2 = inj.injectTag(stage1,"ns2:Hash>", currentHashPhoto );
        String stage3 = inj.injectTag(stage2, "ns2:SignaturePKCS7>", currentPKSC7Photo);
        String stage4 = inj.injectTag(stage3, "ns2:MimeType>", "image/png");
        return stage4;
    };


    public String genMessagePrimaryContent(EBSMessage msg) throws Exception {
        String[] block = new String[10];
        int i = 0;
        block[i]=inj.injectTag(startroot, "bm:RegistrarMnemonic>", msg.otherinfo.RegMnemonic);
        block[++i]=inj.injectTag(block[i-1], "bm:EmployeeId>", msg.otherinfo.OperSNILS);
        block[++i]=inj.injectTag(block[i-1], "bm:Date>", inj.generateTimeStamp());
        block[++i]=inj.injectTag(block[i-1], "bm:RegistrarMnemonic>", msg.otherinfo.RegMnemonic);
        block[++i]=inj.injectTag(block[i-1], "bm:RaId>", msg.otherinfo.RA);
        block[++i]=inj.injectTag(block[i-1], "bm:PersonId>", msg.otherinfo.OID);
        block[++i]=inj.injectTag(block[i-1], "bm:IdpMnemonic>", msg.otherinfo.Mnemonic);

        block[++i]=block[i-1]+generateSoundBlock(msg)+generatePhotoBlock(msg)+endroot;
        return block[i];
    }

    public String  generateAttachTag(){
        return AttachPhotoBlock()+AttachSoundBlock();
    };


    @Override
    public byte[] generateUnsSOAP(byte[] input) throws Exception {
        String[] block = new String[10];
        int i = 0;
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(input);
        block[i]=inj.injectTagDirect(emptySOAP, "MessagePrimaryContent", genMessagePrimaryContent(msg));
        block[++i]=inj.injectTagDirect(block[i-1], "RefAttachmentHeaderList",generateAttachTag());
        return block[i].getBytes();
    }



    public String generateSoundBlock(EBSMessage msg) throws Exception {
        StringBuffer sb = new StringBuffer();
        FileInBinary.suspendToDisk(msg.SoundBLOB);
        String result =uploadfiletoftp(msg.SoundBLOB.filename);
        if (result.equals(""))
            return null;
        Soundguuid = result;
        sb.append(generateSoundHeader(result));
        sb.append(SoundBioMethadata(msg));
        processCryptoGraphy(msg);
        return sb.toString();
    }

    public String generateSoundHeader(String guuid){
        StringBuffer sb = new StringBuffer();
        sb.append("<bm:Data>\n<bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"");
        sb.append(guuid);
        sb.append("\"/>");
        return sb.toString();
    }




    public String SoundBioMethadata(EBSMessage msg){
        String finisher = "</bm:Data>";
        StringBuffer sb = new StringBuffer();

        AudioDict.clear();

        AudioDict.add("voice_1_start");
        AudioDict.add( String.valueOf(msg.SoundBLOB.begin09));

        AudioDict.add("voice_1_end");
        AudioDict.add( String.valueOf(msg.SoundBLOB.end09));

        AudioDict.add("voice_1_desc");
        AudioDict.add( String.valueOf("digits_asc"));


        AudioDict.add("voice_2_start");
        AudioDict.add( String.valueOf(msg.SoundBLOB.begin90));

        AudioDict.add("voice_2_end");
        AudioDict.add( String.valueOf(msg.SoundBLOB.end90));

        AudioDict.add("voice_2_desc");
        AudioDict.add( String.valueOf("digits_desc"));


        AudioDict.add("voice_3_start");
        AudioDict.add( String.valueOf(msg.SoundBLOB.begin090));

        AudioDict.add("voice_3_end");
        AudioDict.add( String.valueOf(msg.SoundBLOB.end090));

        AudioDict.add("voice_3_desc");
        AudioDict.add( String.valueOf("digits_random"));

        for (int i = 0; i < AudioDict.size(); i++){
            String stage1 =inj.injectTag(MatrixAudio, "bm:Key>", AudioDict.get(i));
            String stage2 =inj.injectTag(stage1, "bm:Value>", AudioDict.get(++i));
            sb.append(stage2);
        }
        AudioDict.clear();
        sb.append(finisher);
        return sb.toString();
    }


    public String generatePhotoBlock(EBSMessage msg) throws IOException {
        StringBuffer sb = new StringBuffer();

        FileInBinary.suspendToDisk(msg.PhotoBLOB);
        String result =uploadfiletoftp(msg.PhotoBLOB.filename);
        System.out.println("RESULT=>"+result);
        if (result.equals(""))
            return null;
        Photoguuid = result;
        sb.append(inj.injectAttribute(MatrixPhoto, "attachmentId", result));
        return sb.toString();
    }



    public void printMsg(EBSMessage msg){
        System.out.println(msg.SoundBLOB.begin09);
        System.out.println(msg.SoundBLOB.end09);

        System.out.println(msg.SoundBLOB.begin90);
        System.out.println(msg.SoundBLOB.end90);

        System.out.println(msg.SoundBLOB.begin090);
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