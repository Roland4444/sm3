package util;

public class buildSql {
    public String sql;
    public buildSql(String param){
        this.sql = "set concat_null_yields_null off; SELECT f_body_xml FROM fns_files WHERE f_key='"+param+"';";
    }



    public String result(){
        return this.sql;
    }
}
