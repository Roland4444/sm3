package schedulling.abstractions.OutDataPerform;

import java.io.IOException;
import java.sql.SQLException;

public interface OutPutDataProcess {
    public void ProcessResultsTable() throws SQLException, IOException;
}
