package logging;

import schedulling.abstractions.Freezer;
import util.Event;
import util.NetworkEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NetLogBlock {
    Freezer fr = new Freezer();
    public ArrayList<NetworkEvent> logblock=new ArrayList();
    public void put(NetworkEvent input){
        this.logblock.add(input);
    }
    public void  load(String pathToLog) throws IOException {
        ArrayList<NetworkEvent> log=new ArrayList();
        int counter=1;
        File f = new File(pathToLog+counter+".nel");
        while (f.exists() && !f.isDirectory()) {
            log.add(fr.restoreNetWorkEvent(Files.readAllBytes( Paths.get(f.getPath()))));
            f = new File(pathToLog+(counter++)+".nel");
        }
        this.logblock=log;


    }
}
