package logging;

import util.Event;

import java.io.Serializable;
import java.util.*;

public class LogBlock implements Serializable {
    public ArrayList<Event> logblock=new ArrayList();
    public void put(Event input){
        this.logblock.add(input);
    }
  /*  public void sort(){
        HashMap<Integer, Event> map = new HashMap<Integer, Event>();
        for (int i =0; i<logblock.size();i++){
            map.put(logblock.get(i).id, logblock.get(i));
            TreeMap<Integer, Event>=new
        }


    }*/


}
