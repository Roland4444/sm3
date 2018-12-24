package standart;
import Message.abstractions.BinaryMessage;
import Message.toSMEV.ESIAFind.ESIAFindMessageInitial;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;
import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
public class findesiaTest {
    DependencyContainer deps = new DependencyContainer();

    public findesiaTest() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException {
        msg.ID="";
        msg.Ra="1000321282";
        msg.Surname="Тестов";
        msg.Name="Тест";
        msg.MiddleName="Тестович";
        msg.OperatorSnils="123456789";
        msg.Passseria="1111";
        msg.Passnumber="111111";
        deps.findesia.ProdModeRoutingEnabled=false;
    }
    ESIAFindMessageInitial msg = new ESIAFindMessageInitial();

    @Test
    public void generateSOAP() {
        ESIAFindMessageInitial msg = new ESIAFindMessageInitial();
        msg.ID="";
        msg.Ra="001";
        msg.Surname="Forgerman";
        msg.Name="Roman";
        msg.MiddleName="Otto";
        msg.OperatorSnils="123456789";
        msg.Passseria="1210";
        msg.Passnumber="355555555";

        //deps.findesia.set
        assertNotEquals(null, deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
        System.out.println(deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
    }

    @Test
    public void sendSimple() throws Exception {

        deps.findesia.setinput(deps.findesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg)));
        assertNotEquals(null, deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
        System.out.println(deps.findesia.injectdatainXML(msg, deps.findesia.rawxml));
        deps.findesia.SendSoapSigned();

    }

    @Test
    public void generateSOAP1() throws IOException {
        String etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "   \t<tns:RoutingCode></tns:RoutingCode>\n" +
                "  \t<tns:SnilsOperator></tns:SnilsOperator>\n" +
                "    <tns:ra></tns:ra>\n" +
                "    <tns:lastName></tns:lastName>\n" +
                "    <tns:firstName></tns:firstName>\n" +
                "    <tns:middleName></tns:middleName>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series></ns2:series>\n" +
                "        <ns2:number></ns2:number>\n" +
                "    </tns:doc>\n" +
                "<tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "<tns:snils>229-785-346 20</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n</ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println(deps.findesia.emptySOAP);
        System.out.println(deps.findesia.root);
        System.out.println(deps.findesia.rawxml);

        assertEquals(etalon, deps.findesia.rawxml);
    //    assertNotEquals(etalon, deps.findesia.buildSOAPunsigned(msg));
     //   System.out.println(deps.findesia.buildSOAPunsigned(msg));

    }

    @Test
    public void generateUnsSOAP() throws IOException {

        ESIAFindMessageInitial msg0 = new ESIAFindMessageInitial();
        msg0.OperatorSnils="135-419-238 52";
        msg0.MobileNumber="+7(920)4021351";
        msg0.Ra="1000321282";
        msg0.Name="Тест";
        msg0.MiddleName="Тестович";
        msg0.Surname="Тестов";
        msg0.Passseria="1111";
        msg0.Passnumber="111111";
        msg0.SNILS="229-785-346 20";
        String etalon = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID></ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "   \t<tns:RoutingCode>DEV</tns:RoutingCode>\n" +
                "  \t<tns:SnilsOperator>135-419-238 52</tns:SnilsOperator>\n" +
                "    <tns:ra>1000321282</tns:ra>\n" +
                "    <tns:lastName>Тестов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Тестович</tns:middleName>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>1111</ns2:series>\n" +
                "        <ns2:number>111111</ns2:number>\n" +
                "    </tns:doc>\n" +
                "<tns:mobile>+7(920)4021351</tns:mobile>\n" +
                "<tns:snils>229-785-346 20</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n</ns2:MessagePrimaryContent><ns:TestMessage/></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println(deps.findesia.emptySOAP);
        System.out.println(deps.findesia.root);
        System.out.println(deps.findesia.rawxml);
        String result = new String(deps.findesia.generateUnsSOAP(BinaryMessage.savedToBLOB(msg0)));
        String flushed = deps.inj.flushTagData(result, "ns:MessageID");
        assertEquals(etalon, flushed);
        System.out.println(flushed);
    }
}