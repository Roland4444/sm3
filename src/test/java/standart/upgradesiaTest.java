package standart;

import Message.abstractions.BinaryMessage;
import Message.abstractions.Gender;
import Message.toSMEV.ESIAUpgrade.ESIAUpgradeInitial;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;
import util.SignerXML;
import util.crypto.Sign2018;
import util.crypto.TestSign2001;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class upgradesiaTest {
    DependencyContainer deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()));
    ESIAUpgradeInitial msg = new ESIAUpgradeInitial();

    public upgradesiaTest() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException {
        deps.upgradesia.ProdModeRoutingEnabled=false;
        msg.ID="0000";
        msg.OID="1000350086";
        msg.Birthdate="11.11.1988";
        msg.SNILSOper="135-419-238 52";
        msg.RA="1000321282";
        msg.SNILS="229-785-346 20";
        msg.Name="Тест";
        msg.Surname="Тестов";
        msg.MiddleName="Тестович";
        msg.Gender= Gender.M;
        msg.PassSeria="1111";
        msg.PassNumber="111111";
        msg.IssuedPassID="111111";
        msg.IssuedBy="выдан";
        msg.IssuedDatePass="01.10.2017";
        msg.Mobile="+7(920)4021351";
        msg.Region="23";
        msg.FIAS="720b25da-f43e-4204-9013-3cb06be3e9e4";
        msg.AddressStr="Кемеровская Область, Таштагольский Район, Шерегеш Поселок городского типа";
        msg.ZIP="394000";
        msg.Street="Советская Улица";
        msg.House="86/1";
        msg.Flat="пом.419";
        msg.Frame="204у";
        msg.Building="e";
        msg.BirthPlace="воронеж";
    }

    @Test
    public void raxxmlnotnull(){
        assertNotEquals(null, deps.findesia.rawxml);
    };

    @Test
    public void generateUnsSOAP() throws IOException {
        String Etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent><tns:ESIARegisterBySimplifiedRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/register_by_simplified/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "    <tns:RoutingCode>DEV</tns:RoutingCode>\n" +
                "\t<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
                "    <tns:oid>1000350086</tns:oid>\n" +
                "    <tns:ra>1000321282</tns:ra>\n" +
                "    <tns:snils>229-785-346 20</tns:snils>\n" +
                "    <tns:lastName>Тестов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Тестович</tns:middleName>\n" +
                "    <tns:gender>M</tns:gender>\n" +
                "    <tns:birthDate>11.11.1988</tns:birthDate>\n" +
                "    <tns:birthPlace>воронеж</tns:birthPlace>\n" +
                "    <tns:citizenship>RUS</tns:citizenship>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>1111</ns2:series>\n" +
                "        <ns2:number>111111</ns2:number>\n" +
                "        <ns2:issueId>111111</ns2:issueId>\n" +
                "        <ns2:issueDate>01.10.2017</ns2:issueDate>\n" +
                "        <ns2:issuedBy>выдан</ns2:issuedBy>\n" +
                "    </tns:doc>\n" +
                "    <tns:addressRegistration>\n" +
                "        <ns2:type>PLV</ns2:type>\n" +
                "        <ns2:region>23</ns2:region>\n" +
                "        <ns2:fiasCode>720b25da-f43e-4204-9013-3cb06be3e9e4</ns2:fiasCode>\n" +
                "        <ns2:addressStr>Кемеровская Область, Таштагольский Район, Шерегеш Поселок городского типа</ns2:addressStr>\n" +
                "        <ns2:countryId>RUS</ns2:countryId>\n" +
                "        <ns2:zipCode>394000</ns2:zipCode>\n" +
                "        <ns2:street>Советская Улица</ns2:street>\n" +
                "        <ns2:house>86/1</ns2:house>\n" +
                "        <ns2:flat>пом.419</ns2:flat>\n" +
                "        <ns2:frame>204у</ns2:frame>\n" +
                "        <ns2:building>e</ns2:building>\n" +
                "    </tns:addressRegistration>\n" +
                "    <tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "    <tns:mode>mobile</tns:mode>\n" +
                "</tns:ESIARegisterBySimplifiedRequest></ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println("EMPTY=>"+deps.upgradesia.emptySOAP);
        System.out.println("ROOT=>"+deps.upgradesia.root);
        System.out.println("RAW=>"+deps.upgradesia.rawxml);
        assertNotEquals(null, deps.upgradesia.inj);
        String result = new String(deps.upgradesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg)));
        String flushed = deps.inj.flushTagData(result, "ns:MessageID");
        assertEquals(Etalon, flushed);
        System.out.println(flushed);
    }

    @Test
    public void sendSimple() throws Exception {

        deps.upgradesia.setinput(deps.upgradesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg)));
        assertNotEquals(null, deps.upgradesia.injectdatainXML(msg, deps.upgradesia.rawxml));
        System.out.println(deps.upgradesia.injectdatainXML(msg, deps.upgradesia.rawxml));
        deps.upgradesia.SendSoapSigned();

    }
}