package schedulling.abstractions;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import logging.LogBlock;
import schedulling.Scheduller;
import util.ActivityEvent;
import util.Event;
import util.SignatureProcessorException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class FreezerTest {

    Freezer fr = new Freezer();
    DependencyContainer deps = new DependencyContainer();
    Scheduller sch = new Scheduller(deps);
    RequestData test= new RequestData();
    String pathtoLog = "logs/binary/";
    String ReqDump="binData/ReqInfoDump.bin";
    String resultier="xml4test/resultier.xml";
    String resulttext="xml4test/resulttext.xml";
    String out="xml4test/outtext.xml";
    public FreezerTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
    }

    @Test
    public void save() throws ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {

        String uid1, uid2;
        uid1 = deps.uuidgen.generate();
        uid2 = deps.uuidgen.generate();
        test.GennedId   = uid1;
        test.Identifier = uid2;
        sch.deps.dbReqs.put(test);
        Freezer fr = new Freezer();
        InfoAllRequests clone =fr.restoreInfoAll(fr.savedbReqs(sch));
        assertEquals(clone.get(uid2,true).GennedId, sch.deps.dbReqs.get(uid2,true).GennedId);
    }

    @Test
    public void save2() throws ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {

        String uid1, uid2;
        uid1 = deps.uuidgen.generate();
        uid2 = deps.uuidgen.generate();
        test.GennedId   = uid1;
        test.Identifier =uid2;
        sch.deps.dbReqs.put(test);

        InfoAllRequests clone =fr.restoreInfoAll(fr.savedbReqs(sch));
        assertEquals(clone.get(uid2,true).GennedId, sch.deps.dbReqs.get(uid2,true).GennedId);
    }

    @Test
    public void savedataMap() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException {
        String id="0x000x";
        InputDataContainer mock = new InputDataContainer();
        InputDataBlock blob = new InputDataBlock();
        blob.DataToWork = "String".getBytes();
        blob.operator="gis";
        blob.Id=id;
        assertEquals(0, mock.put(blob));
        assertEquals("gis", mock.pool.get(0).operator);
        InputDataContainer restored = fr.restoreInputFlow(fr.saveInputFlow(mock));
        assertEquals("gis", restored.pool.get(0).operator);
        assertEquals(id, restored.pool.get(0).Id);
        assertEquals("String", new String(restored.pool.get(0).DataToWork));
    }

    //@Test
    public void restore() throws ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, IOException {
        String uid1, uid2;
        String filename="binData/dumpREQS.bin0";
        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
        InfoAllRequests clone =fr.restoreInfoAll(arr);
        for (int i=0; i<clone.pool.size();i++){
            System.out.println(clone.pool.get(i).ResponsedXML);
        }
    }


    @Test
    public void saveandRestoredLogBlocksSingle1() throws IOException {
        String description="starting in queue";
        String GUID="%%%0";
        String pseudo="egr";
        Date date=new Date();
        Event event = new Event(description, GUID, pseudo, date,0);
        assertNotEquals(null, event);
        assertEquals(description, event.description);
        assertEquals(GUID, event.GUID);
        assertEquals(pseudo, event.pseudo);
        assertEquals(date, event.date);
        LogBlock log = new LogBlock();
        log.logblock.add(event);
        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream("binData/log.bin");
        fos.write(fr.saveLogBlocks(log));
        fos.close();
        LogBlock restored = fr.restoreLogBlocks(fr.saveLogBlocks(log));
        assertEquals(restored.logblock.get(0).description, description);
        assertEquals(restored.logblock.get(0).GUID, GUID);
        assertEquals(restored.logblock.get(0).pseudo, pseudo);
        assertEquals(restored.logblock.get(0).date, date);
    }

    @Test
    public void saveandRestoredLogBlocksSingle2() throws IOException {
        String description="stopping in queue";
        String GUID="%%%1";
        String pseudo="gis";
        Date date=new Date();
        Event event = new Event(description, GUID, pseudo, date,0);
        assertNotEquals(null, event);
        assertEquals(description, event.description);
        assertEquals(GUID, event.GUID);
        assertEquals(pseudo, event.pseudo);
        assertEquals(date, event.date);
        LogBlock log = new LogBlock();
        log.logblock.add(event);
        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream("binData/log2.bin");
        fos.write(fr.saveLogBlocks(log));
        fos.close();
        LogBlock restored = fr.restoreLogBlocks(fr.saveLogBlocks(log));
        assertEquals(restored.logblock.get(0).description, description);
        assertEquals(restored.logblock.get(0).GUID, GUID);
        assertEquals(restored.logblock.get(0).pseudo, pseudo);
        assertEquals(restored.logblock.get(0).date, date);
    }

    @Test
    public void saveandRestoredLogBlocksTogether() throws IOException {
        String description="starting in queue";
        String GUID="%%%0";
        String pseudo="egr";
        Date date=new Date();
        String description2="stopping in queue";
        String GUID2="%%%1";
        String pseudo2="gis";
        Date date2=new Date();
        Event event = new Event(description, GUID, pseudo, date,0);
        assertNotEquals(null, event);
        assertEquals(description, event.description);
        assertEquals(GUID, event.GUID);
        assertEquals(pseudo, event.pseudo);
        assertEquals(date, event.date);
        LogBlock log = new LogBlock();
        log.logblock.add(event);
        event = new Event(description2, GUID2, pseudo2, date2,0);
        log.logblock.add(event);

        FileOutputStream fos = new FileOutputStream("binData/logfull.bin");
        fos.write(fr.saveLogBlocks(log));
        fos.close();
        LogBlock restored = fr.restoreLogBlocks(fr.saveLogBlocks(log));
        assertEquals(restored.logblock.get(0).description, description);
        assertEquals(restored.logblock.get(0).GUID, GUID);
        assertEquals(restored.logblock.get(0).pseudo, pseudo);
        assertEquals(restored.logblock.get(0).date, date);
    }

    @Test
    public void testElapsedAdding1in500messages() throws IOException {
        long startTime = System.currentTimeMillis();
        String description="starting in queue";
        String GUID="%%%0";
        String pseudo="egr";
        Date date=new Date();
        Event event = new Event(description, GUID, pseudo, date,0);
        LogBlock log = new LogBlock();
        LogBlock restored;
        log.logblock.add(event);
        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream("binData/loging.bin");
        fos.write(fr.saveLogBlocks(log));
        fos.close();

        for (int i =0;i<500; i++){

            String filename="binData/loging.bin";
            Path p = Paths.get(filename);
            byte[] arr = Files.readAllBytes(p);
            restored = fr.restoreLogBlocks(arr);

            restored.logblock.add(new Event(description, GUID, pseudo, date,0));

            FileOutputStream fos2 = new FileOutputStream("binData/loging.bin");
            fos2.write(fr.saveLogBlocks(restored));
            fos2.close();
        }

        String filename="binData/loging.bin";
        Path p = Paths.get(filename);
        byte[] arr = Files.readAllBytes(p);
        restored = fr.restoreLogBlocks(arr);

        assertTrue(restored.logblock.size()>100);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime);

        long startTime1 = System.currentTimeMillis();

        arr = Files.readAllBytes(p);
        restored = fr.restoreLogBlocks(arr);
        restored.logblock.add(new Event(description, GUID, pseudo, date,0));
        FileOutputStream fos2 = new FileOutputStream("binData/loging.bin");
        fos2.write(fr.saveLogBlocks(restored));
        fos2.close();


        long stopTime1 = System.currentTimeMillis();
        long elapsedTime1 = stopTime1 - startTime1;
        System.out.println("time to add one leemnt="+elapsedTime1);

    }

    @Test
    public void saveEvent() throws IOException {
        String description="starting in queue";
        String GUID="%%%0";
        String pseudo="egr";
        Date date=new Date();

        for (int i =0;i<10; i++){
            Event event = new Event(description, GUID, pseudo, date,0);
            String filename=pathtoLog+i+".bin";
            Path p = Paths.get(filename);

            FileOutputStream fos2 = new FileOutputStream(filename);
            fos2.write(fr.saveEvent(event));
            fos2.close();
        }
    }

    @Test
    public void saveStringArrayList() throws ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        ArrayList<String> initial = new ArrayList<>();
        initial.add("0000");
        assertEquals(initial.get(0), fr.RestoreArrayList(fr.SaveStringArrayList(initial)).get(0));
    }

    @Test
    public void saveActivityEvent() {
        String oper = "inn";
        Date date = new Date();
        ActivityEvent initial = new ActivityEvent(oper, date);
        assertEquals(initial.pseudo, fr.restoreActivityEvent(fr.saveActivityEvent(initial)).pseudo);
    }
}