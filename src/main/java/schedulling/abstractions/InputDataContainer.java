package schedulling.abstractions;

import java.io.Serializable;
import java.util.ArrayList;

public class InputDataContainer implements Serializable {
    public ArrayList<InputDataBlock> pool;
    public InputDataContainer(){
        this.pool = new ArrayList<>();    }

    public int put(InputDataBlock in) {
        for (int i = 0; i < pool.size(); i++) {
            if (this.pool.get(i).Id.equals(in.Id)) {
                System.out.println("Shit happens in adding in rawxml flow");
                return 1;
            }
        }
        this.pool.add(in);
        return 0;
    };

    public InputDataBlock get(String identifier){
        for (int i = 0; i<pool.size(); i++){
            if (this.pool.get(i).Id.equals(identifier)) return this.pool.get(i);
        }
        return null;

    };

    public void destroy(String Identifier){
        for (int i = 0; i < pool.size(); i++) {
            if (this.pool.get(i).Id.equals(Identifier))
                this.pool.remove(i);
                break;
        }

    }







}
