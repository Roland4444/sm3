package util;

import crypto.Gost3411Hash;
import org.apache.xml.security.transforms.TransformationException;
import org.junit.Test;
import se.roland.Extractor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class SmevTransformSpiTest {

    @Test
    public void tarnsformtoSign() throws TransformationException, FileNotFoundException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        InputStream in = new FileInputStream("xml4test/SendRequestRequestNoAttach.xml");
        OutputStream out = new FileOutputStream("xml4test/TRANS.xml");
        test.process(in, out);

    }

    @Test
    public void tarnsformHighligthedData() throws TransformationException, FileNotFoundException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        InputStream in = new FileInputStream("xml4test/1.xml");
        OutputStream out = new FileOutputStream("xml4test/TRANS.xml");
        test.process(in, out);
    }

    @Test
    public void processini() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<ns2:SenderProvidedRequestData xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                " <MessagePrimaryContent xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\">\n" +
                "  <SomeRequest:SomeRequest xmlns:SomeRequest=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">\n" +
                "   <x xmlns=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">qweqwe</x>\n" +
                "  </SomeRequest:SomeRequest>\n" +
                " </MessagePrimaryContent>\n" +
                "</ns2:SenderProvidedRequestData>";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\"><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\"><ns3:SomeRequest xmlns:ns3=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\"><ns3:x>qweqwe</ns3:x></ns3:SomeRequest></ns2:MessagePrimaryContent></ns1:SenderProvidedRequestData>";
        assertEquals(etalon, input);
    }

    @Test
    public void process() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- Тестирование правил 1, 2, 6: \n" +
                "\t- XML declaration выше, этот комментарий, и следующая за ним processing instruction должны быть вырезаны;\n" +
                "\t- Переводы строки должны быть удалены;\n" +
                "\t- Namespace prefixes заменяются на автоматически сгенерированные.\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\">\n" +
                "\t<qwe:elementTwo xmlns:qwe=\"http://test/2\">asd</qwe:elementTwo>  \n" +
                "</elementOne> ";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\">asd</ns2:elementTwo></ns1:elementOne>";
        assertEquals(etalon, input);


    }


    @Test
    public void process2() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- Тестирование правил 1, 2, 6: \n" +
                "\t- XML declaration выше, этот комментарий, и следующая за ним processing instruction должны быть вырезаны;\n" +
                "\t- Переводы строки должны быть удалены;\n" +
                "\t- Namespace prefixes заменяются на автоматически сгенерированные.\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\">\n" +
                "\t<qwe:elementTwo xmlns:qwe=\"http://test/2\">asd</qwe:elementTwo>  \n" +
                "</elementOne>";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\">asd</ns2:elementTwo></ns1:elementOne>";
        assertEquals(etalon, input);


    }


    @Test
    public void process3() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- \n" +
                "\tВсё то же, что в test case 1, плюс правила 4 и 5:\n" +
                "\t- Удалить namespace prefix, которые на текущем уровне объявляются, но не используются.\n" +
                "\t- Проверить, что namespace текущего элемента объявлен либо выше по дереву, либо в текущем элементе. Если не объявлен, объявить в текущем элементе\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\" xmlns:qwe=\"http://test/2\" xmlns:asd=\"http://test/3\">\n" +
                "\t<qwe:elementTwo>\n" +
                "\t\t<asd:elementThree>\n" +
                "\t\t\t<!-- Проверка обработки default namespace. -->\n" +
                "\t\t\t<elementFour> z x c </elementFour>     \n" +
                "\t\t\t<!-- Тестирование ситуации, когда для одного namespace объявляется несколько префиксов во вложенных элементах. -->\n" +
                "\t\t\t<qqq:elementFive xmlns:qqq=\"http://test/2\"> w w w </qqq:elementFive>\n" +
                "\t\t</asd:elementThree>\n" +
                "\t\t<!-- Ситуация, когда prefix был объявлен выше, чем должно быть в нормальной форме, \n" +
                "\t\tпри нормализации переносится ниже, и это приводит к генерации нескольких префиксов  \n" +
                "\t\tдля одного namespace в sibling элементах. -->\n" +
                "\t\t<asd:elementSix>eee</asd:elementSix>\n" +
                "\t</qwe:elementTwo>  \n" +
                "</elementOne>  ";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\"><ns1:elementFour> z x c </ns1:elementFour><ns2:elementFive> w w w </ns2:elementFive></ns3:elementThree><ns4:elementSix xmlns:ns4=\"http://test/3\">eee</ns4:elementSix></ns2:elementTwo></ns1:elementOne>";
        assertEquals(etalon, input);

    }

    @Test
    public void process4() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- \n" +
                "\tВсё то же, что в test case 1, плюс правила 3, 7 и 8:\n" +
                "\t- Атрибуты должны быть отсортированы в алфавитном порядке: сначала по namespace URI (если атрибут - в qualified form), затем – по local name. \n" +
                "\t\tАтрибуты в unqualified form после сортировки идут после атрибутов в qualified form.\n" +
                "\t- Объявления namespace prefix должны находиться перед атрибутами. Объявления префиксов должны быть отсортированы в порядке объявления, а именно:\n" +
                "\t\ta.\tПервым объявляется префикс пространства имен элемента, если он не был объявлен выше по дереву.\n" +
                "\t\tb.\tДальше объявляются префиксы пространств имен атрибутов, если они требуются. \n" +
                "\t\t\tПорядок этих объявлений соответствует порядку атрибутов, отсортированных в алфавитном порядке (см. п.5).\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\" xmlns:qwe=\"http://test/2\" xmlns:asd=\"http://test/3\">\n" +
                "\t<qwe:elementTwo>\n" +
                "\t\t<asd:elementThree xmlns:wer=\"http://test/a\" xmlns:zxc=\"http://test/0\" wer:attZ=\"zzz\" attB=\"bbb\" attA=\"aaa\" zxc:attC=\"ccc\" asd:attD=\"ddd\" asd:attE=\"eee\" qwe:attF=\"fff\"/>\n" +
                "\t</qwe:elementTwo>  \n" +
                "</elementOne> ";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\" xmlns:ns4=\"http://test/0\" xmlns:ns5=\"http://test/a\" ns4:attC=\"ccc\" ns2:attF=\"fff\" ns3:attD=\"ddd\" ns3:attE=\"eee\" ns5:attZ=\"zzz\" attA=\"aaa\" attB=\"bbb\"></ns3:elementThree></ns2:elementTwo></ns1:elementOne>";
        assertEquals(etalon, input);

    }

    @Test
    public void process4testfromstupidGuide() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<ns2:SenderProvidedRequestData xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                " <MessagePrimaryContent xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\">\n" +
                "  <SomeRequest:SomeRequest xmlns:SomeRequest=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">\n" +
                "   <x xmlns=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">qweqwe</x>\n" +
                "  </SomeRequest:SomeRequest>\n" +
                " </MessagePrimaryContent>\n" +
                "</ns2:SenderProvidedRequestData>";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\"><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\"><ns3:SomeRequest xmlns:ns3=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\"><ns3:x>qweqwe</ns3:x></ns3:SomeRequest></ns2:MessagePrimaryContent></ns1:SenderProvidedRequestData>";
        assertEquals(etalon, input);

    }




    @Test
    public void process4test2fromsHabr() throws IOException, TransformationException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawxml.xml");
        OutputStream out = new FileOutputStream("xml4test/out.xml");
        test.process(in, out);
        File f = new File("xml4test/out.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals(etalon, input);
    }

    @Test
    public void process4testonlysenderfromsHabr() throws IOException, TransformationException, NoSuchAlgorithmException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawReqData.xml");
        String write = "<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawReqData.xml");
        OutputStream out = new FileOutputStream("xml4test/rawReqDataOUT.xml");
        test.process(in, out);
        File f = new File("xml4test/rawReqDataOUT.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals(etalon, input);
        Gost3411Hash hash = new Gost3411Hash();
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hash.h_Base64rfc2045(input));
    }

    @Test
    public void process4testonlysenderfromsHabr2() throws IOException, TransformationException, NoSuchAlgorithmException {
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawReqData.xml");
        String write = "<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>";
        wr.write(write);
        wr.close();
        InputStream in = new FileInputStream("xml4test/rawReqData.xml");
        OutputStream out = new FileOutputStream("xml4test/rawReqDataOUT.xml");
        test.process(in, out);
        File f = new File("xml4test/rawReqDataOUT.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals(etalon, input);
        Gost3411Hash hash = new Gost3411Hash();
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hash.h_Base64rfc2045(input));
    }


    @Test
    public void fullchaintestfromHabr() throws IOException, TransformationException, NoSuchAlgorithmException {
        org.apache.xml.security.Init.init();
        Extractor ext = new Extractor();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/full.xml");
        String write = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>J3746ks34pOcPGQpKzc0sz3n9+gjPtzZbSEEs4c3sTwbtfdaY7N/hxXzEIvXc+3ad9bc35Y8yBhZ/BYbloGt+Q==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBcDCCAR2gAwIBAgIEHVmVKDAKBgYqhQMCAgMFADAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNJUzIxCzAJBgNVBAYTAlJVMB4XDTE1MDUwNzEyMTUzMFoXDTE4MDUwNjEyMTUzMFowLTEQMA4GA1UECxMHU1lTVEVNMTEMMAoGA1UEChMDSVMyMQswCQYDVQQGEwJSVTBjMBwGBiqFAwICEzASBgcqhQMCAiMBBgcqhQMCAh4BA0MABEDoWGZlTUWD43G1N7TEm14+QyXrJWProrzoDoCJRem169q4bezFOUODcNooQJNg3PtAizkWeFcX4b93u8fpVy7RoyEwHzAdBgNVHQ4EFgQUaRG++MAcPZvK/E2vR1BBl5G7s5EwCgYGKoUDAgIDBQADQQCg25vA3RJL3kgcJhVOHA86vnkMAtZYr6HBPa7LpEo0HJrbBF0ygKk50app1lzPdZ5TtK2itfmNgTYiuQHX3+nE</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        wr.write(write);
        wr.close();
        FileWriter transformed = new FileWriter("xml4test/transformed.xml");
        transformed.write(ext.parse("xml4test/full.xml", "SenderProvidedRequestData"));
        transformed.close();
        assertEquals("<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>", ext.parse("xml4test/full.xml", "SenderProvidedRequestData"));
        InputStream in = new FileInputStream("xml4test/transformed.xml");
        OutputStream out = new FileOutputStream("xml4test/transformedReady.xml");
        test.process(in, out);
        File f = new File("xml4test/transformedReady.xml");
        BufferedReader b = new BufferedReader(new FileReader(f));
        String readLine = "";
        String input="";
        while ((readLine = b.readLine()) != null)
            input+=readLine;
        String etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals(etalon, input);
        Gost3411Hash hash = new Gost3411Hash();
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hash.h_Base64rfc2045(input));
    }

}