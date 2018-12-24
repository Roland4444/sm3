package schedulling.TaskerImplements;


import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Tasker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class TaskerFromDB implements Tasker {
    public DependencyContainer deps;
    public TaskerFromDB(DependencyContainer deps){
        this.deps=deps;
    }
    public void run() throws SQLException {
      /*  Iterator x = deps.dataImporter.getData().entrySet().iterator();
        while (x.hasNext()){
            Map.Entry pair = (Map.Entry) x.next();
            String identifier = (String) pair.getKey();
            byte[] data = (byte[]) pair.getValue();
            if (deps.datamap.DataConveer.get(identifier)==null){
                    Resolver resulter = new Resolver();
                    System.out.println("SETTING>>>>+");
                    System.out.println(deps.Idgen.getPseudo(identifier));
                    resulter.setOperator(deps.Idgen.getPseudo(identifier));
                    resulter.setDataToWork(data);
                    deps.datamap.DataConveer.put(identifier, resulter);
            }
        }
*/
    };
}
