package schedulling.gettingDataImplem;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class getDataTest {
    DependencyContainer deps = new DependencyContainer();
    Scheduller sch = new Scheduller(deps);
    getData getData = new getData(deps.executor, deps.datasource, deps.Idgen, deps.inputDataFlow, deps.tableProcessor);
    public getDataTest() throws ClassNotFoundException, SQLException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException, IOException {
    }

    @Test
    public void getData() throws SQLException {
        String etalon ="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-12T09:22:53.8541908+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_C9DB5855-4316-4289-8B4E-CC8E44A17CDB\" paymentId=\"10412037290000001107201843871833\" purpose=\"НДФЛ за июнь 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"1331800\" paymentDate=\"2018-07-11\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"Общество с ограниченной ответственностью &quot;Астрагаз&quot;\" payerIdentifier=\"2003017041346302345002\"/><org:Payee name=\"УФК по Астраханской области ( МИФНС России №5 по Астраханской области)\" inn=\"3018018879\" kpp=\"301801001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.06.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-11\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        getData getData = new getData(deps.executor,deps.datasource, deps.Idgen, deps.inputDataFlow, deps.tableProcessor);
        int i=1;
     //   assertNotEquals(null, getData.getData());
        Map<String, byte[]> block = new HashMap<>();
        block=getData.getData();
        Iterator it0 = block.entrySet().iterator();
        if (it0.hasNext()) {
            System.out.println(i++);
            Map.Entry pair = (Map.Entry) it0.next();
            String Id = (String) pair.getKey();
            byte[] data = (byte[]) pair.getValue();
            System.out.println(i++);
            System.out.println(Id);
            System.out.println(new String(data));
            assertEquals(etalon, new String(data));
            it0.remove(); // avoids a ConcurrentModificationException
        }
    }
    @Test
    public void getDataStep2() throws SQLException {

        Map<String, byte[]> block = new HashMap<>();
        block=getData.getData();
        Iterator it0 = block.entrySet().iterator();
        int i=1;
        while (it0.hasNext()) {
            System.out.println(i++);
            Map.Entry pair = (Map.Entry) it0.next();
            String Id = (String) pair.getKey();
            byte[] data = (byte[]) pair.getValue();
            System.out.println(Id+"\n"+ new String(data));
            it0.remove(); // avoids a ConcurrentModificationException
        }
    }

    //@Test
    public void getData1() throws SQLException {
        Map<String, byte[]> block = new HashMap<>();
        block=getData.getData();
        String etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-13T08:31:23.0998280+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_AC0B90D8-757D-4BD2-9843-EA148907D237\" paymentId=\"10412037291014111207201812545317\" purpose=\"КБК 18011301081017000130 Оплата за охрану 2-х объектов согласно доп. соглашениям к договорам К1406 от 21.01.2013 г., К1409 от 08.11.2012 г. согл. распоряжения вкладчика от 12.11.2017 г.\" kbk=\"18011301081017000130\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"58000\" paymentDate=\"2018-07-12\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1210000000301700924632\"/><org:Payee name=\"УФК по Астраханской области (ОВО по городу Астрахани-филиал ФГКУ ОВО ВНГ России по Астраханской области,л/сч 04251D20860)\" inn=\"3015096808\" kpp=\"301543001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"13\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-12\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Сухоруков Владимир Павлович г.Астрахань просп.Губернатора А.Гужвина д.6 кв.36</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(new String(block.get("1641852%gis")), etalon);

    }
}