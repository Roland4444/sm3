package util;

import crypto.Gost3411Hash;
import org.apache.xml.security.transforms.TransformationException;
import org.junit.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class transform35Test {
    @Test
    public void process4test35step1() throws IOException, TransformationException {
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
                "\t<qwe:elementTwo xmlns:qwe=\"http://test/2\">asd</qwe:elementTwo> \n" +
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
    public void process4test35step2() throws IOException, TransformationException {
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
                "\t\t\t<elementFour> z x c </elementFour> \n" +
                "\t\t\t<!-- Тестирование ситуации, когда для одного namespace объявляется несколько префиксов во вложенных элементах. -->\n" +
                "\t\t\t<qqq:elementFive xmlns:qqq=\"http://test/2\"> w w w </qqq:elementFive>\n" +
                "\t\t</asd:elementThree>\n" +
                "\t\t<!-- Ситуация, когда prefix был объявлен выше, чем должно быть в нормальной форме, \n" +
                "\t\tпри нормализации переносится ниже, и это приводит к генерации нескольких префиксов \n" +
                "\t\tдля одного namespace в sibling элементах. -->\n" +
                "\t\t<asd:elementSix>eee</asd:elementSix>\n" +
                "\t</qwe:elementTwo> \n" +
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
        String etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\"><ns1:elementFour> z x c </ns1:elementFour><ns2:elementFive> w w w </ns2:elementFive></ns3:elementThree><ns4:elementSix xmlns:ns4=\"http://test/3\">eee</ns4:elementSix></ns2:elementTwo></ns1:elementOne>";
        assertEquals(etalon, input);
    }

    @Test
    public void process4test35step3() throws IOException, TransformationException {
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
                "\t</qwe:elementTwo> \n" +
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
    public void process4test35habr() throws IOException, TransformationException, NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
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
        System.out.println(input);
        assertEquals("/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=", hash.h_Base64rfc2045(input));
    }


    @Test
    public void process4test35mtom() throws IOException, TransformationException, NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        org.apache.xml.security.Init.init();
        SmevTransformSpi test =  new SmevTransformSpi();
        FileWriter wr = new FileWriter("xml4test/rawxml.xml");
        String write = "<ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>76796044-5e83-11e4-a9ff-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:PersonalSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#PERSONAL_SIGNATURE\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>AUGmtYkL7MD5MqwxwcQULupXi6FtvCrsHED26tKroC0=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>PFtijkGkwB1rSG3q9YSiZwrtpMk2PV53kZfAqDJr/Z29plOShhDwuGTavcrnORMkAspDsYwqQM69hHnOJeqYiQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhjCCATWgAwIBAgIELVa5zjAIBgYqhQMCAgMwLTEQMA4GA1UECxMHU1lTVEVNMTEMMAoGA1UEChMDT1YzMQswCQYDVQQGEwJSVTAeFw0xNDAyMjYwODQ1MTdaFw0xNTAyMjYwODQ1MTdaMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMzELMAkGA1UEBhMCUlUwYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARAvEqmzwoBlO2KHcBQND5WplUT9eBWXoQCrxuzlPQXCE+WKdNRVMkm4TivyPLgLTwgjxMcUnHSIZSeWL8LB97QPqM7MDkwDgYDVR0PAQH/BAQDAgPoMBMGA1UdJQQMMAoGCCsGAQUFBwMCMBIGA1UdEwEB/wQIMAYBAf8CAQUwCAYGKoUDAgIDA0EA1u5TsTfom9Y0levJ0AeFtZT0lqEQUHL2PCKXmygvV1ZKJ1mBBLxVgCtIlii/Hql0SGAVwySDtZX7M20P3gjRgA==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns:PersonalSignature></ns:SenderProvidedRequestData>";
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

        assertEquals("oQj0a7j/Bw3XHLBhZ6CN0Dd6cNjP4DzyYnF8FwPsOo4=", hash.h_Base64rfc2045(input));
    }

}