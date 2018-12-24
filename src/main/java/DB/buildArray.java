package DB;

import java.util.ArrayList;

public class buildArray {
    public ArrayList build(String ip, String db, String user, String pass){
        ArrayList result = new ArrayList();
        result.add(ip);
        result.add(db);
        result.add(user);
        result.add(pass);
        return result;

    }
}
