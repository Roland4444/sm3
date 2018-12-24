package schedulling.abstractions.OutDataPerfomImpl;

import schedulling.abstractions.InfoAllRequests;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.OutPutDataProcess;
import schedulling.abstractions.OutDataPerform.*;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.sql.SQLException;

public class PerfomReceivedData implements OutPutDataProcess {
    private tableResultProcessors  tableProcessors;
    private InfoAllRequests InfoRequests;
    private InputDataContainer InputFlow;
    public PerfomReceivedData(tableResultProcessors tableResult, InfoAllRequests InfoRequests, InputDataContainer inputDataContainer){
        this.InfoRequests=InfoRequests;
        this.tableProcessors=tableResult;
        this.InputFlow=inputDataContainer;
    }



    public void ProcessResultsTable() throws SQLException, IOException {
        //System.out.println("Pool request size ="+this.InfoRequests.pool.size());
        if (this.InputFlow.pool.size()>0){
            for (int i=0; i<this.InfoRequests.pool.size(); i++){
                if (this.InfoRequests.pool.get(i).ResponsedXML == null) continue;
                String pseudo = this.InfoRequests.pool.get(i).operator;
                System.out.print(" In processor table=>"+pseudo);
                ResultProcess operator = this.tableProcessors.TableResultProcessors.get(pseudo);
                operator.perform(InfoRequests.pool.get(i), this.InputFlow);
                InfoRequests.pool.remove(i);    //<<===
            }
        }

    }

    public void setFlow(tableResultProcessors tableResult, InfoAllRequests InfoRequests, InputDataContainer inputDataContainer){
        this.InfoRequests=InfoRequests;
        this.tableProcessors=tableResult;
        this.InputFlow=inputDataContainer;
    }
}
