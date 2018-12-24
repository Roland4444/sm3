package schedulling.ResultsProcessors;

import DB.Executor;
import schedulling.abstractions.Identifier;
import schedulling.abstractions.InputDataContainer;
import schedulling.abstractions.OutDataPerform.ResultProcess;
import schedulling.abstractions.RequestData;
import se.roland.Extractor;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class passResult  implements ResultProcess {
    private Executor exc;
    private Extractor ext;
    private Identifier iden;
    private InputDataContainer inputFlow;
    public passResult(Executor exc, Identifier identify){
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
        System.out.println("CALLING PASS result perform");
        String fkey = iden.getId(Result.Identifier);
        assert (fkey!=null);
        insertResponceXML(Result, fkey);
        System.out.println("insert response xml in fkey "+ fkey );
        assert (ext!=null);

        System.out.println("RESPONCED XML=============>\n\n\n");
        System.out.println(Result.ResponsedXML);

        System.out.println("Insert f_body_a in fms_zap @" +fkey);

        insertStatus(fkey);
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
        PreparedStatement insertResponseXML = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fms_zap SET f_body_a =? WHERE f_key = ?;");
        insertResponseXML.setBytes(1, Result.ResponsedXML.getBytes());
        insertResponseXML.setString(2, fkey);
        insertResponseXML.executeUpdate();
    }

    private void insertStatus(String fkey) throws SQLException {
        PreparedStatement InsertStatus = exc.getConn().prepareStatement("set concat_null_yields_null off;UPDATE fms_zap SET f_stat =? WHERE f_key = ?;");
        InsertStatus.setString(1, toDBStatus.get("success"));
        InsertStatus.setString(2, fkey);
        System.out.println("insert status  xml in fkey "+ fkey + "    "+toDBStatus.get("success"));
        InsertStatus.executeUpdate();
    }



}

