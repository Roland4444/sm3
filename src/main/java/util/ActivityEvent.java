package util;

import java.io.Serializable;
import java.util.Date;

public class ActivityEvent implements Serializable {
    public Date date;
    public String pseudo;

    public ActivityEvent(String pseudo, Date date ){
        this.pseudo = pseudo;
        this.date= date;
    }


}
