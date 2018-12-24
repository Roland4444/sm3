package DB;
import jdk.internal.util.xml.impl.Input;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

public class Executor {
    String ip, db, login, passwd;
    protected Connection conn;
    public Connection getConn(){
        return this.conn;
    }
    public Executor(){

    }

    public Executor(ArrayList params) throws SQLException {
        this.ip     = params.get(0).toString();
        this.db     = params.get(1).toString();
        this.login  = params.get(2).toString();
        this.passwd = params.get(3).toString();
        String connect="jdbc:sqlserver://"+ip+"\\MSSQLSERVER:1433;database="+db+";user="+login+";password="+passwd;
        conn = getConnection(connect);
    }
    public Executor(ArrayList params, boolean jtds) throws SQLException {
        this.ip     = params.get(0).toString();
        this.db     = params.get(1).toString();
        this.login  = params.get(2).toString();
        this.passwd = params.get(3).toString();
        String connect="jdbc:jtds:sqlserver://"+ip+":1433/"+db+";instance=MSSQLSERVER;user="+login+";password="+passwd;
        conn = getConnection(connect);
    }
    Statement stmt=null;
    ResultSet res=null;
    public ResultSet submit(String query) throws SQLException {
        stmt = conn.createStatement();
        res = stmt.executeQuery(query);
        return res;
    }



    public int getLenth(ResultSet res) throws SQLException {
        ResultSet doubles = res;
               int result =1;
               if (doubles.next()){
                    while (true){
                            if (res.getString(result)==null) break;
                            result++;
                }
        }
                return result;
    }

    public String insertBuilder(String Destination, ArrayList data){
        int i=0;
        String query="INSERT INTO "+ Destination +" VALUES(";
        PreparedStatement pst = null;
        while (i++<data.size()){
            if (i==data.size()) {query+="?"; break;};
            query+="?,";
        }
        query+=");";
        return query;
    }

    public String convertDate2String(Date indate)
    {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
        try{
            dateString = sdfr.format( indate );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }

    public void insert(String Destination, ArrayList<Object> data) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(insertBuilder(Destination,data));
        int i=1;

        for (Object o : data) {
            if (o instanceof Integer) {
                pst.setInt(i++, (Integer) o );
            }
            if (o instanceof String) {
                pst.setString(i++, (String)o );
            }
            if (o instanceof Double) {
                pst.setDouble(i++, (Double)o );
            }
        }
        pst.executeUpdate();
    }

    public String prepareforSrored(Map<Object, Object> InputStr ){
        StringBuffer result = new StringBuffer();
        result.append("(");
        for (int i=1; i<InputStr.size();i++)
            result.append("?, ");
        result.append("?)");
        return result.toString();
    }

    public void exexcuteUUID(String P_code, String number) throws SQLException {
        CallableStatement cstml = this.conn.prepareCall("{call dbo.gkh_houseguid(?, ?, ?, ?)}");
        cstml.setString(1, P_code);
        cstml.setString(2, number);
        cstml.setString(3,"");
        cstml.registerOutParameter(4, Types.VARCHAR);
        cstml.execute();
        System.out.println("Total price for order" +"is $"+cstml.getString(4));
    }



    public void executeStored(String Stored, Map<Object, Object> Input) {
        try {
            String call = "{call "+Stored+prepareforSrored(Input)+"}";
            CallableStatement cstmt = this.conn.prepareCall("{{call GetTotal(?, ?, ?, ?)}");
            int counter =1;
            for (Map.Entry<Object, Object> entry : Input.entrySet())
            {
                Object value = entry.getValue();
                if (value == null) {
                    cstmt.registerOutParameter(counter++, Types.NVARCHAR);
                    continue;
                }

                if (value instanceof Integer) {
                    cstmt.setInt(counter++, (Integer) value);
                    continue;
                }

                if (value instanceof String) {
                    cstmt.setString(counter++, (String) value);
                    continue;
                }


            }
            cstmt.execute();
            //System.out.println("Total price for order" + o_id +"is $"+cstmt.getInt(2));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}

