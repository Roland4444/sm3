package schedulling.abstractions;

import org.junit.Test;
import util.timeBasedUUID;

import static org.junit.Assert.*;

public class InfoAllRequestsTest {
    timeBasedUUID gennr = new timeBasedUUID();


    @Test
    public void get() {
        InfoAllRequests all = new InfoAllRequests();
        RequestData test= new RequestData();
        RequestData test2= new RequestData();
        String uid1, uid2, uid3,uid4;
        uid1 = gennr.generate();
        uid2 = gennr.generate();
        uid3 = gennr.generate();
        uid4 = gennr.generate();
        test.GennedId   = uid1;
        test.Identifier = uid2;
        test2.GennedId  = uid3;
        test2.Identifier= uid4;
        all.put(test);
        all.put(test2);
        assertEquals(uid1, all.get(uid2, true).GennedId);
        assertEquals(null, all.get(uid2, false));
        assertEquals(uid4, all.get(uid3, false).Identifier);

    }

    @Test
    public void put() {
        InfoAllRequests all = new InfoAllRequests();
        RequestData test= new RequestData();
        RequestData test2= new RequestData();
        String uid1, uid2, uid3,uid4;
        uid1 = gennr.generate();
        uid2 = gennr.generate();
        test.GennedId   = uid1;
        test.Identifier = uid2;
        test2.GennedId  = uid1;
        test2.Identifier= uid2;
        assertEquals(0, all.put(test));
        assertEquals(1, all.put(test2));
    }

    @Test
    public void printAllIndentifiers() {
        InfoAllRequests all = new InfoAllRequests();
        RequestData test= new RequestData();
        RequestData test2= new RequestData();
        String uid1, uid2, uid3,uid4;
        uid1 = gennr.generate();
        uid2 = gennr.generate();
        uid3 = gennr.generate();
        uid4 = gennr.generate();
        test.GennedId   = uid1;
        test.Identifier = uid2;
        test2.GennedId  = uid3;
        test2.Identifier= uid4;
        assertEquals(0, all.put(test));
        assertEquals(0, all.put(test2));
        all.printAllIndentifiers();
        all.printAllGennedId();

    }

    @Test
    public void check_complete() {
        InfoAllRequests all = new InfoAllRequests();
        RequestData test= new RequestData();
        assertEquals(0, all.put(test));
        assertEquals(false, all.check_complete());
        all.pool.get(0).ResponsedXML="RESP";
        assertEquals(true, all.check_complete());
    }

    @Test
    public void get1() {
        InfoAllRequests all = new InfoAllRequests();
        RequestData test= new RequestData();
        String uid1, uid2, uid3,uid4;
        uid1 = gennr.generate();
    }

    @Test
    public void check_complete1() {
    }
}