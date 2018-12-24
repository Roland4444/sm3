import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Freezer;
import util.SignatureProcessorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import org.junit.Test;
public class MainTest {
    DependencyContainer deps = new DependencyContainer();
    Scheduller sch = new Scheduller(deps);
    String rawSave = "binData/dumpREQS.bin";
    String SaveInputFlow = "binData/dumpINPUTFLOW.bin";

    String rawSave0 = "binData/dumpREQS.bin0";
    String SaveInputFlow0 = "binData/dumpINPUTFLOW.bin0";

    public MainTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
    }

   // @Test
    public void main() throws Exception {
        deps.gis.SupressConsole = true;
        flush();

        Freezer fr = new Freezer();

        deps.dataImporter.loadDataToInputFlow(true);

        sch.processor.sendAll();
        File file = new File(SaveInputFlow);

       // if (!file.exists()) {
           System.out.println("Dumping rawxml flow");
           FileOutputStream fosinput = new FileOutputStream(SaveInputFlow);
           fosinput.write(fr.saveInputFlow(sch.deps.inputDataFlow));
           fosinput.close();
        //}

       // sleep(12000);
        sch.processor.getAllResponses(10);

        File file2 = new File(rawSave);

        //if (!file2.exists()) {
           System.out.println("Dumping outputflow flow");
           FileOutputStream fos = new FileOutputStream(rawSave);
           fos.write(fr.saverawdbReqs(sch.deps.dbReqs));
           fos.close();
                //    sch.deps.performReceiveddata.ProcessResultsTable();

        //}
    }


   // @Test
    public void ProcessResponce() throws IOException, ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        Freezer fr = new Freezer();

        Path p = Paths.get(rawSave);
        byte[] arr = Files.readAllBytes(p);

        Path p1 = Paths.get(SaveInputFlow);
        byte[] arr1 = Files.readAllBytes(p1);

        sch.deps.inputDataFlow = fr.restoreInputFlow(arr1);

        sch.deps.dbReqs = fr.restoreInfoAll(arr);

        sch.deps.performReceiveddata.setFlow(sch.deps.tableResultProcessors, sch.deps.dbReqs, sch.deps.inputDataFlow);
      // assertEquals(1, sch.deps.dbReqs.pool.size());
        System.out.println(sch.deps.dbReqs.pool.get(0).Identifier);
        System.out.println(sch.deps.dbReqs.pool.get(0).GennedId);
        System.out.println(sch.deps.dbReqs.pool.get(0).Status);
        System.out.println(sch.deps.dbReqs.pool.get(0).ResponsedXML);
        System.out.println(sch.deps.dbReqs.pool.get(0).OriginalXML);

        sch.deps.performReceiveddata.ProcessResultsTable();
    }

    @Test
    public void flush() throws Exception {
        String result = getrespreq();

        while (result.indexOf(":MessageID")>0){
            String id=deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id="+ originalid);
            this.deps.gis.Ack(id);
            result = getrespreq();
        }
    }

    String getrespreq() throws Exception {
        String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2014-02-11T17:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;
    };



   //////////////////////// @Test
    public void runn() throws Exception {
        deps.gis.SupressConsole=false;

        while (true){
            deps.dataImporter.loadDataToInputFlow(false);
            sch.processor.sendAll();
            sleep(1000);
            //sch.processor.getAllResponses(20, false);
            sch.processor.getResponses();
            sch.deps.performReceiveddata.ProcessResultsTable();

        }
    }

    public void clearDump(){
        File file = new File(SaveInputFlow);

        if (file.exists())
            file.delete();

        File file2 = new File(rawSave);

        if (file2.exists())
            file2.delete();


    }


   // @Test
    public void runonceSTEP1() throws Exception {
        deps.gis.SupressConsole=false;
        deps.dataImporter.loadDataToInputFlow(false);
        sch.processor.sendAll();
        sleep(1000);
        clearDump();
        File file = new File(SaveInputFlow);

         if (!file.exists()) {
        System.out.println("Dumping rawxml flow");
        FileOutputStream fosinput = new FileOutputStream(SaveInputFlow);
        fosinput.write(deps.freezer.saveInputFlow(sch.deps.inputDataFlow));
        fosinput.close();
        }

        sleep(2000);
        sch.processor.getAllResponses(10);

        File file2 = new File(rawSave);

        if (!file2.exists()) {
        System.out.println("Dumping outputflow flow");
        FileOutputStream fos = new FileOutputStream(rawSave);
        fos.write(deps.freezer.saverawdbReqs(sch.deps.dbReqs));
        fos.close();
        //    sch.deps.performReceiveddata.ProcessResultsTable();

        }


    }

  //  @Test
    public void restoreProcessSTEP2() throws IOException, ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {

        deps.inputDataFlow = deps.freezer.restoreInputFlow(Files.readAllBytes( Paths.get(SaveInputFlow)));

        deps.dbReqs = deps.freezer.restoreInfoAll(Files.readAllBytes(Paths.get(rawSave)));

        sch.deps.performReceiveddata.setFlow(deps.tableResultProcessors, deps.dbReqs, deps.inputDataFlow);

        sch.deps.performReceiveddata.ProcessResultsTable();
    }







    public void run() throws Exception {
        deps.gis.SupressConsole=false;
        while (true){
            deps.dataImporter.loadDataToInputFlow(false);
            sch.processor.sendAll();
            sleep(1000);
            sch.processor.getAllResponses();
            sch.deps.performReceiveddata.ProcessResultsTable();
        }
    }


