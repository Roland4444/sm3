package readfile;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReadfileTest {

    @Test
    public void addressSAAJ() {
        Readfile r = new Readfile("sqlset");
        assertEquals("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws?wsdl", r.addressSAAJ());
    }

    @Test
    public void binaryLogPath() {
        Readfile r = new Readfile("sqlset");
        assertEquals("logs/binary/", r.binaryLogPath());
    }

    @Test
    public void delay() {
        Readfile r = new Readfile("sqlset");
        assertEquals("1000", r.delay());
    }

    @Test
    public void saveRecsMessagesTo() {
        Readfile r = new Readfile("sqlset");
        assertEquals("saved/reqs/", r.saveRecsMessagesTo());
    }

    @Test
    public void saveSendMessagesTo() {
        Readfile r = new Readfile("sqlset");
        assertEquals("saved/send/", r.saveSendMessagesTo());
    }

    @Test
    public void FTP() {
        Readfile r = new Readfile("sqlset");
        assertEquals("#####", r.FTP());
    }
}