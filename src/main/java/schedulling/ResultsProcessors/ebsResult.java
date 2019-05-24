package schedulling.ResultsProcessors;

import Message.BKKCheck.ResponceMessage;
import Message.abstractions.BinaryMessage;
import essent.J8Client;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.ResultProcess;
import schedulling.abstractions.RequestData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class ebsResult implements ResultProcess {
    J8Client httpclient;
    public void setHttpclient(J8Client httpclient){
        this.httpclient = httpclient;
    }
    @Override
    public void perform(RequestData input) throws SQLException {

    }

    @Override
    public void perform(RequestData input, InputDataContainer inputFlow) throws SQLException, IOException {
        String dir = "xmls";
        System.out.println("\n\n\nWRITING TO =>>>>"+input.Identifier+".XML");
        (new File(dir)).mkdirs();
        BinaryMessage.write(input.ResponsedXML.getBytes(), dir+File.separator+input.Identifier+".XML");

        if (input.ResponsedXML.indexOf("<Code>SUCCESS</Code>")>0) {
            System.out.println("\n\nREGISTER SUCCESS\n\n");
            ResponceMessage resp = new ResponceMessage(0, input.Identifier);
            httpclient.send(BinaryMessage.savedToBLOB(resp), inputFlow.get(input.Identifier).addressToReply);
            inputFlow.destroy(input.Identifier);
            return;
        }
        System.out.println("\n\nREGISTER FAILED\n\n");
        ResponceMessage resp = new ResponceMessage(1, input.Identifier);
        httpclient.send(BinaryMessage.savedToBLOB(resp), inputFlow.get(input.Identifier).addressToReply);
        inputFlow.destroy(input.Identifier);

    }
}
