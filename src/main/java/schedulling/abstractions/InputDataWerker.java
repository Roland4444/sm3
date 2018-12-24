package schedulling.abstractions;

import java.sql.SQLException;
import java.util.Map;

public interface InputDataWerker {
   public Map<String, byte[]> getData() throws SQLException;

}
