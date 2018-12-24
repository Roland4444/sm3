package schedulling.abstractions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class inputDataContainerTest {

    @Test
    public void put() {
        InputDataContainer mock = new InputDataContainer();
        InputDataBlock blob = new InputDataBlock();
        blob.DataToWork = "String".getBytes();
        blob.operator="gis";
        blob.Id="0x000x";
        assertEquals(0, mock.put(blob));
        assertEquals(1, mock.put(blob));
    }
}