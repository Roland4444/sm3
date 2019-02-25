package standart;

import Message.abstractions.BinaryMessage;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import org.xml.sax.SAXException;
import schedulling.abstractions.DependencyContainer;
import util.*;
import util.crypto.ESIA.Test6ed;
import util.crypto.ESIA.Test6f1;
import util.crypto.ESIA.Test77e;
import util.crypto.Sign2018;
import util.crypto.TestSign2001;
import util.crypto.TestSign2019;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class gisTest {
  // DependencyContainer deps = new DependencyContainer(new SignerXML(new Sign2018(), new Sign2018()));
   DependencyContainer deps = new DependencyContainer(new SignerXML(new TestSign2019(), new TestSign2019()));
 //   DependencyContainer deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()));
  //DependencyContainer deps = new DependencyContainer(new SignerXML(new Test6f1(), new Test6f1()));


    public gisTest() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException {

    }

    @Test
    public void testtwoClassesOnWork() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>54a8e97e-7ee0-11e8-a102-fbac92a9e3ca</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String data2 = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "  <S:Body>\n" +
                "    <ns2:SendRequestRequest>\n" +
                "      <ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                "        <ns:MessageID>54a8e97e-7ee0-11e8-a102-fbac92a9e3ca</ns:MessageID>\n" +
                "        <ns2:MessagePrimaryContent>\n" +
                "          <ns1:FNSVipULRequest xmlns:ns1=\"urn://x-artefacts-fns-vipip-tosmv-ru/311-15/4.0.6\" ИдДок=\"F69BB651-80C9-4007-B3FB-09A20BBE2DA5\" >\n" +
                "            <ns1:ЗапросИП>\n" +
                "              <ns1:ОГРНИП>305500910900012</ns1:ОГРНИП>\n" +
                "            </ns1:ЗапросИП>\n" +
                "          </ns1:FNSVipULRequest>\n" +
                "        </ns2:MessagePrimaryContent>\n" +
                "        <ns:TestMessage />\n" +
                "      </ns:SenderProvidedRequestData>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">       \n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns2:SendRequestRequest>\n" +
                "  </S:Body>\n" +
                "</S:Envelope>";

        deps.gis.setinput(data.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        assertNotEquals(null, deps.gis.SignedSoap());
        String response = new String(deps.gis.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
           System.out.println("FAULT");
        }
    }

    @Test
    public void preparng() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns:PersonalSignature");
        String prepared2 = deps.inj.flushTagData(prepared, "ns:PersonalSignature");
        System.out.println(prepared2);
        String beforeInject = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared3 = deps.inj.injectAttribute(prepared2, "paymentId", "10471020010005232407201");
        assertEquals("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10471020010005232407201\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>", prepared3);
    }


    @Test
    public void sendInitialRequestRequestGis() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String data2 = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2019-01-01T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String emptytime = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";

        String prepared = deps.inj.flushTagData(data, "ns:PersonalSignature");
        String prepared2 = deps.inj.flushTagData(prepared, "ns:PersonalSignature");
        String prepared3 = deps.inj.injectAttribute(prepared2, "paymentId", "10471020010005232407201700000001");
        deps.gis.setinput(prepared3.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        assertNotEquals(null, deps.gis.SignedSoap());
        String response = new String(deps.gis.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }





    @Test
    public void GetresponcerequestwoFilter() throws Exception {//2014-02-11T17:10:03.616+04:00
            String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "<ns:GetResponseRequest>\n" +
                    "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"><ns2:Timestamp>2018-07-18T09:10:03.616+04:00</ns2:Timestamp></ns2:MessageTypeSelector>\n" +
                    "<!--Optional:-->\n" +
                    "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                    "</ns:GetResponseRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
            deps.gis.setinput(prepared.getBytes());
            assertNotEquals(null, deps.gis.GetSoap());
            String response = new String(deps.gis.GetResponseRequestwoFilter());
            String originalid = deps.ext.extractTagValue(response,":OriginalMessageId");
            String messageId = deps.ext.extractTagValue(response,":MessageId");
            System.out.println("@\n"+ originalid);
            System.out.println(response);
            if (response.indexOf("fault")>0) {
                System.out.println("FAULT");
            }
            if (originalid!=null)
                deps.gis.Ack(messageId);



    }

    @Test
    public void GetresponcerequestwoFilterwithouttimestamp() throws Exception {//2014-02-11T17:10:03.616+04:00
        String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "<ns:GetResponseRequest>\n" +
                "<ns2:MessageTypeSelector xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CALLER\"></ns2:MessageTypeSelector>\n" +
                "<!--Optional:-->\n" +
                "<ns:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CALLER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>iYwGGJIG7q3AuiIBGC8G/Uk50FIIJmC+Vxf24dbh15I=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>7C4yUXubfFseK5eaFQfWsS5eM3+t85lcWqjD3FPGSBcNvYq78t5WMRE/5/5BiLvLww6vq0xM+4sbOH00RTDjYQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:CallerInformationSystemSignature>\n" +
                "</ns:GetResponseRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        String originalid = deps.ext.extractTagValue(response,":OriginalMessageId");
        String messageId = deps.ext.extractTagValue(response,":MessageId");
        System.out.println("@\n"+ originalid);
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
        if (originalid!=null)
            deps.gis.Ack(messageId);



    }


    public void cycleraw() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i=0;i<10;i++){
            System.out.println("Cycle=>"+i+"\n\n");
            String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
            String prepared = deps.inj.flushTagData(data, "ns:PersonalSignature");
            String prepared2 = deps.inj.flushTagData(prepared, "ns:PersonalSignature");
            String prepared3 = deps.inj.injectAttribute(prepared2, "paymentId", "10471020010005232407201700000001");
            deps.gis.setinput(prepared3.getBytes());
            assertNotEquals(null, deps.gis.GetSoap());
            assertNotEquals(null, deps.gis.SignedSoap());
            String response = new String(deps.gis.SendSoapSigned());
            System.out.println(response);
            if (response.indexOf("fault")>0) {
                System.out.println("FAULT");
            }
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime);
    }

    @Test
    public void cycle() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i=0;i<10;i++){
            System.out.println("Cycle=>"+i+"\n\n");
            String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068118</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" paymentId=\"10412037290000000207201843823757\" purpose=\"НДФЛ за июль 2018г.\" kbk=\"18210102010011000110\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"13607800\" paymentDate=\"2018-07-02\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerName=\"АО ВКАБАНК\" payerIdentifier=\"2003015011755301501001\"/><org:Payee name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\" inn=\"3015026737\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"02\" paytReason=\"ТП\" taxPeriod=\"МС.07.2018\" taxDocNumber=\"0\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
            String prepared = deps.inj.flushTagData(data, "ns:PersonalSignature");
            String prepared2 = deps.inj.flushTagData(prepared, "ns:PersonalSignature");
            String prepared3 = deps.inj.injectAttribute(prepared2, "paymentId", "10471020010005232407201700000001");
            deps.gis.setinput(prepared3.getBytes());
            assertNotEquals(null, deps.gis.GetSoap());
            assertNotEquals(null, deps.gis.SignedSoap());
            String response = new String(deps.gis.SendSoapSigned());
            System.out.println(response);
            if (response.indexOf("fault")>0) {
                System.out.println("FAULT");
            }
        }

        String result = getrespreq();
       // result="lll";

        while (deps.ext.extractTagValue(result, "ns2:OriginalMessageId")!=null){
            String id=deps.ext.extractTagValue(result, "ns2:MessageId");
            System.out.println("Extract id="+ id);
            deps.gis.Ack(id);
            result = getrespreq();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("total="+elapsedTime);

    }


    @Test
    public void testRespReq() throws Exception {
        String prepared="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:GetResponseRequest>\n" +
                "      <ns2:MessageTypeSelector xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\">\n" +
                "        <ns2:NamespaceURI>urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1</ns2:NamespaceURI>\n" +
                "        <ns2:RootElementLocalName>ImportPaymentsRequest</ns2:RootElementLocalName>\n" +
                "        <ns2:Timestamp>2018-06-29T10:59:50.937</ns2:Timestamp>\n" +
                "      </ns2:MessageTypeSelector>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CALLER\">\n" +
                "              <ds:Transforms>\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" />\n" +
                "              </ds:Transforms>\n" +
                "              <ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" />\n" +
                "              <ds:DigestValue>000</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "          </ds:SignedInfo>\n" +
                "          <ds:SignatureValue>000</ds:SignatureValue>\n" +
                "          <ds:KeyInfo>\n" +
                "            <ds:X509Data>\n" +
                "              <ds:X509Certificate>000</ds:X509Certificate>\n" +
                "            </ds:X509Data>\n" +
                "          </ds:KeyInfo>\n" +
                "        </ds:Signature>\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:GetResponseRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequest());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }


    @Test
    public void req() throws Exception {
        String prepared="\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:AckRequest>\n" +
                "      <ns2:AckTargetMessage xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\" accepted=\"true\">e1a4c9f0-7eba-11e8-83c9-fa163e24a723</ns2:AckTargetMessage>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CALLER\">\n" +
                "              <ds:Transforms>\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" />\n" +
                "              </ds:Transforms>\n" +
                "              <ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" />\n" +
                "              <ds:DigestValue>000</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "          </ds:SignedInfo>\n" +
                "          <ds:SignatureValue>000</ds:SignatureValue>\n" +
                "          <ds:KeyInfo>\n" +
                "            <ds:X509Data>\n" +
                "              <ds:X509Certificate>000</ds:X509Certificate>\n" +
                "            </ds:X509Data>\n" +
                "          </ds:KeyInfo>\n" +
                "        </ds:Signature>\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:AckRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //    String prepared=inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.Ack("e1a4c9f0-7eba-11e8-83c9-fa163e24a723"));
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void testDirectInject(){
        String input = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:AckRequest>\n" +
                "      <ns2:AckTargetMessage xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\" accepted=\"true\">7a5f52bb-6732-44a3-a49c-5e1855b215e9</ns2:AckTargetMessage>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CALLER\">\n" +
                "              <ds:Transforms>\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" />\n" +
                "              </ds:Transforms>\n" +
                "              <ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" />\n" +
                "              <ds:DigestValue>000</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "          </ds:SignedInfo>\n" +
                "          <ds:SignatureValue>000</ds:SignatureValue>\n" +
                "          <ds:KeyInfo>\n" +
                "            <ds:X509Data>\n" +
                "              <ds:X509Certificate>000</ds:X509Certificate>\n" +
                "            </ds:X509Data>\n" +
                "          </ds:KeyInfo>\n" +
                "        </ds:Signature>\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:AckRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String etalon = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:AckRequest>\n" +
                "      <ns2:AckTargetMessage xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\" accepted=\"true\">15</ns2:AckTargetMessage>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:Signature>\n" +
                "          <ds:SignedInfo>\n" +
                "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\" />\n" +
                "            <ds:Reference URI=\"#SIGNED_BY_CALLER\">\n" +
                "              <ds:Transforms>\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\" />\n" +
                "                <ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\" />\n" +
                "              </ds:Transforms>\n" +
                "              <ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\" />\n" +
                "              <ds:DigestValue>000</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "          </ds:SignedInfo>\n" +
                "          <ds:SignatureValue>000</ds:SignatureValue>\n" +
                "          <ds:KeyInfo>\n" +
                "            <ds:X509Data>\n" +
                "              <ds:X509Certificate>000</ds:X509Certificate>\n" +
                "            </ds:X509Data>\n" +
                "          </ds:KeyInfo>\n" +
                "        </ds:Signature>\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:AckRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String result = deps.inj.injectTagDirect(input, "ns2:AckTargetMessage","15" );
        assertEquals(etalon, result);
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
        //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;
    };


    String getrespreqegr() throws Exception {
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
        //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.egr.setinput(prepared.getBytes());
        assertNotEquals(null, deps.egr.GetSoap());
        String response = new String(deps.egr.GetResponseRequestwoFilter());
        return response;
    };

    @Test
    public void aacksuccesfull() throws Exception {
        assertNotEquals(null, deps.gis.Ack("b20076c0-7ec2-11e8-a63c-fa163e627e23"));
    }

    @Test
    public void ackmanual() throws Exception {


            deps.gis.Ack("14c31a7a-89b0-11e8-9de7-3b7d607c2faf");

    }


    @Test
    public void flushSMEV3() throws Exception {
       String result = getrespreq();
       while (result.indexOf("[Успешный прием необходимой для уплаты информации (начисления)")>0){
           FileWriter wr = new FileWriter("xml4test/temp.xml");
           wr.write(result);
           wr.close();
           String id=deps.ext.extractRaw("xml4test/temp.xml", "ns2:MessageID");
           System.out.println("Extract id="+ id);
           deps.gis.Ack(id);
           result = getrespreq();
       }
    }


    String getrespreqwoTest() throws Exception {
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
        //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;
    };
    @Test
    public void flushSMEV3_woTest() throws Exception {
        String result = getrespreqwoTest();
        while (result.indexOf(":MessageID")>0){
            String id=deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id="+ originalid);
            deps.gis.Ack(id);
            result = getrespreq();
        }
    }



 //   @Test
    public void flushSMEV3_tofirstanswer() throws Exception {
        String result = getrespreq();
        while (true){
            String id=deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id="+ originalid);
            if (id != null) {
                deps.gis.Ack(id);
                return;
            }
            result = getrespreq();
         //   Thread.sleep(0);
        }
    }



   //@Test
    public void flushSMEV3_always() throws Exception {
        String result = getrespreq();
        while (true){
            String id=deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id="+ originalid);
            if (id != null)
                deps.gis.Ack(id);
            result = getrespreq();
            Thread.sleep(1000);
        }
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
    public void flushSMEV3_egr() throws Exception {
        String result = getrespreqegr();
        while (result.indexOf(":MessageID")>0){
            String id=deps.ext.extractTagValue(result, ":MessageID");
            //   System.out.println("Extract id="+ id);
            String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
            System.out.println("Original id="+ originalid);
            deps.gis.Ack(id);
            result = getrespreqegr();
        }
    }


   // @Test
    public void flushall() throws Exception {
        String result = getrespreq();
        FileWriter wr1 = new FileWriter("xml4test/temp2.xml");
        wr1.write(result);
        wr1.close();
        while (deps.ext.extractRaw("xml4test/temp2.xml", ":OriginalMessageId")!=""){
            FileWriter wr = new FileWriter("xml4test/temp.xml");
            wr.write(result);
            wr.close();
            String id=deps.ext.extractRaw("xml4test/temp.xml", ":MessageID");
            System.out.println("Extract id="+ id);
            deps.gis.Ack(id);
            result = getrespreq();
        }
    }


    void decorate(){
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
        System.out.println("*************************************************************************");
    }

    @Test
    public void mg42() throws Exception {
        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>1f7386ac-810e-11e8-ad82-8dc4b4c63943</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" amount=\"3500\" kbk=\"73900000000000012130\" oktmo=\"12620000\" paymentDate=\"2018-07-04\" paymentId=\"10412037291501520407201819705872\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" supplierBillID=\"0\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" correspondentBankAccount=\"30101810700000000729\" name=\"АО ВКАБАНК\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee inn=\"3004003907\" kpp=\"302501001\" name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex paytReason=\"0\" status=\"24\" taxDocDate=\"0\" taxDocNumber=\"01;1211381133\" taxPeriod=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        deps.gis.setinput(data.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        assertNotEquals(null, deps.gis.SignedSoap());
        String response = new String(deps.gis.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void sendwrongInitialRequestRequestGis() throws Exception {
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f6a09006-689a-11e8-8058-012ae3068000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-05T08:56:33.1249556+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_0CA4A062-EC7E-4466-8C07-4F7620274E4D\" paymentId=\"10471020010005232407201700055501\" purpose=\"Дополнительная классификация . Плата по дополнительному образованию детей (Лабковская Анастасия)\" kbk=\"73900000000000012130\" oktmo=\"12620000\" supplierBillID=\"0\" amount=\"3500\" paymentDate=\"2018-07-04\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001211381133\"/><org:Payee name=\"УФК по Астраханской области (МБОУ &quot;Восточнинская ООШ&quot; л/с 20256Ц64450)\" inn=\"3004003907\" kpp=\"302501001\"><com:OrgAccount accountNumber=\"40701810600001000001\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1211381133\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-07-04\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Лабковская Елена Олеговна, Икрянинский р-н, с. Икряное, ул. 70 лет октября, д.1Б,</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String prepared = deps.inj.flushTagData(data, "ns:PersonalSignature");
        String prepared2 = deps.inj.flushTagData(prepared, "ns:PersonalSignature");
     //   String prepared3 = deps.inj.injectAttribute(prepared2, "paymentId", "10471020010005232407201700000001");
        deps.gis.setinput(prepared2.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        assertNotEquals(null, deps.gis.SignedSoap());
        String response = new String(deps.gis.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void testCheck() throws IOException {
        String data="<><><>";
        assertEquals(false, deps.gis.check(data.getBytes()));
        assertEquals(false, deps.gis.check(null));
        String prepared = "\uFEFF<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soapenv:Header />\n" +
                "  <soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "    <ns:AckRequest>\n" +
                "      <ns2:AckTargetMessage xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CALLER\" accepted=\"true\">e1a4c9f0-7eba-11e8-83c9-fa163e24a723</ns2:AckTargetMessage>\n" +
                "      <ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "      </ns4:CallerInformationSystemSignature>\n" +
                "    </ns:AckRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String fine="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-08-02T08:45:18.8228099+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_F1C7A365-E659-4D6D-8251-C7F96C490789\" paymentId=\"10412037291011180108201819727139\" purpose=\"Арендная плата по договору аренды имущества N 12378 от 19.08.2011 г. за август 2018г.\" kbk=\"70611105034040001120\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"142527\" paymentDate=\"2018-08-01\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001202496955\"/><org:Payee name=\"УФК по Астраханской области (Управление муниципального имущества администрации муниципального образования &quot;Город Астрахань&quot;, л/с 04253393400)\" inn=\"3015090933\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1202496955\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-08-01\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Савченко Наталья Васильевна, г. Астрахань, ул. Сен-симона, д.5, кв.6</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        String not_fine="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUME_R\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-08-02T08:45:18.8228099+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_F1C7A365-E659-4D6D-8251-C7F96C490789\" paymentId=\"10412037291011180108201819727139\" purpose=\"Арендная плата по договору аренды имущества N 12378 от 19.08.2011 г. за август 2018г.\" kbk=\"70611105034040001120\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"142527\" paymentDate=\"2018-08-01\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001202496955\"/><org:Payee name=\"УФК по Астраханской области (Управление муниципального имущества администрации муниципального образования &quot;Город Астрахань&quot;, л/с 04253393400)\" inn=\"3015090933\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1202496955\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-08-01\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Савченко Наталья Васильевна, г. Астрахань, ул. Сен-симона, д.5, кв.6</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";

        assertEquals(true, deps.gis.check(fine.getBytes()));

        assertEquals(false, deps.gis.check(not_fine.getBytes()));

        assertEquals(false, deps.gis.check(prepared.getBytes()));

        String fine2="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-08-02T08:45:18.8228099+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_F1C7A365-E659-4D6D-8251-C7F96C490789\" paymentId=\"10412037291011180108201819727139\" purpose=\"Арендная плата по договору аренды имущества N 12378 от 19.08.2011 г. за август 2018г.\" kbk=\"70611105034040001120\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"142527\" paymentDate=\"2018-08-01\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001202496955\"/><org:Payee name=\"УФК по Астраханской области (Управление муниципального имущества администрации муниципального образования &quot;Город Астрахань&quot;, л/с 04253393400)\" inn=\"3015090933\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1202496955\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-08-01\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Савченко Наталья Васильевна, г. Астрахань, ул. Сен-симона, д.5, кв.6</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(true, deps.gis.check(fine2.getBytes()));

        String notfine="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATU___RE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-08-02T08:45:18.8228099+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_F1C7A365-E659-4D6D-8251-C7F96C490789\" paymentId=\"10412037291011180108201819727139\" purpose=\"Арендная плата по договору аренды имущества N 12378 от 19.08.2011 г. за август 2018г.\" kbk=\"70611105034040001120\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"142527\" paymentDate=\"2018-08-01\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001202496955\"/><org:Payee name=\"УФК по Астраханской области (Управление муниципального имущества администрации муниципального образования &quot;Город Астрахань&quot;, л/с 04253393400)\" inn=\"3015090933\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1202496955\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-08-01\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Савченко Наталья Васильевна, г. Астрахань, ул. Сен-симона, д.5, кв.6</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(false, deps.gis.check(notfine.getBytes()));


    }

    @Test
    public void testCheck2() throws IOException {
        String notfine="<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>000</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" Id=\"PERSONAL_SIGNATU___RE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-08-02T08:45:18.8228099+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_F1C7A365-E659-4D6D-8251-C7F96C490789\" paymentId=\"10412037291011180108201819727139\" purpose=\"Арендная плата по договору аренды имущества N 12378 от 19.08.2011 г. за август 2018г.\" kbk=\"70611105034040001120\" oktmo=\"12701000\" supplierBillID=\"0\" amount=\"142527\" paymentDate=\"2018-08-01\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" name=\"АО ВКАБАНК\" correspondentBankAccount=\"30101810700000000729\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"1010000000001202496955\"/><org:Payee name=\"УФК по Астраханской области (Управление муниципального имущества администрации муниципального образования &quot;Город Астрахань&quot;, л/с 04253393400)\" inn=\"3015090933\" kpp=\"301501001\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex status=\"24\" paytReason=\"0\" taxPeriod=\"0\" taxDocNumber=\"01;1202496955\" taxDocDate=\"0\"/><pmnt:AccDoc accDocDate=\"2018-08-01\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Плательщик</com:Name><com:Value>Савченко Наталья Васильевна, г. Астрахань, ул. Сен-симона, д.5, кв.6</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>000</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>000</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>000</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";
        assertEquals(false, deps.gis.check(notfine.getBytes()));
    }

    @Test
    public void signRaw() throws IOException, TransformerException, CertificateException, NoSuchAlgorithmException, ParserConfigurationException, UnrecoverableEntryException, XMLSecurityException, NoSuchProviderException, SignatureProcessorException, KeyStoreException, ClassNotFoundException, SAXException {
        byte[] normalized1 =(Files.readAllBytes(new File("xml4test/check/x277.xml").toPath()));
        byte[] normalized2 =(Files.readAllBytes(new File("xml4test/check/x276.xml").toPath()));


        System.out.println(new String(normalized1));
        deps.gis.setinput(normalized1);

        byte[] signed =deps.gis.SignedSoap();
        assertTrue(deps.gis.check(signed));
        BinaryMessage.write(signed, "xml4test/check/x277signed.xml");

        deps.gis.setinput(normalized2);
        signed =deps.gis.SignedSoap();
        assertTrue(deps.gis.check(signed));
        BinaryMessage.write(signed, "xml4test/check/x276signed.xml");
    }





    @Test
    public void testExternalFlag(){
    }





   // @Test
    public void test10() throws Exception {
        for (int i = 0; i< 20; i++){
            ThreadL thread = new ThreadL();
            thread.start();
        }
        while (true){}
    }




    class ThreadL extends Thread{
        public void run(){
            try {
                cycleraw();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

