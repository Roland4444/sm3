package standart;

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
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class createsia extends Standart {
    public createsia(StreamResult sr, SignerXML sihner, util.Sign personal, Sign Full){
        this.out = sr;
        this.signer =sihner;
        this.personal=personal;
        this.MainSign =Full;
    }
    public void setLink(TempDataContainer temp){
        this.temp=temp;
    }

    @Override
    public boolean check(byte[] input) throws IOException {
        return false;
    }

    @Override
    public void setinput(byte[] input) throws IOException {

    }

    @Override
    public byte[] GetSoap() throws IOException {
        return InfoToRequest;
    }

    @Override
    public byte[] SignedSoap() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, XMLSecurityException, IOException, CertificateException, NoSuchAlgorithmException, TransformerException, ParserConfigurationException, UnrecoverableEntryException, NoSuchProviderException, SAXException, KeyStoreException {
        return new byte[0];
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
        return new byte[0];
    }
}
