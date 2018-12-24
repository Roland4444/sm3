package schedulling;

import DB.Executor;
import logging.MyLogger;
import se.roland.Extractor;
import util.Event;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SQLWrapper {
    private Extractor ext;
    private boolean enableBinaryLogging=false;
    private MyLogger logger;

    public SQLWrapper(){
        this.enableBinaryLogging=false;
        this.logger = null;
    }

    public SQLWrapper(boolean logging, MyLogger logger){
        this.enableBinaryLogging=logging;
        this.logger = logger;
    }

    public void setExtractor(Extractor ext){
        this.ext = ext;
    }

    public void sendResponceSQL(Executor exec, String resss) throws SQLException, IOException {
        PreparedStatement insertResponseXML = exec.getConn().prepareStatement("set concat_null_yields_null on; EXEC smev_answer2s ?;");
        insertResponseXML.setBytes(1,  resss.getBytes());
        assert(ext!=null);
        try {
            String original = ext.extractTagValue(resss,":OriginalMessageId");
            insertResponseXML.executeUpdate();
            if (enableBinaryLogging){
                String description="Вызов smev_answer2s";
                String GUID=original;
                Date date=new Date();
                Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("ok"));
                logger.writeEvent(event);
            }
        } catch (SQLException e) {
            if (enableBinaryLogging){
                String description="SQL Exception smev_answer2s"+e.getMessage();
                String GUID="none";
                Date date=new Date();
                Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("error"));
                logger.writeEvent(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultSet input_call1(Executor exec) throws IOException {
        System.out.println("\n\n\nCall SMEV_QUERY1s");
        try {
            return exec.submit("set concat_null_yields_null off; EXEC smev_query1s 'x', 1;");
        } catch (SQLException e) {
            if (enableBinaryLogging){
                String description="SQL Exception SMEV_QUERY1s"+e.getMessage();
                String GUID="none";
                Date date=new Date();
                Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("error"));
                logger.writeEvent(event);
            }
        }
        return null;
    };

    public void errorqueueingsmev3sql(Executor exec, String f_key, String error) throws SQLException, IOException {
        PreparedStatement callinput2 = null;
        try {
            System.out.println("\n\n\nCall SMEV_QUERY2s");
            callinput2 = exec.getConn().prepareStatement("set concat_null_yields_null off; EXEC smev_query2s ?, ?, ?, ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        callinput2.setString(1, f_key);
        callinput2.setString(2, "none");
        callinput2.setString(3, "none");
        callinput2.setString(4, error);
        try {
            callinput2.executeUpdate();
        } catch (SQLException e) {
            if (enableBinaryLogging){
                assert(logger!=null);
                String description="SQL Exception smev_query2s "+e.getMessage();
                String GUID="none";
                Date date=new Date();
                Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("error"));
                logger.writeEvent(event);
            }
        }

    };

    public ResultSet getReponceSQL(Executor exec) throws IOException {
        ResultSet Select2=null;
        try {
            System.out.println("\n\n\nCall smev_answer1s");
            Select2 = exec.submit( "EXEC smev_answer1s 'x'");
        } catch (SQLException e) {
            if (enableBinaryLogging){
                String description="SQL Exception smev_answer1s "+e.getMessage();
                String GUID="none";
                Date date=new Date();
                Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("error"));
                logger.writeEvent(event);
            }
        }
        return Select2;

    }

    public void successqueuedsql(Executor exec, String f_key, String id, byte[] res) throws SQLException, IOException {
        PreparedStatement insertResponseXML = exec.getConn().prepareStatement("set concat_null_yields_null off; EXEC smev_query2s ?, ?, ?, ?;");
        insertResponseXML.setString(1, f_key);
        insertResponseXML.setString(2,  id);
        insertResponseXML.setBytes(3,  res);
        insertResponseXML.setString(4,  "");
        insertResponseXML.setQueryTimeout(4);
        try {
            insertResponseXML.executeUpdate();
        } catch (SQLException e) {
            if (enableBinaryLogging){
                String description="SQL Exception smev_query2s"+e.getMessage();
                String GUID="none";
                Date date=new Date();
                Event event = new Event(description, GUID, "none", date,logger.StatusLogs.get("ok"));
                logger.writeEvent(event);
            }
        }
    };


}
