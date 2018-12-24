package schedulling.abstractions;

import schedulling.ProcessorImplements.ProcessorPuttinDB;
import schedulling.TaskerImplements.TaskerFromDB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface Controller {
    public Tasker tasker=null;
    public Processor processor=null;
}
