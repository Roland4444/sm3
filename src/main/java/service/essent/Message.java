package service.essent;

import java.io.*;

public class Message implements Serializable {
    public Message(byte[] message, String Address){
        this.Address=Address;
        this.message=message;
    }

    public byte[] message;
    public String Address;

    public static byte[] saveMessagetoByte(Message event){
        byte[] Result=null ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(event);
            out.flush();
            Result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return Result;
    }

    public static Message restoreMessage(byte[] input){
        Object o=null;
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (StreamCorruptedException e){
            System.out.println("\n\n\nCARCHED!!!!!!\n\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return (Message) o;
    }

}
