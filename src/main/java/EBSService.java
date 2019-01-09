import Message.abstractions.BinaryMessage;
import Message.toSMEV.MessageSMEV;
import crypto.Gost3411Hash;
import impl.JAktor;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.InputDataBlock;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;

import java.io.IOException;
import java.sql.SQLException;

public class EBSService extends JAktor {
    Gost3411Hash hash = new Gost3411Hash();
    private DependencyContainer deps_c;
    public EBSService(DependencyContainer deps){
        this.deps_c = deps;
    }
    @Override
    public void receive(byte[] message) throws IOException {
        System.out.println("\n\n\nRECEIVED!!!!!!!!!!!!!1\n\n\n\n");
        MessageSMEV restoring = (MessageSMEV) BinaryMessage.restored(message);
        System.out.println("ID=>   "+restoring.ID);
        System.out.println(restoring.pseudo);
        System.out.println(restoring.addressToReply);
        System.out.println("Base 64 => "+hash.base64(restoring.DataToWork));
        byte[] BinaryXML=deps.tableProcessor.OperatorMap.get(restoring.pseudo).generateUnsSOAP(restoring.DataToWork);

        InputDataBlock InputBlock = new InputDataBlock(restoring.ID, restoring.pseudo, BinaryXML, restoring.addressToReply);
        deps_c.inputDataFlow.put(InputBlock);
    }
    static DependencyContainer deps;
    static Scheduller sch;
    public static void main(String[] args) throws InterruptedException {
        Readfile r = new Readfile("sqlset");
        try {
            deps = new DependencyContainer(r.addressSAAJ(), new SignerXML(new Sign2018(), new Sign2018()));

         //   deps = new DependencyContainer(r.addressSAAJ(), new SignerXML(new TestSign2019(), new TestSign2019()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SignatureProcessorException e) {
            e.printStackTrace();
        } catch (InvalidTransformException e) {
            e.printStackTrace();
        } catch (AlgorithmAlreadyRegisteredException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sch = new Scheduller(deps);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SignatureProcessorException e) {
            e.printStackTrace();
        } catch (InvalidTransformException e) {
            e.printStackTrace();
        } catch (AlgorithmAlreadyRegisteredException e) {
            e.printStackTrace();
        }
        deps.gis.SupressConsole=false;
        EBSService  ebss=new EBSService(deps);
        ebss.setAddress("http://127.0.0.1:20000/");
        ebss.spawn();

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
