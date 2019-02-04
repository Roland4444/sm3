package util;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import org.xml.sax.SAXException;
import ru.CryptoPro.JCPxml.Consts;
import schedulling.abstractions.DependencyContainer;
import util.crypto.Sign2018;
import util.crypto.TestSign2019;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SignerXMLTest {
    TestSign2019 ts = new TestSign2019();
    SignerXML signer2001 = new SignerXML(new Sign2018(), new Sign2018());
    SignerXML XMLSigner = new SignerXML(ts, ts);
    DependencyContainer useExternal = new DependencyContainer(XMLSigner);

    public SignerXMLTest() throws SignatureProcessorException, InvalidTransformException, ClassNotFoundException, IOException, SQLException, AlgorithmAlreadyRegisteredException {
    }

    @Test
    public void constructorTest() {
        assertEquals(Consts.URN_GOST_SIGN_2012_256, ts.SIGNATURE_LINK);
        assertEquals(Consts.URN_GOST_DIGEST_2012_256, ts.DIGEST_LINK);
    }

    @Test
    public void DepesWithexternalTest(){

    }

    @Test
    public void check() throws XMLSecurityException, ParserConfigurationException, SAXException, IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>8f255611-0a9e-11e9-a872-c3d772a48b09</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" amount=\"13607800\" kbk=\"18210102010011000110\" oktmo=\"12701000\" paymentDate=\"2018-07-02\" paymentId=\"10471020010005232407201700000001\" purpose=\"НДФЛ за июль 2018г.\" supplierBillID=\"0\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" correspondentBankAccount=\"30101810700000000729\" name=\"АО ВКАБАНК\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"2003015011755301501001\" payerName=\"АО ВКАБАНК\"/><org:Payee inn=\"3015026737\" kpp=\"301501001\" name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex paytReason=\"ТП\" status=\"02\" taxDocDate=\"0\" taxDocNumber=\"0\" taxPeriod=\"МС.07.2018\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>sPMkpxBYJSUg9xNJdLWkiL1DG2VwH1iHr6AKY+CRjwk=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>BcZ7FEBWslSybj3Fmd9+w0d6BgPCTc670pA1mQqLdUIpFY4yO9g3+SRjYdx/wlK/J0DhhNhR+SENX6QMDKZvnA==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>wNdBGuJGKavyXZQLmd79MbCgzQKMsaeB5uoGHkZp1NQ=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>tJN1JzHMX8TKN8UO/k5y3r1nzRaSSOy7plhKWSX9qHZjOPgeM22M3zwND5klhqWJA6B7L2DvpviTFZl37Wxkow==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        assertTrue(signer2001.check(xml.getBytes()));
    }

    @Test
    public void check2012() throws XMLSecurityException, ParserConfigurationException, SAXException, IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>f4ca74cd-0a9e-11e9-a243-3ba4dd58bf8f</ns:MessageID><ns2:MessagePrimaryContent><req:ImportPaymentsRequest xmlns:req=\"urn://roskazna.ru/gisgmp/xsd/services/import-payments/2.0.1\" xmlns:bdi=\"http://roskazna.ru/gisgmp/xsd/BudgetIndex/2.0.1\" xmlns:chg=\"http://roskazna.ru/gisgmp/xsd/Charge/2.0.1\" xmlns:com=\"http://roskazna.ru/gisgmp/xsd/Common/2.0.1\" xmlns:org=\"http://roskazna.ru/gisgmp/xsd/Organization/2.0.1\" xmlns:pkg=\"http://roskazna.ru/gisgmp/xsd/Package/2.0.1\" xmlns:pmnt=\"http://roskazna.ru/gisgmp/xsd/Payment/2.0.1\" xmlns:rfd=\"http://roskazna.ru/gisgmp/xsd/Refund/2.0.1\" Id=\"PERSONAL_SIGNATURE\" senderIdentifier=\"3637c9\" senderRole=\"9\" timestamp=\"2018-07-03T08:46:57.7225950+04:00\"><pkg:PaymentsPackage><pkg:ImportedPayment Id=\"I_E345B70D-3650-4A52-9B36-3A2F2E3ED332\" amount=\"13607800\" kbk=\"18210102010011000110\" oktmo=\"12701000\" paymentDate=\"2018-07-02\" paymentId=\"10471020010005232407201700000001\" purpose=\"НДФЛ за июль 2018г.\" supplierBillID=\"0\" transKind=\"01\"><pmnt:PaymentOrg><org:Bank bik=\"041203729\" correspondentBankAccount=\"30101810700000000729\" name=\"АО ВКАБАНК\"/></pmnt:PaymentOrg><pmnt:Payer payerIdentifier=\"2003015011755301501001\" payerName=\"АО ВКАБАНК\"/><org:Payee inn=\"3015026737\" kpp=\"301501001\" name=\"Управление федерального казначейства по Астраханской области(ИФНС по Кировскому району)\"><com:OrgAccount accountNumber=\"40101810400000010009\"><com:Bank bik=\"041203001\"/></com:OrgAccount></org:Payee><pmnt:BudgetIndex paytReason=\"ТП\" status=\"02\" taxDocDate=\"0\" taxDocNumber=\"0\" taxPeriod=\"МС.07.2018\"/><pmnt:AccDoc accDocDate=\"2018-07-02\"/><com:ChangeStatus meaning=\"1\"/><com:AdditionalData><com:Name>Наименование банка получателя</com:Name><com:Value>ОТДЕЛЕНИЕ АСТРАХАНЬ г. Астрахань</com:Value></com:AdditionalData></pkg:ImportedPayment></pkg:PaymentsPackage></req:ImportPaymentsRequest></ns2:MessagePrimaryContent><ns:PersonalSignature><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34102012-gostr34112012-256\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34112012-256\"/><ds:DigestValue>ikSeAK2FLNidmwYPAOyk+4UQ2bP107HcKJ1gW2JqjV8=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>BfqkaD6Cb9Glc2wkBKEXNX3eKcQnUqmin1kvgnjjvmJhIHwFZDx9XZiShcfFC0kUwphnqWTUHg/n+oGK0Wm3Zw==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34102012-gostr34112012-256\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34112012-256\"/><ds:DigestValue>A470CUIKvon57BKFJ6Fe3BTJ3qZhKIGwws+gjaNPybQ=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>fhiKUAJsd3Hmy35AhGOa5pcweSyGfMnoOJTYcBL0dTbEW11NHUIlUG0etyuBMSXK5UUcYDVrLalx85Jt1eVGog==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        assertTrue(XMLSigner.check(xml.getBytes()));
    }


}