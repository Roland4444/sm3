package schedulling.abstractions;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.Scheduller;
import util.SignatureProcessorException;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DependencyContainerTest {
    @Test
    public void TestDependencyContainer() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        DependencyContainer deps = new DependencyContainer();
        assertNotEquals(null, deps.datasource.Source);
    }
}