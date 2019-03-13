import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import transport.SAAJ;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.TestSign2001;
import java.io.IOException;
import java.sql.SQLException;

public class EBSApp {
    static DependencyContainer deps;
    static Scheduller sch;

    public EBSApp() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
    }

    public static void main(String[] args) throws Exception {
        Readfile r = new Readfile("sqlset");
        deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()), true);
        deps.transport = new SAAJ(r.addressSAAJ());
        deps.ftpAddr=r.FTP();
        deps.ignite();
        deps.gis.SupressConsole=false;
        sch = new Scheduller(deps);
        Integer delay = Integer.valueOf(r.delay());
        int i=0;
        while (true){
            try {
                System.out.println("\n\n\nSENDING ALL ==>");
                sch.processor.sendAll();

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("\n\n\nGET RESPONCES ==>");
                sch.processor.getResponses();
                //     sch.processor.getResponsesEBS();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("\n\n\nPERFORM RECEIVED!!! ==>");
                sch.deps.performReceiveddata.ProcessResultsTable();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(delay);
            System.out.println(i++);
        }

    }

}

