package schedulling.abstractions.OutDataPerform;

import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.RequestData;

import java.io.IOException;
import java.sql.SQLException;

public interface ResultProcess {
    public void perform(RequestData input) throws SQLException;

    public void perform(RequestData input, InputDataContainer inputFlow) throws SQLException, IOException;


}
