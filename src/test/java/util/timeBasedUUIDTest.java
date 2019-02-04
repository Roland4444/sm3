package util;

import org.junit.Test;

import static org.junit.Assert.*;

public class timeBasedUUIDTest {

    @Test
    public void generate() {
        timeBasedUUID gen = new timeBasedUUID();
        System.out.println(gen.generate());
    }
}