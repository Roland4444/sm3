package schedulling.gettingDataImplem;

import DB.Executor;

import logging.MyLogger;
import schedulling.SQLWrapper;
import schedulling.abstractions.*;
import util.Event;
import util.Injector;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class getData implements InputDataWerker {
    private Executor exec;
    private DataSource source;
    private Identifier identifier;
    private InputDataContainer inputFlow;
    private Injector inj;
    private HashMap<String, String> ignored ;
    private MapProcessor mapProcessor;
    public boolean enableBinaryLogging=false;
    private SQLWrapper sqlWrapper;
    public MyLogger logger;

    public void setLogger(MyLogger logger){
        this.logger=logger;
    }

    public getData(Executor exc, DataSource source, Identifier iden, InputDataContainer inputFlow, MapProcessor mapProcessor) throws SQLException {
        this.exec=exc;
        this.source=source;
        this.identifier=iden;
        this.inputFlow=inputFlow;
        this.mapProcessor=mapProcessor;

    }

    public void setSQLWrapper(SQLWrapper sql){
        this.sqlWrapper =sql;
    }

    public void setInjector(Injector inj){

        this.inj = inj;
    }

    public void setIgnored(HashMap<String,String> ignored){
        this.ignored = ignored;
    }

    void inputcall1Exception(String exception) throws IOException {
        if (enableBinaryLogging){
            String description="SQL Exception smev_query1s "+exception;
            String GUID="none";
            Date date=new Date();
            Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("error"));
            logger.writeEvent(event);
        }
    }

    void nullinfbodyxml(String Id) throws IOException, SQLException {
        if (enableBinaryLogging){
            String description="NULL! in f_body_xml smev_query1s";
            Date date=new Date();
            Event event = new Event(description, Id, "none", date,logger.StatusLogs.get("error"));
            logger.writeEvent(event);
        }
        sqlWrapper.errorqueueingsmev3sql(exec, Id, "NULL in f_body_xml"  );

    }

    void loggettingDatafromsql(String Id, String pseudo) throws IOException {
        if (enableBinaryLogging){
            String description="получены данные от sql";
            String GUID=Id;
            Date date=new Date();
            Event event = new Event(description, GUID, pseudo, date,logger.StatusLogs.get("ok"));
            logger.writeEvent(event);
        }
    }

    void errorwhenchecking(String Id, String pseudo) throws IOException, SQLException {
        String description="данные не прошли проверку на корректность! Смотри id=>";
        String GUID=Id;
        Date date=new Date();
        Event event = new Event(description, GUID, pseudo, date,logger.StatusLogs.get("err"));
        logger.writeEvent(event);
        sqlWrapper.errorqueueingsmev3sql(exec, Id, "данные не прошли проверку на корректность! Смотри id=>"  );
    }

    public void loadDataToInputFlowiniversal(boolean TEST_MODE, boolean applyGisHooks) throws IOException {
        assert(sqlWrapper!=null);
        ResultSet Select = null;
        Select = sqlWrapper.input_call1(exec);

        try {
            if (Select.next()) {
                String pseudo = null;
                try {
                    pseudo = Select.getString("f_srv");
                } catch (SQLException e) {
                    inputcall1Exception(e.getMessage());
                    return;
                }
                System.out.println("pseudo ==>"+pseudo);
                System.out.println("source ==>"+source);
                String res = null;
                try {
                    res = Select.getString("f_body_xml");
                } catch (SQLException e) {
                    inputcall1Exception(e.getMessage());
                    return;
                }
                String Id = null;
                try {
                    Id = Select.getString("f_key");
                } catch (SQLException e) {
                    inputcall1Exception(e.getMessage());
                    return;
                }
                System.out.println("ID>>>>"+Id);
                System.out.println("pseudo>>>>"+pseudo);
                System.out.println("res>>>>"+res);
                if (res==null){
                    System.out.println("NULLL");
                    try {
                        nullinfbodyxml(Id);
                    } catch (SQLException e) {
                        inputcall1Exception(e.getMessage());
                        return;
                    }

                }
                if (TEST_MODE){
                    System.out.println("dropped cause test mode");
                    if (!Id.equals("3590")) return;     //<===test blob


                }
                System.out.print(identifier.gen(Id, pseudo));
                InputDataBlock input = new InputDataBlock();
                input.operator=pseudo;
                input.Id=identifier.gen(Id, pseudo);
                System.out.println("Input id"+input.Id);
                if (ignored.get(identifier.gen(Id, pseudo))!=null){
                    System.out.println("in ignored list!");
                }

                input.DataToWork=res.getBytes();
                if (applyGisHooks==true)
                    input.DataToWork=applyHooks(res).getBytes();
                System.out.println("DataToWork "+new String(input.DataToWork));
                if  (mapProcessor.OperatorMap.get(pseudo).check(res.getBytes())==false) {
                    try {
                        errorwhenchecking(Id, pseudo);
                    } catch (SQLException e) {
                        inputcall1Exception(e.getMessage());
                        return;
                    }
                    ;
                }
                inputFlow.put(input);
                loggettingDatafromsql(Id, pseudo);
            }
        } catch (SQLException e) {
            inputcall1Exception(e.getMessage());
        }
    }






    public void loadDataToInputFlow(boolean TEST_MODE, boolean applyGisHooks) throws SQLException, IOException {
        Map<String, byte[]> blob = new HashMap<>();
        Iterator it = source.Source.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pseudo = (String) pair.getKey();
            String source = (String) pair.getValue();
            System.out.println("pseudo ==>"+pseudo);
            System.out.println("source ==>"+source);

            ResultSet Select2 = exec.submit("set concat_null_yields_null off; SELECT * FROM " + source + " WHERE f_stat='0';");
            if (Select2 == null) continue;
            while (Select2.next()) {
                String res = Select2.getString("f_body_xml");
                byte[] data = res.getBytes();
                String Id = Select2.getString("f_key");

                if (TEST_MODE){
                    if (!Id.equals("3590")) continue;     //<===test blob
                }

                System.out.print(identifier.gen(Id, pseudo));
                InputDataBlock input = new InputDataBlock();
                input.operator=pseudo;
                input.Id=identifier.gen(Id, pseudo);
                if (ignored.get(identifier.gen(Id, pseudo))!=null)
                    continue;
                input.DataToWork=res.getBytes();
                if  (mapProcessor.OperatorMap.get(pseudo).check(res.getBytes())==false) {
                    changeStatus(source, Id, "dropped");
                    continue;
                }

                if (applyGisHooks==true)
                    input.DataToWork=applyHooks(res).getBytes();
                System.out.println("DataToWork "+new String(input.DataToWork));
                inputFlow.put(input);
                changeStatus(source, Id );
            }
            //  it.remove();
        }

    }

    private String applyHooks(String input) {
        System.out.println("Hooks applied!!");
        return inj.injectAttribute(input, "paymentId", "10471020010005232407201700000001");
    }


    public void loadDataToInputFlow(boolean TEST_MODE) throws SQLException {
        Map<String, byte[]> blob = new HashMap<>();
        Iterator it = source.Source.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pseudo = (String) pair.getKey();
            String source = (String) pair.getValue();
            System.out.println("pseudo ==>"+pseudo);
            System.out.println("source ==>"+source);

            //  System.out.println("iterate into   "+ source);
          //  System.out.println("EXECUTE  SELECT * FROM " + source + " WHERE f_stat='0';");
            ResultSet Select2 = exec.submit("set concat_null_yields_null off; SELECT * FROM " + source + " WHERE f_stat='0';");
            if (Select2 == null) continue;
            while (Select2.next()) {
                String res = Select2.getString("f_body_xml");
                byte[] data = res.getBytes();
                String Id = Select2.getString("f_key");

                if (TEST_MODE){
                    if (!Id.equals("3590")) continue;     //<===test blob
                }

                System.out.print(identifier.gen(Id, pseudo));
                InputDataBlock input = new InputDataBlock();
                input.operator=pseudo;
                input.Id=identifier.gen(Id, pseudo);
                input.DataToWork=res.getBytes();
                System.out.println("DataToWork "+new String(input.DataToWork));
                if (checktoAccept(res.getBytes(), pseudo)){
                    inputFlow.put(input);
                    changeStatus(source, Id );
                }
                else {
                    changeStatus(source, Id, "333");
                }

            }
          //  it.remove();
        }

    }

    public boolean checktoAccept(byte[] input, String pseudo){

        return true;

    }


    public void changeStatus(String source, String fkey) throws SQLException {
        System.out.println("Prepared "+"set concat_null_yields_null off;UPDATE "+source+" SET f_stat =? WHERE f_key = ?;"+fkey);
        PreparedStatement ChangeStatus = exec.getConn().prepareStatement("set concat_null_yields_null off;UPDATE "+source+" SET f_stat =? WHERE f_key = ?;");
        ChangeStatus.setString(1, "12");
        ChangeStatus.setString(2, fkey);
        ChangeStatus.executeUpdate();
    }

    public void changeStatus(String source, String fkey, String status) throws SQLException {
        System.out.println("Prepared "+"set concat_null_yields_null off;UPDATE "+source+" SET f_stat =? WHERE f_key = ?;"+fkey);
        PreparedStatement ChangeStatus = exec.getConn().prepareStatement("set concat_null_yields_null off;UPDATE "+source+" SET f_stat =? WHERE f_key = ?;");
        ChangeStatus.setString(1, status);
        ChangeStatus.setString(2, fkey);
        ChangeStatus.executeUpdate();

    }

    public Map<String, byte[]> getData() throws SQLException {
        Map<String, byte[]> blob = new HashMap<>();
        Iterator it = source.Source.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pseudo = (String) pair.getKey();
            String source = (String) pair.getValue();
            System.out.println("iterate into   "+ source);
            ResultSet Select2 = exec.submit("set concat_null_yields_null off; SELECT * FROM " + source + " WHERE f_stat='0';");
            if (Select2 == null) continue;
            while (Select2.next()) {
                String res = Select2.getString("f_body_xml");
                byte[] data = res.getBytes();
                String Id = Select2.getString("f_key");
                if (!Id.equals("1641852")) continue;     //<===test blob
                blob.put(identifier.gen(Id, pseudo), data);
                System.out.print(identifier.gen(Id, pseudo));
            }
           // it.remove(); // avoids a ConcurrentModificationException
        }
        return blob;
    }






}
