package logging;

import org.junit.Test;
import schedulling.abstractions.Freezer;
import util.Event;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class MyLoggerTest {

    @Test
    public void readLog() throws IOException {
        Freezer fr = new Freezer();
        String pathtoLog = "logs/binary/";
        String description="starting in queue";
        String GUID="%%%0";
        String pseudo="egr";
        Date date=new Date();

        Event event = new Event(description, GUID, pseudo, date,0);
        String filename=pathtoLog+"1.bin";
        Path p = Paths.get(filename);

        FileOutputStream fos2 = new FileOutputStream(filename);
        fos2.write(fr.saveEvent(event));
        fos2.close();
        LogBlock log = new LogBlock();
        log.logblock.add(event);
        MyLogger mylogger = new MyLogger(pathtoLog);
        mylogger.setFreezer(fr);
        assertEquals(mylogger.readLog(pathtoLog).logblock.get(0).GUID, log.logblock.get(0).GUID);
        assertEquals(mylogger.readLog(pathtoLog).logblock.get(0).date, log.logblock.get(0).date);
        assertEquals(mylogger.readLog(pathtoLog).logblock.get(0).pseudo, log.logblock.get(0).pseudo);
        assertEquals(mylogger.readLog(pathtoLog).logblock.get(0).description, log.logblock.get(0).description);
    }

    @Test
    public void readLogBig() throws IOException {
        Freezer fr = new Freezer();
        String pathtoLog = "logs/binary/";
        String description="starting in queue";
        String GUID="%%%0";
        String pseudo="egr";
        Date date=new Date();

        for (int i =0;i<100; i++){
            Event event = new Event(description, GUID, pseudo, date,i%3);
            String filename=pathtoLog+i+".bin";
            Path p = Paths.get(filename);

            FileOutputStream fos2 = new FileOutputStream(filename);
            fos2.write(fr.saveEvent(event));
            fos2.close();
        }

        MyLogger mylogger = new MyLogger(pathtoLog);
        mylogger.setFreezer(fr);
        assertNotEquals(0, mylogger.readLog(pathtoLog).logblock.size());
        FileOutputStream fos = new FileOutputStream("binData/logfull.bin");
        fos.write(fr.saveLogBlocks(mylogger.readLog(pathtoLog)));
        fos.close();
    }

    @Test
    public void createLog() throws IOException, InterruptedException {
        Freezer fr = new Freezer();
        String pathtoLog = "logs/binary/";


        for (int i =0;i<10; i++){
            String description="starting in queue";
            String GUID="%%%0";
            String pseudo="egr";
            Date date=new Date();
            Event event = new Event(description, GUID, pseudo, date,i%3);
            String filename=pathtoLog+i+".bin";

            sleep(1000);
            FileOutputStream fos2 = new FileOutputStream(filename);
            fos2.write(fr.saveEvent(event));
            fos2.close();
        }


    }

   // @Test
    public void setFreezer() throws IOException {//see qunatuty files on logs/binary
        Freezer fr = new Freezer();
        String pathtoLog = "logs/binary/";
        MyLogger logger = new MyLogger(pathtoLog);
        logger.setFreezer(fr);
        assertEquals(30, logger.currentEventId);

    }

   // @Test
    public void counter() throws IOException {
        Freezer fr = new Freezer();
        String pathtoLog = "logs/binary/";
        MyLogger logger = new MyLogger(pathtoLog);
        logger.setFreezer(fr);
        assertEquals(4504, logger.currentEventId);
    }
}