package schedulling.ResultsProcessors;

import Message.abstractions.BinaryMessage;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.ResultProcess;
import schedulling.abstractions.RequestData;

import java.io.IOException;
import java.sql.SQLException;

public class ebsResult implements ResultProcess {
    @Override
    public void perform(RequestData input) throws SQLException {

    }

    @Override
    public void perform(RequestData input, InputDataContainer inputFlow) throws SQLException, IOException {
        System.out.println("\n\n\nWRITING TO =>>>>"+input.Identifier+".XML");
        BinaryMessage.write(input.ResponsedXML.getBytes(), input.Identifier+".XML");
        inputFlow.destroy(input.Identifier);
    }
}
