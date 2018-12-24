package logging;

import schedulling.abstractions.Freezer;
import util.NetworkEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NetworkLogger extends Thread {
    ArrayList<NetworkEvent> pool= new ArrayList();
    private Freezer fr=new Freezer();
    public Map<String, Integer> StatusLogs=new HashMap<String, Integer>();
    private String pathtoLog="";

    NetworkEvent getFromPool(String name){
        for (int i=0; i<pool.size();i++)
            if (this.pool.get(i).Name.equals(name))
                return this.pool.get(i);
        return null;
    }

    public void setFreezer(Freezer fr){
        this.fr=fr;
    }

    public void update(NetworkEvent event){
        for (int i =0; i<pool.size();i++){
            if (this.pool.get(i).Name.equals(event))
                pool.set(i, event);
        }
    }

    public int currentEventId=0;
    public NetworkLogger(String path) throws IOException {
        StatusLogs.put("init",0);
        StatusLogs.put("upChange",1);
    //    StatusLogs.put("IPChange",2);
        this.pathtoLog=path;
        File directory = new File(path);
        if (! directory.exists())
            directory.mkdirs();
        else {
            Files.walk(Paths.get(path))
                    .filter(p->p.toString().indexOf(".nel")>0)
                    .sorted()
                    .forEach(a-> {
                        currentEventId++;
                    });
        }
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)){
            NetworkEvent atom = getNetworkEventAtom(netint);
            if (atom == null) continue;
            if (getFromPool(atom.Name)==null){
                atom.status=StatusLogs.get("init");
                pool.add(atom);
                writeNetworkEvent(atom);
            }

        }

    };


    public  NetworkEvent getNetworkEventAtom(NetworkInterface netint) throws SocketException {
        if (netint.isLoopback())
            return null;
        return  new NetworkEvent(netint.getName(),  new Date(),netint.isUp() );//netint.getInetAddresses()  2index

    }

    public void tryWriteNetworkEvent(NetworkEvent ne) throws IOException {
        if (ne.Name==null)
            System.out.println("null in rawxml");
       // System.out.println(ne.Name);

        if (getFromPool(ne.Name)==null){
            pool.add(ne);
            writeNetworkEvent(ne);
            return;
        }
        if (getFromPool(ne.Name).isUp!=ne.isUp) {
            NetworkEvent event = ne;
            event.date=new Date();
            event.status=StatusLogs.get("upChange");
            update(event);
            writeNetworkEvent(event);
        }
      /*  if (getFromPool(ne.Name).IP!=ne.IP) {
            NetworkEvent event = ne;
            event.date=new Date();
            event.status=StatusLogs.get("IPChange");
            update(event);
            writeNetworkEvent(event);
        }*/

    }

    public void writeNetworkEvent(NetworkEvent event) throws IOException {
        String filename=pathtoLog+(++currentEventId)+".nel";
        FileOutputStream fos2 = new FileOutputStream(filename);
        fos2.write(fr.saveNetWorkEvent(event));
        fos2.close();
    }

    public void watch() throws IOException, InterruptedException {

        while (true){
            sleep(1000);
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)){
                NetworkEvent atom= getNetworkEventAtom(netint);
                if (atom==null) continue;
                tryWriteNetworkEvent(atom);

            }

        }
    }

    public static void main(String[] args) throws IOException {
        NetworkLogger nl = new NetworkLogger( "logs/binary/");
        nl.setFreezer(new Freezer());
        while (true){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Enumeration<NetworkInterface> nets = null;
            try {
                nets = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            for (NetworkInterface netint : Collections.list(nets)) {
                try {
                    nl.tryWriteNetworkEvent(nl.getNetworkEventAtom(netint));
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void run(){
        while (true){

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Enumeration<NetworkInterface> nets = null;

            try {
                nets = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                e.printStackTrace();
            }

            for (NetworkInterface netint : Collections.list(nets)){
                NetworkEvent atom= null;
                try {
                    atom = getNetworkEventAtom(netint);
                } catch (SocketException e) {
                    e.printStackTrace();
                }


                if (atom==null) continue;

                try {
                    tryWriteNetworkEvent(atom);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }
}
