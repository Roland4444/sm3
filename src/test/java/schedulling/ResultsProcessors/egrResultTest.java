package schedulling.ResultsProcessors;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class egrResultTest {
    DependencyContainer deps = new DependencyContainer(new SignerXML(new Sign2018(), new Sign2018()));
    public egrResultTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
    }

  //  @Test
    public void perform() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        DependencyContainer deps = new DependencyContainer(new SignerXML(new Sign2018(), new Sign2018()));        PreparedStatement pst = deps.executor.getConn().prepareStatement("EXEC fns_ul_ip_smev3 '3582';");
        pst.executeUpdate();
    }


   // @Test
    public void perform2() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
        DependencyContainer deps = new DependencyContainer(new SignerXML(new Sign2018(), new Sign2018()));        PreparedStatement pst = deps.executor.getConn().prepareStatement("EXEC fns_ul_ip_smev3 '"+"3582"+"';");
        pst.executeUpdate();
    }


    @Test
    public void perform1() throws IOException {
        String filename="xml4test/res.xml";
        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
    }

 //   @Test
    public void get_f_rec_id() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {

        Scheduller sch = new Scheduller(deps);
        assertEquals("1864", deps.egrResult.get_f_rec_id("3609"));

    }

    @Test
    public void FIO() throws IOException, SQLException {
        String filename="xml4test/res.xml";
        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
        assertEquals("ИМЯ 55009109000004ФАМИЛИЯ 55009109000004ОТЧЕСТВО 55009109000004",deps.egrResult.FIO(arr));
    }


    @Test
    public void FIO1() throws IOException, SQLException {
        String filename="xml4test/545.xml";
        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
        assertEquals("ИМЯ 55009109000004ФАМИЛИЯ 55009109000004ОТЧЕСТВО 55009109000004",deps.egrResult.FIO(arr));
    }

}