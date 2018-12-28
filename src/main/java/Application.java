import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;

import java.sql.SQLException;

public class Application {
    static DependencyContainer deps;
    static Scheduller sch;

    public Application() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
    }

    public static void main(String[] args) throws Exception {
        Readfile r = new Readfile("sqlset");
        deps = new DependencyContainer(r.addressSAAJ(), new SignerXML(new Sign2018(), new Sign2018()));
        sch = new Scheduller(deps);
        deps.gis.SupressConsole=false;
        while (true){
            deps.dataImporter.loadDataToInputFlow(false, false);
            sch.processor.sendAll();
            sch.processor.getResponses();
            sch.deps.performReceiveddata.ProcessResultsTable();
        }
    }
}
