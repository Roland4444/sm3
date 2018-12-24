import org.junit.Test;

import static org.junit.Assert.*;

public class EBSServiceTest {

    @Test
    public void main() throws InterruptedException {
        EBSService ebs = new EBSService(null);
        Thread.sleep(4000);
        assertNotEquals(null, ebs);

    }
}