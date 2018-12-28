package schedulling.abstractions;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.Scheduller;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.TestSign2019;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DependencyContainerTest {
    DependencyContainer depsUseExtern = new DependencyContainer(true);

    public DependencyContainerTest() throws SignatureProcessorException, InvalidTransformException, ClassNotFoundException, IOException, SQLException, AlgorithmAlreadyRegisteredException {
        depsUseExtern.initContainer();

    }

    @Test
    public void TestDependencyContainer() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        assertNotEquals(null,depsUseExtern.sign);
    }

    @Test
    public void initContainer() {
    }
}