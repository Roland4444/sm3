package logging;

import org.junit.Test;
import schedulling.abstractions.Freezer;
import util.NetworkEvent;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class NetworkLoggerTest {

   // @Test
    public void setFreezer() throws IOException {
        String pathtoLog = "logs/binary/";
        NetworkLogger nl = new NetworkLogger(pathtoLog);
        nl.setFreezer(new Freezer());
        assertEquals(0, nl.currentEventId);
        nl.writeNetworkEvent(new NetworkEvent("ens",  new Date(), true));
        assertEquals(1, nl.currentEventId);

    }

    @Test
    public void tryWriteNetworkEvent() throws IOException, InterruptedException {
        String pathtoLog = "logs/binary/";
        NetworkLogger nl = new NetworkLogger(pathtoLog);
        nl.setFreezer(new Freezer());
    //    nl.watch();
    }

    @Test
    public void run() throws IOException {
        String pathtoLog = "logs/binary/";
        NetworkLogger nl = new NetworkLogger(pathtoLog);
        nl.setFreezer(new Freezer());
        assertNotEquals(null, nl.pool);
    }

    @Test
    public void getFromPool() throws IOException, InterruptedException {
        String pathtoLog = "logs/binary/";
        NetworkLogger nl = new NetworkLogger(pathtoLog);
        assertEquals(null, nl.getFromPool("ghgh"));
        assertNotEquals(null, nl.getFromPool("enp2s0"));
    //    nl.watch();

    }
}