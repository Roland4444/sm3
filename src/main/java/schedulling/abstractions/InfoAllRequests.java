package schedulling.abstractions;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoAllRequests implements Serializable {
    public ArrayList<RequestData> pool;
    public InfoAllRequests(){
        this.pool =  new ArrayList<RequestData>();
    }
    public RequestData get(String Idenifier, boolean useIdentifier){
        if (useIdentifier){
            System.out.println("Using identifier   "+Idenifier);
            for (int i = 0; i<pool.size(); i++){
                System.out.println("Identifier =>"+this.pool.get(i).Identifier);
                if (this.pool.get(i).Identifier.equals(Idenifier)) return this.pool.get(i);
            }
        };
        if (!useIdentifier){
            System.out.println("Not Using identifier"+Idenifier);
            for (int i = 0; i<pool.size(); i++){
                System.out.println("GennedId =>"+this.pool.get(i).GennedId);
                if (this.pool.get(i).GennedId.equals(Idenifier)) return this.pool.get(i);
            }
        }
        return null;
    }

    public int put(RequestData in){
        for (int i = 0; i<pool.size(); i++){
            if ((this.pool.get(i).Identifier.equals(in.Identifier)) || (this.pool.get(i).GennedId.equals(in.GennedId))) {
                System.out.println("Shit happens");
                return 1;
            }
        }
        this.pool.add(in);
        return 0;
    }

    public void flush(){
        this.pool.clear();
    }

    public void printAllIndentifiers(){
        System.out.println("********************************************");
        System.out.println("***************IDENTIFIERS******************");
        for (int i=0; i<pool.size();i++)
            System.out.println(pool.get(i).Identifier);

    }

    public void printAllGennedId(){
        System.out.println("********************************************");
        System.out.println("***************GENNED IDs*******************");
        for (int i=0; i<pool.size();i++)
            System.out.println(pool.get(i).GennedId);
    }

    public boolean check_complete(){
        boolean result =false;
        for (int i = 0; i<pool.size(); i++){
            if  (this.pool.get(i).ResponsedXML == null) return false;
        }
        return true;
    }
}
