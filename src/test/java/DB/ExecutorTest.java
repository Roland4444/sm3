package DB;

import crypto.Gost3411Hash;
import org.junit.Test;
import readfile.Readfile;
import schedulling.abstractions.Freezer;
import util.Event;
import util.buildSql;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ExecutorTest {

    @Test
    public void getLenth() {
    }

    @Test
    public void submit() throws SQLException, IOException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        buildSql sql = new buildSql("3567");
        assertNotEquals(null, f.submit(sql.result()) );
        FileWriter wr = new FileWriter("3567");
        ResultSet Select2 = f.submit("set concat_null_yields_null off; SELECT * FROM dbo.smev3files WHERE f_key=19;");
        Gost3411Hash gost = new Gost3411Hash();
        if (Select2.next()){
            String res = String.valueOf(gost.getBytesFromBase64(Select2.getString("f_body_xml")));
            System.out.print(res);
        }
        wr.close();

    }
    @Test
    public void submitver() throws SQLException, IOException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        buildSql sql = new buildSql("3567");
        assertNotEquals(null, f.submit(sql.result()) );
        FileWriter wr = new FileWriter("3567");
        ResultSet Select2 = f.submit("set concat_null_yields_null off; SELECT @@version;");
        Gost3411Hash gost = new Gost3411Hash();
        if (Select2.next()){
            String res = Select2.getString(1);
            System.out.print(res);
        }
        wr.close();

    }

    @Test
    public void submit2() throws SQLException, IOException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        buildSql sql = new buildSql("3567");
        assertNotEquals(null, f.submit(sql.result()) );
        FileWriter wr = new FileWriter("xml4test/1637006.xml");
        ResultSet Select2 = f.submit("set concat_null_yields_null off; SELECT f_body_xml FROM gis_files WHERE f_key='1647314';");
        Gost3411Hash gost = new Gost3411Hash();
        if (Select2.next()){
            String res =Select2.getString("f_body_xml");
            System.out.print(res);
            wr.write(res);
        }
        wr.close();
    }

    @Test
    public void gettasker() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        ResultSet Select2 = null;
        try {
            Select2 = f.submit("set concat_null_yields_null off; SELECT f_key FROM fns_files WHERE f_stat='12';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Select2.next()){
                String res =Select2.getString("f_key");
                System.out.print(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void gettasker2() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        ResultSet Select2 = null;
        try {
            Select2 = f.submit( "SELECT * FROM  fns_files  WHERE f_key='3609';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Select2.next()){
                String res =Select2.getString("f_rec_id");
                System.out.println(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void gettasker22() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        ResultSet Select2 = null;
        try {
            Select2 = f.submit( "SELECT * FROM  smev3files WHERE f_key='4';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Select2.next()){
                String res =Select2.getString("f_body_xml");
                System.out.println(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void gettasker21() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        ResultSet Select2 = null;
        try {
            Select2 = f.submit( "set concat_null_yields_null off; EXEC smev_query1s 'x', 1;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int counter=0;
        try {
            while (Select2.next()){
                String res =Select2.getString("f_key");
                System.out.println(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getfiasguuid() throws SQLException, IOException {

    }

    //UPDATE fns_egr SET f_namp =? WHERE f_key = ?;"
    @Test
    public void gettasker221() throws SQLException, IOException {
        long startTime = System.currentTimeMillis();



        ArrayList<String> datablock = new ArrayList<>();
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        ResultSet Select2 = null;
        try {
            Select2 = f.submit( "SELECT f_narrative FROM  gkh_payment;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int counter=0;
        try {
            while (Select2.next()){
                String res =Select2.getString("f_narrative");
                datablock.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime/1000+"   Sekunden");
        System.out.print("freezing...");
        Freezer fr = new Freezer();
        FileOutputStream fos2 = new FileOutputStream("binData/array.bin");
        fos2.write(fr.SaveStringArrayList(datablock));
        fos2.close();

    }



    @Test
    public void gettasker212225() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        String target ="xml4test/VIPUL-respansw-0001.XML";
        Path p = Paths.get(target);
        String data="0";
        byte[] arr =data.getBytes();
        String table = "fms_zap";
        PreparedStatement pst = f.getConn().prepareStatement("set concat_null_yields_null on; EXEC smev_answer2s ?;");
        pst.setBytes(1,data.getBytes());
        pst.executeUpdate();

    }

    @Test
    public void gettasker2125() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        String target ="xml4test/VIPUL-respansw-0001.XML";
        Path p = Paths.get(target);
        String data="0";
        byte[] arr =data.getBytes();
        String table = "fms_zap";
        PreparedStatement pst = f.getConn().prepareStatement("UPDATE "+table+" SET f_stat =? WHERE f_key = ?;");
        pst.setString(1,data);
        pst.setString(2, "48827");
        pst.executeUpdate();

    }
//set concat_null_yields_null off; EXEC cb2017_vypiska_roma '"+p1+"', '"+p2+"', '"+p3+"', '2018-04-30'
    @Test
    public void gettasker21255() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
      //  byte[] arr = Files.readAllBytes(p);
        PreparedStatement pst = f.getConn().prepareStatement("UPDATE fns_files SET f_stat ='0' WHERE  f_stat ='12';");
        //   pst.setBytes(1,arr);
        pst.executeUpdate();
         pst = f.getConn().prepareStatement("UPDATE gis_files SET f_stat ='0' WHERE  f_stat ='12';");
        //   pst.setBytes(1,arr);
        pst.executeUpdate();
         pst = f.getConn().prepareStatement("UPDATE fms_zap SET f_stat ='0' WHERE  f_stat ='12';");
        //   pst.setBytes(1,arr);
        pst.executeUpdate();

    }


    //@Test
    public void callstored() throws SQLException, IOException {
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        //  byte[] arr = Files.readAllBytes(p);
        ResultSet result =f.submit("set concat_null_yields_null off; EXEC smev_query 'x';");
        assertNotEquals(null, result);

    }

    @Test
    public void gettasker212() throws SQLException, IOException {
        System.out.println("in tasker==>");
        String etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-13T08:31:23.0998280+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_AC0B90D8-757D-4BD2-9843-EA148907D237\" paymentId=\"10412037291014111207201812545317\" purpose=\"КБК 18011301081017000130 Оплата за охрану 2-х объектов согласно доп. соглашениям к договорам К1406 от 21.01.2013 г., К1409 от 08.11.2012 г. согл. распоряжения вкладчика от 12.11.2017 г.\" kbk=\"18011301081017000130\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"58000\" paymentDate=\"2018-07-12\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1210000000301700924632\"/><org:Payee name=\"УФК по Астраханской области (ОВО по городу Астрахани-филиал ФГКУ ОВО ВНГ России по Астраханской области,л/сч 04251D20860)\" inn=\"3015096808\" kpp=\"301543001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"13\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-12\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Сухоруков Владимир Павлович г.Астрахань просп.Губернатора А.Гужвина д.6 кв.36</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        String target ="xml4test/VIPUL-respansw-0001.XML";
        Path p = Paths.get(target);
        byte[] arr = Files.readAllBytes(p);
        PreparedStatement pst = f.getConn().prepareStatement("UPDATE fns_files SET f_stat =? WHERE f_key = '3590';");
        pst.setString(1,"0");
        pst.executeUpdate();

    }



    @Test
    public void smev1() throws SQLException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);

        ResultSet Select2=null;
        try {
            Select2 = f.submit( "EXEC smev_answer1s 'x'");
        } catch (SQLException e) {
        }
        if (Select2.next())
            System.out.println( Select2.getString("f_srv"));

    }

    //@Test
    public void banksread() throws SQLException, IOException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);


        ResultSet Select2 = f.submit(
                        "SELECT *\n" +
                                "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                                "WHERE TABLE_NAME = N'banks'");//"set concat_null_yields_null off; SELECT * from dbo.banks;");
        
        assertNotEquals(null, Select2);
        if (Select2.next()){
            for (int i=1; i<Select2.getFetchSize();i++){
                String res = Select2.getString(i);
                System.out.println(res);
            }

        }


    }


    @Test
    public void prepareforSrored() throws SQLException {
        String etalon = "(?, ?)";
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        Map<Object, Object> input = new HashMap<>();
        input.put("gerbert", "gerbert".getBytes());
        input.put("gerbert2", "gerbert2".getBytes());
        assertEquals(etalon, f.prepareforSrored(input));
    }

    @Test
    public void executeStored() throws SQLException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        Map<String, String> input = new HashMap<>();
        String P_code = "30000001000038900";
        f.exexcuteUUID(P_code, "38");


    }
}