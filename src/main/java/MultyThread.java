import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.gettingDataImplem.DataSource;
import util.SignatureProcessorException;
import util.SignerXML;

import java.io.IOException;
import java.sql.SQLException;

public class MultyThread  extends Thread{
    public String str;
    public DependencyContainer deps;
    public Scheduller sch;

    public MultyThread() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
    }

    public void setSign(SignerXML sign) throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        deps=new DependencyContainer(sign);
        sch = new Scheduller(deps);
    }

    public void setSource(DataSource input){
        this.deps.datasource= input;
    }

    public void run(){
        deps.gis.SupressConsole=true;
        while (true){
            try {
                this.sch.deps.dataImporter.loadDataToInputFlow(false, true);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.sch.processor.sendAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.sch.processor.getResponses();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.deps.performReceiveddata.ProcessResultsTable();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
