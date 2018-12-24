import org.junit.Test;

import static org.junit.Assert.*;

public class forTestTest {

    @Test
    public void summ() {
        forTest ft = new forTest();
        assertEquals(5, ft.summ(1,4));
    }
}