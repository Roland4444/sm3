package schedulling.abstractions;

import java.io.Serializable;

public class InputDataBlock implements Serializable {
    public InputDataBlock(){


    };
    public InputDataBlock(String Id, String operator, byte[] DataToWork){
        this.Id=Id;
        this.DataToWork=DataToWork;
        this.operator=operator;
    }
    public InputDataBlock(String Id, String operator, byte[] DataToWork, String replyTo){
        this.Id=Id;
        this.DataToWork=DataToWork;
        this.operator=operator;
        this.addressToReply = replyTo;
    }

    public String Id;
    public String operator;
    public byte[] DataToWork;
    public String addressToReply;
}
