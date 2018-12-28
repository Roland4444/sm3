package schedulling.ResultsProcessors;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;

import java.io.IOException;
import java.sql.SQLException;

public class gisResultTest {

    @Test
    public void perform() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        DependencyContainer deps = new DependencyContainer(new SignerXML(new Sign2018(), new Sign2018()));

    }
}