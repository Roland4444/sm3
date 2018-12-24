package util;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class xmltransform {


    public String xmldsig(byte[] input) throws InvalidCanonicalizerException, ParserConfigurationException, SAXException, CanonicalizationException, IOException {
        org.apache.xml.security.Init.init();
        Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        byte canonXmlBytes[] = canon.canonicalize(input);
        return (new String(canonXmlBytes));
    }

    public void xmldsig(String input, String output) throws InvalidCanonicalizerException, ParserConfigurationException, SAXException, CanonicalizationException, IOException {
        org.apache.xml.security.Init.init();
        StringBuffer str = new StringBuffer();
        str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        BufferedReader b = new BufferedReader(new FileReader(input));
        String readLine = "";
        while ((readLine = b.readLine()) != null)
            str.append(readLine);
        System.out.print(str.toString());
        Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        byte canonXmlBytes[] = canon.canonicalize(str.toString().getBytes());
        FileWriter wr = new FileWriter(output);
        wr.write(xmldsig(canonXmlBytes));
        wr.close();
    }
}
