import org.junit.Test;

import static org.junit.Assert.*;

public class SVisor_Test {

    @Test
    public void getbinaryPath() {
        SVisor_  sv = new SVisor_();
        assertEquals(sv.getbinaryPath(), "logs/binary/");
        assertNotEquals(sv.getbinaryPath(), "___logs/binary/");
    }
}