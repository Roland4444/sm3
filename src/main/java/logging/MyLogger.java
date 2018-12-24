package logging;

import schedulling.abstractions.Freezer;
import se.roland.Extractor;
import util.ActivityEvent;
import util.Event;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyLogger {
    private Freezer fr;
    public Map<String, Integer> StatusLogs=new HashMap<String, Integer>();
    public void setFreezer(Freezer fr){
        this.fr=fr;
    }
    public void setExtractor(Extractor ext){
        this.ext=ext;

    }
    private String pathtoLog="";
    public int currentEventId=0;
    private Extractor ext;
    public MyLogger(String pathToLog) throws IOException {

        this.pathtoLog=pathToLog;
        StatusLogs.put("ok",0);
        StatusLogs.put("warn",1);
        StatusLogs.put("err",2);
        StatusLogs.put("error",2);
        StatusLogs.put("start",3);

        File directory = new File(pathToLog);
        if (! directory.exists())
            directory.mkdirs();
        else {
            Files.walk(Paths.get(pathToLog))
                    .filter(p->p.toString().indexOf(".bin")>0)
                    .sorted()
                    .forEach(a-> {
                        currentEventId++;
                    });
        }


    }


    public void writeActivityEvent(ActivityEvent event) throws IOException {
        String filename=pathtoLog+"last.activity";
        FileOutputStream fos2 = new FileOutputStream(filename);
        fos2.write(fr.saveActivityEvent(event));
        fos2.close();
    }

    public ActivityEvent readAcrivityEvent() throws IOException {
        File ActivityFile=new File(pathtoLog+"last.activity");
        return fr.restoreActivityEvent(Files.readAllBytes( Paths.get(ActivityFile.getPath())));
    }


    public void writeEvent(Event event) throws IOException {
        String filename=pathtoLog+(++currentEventId)+".bin";
        FileOutputStream fos2 = new FileOutputStream(filename);
        fos2.write(fr.saveEvent(event));
        fos2.close();
    }

    public void counter(){

    }

    public LogBlock readLog(String walkthrow) throws IOException {
        LogBlock log = new LogBlock();

        int counter=1;
        File f = new File(walkthrow+counter+".bin");
        while (f.exists() && !f.isDirectory()) {

            log.put(fr.restoreEvent(Files.readAllBytes( Paths.get(f.getPath()))));
            f = new File(walkthrow+(counter++)+".bin");
        }

        return log;
    }

    StringBuffer bufferlog = new StringBuffer();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date;
    public void log(String log){
        this.date = new Date();
        System.out.println(log+"@"+dateFormat.format(date));
    }

    public void logToBuffer(String log){
        this.date = new Date();
        bufferlog.append(log+"@"+dateFormat.format(date)+'\n');

    }

    public void logToBuffer(String prefix, String log){
        this.date = new Date();
        bufferlog.append("\n-----------------------"+prefix.toUpperCase()+"@"+dateFormat.format(date)+"----------------------------------\n");
        bufferlog.append(log);
        bufferlog.append("\n-----------------------END   "+prefix.toUpperCase()+ "----------------------------------\n");

    }

    public void suspend(String inp) throws IOException {
        FileWriter wr = new FileWriter(inp);
        wr.write(bufferlog.toString());

    }
}
