package schedulling.ResultsProcessors;

import DB.Executor;
import schedulling.abstractions.Identifier;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.ResultProcess;
import schedulling.abstractions.RequestData;
import se.roland.Extractor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class gisResult implements ResultProcess {
    private Executor exc;
    private Identifier iden;
    private InputDataContainer inputFlow;
    private Extractor ext;
    public gisResult(Executor exc, Identifier identify){
      this.exc = exc;
      this.iden =identify;
      this.inputFlow=inputFlow;
      this.toDBStatus=new HashMap<>();
      this.toDBStatus.put("success", "9+");
    };
    public void setExtractor(Extractor ext){
        this.ext=ext;
    }
    public void setInputFlow(InputDataContainer inputFlow){
        this.inputFlow = inputFlow;

    }
    public Map<String,String> toDBStatus;

    @Override
    public void perform(RequestData Result) throws SQLException {

    }

    @Override
    public void perform(RequestData Result, InputDataContainer inputFlow) throws SQLException {

        System.out.println("CALLING Gis result perform");
        String fkey = iden.getId(Result.Identifier);
        String extractError = ext.extractTagValue(Result.ResponsedXML, "RejectionReasonDescription");
        System.out.println("extract error ==>"+extractError);
        if (extractError!=null) {
            PreparedStatement insertErrorXML = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE gis_files SET f_err =? WHERE f_key = ?;");
            insertErrorXML.setString(1, "error");//extractError.getBytes());
            insertErrorXML.setString(2, fkey);
            System.out.println("inbsert errror xml in fkey " + fkey);
            insertErrorXML.executeUpdate();
        } else{
            PreparedStatement insertErrorXML = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE gis_files SET f_err =? WHERE f_key = ?;");
            insertErrorXML.setString(1, f_err(Result.ResponsedXML.getBytes()));//extractError.getBytes());
            insertErrorXML.setString(2, fkey);
            System.out.println("inbsert sucess xml in fkey " + fkey);
            insertErrorXML.executeUpdate();
        };
        PreparedStatement insertResponseXML = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE gis_files SET f_body_a =? WHERE f_key = ?;");
        insertResponseXML.setBytes(1, Result.ResponsedXML.getBytes());
        insertResponseXML.setString(2, fkey);
        System.out.println("insert respoinse xml in fkey " + fkey);
        insertResponseXML.executeUpdate();

        PreparedStatement InsertStatus = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE gis_files SET f_stat =? WHERE f_key = ?;");
        InsertStatus.setString(1, toDBStatus.get("success"));
        InsertStatus.setString(2, fkey);
        System.out.println("insert status  xml in fkey " + fkey + "    " + toDBStatus.get("success"));
        InsertStatus.executeUpdate();
        System.out.println("Clean up in rawxml flow id" + Result.Identifier);
        inputFlow.destroy(Result.Identifier);
    }

    public String f_err(byte[] input){
        String result = ext.extractAttribute(input, "code")+
                ext.extractAttribute(input, "description");
        return result;
    }
}
