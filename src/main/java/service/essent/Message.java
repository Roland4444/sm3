package service.essent;

import Message.abstractions.BinaryMessage;

public class Message implements BinaryMessage {
    public Message(byte[] message, String Address){
        this.Address=Address;
        this.message=message;
    }

    public byte[] message;
    public String Address;



}
