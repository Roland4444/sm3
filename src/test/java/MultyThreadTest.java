import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.abstractions.DependencyContainer;
import schedulling.gettingDataImplem.DataSource;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class MultyThreadTest {

    public final String gis_  ="gis";
    public final String egr_  ="egr";
    public final String pass_ ="pass";

    public MultyThreadTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
    }

   ////////////////// @Test
    public void run() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, InterruptedException, IOException {
        SignerXML signer = new SignerXML(new Sign2018(), new Sign2018());

        DataSource datasource1 = new DataSource();
        datasource1.Source.put(pass_, "fms_zap");

        DataSource datasource2 = new DataSource();
        datasource1.Source.put(gis_, "gis_files");
        datasource1.Source.put(egr_, "fns_files");

        MultyThread thrFMS = new MultyThread();
        thrFMS.setSign(signer);
        thrFMS.setSource(datasource1);
        thrFMS.deps.gis.SupressConsole=true;
        thrFMS.start();

        MultyThread throther = new MultyThread();
        throther.setSign(signer);
        throther.setSource(datasource2);
        throther.deps.gis.SupressConsole=true;
        throther.start();
        throther.join();
    }
}