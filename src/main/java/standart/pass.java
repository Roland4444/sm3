package standart;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;
import schedulling.abstractions.Sign;
import schedulling.abstractions.TempDataContainer;
import transport.Transport;
import util.Injector;
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

public class pass extends Standart {
    public pass(StreamResult sr, SignerXML sihner, Injector inj, Transport transport, TempDataContainer temp){
        super(sr, sihner, inj, transport, temp);
    }
    public byte[] GetSoap() {
        System.out.println("GET SOAP===>");
        System.out.println(new String(InfoToRequest));
        return InfoToRequest;
    };

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
        InputStream in = new ByteArrayInputStream(signer.signcallernsbycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] ack() throws Exception {
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }
 //ю   urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1
 //   urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\
    public byte[] GetResponceFilteredCompiled() throws Exception {
        String prepared="\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:GetResponseRequest>\n" +
                "      <ns2:MessageTypeSelector xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\">\n" +
                "        <ns2:NamespaceURI>urn://ru/mvd/sovm/p001/1.0.0</ns2:NamespaceURI>\n" +
                "        <ns2:RootElementLocalName>request</ns2:RootElementLocalName>\n" +
                "        <ns2:Timestamp>2018-07-24T16:06:49.623</ns2:Timestamp>\n" +
                "      </ns2:MessageTypeSelector>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:GetResponseRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        this.setinput(prepared.getBytes());
        InputStream in = new ByteArrayInputStream(signer.signcallerns4bycaller(signer.getmainSign(), GetSoap()));
        StreamSource input = new StreamSource(in);
        return this.transport.send(input, SupressConsole);
    }

    public byte[] GetResponceRequestCompiled() throws Exception {
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
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        setinput(prepared.getBytes());
        return GetResponseRequestwoFilter();
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
        return new byte[0];
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

    public String getName() {
        return "PASS";
    }
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
        //String wiNumberDeal = inj.injectAttribute(dwithId,"ИдДок", injectdatainXML);
        this.InfoToRequest = dwithId2.getBytes();
    }
}