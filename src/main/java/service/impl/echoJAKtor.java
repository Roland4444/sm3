package service.impl;

import java.io.IOException;

public class echoJAKtor extends JAktor {
    @Override
    public void receive(byte[] message) throws IOException {
        this.received=new String(message);
        System.out.println("RECEIVED=>"+received);
    }
}
