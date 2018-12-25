package schedulling.ProcessorImplements;

import DB.Executor;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import readfile.Readfile;

import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Freezer;
import schedulling.abstractions.InputDataBlock;
import schedulling.abstractions.OutDataPerfomImpl.PerfomReceivedData;
import schedulling.gettingDataImplem.getData;
import se.roland.Extractor;
import util.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ProcessorPuttinDBTest {
    DependencyContainer deps = new DependencyContainer();
    Scheduller sch = new Scheduller(deps);

    Extractor ext = new Extractor();
    Injector inj = new Injector();
    InputDataBlock resulter = new InputDataBlock();

    PersonalSign ps = new PersonalSign();
    OutputStream os = new ByteArrayOutputStream();
    StreamResult sr = new StreamResult(os);

    String rawSave="binData/raw.bin";
    String initial="binData/initial.bin";
    String genned="binData/injectdatainXML.bin";
    String datamapFile="binData/datamap.bin";
    String inputFlowDump="binData/inputflow.bin";
    String ReqDump="binData/ReqInfoDump.bin";
    String resulttext="xml4test/resulttext.xml";


    public ProcessorPuttinDBTest() throws ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, SQLException, IOException {
    }

    public void init(){
        
    };
    
    @Test
    public void run() throws SQLException, IOException, ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        Readfile r = new Readfile("sqlset");

        PreparedStatement pst = deps.executor.getConn().prepareStatement("INSERT INTO lab.gis_files(?,?,?);");
        FileWriter wr = new FileWriter("xml4test/extract.xml");
        ResultSet Select2 = deps.executor.submit("set concat_null_yields_null off; SELECT f_body_xml FROM gis_files WHERE f_stat='0';");
        if (Select2.next()){
            String res =Select2.getString("f_body_xml");
            System.out.print(res);
            wr.write(res);
        }
        wr.close();
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        pst.setObject(1, data);
        pst.setObject(2, "0");
        pst.setObject(3, "000");
       // pst.execute();
    }

    @Test
    public void run1() {
    }

    @Test
    public void launchAll() {
    }

    @Test
    public void succesquued() throws Exception {
    /*    String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String resError = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700055501\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        Resolver resulter = new Resolver();
        Resolver resulter2 = new Resolver();
        resulter.setOperator("gis");
        resulter.DataToWork=res.getBytes();
        String msgId = deps.ext.extractTagValue(res, ":MessageID");
        deps.gis.bypassID=true;
        deps.datamap.DataConveer.put(msgId, resulter);
        resulter2.setOperator("gis");
        resulter2.setDataToWork(resError.getBytes());
        msgId = deps.ext.extractTagValue(resError, ":MessageID");
        deps.datamap.DataConveer.put(msgId, resulter2);
        sch.processor.sendAll();

        assertEquals("error", sch.deps.dbReqs.get("f6a09006-689a-11e8-8058-012ae3068118",true).Status);
        assertEquals("f6a09006-689a-11e8-8058-012ae3068118", sch.deps.dbReqs.get("f6a09006-689a-11e8-8058-012ae3068118",true).GennedId);
     //   assertEquals("error",sch.deps.relation.ContainerWithGenned.get("f6a09006-689a-11e8-8058-012ae3068000").status);
      //  assertEquals("f6a09006-689a-11e8-8058-012ae3068000", sch.deps.relation.ContainerWithGenned.get("f6a09006-689a-11e8-8058-012ae3068000").gennedId);
        sch.deps.dbReqs.flush();
        deps.gis.bypassID=false;
        sch.processor.sendAll();
        assertEquals("queued", sch.deps.dbReqs.get("f6a09006-689a-11e8-8058-012ae3068118",true).Status);
      //  assertEquals("queued",sch.deps.relation.ContainerWithGenned.get("f6a09006-689a-11e8-8058-012ae3068000").status);
    */}



    @Test
    public void getAllResponsesPart1() throws Exception {
        flush();
    }

    @Test
    public void getAllResponsesPart2() throws Exception {
        String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        resulter.operator="gis";
        resulter.DataToWork=(res.getBytes());
        resulter.Id="0012";
        String msgId = deps.ext.extractTagValue(res, ":MessageID");
        assertEquals("f6a09006-689a-11e8-8058-012ae3068118", msgId);
        deps.gis.bypassID=true;
        deps.inputDataFlow.put(resulter);
        sch.processor.sendAll();
        assertNotEquals(null, sch.deps.dbReqs);
        System.out.println("size==>"+sch.deps.dbReqs.pool.size());
        assertNotEquals("f6a09006-689a-11e8-8058-012ae3068118", new String(deps.gis.InfoToRequest));
        System.out.println("InfoToRequest  =>>"+ new String(deps.gis.InfoToRequest));

        //System.out.println(">>>>"+sch.deps.dbReqs.get(msgId,true).GennedId);
        System.out.println("msgId  =>>"+ msgId);

        assertEquals(resulter.Id, sch.deps.dbReqs.get(msgId,false).Identifier);

        assertEquals(sch.deps.dbReqs.get(msgId,false).GennedId, sch.deps.ext.extractTagValue(new String(deps.gis.InfoToRequest), ":MessageID"));

        FileOutputStream fos0 = new FileOutputStream(genned);
        fos0.write(sch.deps.dbReqs.get(msgId,false).GennedId.getBytes());
        fos0.close();
        FileOutputStream fos01 = new FileOutputStream(initial);
        fos01.write(msgId.getBytes());
        fos01.close();
        sch.deps.dbReqs.printAllGennedId();
        sch.deps.dbReqs.printAllIndentifiers();
        System.out.println(msgId);
        System.out.println(sch.deps.ext.extractTagValue(new String(deps.gis.InfoToRequest), ":MessageID"));
        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream(rawSave);
        fos.write(fr.saverawdbReqs(sch.deps.dbReqs));
        fos.close();

    }


    //@Test
    public void getAllResponsesPart3() throws Exception {
        Path p = Paths.get(rawSave);
        byte[] arr = Files.readAllBytes(p);
        Path gen = Paths.get(genned);
        byte[] gen_ = Files.readAllBytes(gen);
        Path original = Paths.get(initial);
        byte[] original_ = Files.readAllBytes(original);
        Freezer fr = new Freezer();
        sch.deps.dbReqs=fr.restoreInfoAll(arr);
        assertEquals(new String(gen_), sch.deps.dbReqs.get(new String(original_),true).GennedId);
        assertEquals(new String(original_), sch.deps.dbReqs.get(new String(original_),true).Identifier);
        sch.processor.getAllResponses();
        assertNotEquals(null, sch.deps.dbReqs.get(new String(original_),true).ResponsedXML);

        /*    String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        Resolver resulter = new Resolver();
        resulter.setOperator("gis");
        resulter.DataToWork=res.getBytes();
        String msgId = deps.ext.extractTagValue(res, ":MessageID");
        deps.gis.bypassID=false;
        deps.datamap.DataConveer.put(msgId, resulter);
        sch.processor.sendAll();
        sleep(15000);
        sch.processor.getAllResponses();
        // assertNotEquals(null, this.deps.dbReqs.get(msgId,false).ResponsedXML);
        sch.deps.dbReqs.printAllGennedId();*/
    }


        @Test
        public void flushSMEV3_() throws Exception {
            String result = getrespreq();
            while (result.indexOf(":MessageID")>0){
                String id=deps.ext.extractTagValue(result, ":MessageID");
                //   System.out.println("Extract id="+ id);
                String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
                System.out.println("Original id="+ originalid);
                deps.gis.Ack(id);
                result = getrespreq();
            }
        }


    @Test
    public void flush() throws Exception {
        String result = getrespreq();
        while (result.indexOf(":MessageID")>0){
            String id=ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid=ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id="+ originalid);
            this.deps.gis.Ack(id);
            result = getrespreq();
        }
    }

    String getrespreq() throws Exception {
        String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2014-02-11T17:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        sch.deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, sch.deps.gis.GetSoap());
        String response = new String(sch.deps.gis.GetResponceRequestCompiled());
        return response;
    };



    @Test
    public void getAllResponsesPart1Integrational() throws Exception {
        flush();
    }

    @Test
    public void getAllResponsesPart2Integrational() throws Exception {
        String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String msgId = deps.ext.extractTagValue(res, ":MessageID");
        assertEquals("f6a09006-689a-11e8-8058-012ae3068118", msgId);
        deps.gis.bypassID=false;
        String info = deps.inj.injectTagDirect(res, ":MessageID", sch.deps.uuidgen.generate() );

        InputDataBlock inputBlock = new InputDataBlock();

        inputBlock.operator=("gis");
        inputBlock.DataToWork=(info.getBytes());
        inputBlock.Id=msgId;

        deps.inputDataFlow.put(inputBlock);

        sch.processor.sendAll();

        System.out.println(sch.deps.dbReqs.pool.size());
        assertEquals(1, sch.deps.dbReqs.pool.size());

        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream(rawSave);
        fos.write(fr.saverawdbReqs(sch.deps.dbReqs));
        fos.close();


    }


  //  @Test
    public void getAllResponsesPart3Integrational() throws Exception {
        long startTime = System.currentTimeMillis();
        Freezer fr = new Freezer();
        Path p = Paths.get(rawSave);
        byte[] arr = Files.readAllBytes(p);
        sch.deps.dbReqs=fr.restoreInfoAll(arr);
        sch.processor.getAllResponses(20);
        sch.deps.logger.suspend("logs/fulllog.log");
        assertNotEquals(null, sch.deps.dbReqs.pool.get(0).ResponsedXML);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime/1000+"   Sekunden");
        /*    String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        Resolver resulter = new Resolver();
        resulter.setOperator("gis");
        resulter.DataToWork=res.getBytes();
        String msgId = deps.ext.extractTagValue(res, ":MessageID");
        deps.gis.bypassID=false;
        deps.datamap.DataConveer.put(msgId, resulter);
        sch.processor.sendAll();
        sleep(15000);
        sch.processor.getAllResponses();
        // assertNotEquals(null, this.deps.dbReqs.get(msgId,false).ResponsedXML);
        sch.deps.dbReqs.printAllGennedId();*/
    }






    public void getAllResponsesFULLIntegrational() throws Exception {
        long startTime = System.currentTimeMillis();
        deps.gis.SupressConsole=false;

        flush();
        String res = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700000001\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String msgId = deps.ext.extractTagValue(res, ":MessageID");
        assertEquals("f6a09006-689a-11e8-8058-012ae3068118", msgId);
        deps.gis.bypassID=false;
        String info = deps.inj.injectTagDirect(res, ":MessageID", sch.deps.uuidgen.generate() );

        InputDataBlock inputBlock = new InputDataBlock();

        inputBlock.operator=("gis");
        inputBlock.DataToWork=(info.getBytes());
        inputBlock.Id=msgId;

        deps.inputDataFlow.put(inputBlock);

        sch.processor.sendAll();

        System.out.println(sch.deps.dbReqs.pool.size());
        assertEquals(1, sch.deps.dbReqs.pool.size());

        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream(rawSave);
        fos.write(fr.saverawdbReqs(sch.deps.dbReqs));
        fos.close();

        Path p = Paths.get(rawSave);
        byte[] arr = Files.readAllBytes(p);
        sch.deps.dbReqs=fr.restoreInfoAll(arr);
        sch.processor.getAllResponses(20);
        sch.deps.logger.suspend("logs/fulllog.log");
        assertNotEquals(null, sch.deps.dbReqs.pool.get(0).ResponsedXML);
        System.out.println(sch.deps.dbReqs.pool.get(0).ResponsedXML);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime/1000+"   Sekunden");

    }

   // @Test
    public void getAllResponsesFULLIntegrationaKurzDatabase() throws Exception {
        long startTime = System.currentTimeMillis();
        deps.gis.SupressConsole=false;
        getData getData = new getData(deps.executor, deps.datasource, deps.Idgen, deps.inputDataFlow, deps.tableProcessor);
        getData.loadDataToInputFlow(true);

        flush();

        sch.processor.sendAll();

        System.out.println(sch.deps.dbReqs.pool.size());
        assertEquals(1, sch.deps.dbReqs.pool.size());

        Freezer fr = new Freezer();
        FileOutputStream fos = new FileOutputStream(rawSave);
        fos.write(fr.saverawdbReqs(sch.deps.dbReqs));
        fos.close();

        File file = new File(inputFlowDump);


        if (!file.exists()) {
            System.out.println("\n\n\ndumping InputFlow\n\n\n");
            FileOutputStream fos2 = new FileOutputStream(inputFlowDump);
            fos2.write(fr.saveInputFlow(sch.deps.inputDataFlow));
            fos2.close();
        }
        else {
            System.out.println("\n\n\nFILE DUMP  InputFlow EXIST!\n\n\n");
            System.out.print("USE NEXT TEST getAllResponsesFULLIntegrationaloadFromFileStateKurzDatabase");
        }

        assertEquals(false, file.exists());
        Path p = Paths.get(rawSave);
        byte[] arr = Files.readAllBytes(p);
        sch.deps.dbReqs=fr.restoreInfoAll(arr);
        sch.processor.getAllResponses(20);
        sch.deps.logger.suspend("logs/fulllog.log");
        assertNotEquals(null, sch.deps.dbReqs.pool.get(0).ResponsedXML);
        System.out.println(sch.deps.dbReqs.pool.get(0).ResponsedXML);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime/1000+"   Sekunden");
    }



  //  @Test
    public void dumpinginput() throws Exception {
        long startTime = System.currentTimeMillis();
        flush();
        deps.gis.SupressConsole=false;
        getData getData = new getData(deps.executor, deps.datasource, deps.Idgen, deps.inputDataFlow, deps.tableProcessor);
        getData.loadDataToInputFlow(true);


        File file = new File(inputFlowDump);

        if (!file.exists()) {
            System.out.println("\n\n\ndumping InputFlow\n\n\n");
            FileOutputStream fos2 = new FileOutputStream(inputFlowDump);
            fos2.write(sch.deps.freezer.saveInputFlow(sch.deps.inputDataFlow));
            fos2.close();
        }
        sch.processor.sendAll();
        sch.processor.getAllResponses();
        File file2 = new File(ReqDump);
        assertNotEquals(null, sch.deps.dbReqs.pool.get(0).ResponsedXML);
        if (!file2.exists()) {
            System.out.println("\n\n\ndumping RequestFLOW\n\n\n");
            FileOutputStream fos3 = new FileOutputStream(ReqDump);
            fos3.write(sch.deps.freezer.saverawdbReqs(sch.deps.dbReqs));
            fos3.close();
        }
        else {
            System.out.println("\n\n\nFILE DUMP  RequestFLOW EXIST!\n\n\n");
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime/1000+"   Sekunden");
    }

  //  @Test
    public void getAllResponsesFULLIntegrationaloadFromFileStateKurzDatabase() throws Exception {
        long startTime = System.currentTimeMillis();
        deps.gis.SupressConsole=false;

        flush();

        Freezer fr = new Freezer();

        Path p2 = Paths.get(inputFlowDump);
        byte[] bytesInputFlow = Files.readAllBytes(p2);

        sch.deps.inputDataFlow=fr.restoreInputFlow(bytesInputFlow);

        sch.processor.sendAll();

        System.out.println(sch.deps.dbReqs.pool.size());
        assertEquals(1, sch.deps.dbReqs.pool.size());

        sch.processor.getAllResponses(20);

        FileOutputStream fos = new FileOutputStream(rawSave);
        fos.write(fr.saverawdbReqs(sch.deps.dbReqs));
        fos.close();

        File file = new File(ReqDump);

        if (!file.exists()) {
            System.out.println("\n\n\ndumping RequestFLOW\n\n\n");
            FileOutputStream fos3 = new FileOutputStream(ReqDump);
            fos3.write(fr.saverawdbReqs(sch.deps.dbReqs));
            fos3.close();
        }
        else {
            System.out.println("\n\n\nFILE DUMP  RequestFLOW EXIST!\n\n\n");
        }

        sch.deps.logger.suspend("logs/fulllog.log");
        assertNotEquals(null, sch.deps.dbReqs.pool.get(0).ResponsedXML);
        System.out.println(sch.deps.dbReqs.pool.get(0).ResponsedXML);
        FileOutputStream fos55 = new FileOutputStream(rawSave);
        fos55.write(fr.saverawdbReqs(sch.deps.dbReqs));
        fos55.close();
        FileWriter wr=new FileWriter(resulttext);
        wr.write(sch.deps.dbReqs.pool.get(0).ResponsedXML);
        sch.deps.performReceiveddata.ProcessResultsTable();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime/1000+"   Sekunden");

    }

    //@Test
    public void pushResultTestFromsavedReq() throws IOException, ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        Path p2 = Paths.get(ReqDump);
     //   this.performReceiveddata = new PerfomReceivedData(this.tableResultProcessors, this.dbReqs);
        Path p0 = Paths.get(inputFlowDump);
        byte[] bytesInputFlow = Files.readAllBytes(p0);
        sch.deps.inputDataFlow=sch.deps.freezer.restoreInputFlow(bytesInputFlow);

        byte[] Reqs = Files.readAllBytes(p2);
        sch.deps.dbReqs=sch.deps.freezer.restoreInfoAll(Reqs);
        sch.deps.inputDataFlow=sch.deps.freezer.restoreInputFlow(bytesInputFlow);
        assertEquals(1, sch.deps.dbReqs.pool.size());
        assertEquals("gis", sch.deps.dbReqs.pool.get(0).operator);
        assertEquals(1, sch.deps.inputDataFlow.pool.size());
        System.out.println(sch.deps.dbReqs.pool.get(0).operator);
        System.out.println(sch.deps.dbReqs.pool.get(0).ResponsedXML);
        System.out.println(sch.deps.dbReqs.pool.get(0).Status);
        System.out.println(sch.deps.dbReqs.pool.get(0).GennedId);
        System.out.println(sch.deps.dbReqs.pool.get(0).Identifier);
        sch.deps.performReceiveddata.setFlow(sch.deps.tableResultProcessors, sch.deps.dbReqs, sch.deps.inputDataFlow);
        sch.deps.performReceiveddata.ProcessResultsTable();
        System.out.println("in tasker==>");
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        ResultSet Select2 = null;
        try {
            Select2 = f.submit( "SELECT * FROM  gis_files  WHERE f_key='1642575';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int counter=0;
        try {
            while (Select2.next()){
                String res =Select2.getString("f_stat");
                assertEquals("9+", res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(0, sch.deps.inputDataFlow.pool.size());

    }


/*
    @Test
    public void test1LoadDataFropmDbExchange() throws Exception {
        flush();

    }

    @Test
    public void test2LoadDataFropmDbExchange() throws SQLException, IOException {
      //  deps.datamap.DataConveer.put(identifier, resulter);

            sch.tasker.run();
            assertEquals(deps.gis_, sch.deps.datamap.DataConveer.get("1641258%gis").operator);
            assertNotEquals(null, sch.deps.datamap.DataConveer.get("1641258%gis"));
            assertEquals(null, sch.deps.datamap.DataConveer.get("1641258%gi0s"));
            Freezer fr = new Freezer();
            FileOutputStream fos = new FileOutputStream(datamapFile);
            fos.write(fr.savedataMap(sch.deps.datamap));
            fos.close();

    }

    @Test
    public void test3LoadDataFropmDbExchange() throws Exception {
        Freezer fr = new Freezer();
        Path p = Paths.get(datamapFile);
        byte[] arr = Files.readAllBytes(p);
        sch.deps.datamap=fr.restoreInputFlow(arr);
        assertEquals(deps.gis_, sch.deps.datamap.DataConveer.get("1641258%gis").operator);
        assertNotEquals(null, sch.deps.datamap.DataConveer.get("1641258%gis"));
        assertEquals(null, sch.deps.datamap.DataConveer.get("1641258%gi0s"));
        sch.processor.sendAll();
     //   assertEquals(sch.deps.dbReqs.get("1641258%gis", true).Status, deps.sucesfullqueed);

    }
*/
    }




