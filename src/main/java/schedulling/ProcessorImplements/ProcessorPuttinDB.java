package schedulling.ProcessorImplements;


import DB.Executor;
import Message.abstractions.BinaryMessage;
import logging.MyLogger;
import schedulling.SQLWrapper;
import schedulling.abstractions.*;
import se.roland.Extractor;
import util.ActivityEvent;
import util.Event;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class ProcessorPuttinDB implements Processor {
    private String Identifier ="";
    private String currentnewId="";
    private TempDataContainer temp;
    private InputDataContainer inputFlow;
    private InfoAllRequests dbReqs;
    private MyLogger mylogger;
    private MapProcessor mapprocessor;
    private String IdBufer;
    private String sucesfullqueed;
    private String errorqueed;
    private String pseudoStarterOperator;
    private String stopperGetResponce;
    private Extractor ext;
    private HashMap<String, String> ignored;
    private Freezer freezer ;
    private Executor exec;
    private Identifier iden;
    public SQLWrapper  sqlWrapper;
    public boolean enableBinaryLogging=false;
    public boolean AckEnabled=true;
    public ProcessorPuttinDB(InputDataContainer inputFlow, InfoAllRequests infoReqs, MyLogger mylogger, MapProcessor mapprocessor, TempDataContainer temp, String inputStr1, String inputStr2
    , String pseudoStarter, String stopperGetResponce, Extractor ext, HashMap<String, String> ignored, Freezer freezer) throws SQLException {
        this.inputFlow=inputFlow;
        this.dbReqs=infoReqs;
        this.mylogger=mylogger;
        this.mapprocessor=mapprocessor;
        this.temp = temp;
        this.sucesfullqueed=inputStr1;
        this.errorqueed=inputStr2;
        this.pseudoStarterOperator=pseudoStarter;
        this.stopperGetResponce=stopperGetResponce;
        this.ext=ext;
        this.ignored = ignored;
        this.freezer=freezer;
    }

    public void setSQLWrapper(SQLWrapper sql){
        this.sqlWrapper =sql;
    }


    public void setExecutor(Executor exec){
        this.exec=exec;
    }

    public void setIdentifier(Identifier iden){
        this.iden=iden;
    }


    public void succesquued(Object key){
        this.dbReqs.put((RequestData) key);
        RequestData.printThat((RequestData) key);
        System.out.println("\n\n\n\nPUTTING IN TABLE REQUEST==>");
        System.out.println("GENNED ID>>"+ ((RequestData) key).GennedId);
        this.mylogger.log("id ="+ Identifier +"  success queueuing");
    }

    public void errorquued(Object key){
        this.dbReqs.put((RequestData) key);
        this.mylogger.log("id ="+ Identifier +"  error queueuing");
    }

    public boolean check_passed(String input){
        return true;
    }

    void logSucessqueueing(String operator, String f_key, String GUID, byte[] data) throws IOException, SQLException {
        String description="Успешно постановлено в очередь СМЕВ3 ";
        if (enableBinaryLogging){
            Date date=new Date();
            Event event = new Event(description, GUID, operator, date,mylogger.StatusLogs.get("ok"));
            mylogger.writeEvent(event);
        }


    }
    public void succesquueduniversal(Object key, String res) throws IOException, SQLException {
        System.out.println("QUERY2s call"+ ((RequestData) key).GennedId);

        String f_key = this.iden.getId( ((RequestData) key).Identifier);
        logSucessqueueing(((RequestData) key).operator, f_key, ((RequestData) key).GennedId, res.getBytes() );

      //  sqlWrapper.successqueuedsql(exec, f_key, ((RequestData) key).GennedId, res.getBytes());  <<=sending response smev3
        sqlWrapper.successqueuedsql(exec, f_key, ((RequestData) key).GennedId, ((RequestData) key).OriginalXML.getBytes());  //  <<==sending signed xml

        this.dbReqs.put((RequestData) key);
        System.out.println("\n\n\n\nPUTTING IN TABLE REQUEST==>");
        System.out.println("GENNED ID>>"+ ((RequestData) key).GennedId);
        this.mylogger.log("id ="+ Identifier +"  success queueuing");

    }

    void logErrorqueueing(String id) throws IOException, SQLException {
        String description="Ошибка постановки в очередь СМЕВ3 ";
        if (enableBinaryLogging){
            Date date=new Date();
            Event event = new Event(description, id, "none", date,mylogger.StatusLogs.get("error"));
            mylogger.writeEvent(event);
        }


    }

    public void logActivity(String pseudo) throws IOException {
        if (enableBinaryLogging){
            Date date=new Date();
            ActivityEvent event = new ActivityEvent(pseudo, date);
            mylogger.writeActivityEvent(event);
        }
    }

    public void errorquueduniversal(Object key) throws IOException, SQLException {
        //this.dbReqs.put((RequestData) key);
        this.mylogger.log("id ="+ Identifier +"  error queueuing");
        String description="Ошибка постановки в очередь СМЕВ3 ";
        String f_key = this.iden.getId( ((RequestData) key).Identifier);
        sqlWrapper.errorqueueingsmev3sql(exec, f_key, description);
        logErrorqueueing(iden.getId(Identifier));

    }

    public void sendAllUniversal() throws Exception {
        if (this.inputFlow.pool.size()==0){
            System.out.println("Empty Input Flow");
            return ;
        }
        for (int i=0; i<this.inputFlow.pool.size();i++) {

            InputDataBlock inputcluster = this.inputFlow.pool.get(i);

            if (this.ignored.get(inputcluster.Id)!=null){
               // System.out.println("This id"+inputcluster.Id+"already queued... skipping!!!");
                continue;
            }
            System.out.println("***********getOperator*****************");
            System.out.println(inputcluster.operator);
            String pseudoOperator = inputcluster.operator;
            System.out.println(pseudoOperator+" perfom");
            String originalMessage = new String(inputcluster.DataToWork);
            Identifier = inputcluster.Id;

            System.out.println(Identifier+"    sended and blacklisted to againsted queued!");
            this.ignored.put(Identifier, "in process");

            this.mapprocessor.OperatorMap.get(pseudoOperator).setinput(originalMessage.getBytes());
            System.out.println(this.mapprocessor.OperatorMap.get(pseudoOperator).getName());
            System.out.println("current ID Operator injectdatainXML"+this.temp.StringContainer);

            System.out.println("GENNED ID into procesor puttin"+this.temp.StringContainer);

            RequestData info = new RequestData();
            info.Identifier = Identifier;
            info.GennedId = this.temp.StringContainer;

            System.out.println("************************************");
            System.out.println(this.temp.StringContainer+"quwuwd!");
            System.out.println("************************************");
            info.operator = pseudoOperator;
            String result = new String(this.mapprocessor.OperatorMap.get(info.operator).SendSoapSigned());
            info.OriginalXML=new String(this.mapprocessor.OperatorMap.get(info.operator).SignedSoap());  //<<--adding original signed xml
            if (result.indexOf("requestIsQueued") > 0) {
                info.Status = this.currentnewId;
                this.succesquueduniversal(info, result);
            } else {
                info.Status = this.errorqueed;
                this.errorquueduniversal(info);
            }
        }
    }





    public void sendAll() throws Exception {
        if (this.inputFlow.pool.size()==0){
            System.out.println("Empty Input Flow");
            return ;
        }
        for (int i=0; i<this.inputFlow.pool.size();i++) {

            InputDataBlock inputcluster = this.inputFlow.pool.get(i);


            if (this.ignored.get(inputcluster.Id)!=null){
                System.out.println("This id"+inputcluster.Id+"already queued... skipping!!!");
                continue;
            }
            System.out.println("***********getOperator*****************");
            System.out.println(inputcluster.operator);
            String pseudoOperator = inputcluster.operator;
            System.out.println(pseudoOperator+" perfom");
            byte[] originalMessage =inputcluster.DataToWork;
            Identifier = inputcluster.Id;

            System.out.println(Identifier+"    sended and blacklisted to againsted queued!");
            this.ignored.put(Identifier, "in process");

            this.mapprocessor.OperatorMap.get(pseudoOperator).setinput(originalMessage);
            System.out.println(this.mapprocessor.OperatorMap.get(pseudoOperator).getName());
            System.out.println("current ID Operator injectdatainXML"+this.temp.StringContainer);

            System.out.println("GENNED ID into procesor puttin"+this.temp.StringContainer);

            RequestData info = new RequestData();
            info.Identifier = Identifier;
            assert (info.Identifier.equals(inputcluster.Id));
            info.GennedId = this.temp.StringContainer;
            info.OriginalXML=new String(this.mapprocessor.OperatorMap.get(pseudoOperator).SignedSoap());  //<<--adding original signed xml
            System.out.println("************************************");
            System.out.println(this.temp.StringContainer+"quwuwd!");
            System.out.println("************************************");
            info.operator = pseudoOperator;
            String result = new String(this.mapprocessor.OperatorMap.get(info.operator).SendSoapSigned());
            //****************
            String dir = "xmls";
            (new File(dir)).mkdirs();
            BinaryMessage.write(info.OriginalXML.getBytes(), dir+File.separator+info.GennedId+"SENDED.XML");
            System.out.println("\n\n\n\n\nWRITE ORIGINAL to >>>>>\n\n\n"+info.GennedId+"SENDED.XML");

            //****************
            if (result.indexOf("requestIsQueued") > 0) {
                info.Status = this.currentnewId;
                this.succesquued(info);
            } else {
                info.Status = this.errorqueed;
                this.errorquued(info);
            }
        }
    }

    public void getAllResponses() throws Exception {
        while (!this.dbReqs.check_complete()){
            String resss = new String(this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
            while (!resss.equals(this.stopperGetResponce)) {
                System.out.println("IN WHILE");
                System.out.println("***************************************ResultProcess>>>>>");
                System.out.println(resss);
                String originalMessageID = this.ext.extractTagValue(resss, ":OriginalMessageId");
                String MessageID = this.ext.extractTagValue(resss, ":MessageId");
                this.mylogger.log("getting data @MessageIDReq" + originalMessageID);
                if (this.dbReqs.get(originalMessageID, false) != null)
                    this.dbReqs.get(originalMessageID, false).ResponsedXML = resss;
                this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).Ack(MessageID);
                resss = new String(this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
            }
        }
    }

    public void getAllResponses(Integer delayInSeconds) throws Exception {

        while (!this.dbReqs.check_complete()){
            String resss = new String(this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
            //this.deps.logger.logToBuffer("received", resss);
            while (!resss.equals(this.stopperGetResponce)) {
                String originalMessageID = this.ext.extractTagValue(resss, ":OriginalMessageId");
                System.out.println( "Original ID\n"+ originalMessageID+"\n" );
                String MessageID = this.ext.extractTagValue(resss, ":MessageId");
                this.mylogger.logToBuffer("getting data @MessageIDReq" + originalMessageID);
                if (this.dbReqs.get(originalMessageID, false) != null){
                    this.dbReqs.get(originalMessageID,  false).ResponsedXML = resss;
                    System.out.println("\n\n\n\n\n\nWRITING RESPONCE XML!\n\n\n\n\n");
                    this.mylogger.logToBuffer("SIZE!"+originalMessageID,  String.valueOf(resss.length()));
                }
                if (AckEnabled){
                    this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).Ack(MessageID);
                    this.mylogger.logToBuffer("ack",  MessageID);
                    resss = new String( this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
                }

            }
            sleep(delayInSeconds*1000);
        }
    }

    public void getResponcesAnyUniversal() throws Exception {
        System.out.println("EXEC smev_answer1s 'x'");
        ResultSet Select2 = null;
        try {
            Select2 = sqlWrapper.getReponceSQL(exec);
        } catch (IOException e) {
            System.out.println("SQL delay");
            return;
        }
        String res=null;
        try {
            if (Select2.next()){
                System.out.println("Select2 not empty");
                res = Select2.getString("f_srv");
            }
            else return;
        } catch (SQLException e) {
           return;
        }
        assert(res!=null);
        logActivity(res);
        String resss = new String(this.mapprocessor.OperatorMap.get(res).GetResponceFilteredCompiled());
        if (!resss.equals(this.stopperGetResponce)) {
            try {
                sqlWrapper.sendResponceSQL(exec, resss);
            } catch (SQLException e) {
                System.out.println("SQL delay");
                return;
            } catch (IOException e) {
                System.out.println("SQL delay");
                return;
            }
            String original = ext.extractTagValue(resss,":OriginalMessageId");
            String id=ext.extractTagValue(resss, ":MessageID");
            if (id==null) return;
            if (AckEnabled){
                System.out.println("\n\n\nAck==>"+id);
                this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).Ack(id);
                logack(original);
            }

        }
    }

    void logack(String original) throws IOException {
        if (enableBinaryLogging){
            if (enableBinaryLogging){
                String description="Делаю ack на ";
                String GUID=original;
                Date date=new Date();
                Event event = new Event(description, GUID, "uknown", date,mylogger.StatusLogs.get("ok"));
                mylogger.writeEvent(event);
            }
        }
    }

    public void getResponsesEBS() throws Exception {
        if (inputFlow.pool.size()==0) return;
        System.out.println("\n\n\n\nGet responces!!!!\n\n\n\n");
        String resss = new String(this.mapprocessor.OperatorMap.get("findesia").GetResponceRequestCompiled());  //<=ebs

        //this.deps.logger.logToBuffer("received", resss);
        while (!resss.equals(this.stopperGetResponce)) {
            String originalMessageID = this.ext.extractTagValue(resss, ":OriginalMessageId");
            System.out.println( "Original ID\n"+ originalMessageID+"\n" );
            String MessageID = this.ext.extractTagValue(resss, ":MessageId");
            if (MessageID==null)
                return;
            this.mylogger.logToBuffer("getting data @MessageIDReq" + originalMessageID);
            if (this.dbReqs.get(originalMessageID, false) != null){
                this.dbReqs.get(originalMessageID,  false).ResponsedXML = resss;
                System.out.println("\n\n\n\n\n\nWRITING RESPONCE XML!\n\n\n\n\n");
                this.mylogger.logToBuffer("SIZE!"+originalMessageID,  String.valueOf(resss.length()));
            }
            System.out.println("MAKING ACK TO"+MessageID+"Oper=>"+"ebs"+"\n\n\n");
            this.mapprocessor.OperatorMap.get("ebs").Ack(MessageID);
            this.mylogger.logToBuffer("ack",  MessageID);
            resss = new String( this.mapprocessor.OperatorMap.get("ebs").GetResponceRequestCompiled());
        }

    }

    public void getResponses() throws Exception {
        if (inputFlow.pool.size()==0) return;
        System.out.println("\n\n\n\nGet responces!!!!\n\n\n\n");
        String resss = new String(this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());

            //this.deps.logger.logToBuffer("received", resss);
            while (!resss.equals(this.stopperGetResponce)) {
                String originalMessageID = this.ext.extractTagValue(resss, ":OriginalMessageId");
                System.out.println( "Original ID\n"+ originalMessageID+"\n" );
                String MessageID = this.ext.extractTagValue(resss, ":MessageId");
                if (MessageID == null)
                    return;
                this.mylogger.logToBuffer("getting data @MessageIDReq" + originalMessageID);
                if (this.dbReqs.get(originalMessageID, false) != null){
                    this.dbReqs.get(originalMessageID,  false).ResponsedXML = resss;
                    System.out.println("\n\n\n\n\n\nWRITING RESPONCE XML!\n\n\n\n\n");
                    this.mylogger.logToBuffer("SIZE!"+originalMessageID,  String.valueOf(resss.length()));
                }
                System.out.println("MAKING ACK TO"+MessageID+"Oper=>"+this.pseudoStarterOperator+"\n\n\n");
                this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).Ack(MessageID);
                this.mylogger.logToBuffer("ack",  MessageID);
                resss = new String( this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
            }

    }


    private String extractTrace(String input) {
        return this.ext.extractTagValue(input, "ns:MessageID");
    }


    public void run() throws Exception {
        String result = new String(this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
        System.out.println("result>>>>\n"+result);
        while (result.indexOf("ns2:MessageID")>0){
            String id=this.ext.extractTagValue(result, ":MessageID");
            if (id.equals(null))
                break;
            System.out.println("Extract id="+ id);
            perfom(result);
            this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).Ack(id);
            result = new String(this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
        }
    };

    public void perfom(String result){};

    public void dump() throws IOException {
        String dumpp = "dumped.bin";
        File file = new File(dumpp);

        //Delete the file; we will create a new file
        file.delete();

        // Get file channel in readonly mode
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

        // Get direct byte buffer access using channel.map() operation
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096 * 8 * 8);

        //Write the content using put methods
        buffer.put(this.freezer.saveInputFlow(this.inputFlow));
    }

    public Map<String, Boolean> calculatesInputOpers(){
        Map<String, Boolean> opers = new HashMap<>();
        for (int i = 0; i< inputFlow.pool.size(); i++)
            opers.put(inputFlow.pool.get(i).operator, true);
        return opers;
    }

    public void getResponsesIntelligent() throws Exception {
        for (Map.Entry<String, Boolean> entry : calculatesInputOpers().entrySet()) {
            System.out.println("FOREACHING INPUT FLOW(<GETTING FILTERED RESPONCES>)");
            String activeOperator = entry.getKey();
            System.out.println("\n\n\n\nGet responces!!!!\n\n\n\n");
            System.out.println("OPERATOR >>"+activeOperator);
            String resss = new String(this.mapprocessor.OperatorMap.get(activeOperator).GetResponceFilteredCompiled());
            while (!resss.equals(this.stopperGetResponce)) {
                String originalMessageID = this.ext.extractTagValue(resss, ":OriginalMessageId");
                System.out.println( "Original ID\n"+ originalMessageID+"\n" );
                String MessageID = this.ext.extractTagValue(resss, ":MessageId");
                if (MessageID == null)
                    return;
                this.mylogger.logToBuffer("getting data @MessageIDReq" + originalMessageID);
                if (this.dbReqs.get(originalMessageID, false) != null){
                    this.dbReqs.get(originalMessageID,  false).ResponsedXML = resss;
                    System.out.println("\n\n\n\n\n\nWRITING RESPONCE XML!\n\n\n\n\n");
                    this.mylogger.logToBuffer("SIZE!"+originalMessageID,  String.valueOf(resss.length()));
                }
                System.out.println("MAKING ACK TO"+MessageID+"Oper=>"+this.pseudoStarterOperator+"\n\n\n");
                this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).Ack(MessageID);
                this.mylogger.logToBuffer("ack",  MessageID);
                resss = new String( this.mapprocessor.OperatorMap.get(this.pseudoStarterOperator).GetResponceRequestCompiled());
            }
        }
        System.out.println("\n\n###########REQUESTS COMPLETES!########");
    }
}