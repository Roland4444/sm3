import Message.abstractions.BinaryMessage;
import Message.toSMEV.MessageSMEV;
import crypto.Gost3411Hash;
import impl.JAktor;
import impl.echoJAKtor;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import readfile.Readfile;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.InputDataBlock;
import transport.SAAJ;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.FNS2001;
import util.crypto.TestSign2001;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.sql.SQLException;

public class SingleShot extends JAktor {
    Gost3411Hash hash = new Gost3411Hash();
    private DependencyContainer deps_c;

    public SingleShot(DependencyContainer deps) {
        this.deps_c = deps;
    }

    @Override
    public void receive(byte[] message) throws IOException {
        System.out.println("\n\n\nRECEIVED!!!!!!!!!!!!!1\n\n\n\n");
        MessageSMEV restoring = (MessageSMEV) BinaryMessage.restored(message);
        System.out.println("ID=>   " + restoring.ID);
        System.out.println(restoring.pseudo);
        System.out.println(restoring.addressToReply);
        System.out.println("Base 64 => " + hash.base64(restoring.DataToWork));
        byte[] BinaryXML = null;
        try {
            BinaryXML = deps.tableProcessor.OperatorMap.get(restoring.pseudo).generateUnsSOAP(restoring.DataToWork);
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputDataBlock InputBlock = new InputDataBlock(restoring.ID, restoring.pseudo, BinaryXML, restoring.addressToReply);
        deps_c.inputDataFlow.put(InputBlock);
    }

    static DependencyContainer deps;
    static Scheduller sch;

    public static void main(String[] args) throws InterruptedException {

        Readfile r = new Readfile("sqlset");
        try {
            deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()), true);
            deps.transport = new SAAJ(r.addressSAAJ());
            deps.ftpAddr=r.FTP();
            deps.ignite();
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
            deps.gis.SupressConsole = false;
            SingleShot ebss = new SingleShot(deps);
            ebss.setAddress("http://127.0.0.1:20005/");
            ebss.spawn();

            Integer delay = Integer.valueOf(r.delay());
            int i = 0;
            ClientMock cmck = new ClientMock();
            cmck.run();
            while (true) {
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

        } catch (SignatureProcessorException e) {
            e.printStackTrace();
        } catch (InvalidTransformException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AlgorithmAlreadyRegisteredException e) {
            e.printStackTrace();
        }
    }

    static class ClientMock extends Thread{
        public void run(){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            echoJAKtor sender = new echoJAKtor();
            sender.setAddress("http://127.0.0.1:12121/");
            try {
                sender.spawn();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MessageSMEV msg = new MessageSMEV();
            msg.ID="0000";
            msg.pseudo="ebs";
            try {
                msg.DataToWork =BinaryMessage.readBytes("4ProdGenned.bin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            msg.addressToReply="http://127.0.0.1:12121/";
            //     for (int i=0; i<10; i++){
            msg.ID=Integer.toString(0);
            //       sender.send(BinaryMessage.savedToBLOB(msg),"http://127.0.0.1:20005/");//<==8!!!!
        }
    }
}
