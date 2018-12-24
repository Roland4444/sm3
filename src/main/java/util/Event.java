package util;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    public String description;
    public String GUID;
    public String pseudo;
    public Date date;
    public int status;

    public Event(){

    }

    public Event(String description, String guid, String pseudo, Date date, int status){
        this.description=description;
        this.GUID=guid;
        this.pseudo=pseudo;
        this.date=date;
        this.status=status;

    }



}
