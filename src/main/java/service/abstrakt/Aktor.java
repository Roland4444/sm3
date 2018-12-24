package service.abstrakt;

import java.io.IOException;

public abstract class Aktor {
    public abstract void receive(byte[] message) throws IOException;
    public abstract int send(byte[] message, String address) throws IOException;
    public String Address;
    public abstract void spawn() throws InterruptedException;
    public abstract void terminate() throws InterruptedException;
    public int getPortFromURL(String url){
        return Integer.valueOf(url.substring(url.lastIndexOf(":") + 1, url.length()- 1));
    }


}
