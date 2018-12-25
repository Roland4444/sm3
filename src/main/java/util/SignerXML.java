package util;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.XPath2FilterContainer;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.CryptoPro.JCPxml.Consts;
import schedulling.abstractions.Sign;
import util.crypto.Sign2018;
import util.crypto.Sign2019;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class SignerXML {
    private static final String CANONICALIZATION_METHOD = "http://www.w3.org/2001/10/xml-exc-c14n#";
    private static final String DS_SIGNATURE = "//ds:Signature";
    private static final String SIG_ID = "sigID";
    private static final String COULD_NOT_FIND_XML_ELEMENT_NAME = "ERROR! Could not find xmlElementName = ";
    private static final String GRID = "#";
    private static final String XML_SIGNATURE_ERROR = "xmlDSignature ERROR: ";
    private Sign x ;
    public void setSign(Sign sign){
        this.x=sign;
    }
    public SignerXML() throws InvalidTransformException,  ClassNotFoundException, SignatureProcessorException {
        System.out.println("1:   ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit.init();");
        ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit.init();
        System.out.println("1:ok");
        try {
            System.out.println("2:   REGISTER smev TRANS;");
            Transform.register(SmevTransformSpi.ALGORITHM_URN, SmevTransformSpi.class.getName());
            System.out.println("2:ok");

        } catch (AlgorithmAlreadyRegisteredException e) {
            System.out.println("2:ERROR");


        }
        santuarioIgnoreLineBreaks(true);
        System.out.println("3:END");

    }

    public static void main(String[] args){
        System.out.print("init JCP");
    }

    private static final String IGNORE_LINE_BREAKS_FIELD = "ignoreLineBreaks";

    private void santuarioIgnoreLineBreaks(Boolean mode) {
        try {
            Boolean currMode = mode;
            AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>() {
                public Boolean run() throws Exception {
                    Field f = XMLUtils.class.getDeclaredField(IGNORE_LINE_BREAKS_FIELD);
                    f.setAccessible(true);
                    f.set(null, currMode);
                    return false;
                }
            });
        } catch (Exception e) {
       //     LOGGER.warning("santuarioIgnoreLineBreaks " );
        }
    }

    public byte[] sign(Sign signer, byte[] data, String xmlElementName, String xmlElementID ) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, IOException, SAXException, ParserConfigurationException, XMLSecurityException, TransformerException {
        X509Certificate certificate=(X509Certificate)signer.getCert();
        PrivateKey privateKey=signer.getPrivate();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setCoalescing(true);
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(data));

        final String canonicalizationMethod = CANONICALIZATION_METHOD;
        String[][] filters = {{XPath2FilterContainer.SUBTRACT, DS_SIGNATURE}};
        String sigId = SIG_ID;
        XMLSignature sig = new XMLSignature(doc, "", Consts.URN_GOST_SIGN, canonicalizationMethod);
        sig.setId(sigId);
        Element anElement = null;
        if (xmlElementName == null) {
            anElement = doc.getDocumentElement();
        } else {
            NodeList nodeList = doc.getElementsByTagName(xmlElementName);
            anElement = (Element) nodeList.item(0);
        }
        if (anElement != null)
            anElement.appendChild(sig.getElement());
        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);
        transforms.addTransform(SmevTransformSpi.ALGORITHM_URN);
        sig.addDocument(xmlElementID == null ? "" : GRID + xmlElementID, transforms, Consts.URN_GOST_DIGEST);
        sig.addKeyInfo(certificate);
        sig.sign(privateKey);
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(bais));
        bais.close();
        return bais.toByteArray();
    }


    public byte[] signconsumerns4(Sign signer, byte[] data) throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        System.out.println("Transferring data>>>>>>>");
       return sign(signer, data, "ns4:CallerInformationSystemSignature", "SIGNED_BY_CONSUMER" );
    }

    public byte[] signcallerns4bycaller(Sign signer, byte[] data) throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        return sign(signer, data, "ns4:CallerInformationSystemSignature", "SIGNED_BY_CALLER" );
    }


    public byte[] signcallernsbycaller(Sign signer, byte[] data) throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        System.out.println("Transferring data>>>>>>>");
        return sign(signer, data, "ns:CallerInformationSystemSignature", "SIGNED_BY_CALLER" );
    }
    public byte[] signcallerns2(Sign signer, byte[] data) throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        return sign(signer, data, "ns2:CallerInformationSystemSignature", "SIGNED_BY_CONSUMER" );
    }

    public byte[] personalsign(Sign signer, byte[] data) throws ParserConfigurationException, IOException, SAXException, XMLSecurityException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, NoSuchProviderException, TransformerException {
        return sign(signer, data, "ns:PersonalSignature", "PERSONAL_SIGNATURE" );
    }
}
