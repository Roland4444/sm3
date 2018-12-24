package schedulling.ResultsProcessors;

import DB.Executor;
import schedulling.abstractions.Identifier;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.ResultProcess;
import schedulling.abstractions.RequestData;
import se.roland.Extractor;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class egrResult implements ResultProcess {
    private Executor exc;
    private Extractor ext;
    private Identifier iden;
    private InputDataContainer inputFlow;
    public egrResult(Executor exc, Identifier identify){
        this.exc = exc;
        this.iden =identify;
        this.inputFlow=inputFlow;
        this.toDBStatus=new HashMap<>();
        this.toDBStatus.put("success", "9+");
        this.toDBStatus.put("ok", "ok");
        this.CodeErrors=new HashMap<>();
        this.CodeErrors.put("01", "01 - не найден в системе");
        this.CodeErrors.put("53", "53 - в электронном виде не существует");

    };
    public void setExtractor(Extractor ext){
        this.ext=ext;
    }
    public void setInputFlow(InputDataContainer inputFlow){
        this.inputFlow = inputFlow;
    }
    public Map<String,String> toDBStatus;
    private Map<String, String> CodeErrors;

    @Override
    public void perform(RequestData Result) throws SQLException {

    }

    @Override
    public void perform(RequestData Result, InputDataContainer inputFlow) throws SQLException, IOException {
        System.out.println("CALLING egr result perform");
        String fkey = iden.getId(Result.Identifier);
        assert (fkey!=null);
        insertResponceXML(Result, fkey);
        System.out.println("insert response xml in fkey "+ fkey );
        assert (ext!=null);
        String f_rec_id = get_f_rec_id(fkey);
        assert (f_rec_id!=null);
        String codeResult = ext.extractTagValue(Result.ResponsedXML, "ns1:КодОбр");
        System.out.println("RESPONCED XML=============>\n\n\n");
        System.out.println(Result.ResponsedXML);
        insertError(codeResult, fkey);
        insertStatus(fkey);
        System.out.println("Clean up in rawxml flow id"+Result.Identifier);
        System.out.println("IN REC ID=>"+f_rec_id);
        String f_body_a="nothing";
        System.out.println("Insert f_body_a in egr_files @" + f_rec_id);
        if (codeResult==null) {
            System.out.println("Error not found...");
            PreparedStatement InsertName = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fns_egr SET f_namp =? WHERE f_key = ?;");
            if (Result.ResponsedXML.indexOf("ns1:FNSVipIPResponse") > 0) {
                f_body_a=ext.parseTagFromByte(Result.ResponsedXML.getBytes(), "ns1:FNSVipIPResponse");
                System.out.println(f_body_a);//FIO(Result.ResponsedXML.getBytes()));
                InsertName.setString(1, FIO(Result.ResponsedXML.getBytes()));
                InsertName.setString(2, f_rec_id);
                InsertName.executeUpdate();
                System.out.println("Insert FIO in egr_files @" + f_rec_id);
            }
            if (Result.ResponsedXML.indexOf("ns1:FNSVipULResponse") > 0) {
                f_body_a=ext.parseTagFromByte(Result.ResponsedXML.getBytes(), "ns1:FNSVipULResponse");
                System.out.println(f_body_a);
                InsertName.setString(1, FullName(Result.ResponsedXML.getBytes()));
                InsertName.setString(2, f_rec_id);
                InsertName.executeUpdate();
                System.out.println("Insert FullName in egr_files @" + f_rec_id);
            }
        }
        else f_namp_error(codeResult, f_rec_id);

        PreparedStatement insertf_body_a = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fns_egr SET f_body_a =? WHERE f_key = ?;");
        insertf_body_a.setString(1, f_body_a);
        insertf_body_a.setString(2, f_rec_id);
        insertf_body_a.executeUpdate();
        inputFlow.destroy(Result.Identifier);
    }

    private void f_namp_error(String codeResult, String f_rec_id) throws SQLException {
        String appendix = "";
        if (this.CodeErrors.get(codeResult) == null)
           appendix = codeResult;
        else appendix = this.CodeErrors.get(codeResult);
        PreparedStatement InsertName = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fns_egr SET f_namp =? WHERE f_key = ?;");
        InsertName.setString(1, appendix);
        InsertName.setString(2, f_rec_id);
        InsertName.executeUpdate();
        System.out.println("Insert error code in egr_files @" + f_rec_id);

    }

    private void insertResponceXML(RequestData Result, String fkey) throws SQLException {
        PreparedStatement insertResponseXML = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fns_files SET f_body_a =? WHERE f_key = ?;");
        insertResponseXML.setBytes(1, Result.ResponsedXML.getBytes());
        insertResponseXML.setString(2, fkey);
        insertResponseXML.executeUpdate();
    }

    private void insertStatus(String fkey) throws SQLException {
        PreparedStatement InsertStatus = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fns_files SET f_stat =? WHERE f_key = ?;");
        InsertStatus.setString(1, toDBStatus.get("success"));
        InsertStatus.setString(2, fkey);
        System.out.println("insert status  xml in fkey "+ fkey + "    "+toDBStatus.get("success"));
        InsertStatus.executeUpdate();
    }

    private void insertError(String codeResult, String fkey) throws SQLException {
        PreparedStatement InsertError = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fns_files SET f_err =? WHERE f_key = ?;");
        if (codeResult != null)
            InsertError.setString(1, codeResult);
        else InsertError.setString(1, toDBStatus.get("ok"));
        InsertError.setString(2, fkey);
        InsertError.executeUpdate();
    }

    public String get_f_rec_id(String f_key) throws SQLException {
        ResultSet Select2 = null;
        Select2 = exc.submit( "SELECT * FROM  fns_files  WHERE f_key='"+f_key+"';");
        if (Select2.next())
            return Select2.getString("f_rec_id");
        return null;
    };

    public String FIO(byte[] input) throws SQLException {
        String result = ext.extractAttribute(input, "Имя")+
                ext.extractAttribute(input, "Фамилия")+
                ext.extractAttribute(input, "Отчество");
        return result;
    };

    public String FullName(byte[] input) throws SQLException {
        String result = ext.extractAttribute(input, "НаимЮЛПолн");
        return result;
    };


}
