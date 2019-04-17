package schedulling.ResultsProcessors;

import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIAFind.ESIAFindMessageResult;
import essent.Client;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.ResultProcess;
import schedulling.abstractions.RequestData;
import se.roland.Extractor;
import java.io.IOException;
import java.sql.SQLException;

public class findesiaResult implements ResultProcess {
    private Client HttpClient;
    private Extractor Ext;
    public void setPost(Client postman){
        this.HttpClient =postman;
    }
    public void setExtractor(Extractor ext){
        this.Ext=ext;
    }

    @Override
    public void perform(RequestData input) throws SQLException {

    }

    @Override
    public void perform(RequestData Result, InputDataContainer inputFlow) throws SQLException, IOException {
        System.out.println("\n\n\nFLASHING UUID=>"+Result.Identifier );
        String result = Result.ResponsedXML;
        ESIAFindMessageResult Rmsg = new ESIAFindMessageResult();
        Rmsg.oid=Ext.extractTagValue(result, "oid");
        Rmsg.trusted=Ext.extractTagValue(result, "stu");
        Rmsg.ID=inputFlow.get(Result.Identifier).Id;
        Rmsg.BioStu=Ext.extractTagValue(result, "biomStu");
        System.out.println(Ext.extractTagValue(result, "biomStu"));
        System.out.println("Sending asyncronously! to "+inputFlow.get(Result.Identifier).addressToReply);
        HttpClient.send(BinaryMessage.savedToBLOB(Rmsg), inputFlow.get(Result.Identifier).addressToReply);
        System.out.println("Cleaning UP UUID=>"+ Result.Identifier);
        inputFlow.destroy(Result.Identifier);
    }


    public void perform_dumped(RequestData Result, InputDataContainer inputFlow) throws SQLException, IOException {
        System.out.println("\n\n\nFLASHING UUID=>"+Result.Identifier );
        String result = Result.ResponsedXML;
        ESIAFindMessageResult Rmsg = new ESIAFindMessageResult();
        Rmsg.oid=Ext.extractTagValue(result, "oid");
        Rmsg.trusted=Ext.extractTagValue(result, "tns:stu");
        Rmsg.ID=inputFlow.get(Result.Identifier).Id;
        System.out.println("Sending asyncronously! to "+inputFlow.get(Result.Identifier).addressToReply);
        HttpClient.send(BinaryMessage.savedToBLOB(Rmsg), inputFlow.get(Result.Identifier).addressToReply);
        System.out.println("Cleaning UP UUID=>"+ Result.Identifier);
        inputFlow.destroy(Result.Identifier);
    }


    public void performCreate(RequestData Result, InputDataContainer inputFlow) throws SQLException, IOException {
        System.out.println("\n\n\nFLASHING UUID=>"+Result.Identifier );
        String result = Result.ResponsedXML;
        ESIAFindMessageResult Rmsg = new ESIAFindMessageResult();
        Rmsg.oid=null;
        Rmsg.trusted="bad";
        Rmsg.ID=inputFlow.get(Result.Identifier).Id;
        System.out.println("Sending asyncronously! to "+inputFlow.get(Result.Identifier).addressToReply);
        HttpClient.send(BinaryMessage.savedToBLOB(Rmsg), inputFlow.get(Result.Identifier).addressToReply);
        System.out.println("Cleaning UP UUID=>"+ Result.Identifier);
        inputFlow.destroy(Result.Identifier);
    }

    public void performUpgrade(RequestData Result, InputDataContainer inputFlow) throws SQLException, IOException {
        System.out.println("\n\n\nFLASHING UUID=>"+Result.Identifier );
        String result = Result.ResponsedXML;
        ESIAFindMessageResult Rmsg = new ESIAFindMessageResult();
        Rmsg.oid="877687";
        Rmsg.trusted="bad";
        Rmsg.ID=inputFlow.get(Result.Identifier).Id;
        System.out.println("Sending asyncronously! to "+inputFlow.get(Result.Identifier).addressToReply);
        HttpClient.send(BinaryMessage.savedToBLOB(Rmsg), inputFlow.get(Result.Identifier).addressToReply);
        System.out.println("Cleaning UP UUID=>"+ Result.Identifier);
        inputFlow.destroy(Result.Identifier);
    }

}
