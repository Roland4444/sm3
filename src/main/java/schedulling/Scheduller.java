package schedulling;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import schedulling.ProcessorImplements.ProcessorPuttinDB;
import schedulling.TaskerImplements.TaskerFromDB;
import schedulling.abstractions.*;
import util.*;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Scheduller implements Controller, Serializable {
    public SQLWrapper sqlWrapper;
    public TaskerFromDB tasker;
    public ProcessorPuttinDB processor;
    public DependencyContainer deps;

    public Scheduller(DependencyContainer deps) throws SQLException, ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        this.deps = deps;
        tasker=new TaskerFromDB(this.deps);
        processor= new ProcessorPuttinDB(this.deps.inputDataFlow, this.deps.dbReqs, this.deps.logger, this.deps.tableProcessor,
                this.deps.temp, this.deps.sucesfullqueed, this.deps.errorqueed, this.deps.gis_, this.deps.stopperGetResponce, this.deps.ext, this.deps.ignored, this.deps.freezer);
        this.sqlWrapper = new SQLWrapper();
        this.sqlWrapper.setExtractor(this.deps.ext);
    }

    public Scheduller(DependencyContainer deps, boolean logging) throws SQLException, ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, IOException {
        this.deps = deps;
        tasker=new TaskerFromDB(this.deps);
        processor= new ProcessorPuttinDB(this.deps.inputDataFlow, this.deps.dbReqs, this.deps.logger, this.deps.tableProcessor,
                this.deps.temp, this.deps.sucesfullqueed, this.deps.errorqueed, this.deps.gis_, this.deps.stopperGetResponce, this.deps.ext, this.deps.ignored, this.deps.freezer);
        this.sqlWrapper = new SQLWrapper(logging, deps.logger);
        this.sqlWrapper.setExtractor(this.deps.ext);
        if (logging){
            String description="Starting Scheduller";
            String GUID="none";
            Date date=new Date();
            Event event = new Event(description, GUID, "none", date,deps.logger.StatusLogs.get("start"));
            deps.logger.writeEvent(event);
        }

    }

    public void run() throws Exception {
        deps.gis.SupressConsole=false;
        while (true){
            deps.dataImporter.loadDataToInputFlow(false, true);
            this.processor.sendAll();
            this.processor.getResponses();
            this.deps.performReceiveddata.ProcessResultsTable();
        }
    }

    public void loop() throws Exception {
        while (true) {
            this.tasker.run();
            sleep(12);
            this.processor.run();
        }
    };
    public void loop(int counts) throws Exception {
        for (int i =0; i<counts; i++){
            System.out.println(i+"    trying");
            // tasker.loop();
         //   processor.sendAll();
            sleep(5000);
            processor.run();
        }
    }



}