//loadDataToInputFlowiniversal

  ////////////////////  @Test
    public void run2ProductionReady() throws Exception {
        deps.gis.SupressConsole=false;
       // deps.inputDataFlow = deps.freezer.restoreInputFlow(Files.readAllBytes( Paths.get(SaveInputFlow)));
        while (true){
            deps.dataImporter.loadDataToInputFlow(false, true);
            sch.processor.sendAll();

            //  sleep(1000);
            sch.processor.getResponses();
            sch.deps.performReceiveddata.ProcessResultsTable();
        }
    }


    ////////////////////@Test
    public void run2ProductionReadyuniversal() throws Exception {
        deps.gis.SupressConsole=false;
        // deps.inputDataFlow = deps.freezer.restoreInputFlow(Files.readAllBytes( Paths.get(SaveInputFlow)));
        sch.processor.setExecutor(deps.executor);
        sch.processor.setIdentifier(deps.Idgen);
        while (true){
            deps.dataImporter.loadDataToInputFlowiniversal(false, true);
            sch.processor.sendAllUniversal();

            sleep(200);
            sch.processor.getResponcesAnyUniversal();
           // sch.deps.performReceiveddata.ProcessResultsTable();
        }
    }

 //   @Test
    public void runonceSTEP1un() throws Exception {
        deps.gis.SupressConsole=false;
        deps.dataImporter.loadDataToInputFlowiniversal(false, true);
        sch.processor.sendAll();
        sleep(1000);
        clearDump();
        File file = new File(SaveInputFlow);

        if (!file.exists()) {
            System.out.println("Dumping rawxml flow");
            FileOutputStream fosinput = new FileOutputStream(SaveInputFlow);
            fosinput.write(deps.freezer.saveInputFlow(sch.deps.inputDataFlow));
            fosinput.close();
        }

        sleep(2000);
        sch.processor.getAllResponses(10);

        File file2 = new File(rawSave);

        if (!file2.exists()) {
            System.out.println("Dumping outputflow flow");
            FileOutputStream fos = new FileOutputStream(rawSave);
            fos.write(deps.freezer.saverawdbReqs(sch.deps.dbReqs));
            fos.close();
            //    sch.deps.performReceiveddata.ProcessResultsTable();

        }

        deps.inputDataFlow = deps.freezer.restoreInputFlow(Files.readAllBytes( Paths.get(SaveInputFlow)));

        deps.dbReqs = deps.freezer.restoreInfoAll(Files.readAllBytes(Paths.get(rawSave)));

        sch.deps.performReceiveddata.setFlow(deps.tableResultProcessors, deps.dbReqs, deps.inputDataFlow);

        sch.deps.performReceiveddata.ProcessResultsTable();


    }



    @Test
    public void perfom() throws IOException {
        String filename="xml4test/res.xml";
        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
        String xml = new String(arr);
        assertNotEquals(null, xml);
        System.out.println(xml);
    }
}