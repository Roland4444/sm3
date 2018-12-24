package schedulling.ResultsProcessors;

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
        System.out.println(input.ResponsedXML);
        inputFlow.destroy(input.Identifier);
    }
}
