package schedulling.abstractions;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentifierTest {

    @Test
    public void getPseudo() {
        Identifier mx = new Identifier();
        String in1="lol";
        String in2="lol2";
        String result = mx.gen(in1,in2);
        assertEquals(in2, mx.getPseudo(result));
    }

    @Test
    public void getId() {
        Identifier mx = new Identifier();
        String in1="lol";
        String in2="lol2";
        String result = mx.gen(in1,in2);
        assertEquals(in1, mx.getId(result));

    }
}