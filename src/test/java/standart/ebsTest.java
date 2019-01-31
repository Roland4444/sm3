package standart;

import Message.abstractions.BinaryMessage;
import Message.toSMEV.EBS.EBSMessage;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import org.xml.sax.SAXException;
import schedulling.Scheduller;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Sign;
import util.Injector;
import util.SignatureProcessorException;
import util.SignerXML;
import util.TransXML;
import util.crypto.Sign2018;
import util.crypto.TestSign2001;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
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


public class ebsTest {
    TransXML trans = new TransXML();
    EBSMessage msg;
    byte[] buff;
    String prepared2Tag ="<ns2:RefAttachmentHeader><ns2:uuid>872b8bb6-2550-11e9-9666-6f90870395fd</ns2:uuid><ns2:Hash>LwX76ZiKWn7FXljFxxL09Q1J+lCj9aXoWNFZDkuTZZU=</ns2:Hash><ns2:MimeType>audio/x-wav</ns2:MimeType><ns2:SignaturePKCS7>MIIJuwYJKoZIhvcNAQcCoIIJrDCCCagCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggfA\n" +
            "MIIHvDCCB2ugAwIBAgIRAXILAVZQABCz6BEejDlOj3AwCAYGKoUDAgIDMIIBRjEYMBYGBSqFA2QB\n" +
            "Eg0xMjM0NTY3ODkwMTIzMRowGAYIKoUDA4EDAQESDDAwMTIzNDU2Nzg5MDEpMCcGA1UECQwg0KHR\n" +
            "g9GJ0LXQstGB0LrQuNC5INCy0LDQuyDQtC4gMjYxFzAVBgkqhkiG9w0BCQEWCGNhQHJ0LnJ1MQsw\n" +
            "CQYDVQQGEwJSVTEYMBYGA1UECAwPNzcg0JzQvtGB0LrQstCwMRUwEwYDVQQHDAzQnNC+0YHQutCy\n" +
            "0LAxJDAiBgNVBAoMG9Ce0JDQniDQoNC+0YHRgtC10LvQtdC60L7QvDEwMC4GA1UECwwn0KPQtNC+\n" +
            "0YHRgtC+0LLQtdGA0Y/RjtGJ0LjQuSDRhtC10L3RgtGAMTQwMgYDVQQDDCvQotC10YHRgtC+0LLR\n" +
            "i9C5INCj0KYg0KDQotCaICjQoNCi0JvQsNCx0YEpMB4XDTE4MDcyMDEzMDE0MVoXDTE5MDcyMDEz\n" +
            "MTE0MVowgfAxHTAbBgkqhkiG9w0BCQIMDtCS0JrQkNCR0JDQndCaMRowGAYIKoUDA4EDAQESDDAw\n" +
            "MzAxNTAxMTc1NTEYMBYGBSqFA2QBEg0xMDIzMDAwMDAwMjEwMRwwGgYDVQQKDBPQkNCeINCS0JrQ\n" +
            "kNCR0JDQndCaMRswGQYDVQQHDBLQkNGB0YLRgNCw0YXQsNC90YwxMzAxBgNVBAgMKjMwINCQ0YHR\n" +
            "gtGA0LDRhdCw0L3RgdC60LDRjyDQvtCx0LvQsNGB0YLRjDELMAkGA1UEBhMCUlUxHDAaBgNVBAMM\n" +
            "E9CQ0J4g0JLQmtCQ0JHQkNCd0JowYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARA\n" +
            "qjtC1dM6zvtwmhJbUMVVOiC+8kbOOgufkJJFKHy5rMaFG6jWxUiGKvI8AAcEE7rP93ui2TMVzaDe\n" +
            "cGOrspIW6KOCBIMwggR/MA4GA1UdDwEB/wQEAwIE8DAdBgNVHQ4EFgQU541ASZ2wBv/db7s8wxlc\n" +
            "nshsQxAwggGIBgNVHSMEggF/MIIBe4AUPu8ZPw+5ebDx5ikho+S5lbml7pChggFOpIIBSjCCAUYx\n" +
            "GDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1Njc4OTAxKTAn\n" +
            "BgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZIhvcNAQkBFghj\n" +
            "YUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEVMBMGA1UEBwwM\n" +
            "0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+0LwxMDAuBgNV\n" +
            "BAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIGA1UEAwwr0KLQ\n" +
            "tdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKYIRAXILAVZQALmz5xHPOr40\n" +
            "d6AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMCcGCSsGAQQBgjcVCgQaMBgwCgYIKwYB\n" +
            "BQUHAwIwCgYIKwYBBQUHAwQwHQYDVR0gBBYwFDAIBgYqhQNkcQEwCAYGKoUDZHECMCsGA1UdEAQk\n" +
            "MCKADzIwMTgwNzIwMTMwMTQxWoEPMjAxOTA3MjAxMzAxNDFaMIIBNAYFKoUDZHAEggEpMIIBJQwr\n" +
            "ItCa0YDQuNC/0YLQvtCf0YDQviBDU1AiICjQstC10YDRgdC40Y8gMy45KQwsItCa0YDQuNC/0YLQ\n" +
            "vtCf0YDQviDQo9CmIiAo0LLQtdGA0YHQuNC4IDIuMCkMY9Ch0LXRgNGC0LjRhNC40LrQsNGCINGB\n" +
            "0L7QvtGC0LLQtdGC0YHRgtCy0LjRjyDQpNCh0JEg0KDQvtGB0YHQuNC4IOKEliDQodCkLzEyNC0y\n" +
            "NTM5INC+0YIgMTUuMDEuMjAxNQxj0KHQtdGA0YLQuNGE0LjQutCw0YIg0YHQvtC+0YLQstC10YLR\n" +
            "gdGC0LLQuNGPINCk0KHQkSDQoNC+0YHRgdC40Lgg4oSWINCh0KQvMTI4LTI4ODEg0L7RgiAxMi4w\n" +
            "NC4yMDE2MDYGBSqFA2RvBC0MKyLQmtGA0LjQv9GC0L7Qn9GA0L4gQ1NQIiAo0LLQtdGA0YHQuNGP\n" +
            "IDMuOSkwZQYDVR0fBF4wXDBaoFigVoZUaHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku\n" +
            "cnUvY2RwLzNlZWYxOTNmMGZiOTc5YjBmMWU2MjkyMWEzZTRiOTk1YjlhNWVlOTAuY3JsMFcGCCsG\n" +
            "AQUFBwEBBEswSTBHBggrBgEFBQcwAoY7aHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku\n" +
            "cnUvY2RwL3Rlc3RfY2FfcnRsYWJzMi5jZXIwCAYGKoUDAgIDA0EAWIKbobPiDap0i63WV/XyVw9I\n" +
            "eSeOGvQAgsverXl1IdpLqXAvHX1prvCUumTiu+aYvhGJIvcxjDyLuGhb3OQjGjGCAcIwggG+AgEB\n" +
            "MIIBXTCCAUYxGDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1\n" +
            "Njc4OTAxKTAnBgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZI\n" +
            "hvcNAQkBFghjYUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEV\n" +
            "MBMGA1UEBwwM0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+\n" +
            "0LwxMDAuBgNVBAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIG\n" +
            "A1UEAwwr0KLQtdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKQIRAXILAVZQ\n" +
            "ABCz6BEejDlOj3AwCgYGKoUDAgIJBQAwCgYGKoUDAgITBQAEQPJ5GfornCHIxDyaLFkWf6gkFBAy\n" +
            "Q7NHWuRZJ8fbB71ScxeUiFa9MN02RvszloYvoajxeOdiobR1aqDK5ALl+T4=</ns2:SignaturePKCS7></ns2:RefAttachmentHeader>";

    String preparedAudioTag ="<ns2:RefAttachmentHeader><ns2:uuid>872b8bb6-2550-11e9-9666-6f90870395fd</ns2:uuid><ns2:Hash>LwX76ZiKWn7FXljFxxL09Q1J+lCj9aXoWNFZDkuTZZU=</ns2:Hash><ns2:MimeType>audio/x-wav</ns2:MimeType><ns2:SignaturePKCS7>MIIJuwYJKoZIhvcNAQcCoIIJrDCCCagCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggfA" +
            "MIIHvDCCB2ugAwIBAgIRAXILAVZQABCz6BEejDlOj3AwCAYGKoUDAgIDMIIBRjEYMBYGBSqFA2QB" +
            "Eg0xMjM0NTY3ODkwMTIzMRowGAYIKoUDA4EDAQESDDAwMTIzNDU2Nzg5MDEpMCcGA1UECQwg0KHR" +
            "g9GJ0LXQstGB0LrQuNC5INCy0LDQuyDQtC4gMjYxFzAVBgkqhkiG9w0BCQEWCGNhQHJ0LnJ1MQsw" +
            "CQYDVQQGEwJSVTEYMBYGA1UECAwPNzcg0JzQvtGB0LrQstCwMRUwEwYDVQQHDAzQnNC+0YHQutCy" +
            "0LAxJDAiBgNVBAoMG9Ce0JDQniDQoNC+0YHRgtC10LvQtdC60L7QvDEwMC4GA1UECwwn0KPQtNC+" +
            "0YHRgtC+0LLQtdGA0Y/RjtGJ0LjQuSDRhtC10L3RgtGAMTQwMgYDVQQDDCvQotC10YHRgtC+0LLR" +
            "i9C5INCj0KYg0KDQotCaICjQoNCi0JvQsNCx0YEpMB4XDTE4MDcyMDEzMDE0MVoXDTE5MDcyMDEz" +
            "MTE0MVowgfAxHTAbBgkqhkiG9w0BCQIMDtCS0JrQkNCR0JDQndCaMRowGAYIKoUDA4EDAQESDDAw" +
            "MzAxNTAxMTc1NTEYMBYGBSqFA2QBEg0xMDIzMDAwMDAwMjEwMRwwGgYDVQQKDBPQkNCeINCS0JrQ" +
            "kNCR0JDQndCaMRswGQYDVQQHDBLQkNGB0YLRgNCw0YXQsNC90YwxMzAxBgNVBAgMKjMwINCQ0YHR" +
            "gtGA0LDRhdCw0L3RgdC60LDRjyDQvtCx0LvQsNGB0YLRjDELMAkGA1UEBhMCUlUxHDAaBgNVBAMM" +
            "E9CQ0J4g0JLQmtCQ0JHQkNCd0JowYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARA" +
            "qjtC1dM6zvtwmhJbUMVVOiC+8kbOOgufkJJFKHy5rMaFG6jWxUiGKvI8AAcEE7rP93ui2TMVzaDe" +
            "cGOrspIW6KOCBIMwggR/MA4GA1UdDwEB/wQEAwIE8DAdBgNVHQ4EFgQU541ASZ2wBv/db7s8wxlc" +
            "nshsQxAwggGIBgNVHSMEggF/MIIBe4AUPu8ZPw+5ebDx5ikho+S5lbml7pChggFOpIIBSjCCAUYx" +
            "GDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1Njc4OTAxKTAn" +
            "BgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZIhvcNAQkBFghj" +
            "YUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEVMBMGA1UEBwwM" +
            "0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+0LwxMDAuBgNV" +
            "BAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIGA1UEAwwr0KLQ" +
            "tdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKYIRAXILAVZQALmz5xHPOr40" +
            "d6AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMCcGCSsGAQQBgjcVCgQaMBgwCgYIKwYB" +
            "BQUHAwIwCgYIKwYBBQUHAwQwHQYDVR0gBBYwFDAIBgYqhQNkcQEwCAYGKoUDZHECMCsGA1UdEAQk" +
            "MCKADzIwMTgwNzIwMTMwMTQxWoEPMjAxOTA3MjAxMzAxNDFaMIIBNAYFKoUDZHAEggEpMIIBJQwr" +
            "ItCa0YDQuNC/0YLQvtCf0YDQviBDU1AiICjQstC10YDRgdC40Y8gMy45KQwsItCa0YDQuNC/0YLQ" +
            "vtCf0YDQviDQo9CmIiAo0LLQtdGA0YHQuNC4IDIuMCkMY9Ch0LXRgNGC0LjRhNC40LrQsNGCINGB" +
            "0L7QvtGC0LLQtdGC0YHRgtCy0LjRjyDQpNCh0JEg0KDQvtGB0YHQuNC4IOKEliDQodCkLzEyNC0y" +
            "NTM5INC+0YIgMTUuMDEuMjAxNQxj0KHQtdGA0YLQuNGE0LjQutCw0YIg0YHQvtC+0YLQstC10YLR" +
            "gdGC0LLQuNGPINCk0KHQkSDQoNC+0YHRgdC40Lgg4oSWINCh0KQvMTI4LTI4ODEg0L7RgiAxMi4w" +
            "NC4yMDE2MDYGBSqFA2RvBC0MKyLQmtGA0LjQv9GC0L7Qn9GA0L4gQ1NQIiAo0LLQtdGA0YHQuNGP" +
            "IDMuOSkwZQYDVR0fBF4wXDBaoFigVoZUaHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku" +
            "cnUvY2RwLzNlZWYxOTNmMGZiOTc5YjBmMWU2MjkyMWEzZTRiOTk1YjlhNWVlOTAuY3JsMFcGCCsG" +
            "AQUFBwEBBEswSTBHBggrBgEFBQcwAoY7aHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku" +
            "cnUvY2RwL3Rlc3RfY2FfcnRsYWJzMi5jZXIwCAYGKoUDAgIDA0EAWIKbobPiDap0i63WV/XyVw9I" +
            "eSeOGvQAgsverXl1IdpLqXAvHX1prvCUumTiu+aYvhGJIvcxjDyLuGhb3OQjGjGCAcIwggG+AgEB" +
            "MIIBXTCCAUYxGDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1" +
            "Njc4OTAxKTAnBgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZI" +
            "hvcNAQkBFghjYUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEV" +
            "MBMGA1UEBwwM0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+" +
            "0LwxMDAuBgNVBAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIG" +
            "A1UEAwwr0KLQtdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKQIRAXILAVZQ" +
            "ABCz6BEejDlOj3AwCgYGKoUDAgIJBQAwCgYGKoUDAgITBQAEQChOJKleasE20eVYcChObO3Via+v" +
            "WPTOjIL+BbP1xGpkekA3OP8Dtljmqvpxr0OpwEaPoFguUW645ay17r2lgMI=</ns2:SignaturePKCS7></ns2:RefAttachmentHeader>";


    String dumped = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
            "   <S:Body>\n" +
            "      <ns2:SendRequestRequest xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\">\n" +
            "         <ns:SenderProvidedRequestData xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\t<ns:MessageID>24df9c9c-2462-11e9-9fa1-af0411f79ff8</ns:MessageID><ns2:MessagePrimaryContent><bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
            "    <bm:RegistrarMnemonic>981601_3T</bm:RegistrarMnemonic>\n" +
            "    <bm:EmployeeId>000-000-600 06</bm:EmployeeId>\n" +
            "    <bm:BiometricData>\n" +
            "        <bm:Id>ID-1</bm:Id>\n" +
            "        <bm:Date>2019-01-30T11:39:12.2000000+04:00</bm:Date>\n" +
            "        <bm:RaId>1000300890</bm:RaId>\n" +
            "        <bm:PersonId>1000368305</bm:PersonId>\n" +
            "        <bm:IdpMnemonic>TESIA</bm:IdpMnemonic>      \n" +
            "<bm:Data>\n" +
            "<bm:Modality>SOUND</bm:Modality>\n" +
            "            <bm:AttachmentRef attachmentId=\"22e2bb2a-2462-11e9-9fa1-67a6cbfa98e1\"/><bm:BioMetadata><bm:Key>voice_1_start</bm:Key><bm:Value>0.0</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_1_end</bm:Key><bm:Value>1.0</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_1_desc</bm:Key><bm:Value>digits_asc</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_2_start</bm:Key><bm:Value>2.0</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_2_end</bm:Key><bm:Value>3.0</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_2_desc</bm:Key><bm:Value>digits_desc</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_3_start</bm:Key><bm:Value>4.0</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_3_end</bm:Key><bm:Value>5.0</bm:Value></bm:BioMetadata>\n" +
            "<bm:BioMetadata><bm:Key>voice_3_desc</bm:Key><bm:Value>digits_random</bm:Value></bm:BioMetadata>\n" +
            "</bm:Data><bm:Data><bm:Modality>PHOTO</bm:Modality><bm:AttachmentRef attachmentId=\"245054eb-2462-11e9-9fa1-05e9c07bdd87\"/></bm:Data>    </bm:BiometricData>\n" +
            "</bm:RegisterBiometricDataRequest></ns2:MessagePrimaryContent>\t<ns2:RefAttachmentHeaderList><ns2:RefAttachmentHeader>\t\t<ns2:uuid>245054eb-2462-11e9-9fa1-05e9c07bdd87</ns2:uuid>\t\t<ns2:Hash>6k9/NWk/nX1nyyCsJQ8vcoO229ZDysnpFqgJxI1dYzA=</ns2:Hash>\t\t<ns2:MimeType>image/png</ns2:MimeType>\t<ns2:SignaturePKCS7>MIIJuwYJKoZIhvcNAQcCoIIJrDCCCagCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggfA\n" +
            "MIIHvDCCB2ugAwIBAgIRAXILAVZQABCz6BEejDlOj3AwCAYGKoUDAgIDMIIBRjEYMBYGBSqFA2QB\n" +
            "Eg0xMjM0NTY3ODkwMTIzMRowGAYIKoUDA4EDAQESDDAwMTIzNDU2Nzg5MDEpMCcGA1UECQwg0KHR\n" +
            "g9GJ0LXQstGB0LrQuNC5INCy0LDQuyDQtC4gMjYxFzAVBgkqhkiG9w0BCQEWCGNhQHJ0LnJ1MQsw\n" +
            "CQYDVQQGEwJSVTEYMBYGA1UECAwPNzcg0JzQvtGB0LrQstCwMRUwEwYDVQQHDAzQnNC+0YHQutCy\n" +
            "0LAxJDAiBgNVBAoMG9Ce0JDQniDQoNC+0YHRgtC10LvQtdC60L7QvDEwMC4GA1UECwwn0KPQtNC+\n" +
            "0YHRgtC+0LLQtdGA0Y/RjtGJ0LjQuSDRhtC10L3RgtGAMTQwMgYDVQQDDCvQotC10YHRgtC+0LLR\n" +
            "i9C5INCj0KYg0KDQotCaICjQoNCi0JvQsNCx0YEpMB4XDTE4MDcyMDEzMDE0MVoXDTE5MDcyMDEz\n" +
            "MTE0MVowgfAxHTAbBgkqhkiG9w0BCQIMDtCS0JrQkNCR0JDQndCaMRowGAYIKoUDA4EDAQESDDAw\n" +
            "MzAxNTAxMTc1NTEYMBYGBSqFA2QBEg0xMDIzMDAwMDAwMjEwMRwwGgYDVQQKDBPQkNCeINCS0JrQ\n" +
            "kNCR0JDQndCaMRswGQYDVQQHDBLQkNGB0YLRgNCw0YXQsNC90YwxMzAxBgNVBAgMKjMwINCQ0YHR\n" +
            "gtGA0LDRhdCw0L3RgdC60LDRjyDQvtCx0LvQsNGB0YLRjDELMAkGA1UEBhMCUlUxHDAaBgNVBAMM\n" +
            "E9CQ0J4g0JLQmtCQ0JHQkNCd0JowYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARA\n" +
            "qjtC1dM6zvtwmhJbUMVVOiC+8kbOOgufkJJFKHy5rMaFG6jWxUiGKvI8AAcEE7rP93ui2TMVzaDe\n" +
            "cGOrspIW6KOCBIMwggR/MA4GA1UdDwEB/wQEAwIE8DAdBgNVHQ4EFgQU541ASZ2wBv/db7s8wxlc\n" +
            "nshsQxAwggGIBgNVHSMEggF/MIIBe4AUPu8ZPw+5ebDx5ikho+S5lbml7pChggFOpIIBSjCCAUYx\n" +
            "GDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1Njc4OTAxKTAn\n" +
            "BgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZIhvcNAQkBFghj\n" +
            "YUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEVMBMGA1UEBwwM\n" +
            "0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+0LwxMDAuBgNV\n" +
            "BAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIGA1UEAwwr0KLQ\n" +
            "tdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKYIRAXILAVZQALmz5xHPOr40\n" +
            "d6AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMCcGCSsGAQQBgjcVCgQaMBgwCgYIKwYB\n" +
            "BQUHAwIwCgYIKwYBBQUHAwQwHQYDVR0gBBYwFDAIBgYqhQNkcQEwCAYGKoUDZHECMCsGA1UdEAQk\n" +
            "MCKADzIwMTgwNzIwMTMwMTQxWoEPMjAxOTA3MjAxMzAxNDFaMIIBNAYFKoUDZHAEggEpMIIBJQwr\n" +
            "ItCa0YDQuNC/0YLQvtCf0YDQviBDU1AiICjQstC10YDRgdC40Y8gMy45KQwsItCa0YDQuNC/0YLQ\n" +
            "vtCf0YDQviDQo9CmIiAo0LLQtdGA0YHQuNC4IDIuMCkMY9Ch0LXRgNGC0LjRhNC40LrQsNGCINGB\n" +
            "0L7QvtGC0LLQtdGC0YHRgtCy0LjRjyDQpNCh0JEg0KDQvtGB0YHQuNC4IOKEliDQodCkLzEyNC0y\n" +
            "NTM5INC+0YIgMTUuMDEuMjAxNQxj0KHQtdGA0YLQuNGE0LjQutCw0YIg0YHQvtC+0YLQstC10YLR\n" +
            "gdGC0LLQuNGPINCk0KHQkSDQoNC+0YHRgdC40Lgg4oSWINCh0KQvMTI4LTI4ODEg0L7RgiAxMi4w\n" +
            "NC4yMDE2MDYGBSqFA2RvBC0MKyLQmtGA0LjQv9GC0L7Qn9GA0L4gQ1NQIiAo0LLQtdGA0YHQuNGP\n" +
            "IDMuOSkwZQYDVR0fBF4wXDBaoFigVoZUaHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku\n" +
            "cnUvY2RwLzNlZWYxOTNmMGZiOTc5YjBmMWU2MjkyMWEzZTRiOTk1YjlhNWVlOTAuY3JsMFcGCCsG\n" +
            "AQUFBwEBBEswSTBHBggrBgEFBQcwAoY7aHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku\n" +
            "cnUvY2RwL3Rlc3RfY2FfcnRsYWJzMi5jZXIwCAYGKoUDAgIDA0EAWIKbobPiDap0i63WV/XyVw9I\n" +
            "eSeOGvQAgsverXl1IdpLqXAvHX1prvCUumTiu+aYvhGJIvcxjDyLuGhb3OQjGjGCAcIwggG+AgEB\n" +
            "MIIBXTCCAUYxGDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1\n" +
            "Njc4OTAxKTAnBgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZI\n" +
            "hvcNAQkBFghjYUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEV\n" +
            "MBMGA1UEBwwM0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+\n" +
            "0LwxMDAuBgNVBAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIG\n" +
            "A1UEAwwr0KLQtdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKQIRAXILAVZQ\n" +
            "ABCz6BEejDlOj3AwCgYGKoUDAgIJBQAwCgYGKoUDAgITBQAEQIGYFUkImAwTkfALWbt4D6lt+KAO\n" +
            "StGlaZHAtB9b423UTgzH0bFdE5Lf+EcUtnt6bMlxzwQC0EOWS2WnbghXl1Q=</ns2:SignaturePKCS7>\t</ns2:RefAttachmentHeader><ns2:RefAttachmentHeader>\t\t<ns2:uuid>22e2bb2a-2462-11e9-9fa1-67a6cbfa98e1</ns2:uuid>\t\t<ns2:Hash>LwX76ZiKWn7FXljFxxL09Q1J+lCj9aXoWNFZDkuTZZU=</ns2:Hash>\t\t<ns2:MimeType>audio/x-wav</ns2:MimeType>\t<ns2:SignaturePKCS7>MIIJuwYJKoZIhvcNAQcCoIIJrDCCCagCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggfA\n" +
            "MIIHvDCCB2ugAwIBAgIRAXILAVZQABCz6BEejDlOj3AwCAYGKoUDAgIDMIIBRjEYMBYGBSqFA2QB\n" +
            "Eg0xMjM0NTY3ODkwMTIzMRowGAYIKoUDA4EDAQESDDAwMTIzNDU2Nzg5MDEpMCcGA1UECQwg0KHR\n" +
            "g9GJ0LXQstGB0LrQuNC5INCy0LDQuyDQtC4gMjYxFzAVBgkqhkiG9w0BCQEWCGNhQHJ0LnJ1MQsw\n" +
            "CQYDVQQGEwJSVTEYMBYGA1UECAwPNzcg0JzQvtGB0LrQstCwMRUwEwYDVQQHDAzQnNC+0YHQutCy\n" +
            "0LAxJDAiBgNVBAoMG9Ce0JDQniDQoNC+0YHRgtC10LvQtdC60L7QvDEwMC4GA1UECwwn0KPQtNC+\n" +
            "0YHRgtC+0LLQtdGA0Y/RjtGJ0LjQuSDRhtC10L3RgtGAMTQwMgYDVQQDDCvQotC10YHRgtC+0LLR\n" +
            "i9C5INCj0KYg0KDQotCaICjQoNCi0JvQsNCx0YEpMB4XDTE4MDcyMDEzMDE0MVoXDTE5MDcyMDEz\n" +
            "MTE0MVowgfAxHTAbBgkqhkiG9w0BCQIMDtCS0JrQkNCR0JDQndCaMRowGAYIKoUDA4EDAQESDDAw\n" +
            "MzAxNTAxMTc1NTEYMBYGBSqFA2QBEg0xMDIzMDAwMDAwMjEwMRwwGgYDVQQKDBPQkNCeINCS0JrQ\n" +
            "kNCR0JDQndCaMRswGQYDVQQHDBLQkNGB0YLRgNCw0YXQsNC90YwxMzAxBgNVBAgMKjMwINCQ0YHR\n" +
            "gtGA0LDRhdCw0L3RgdC60LDRjyDQvtCx0LvQsNGB0YLRjDELMAkGA1UEBhMCUlUxHDAaBgNVBAMM\n" +
            "E9CQ0J4g0JLQmtCQ0JHQkNCd0JowYzAcBgYqhQMCAhMwEgYHKoUDAgIkAAYHKoUDAgIeAQNDAARA\n" +
            "qjtC1dM6zvtwmhJbUMVVOiC+8kbOOgufkJJFKHy5rMaFG6jWxUiGKvI8AAcEE7rP93ui2TMVzaDe\n" +
            "cGOrspIW6KOCBIMwggR/MA4GA1UdDwEB/wQEAwIE8DAdBgNVHQ4EFgQU541ASZ2wBv/db7s8wxlc\n" +
            "nshsQxAwggGIBgNVHSMEggF/MIIBe4AUPu8ZPw+5ebDx5ikho+S5lbml7pChggFOpIIBSjCCAUYx\n" +
            "GDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1Njc4OTAxKTAn\n" +
            "BgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZIhvcNAQkBFghj\n" +
            "YUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEVMBMGA1UEBwwM\n" +
            "0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+0LwxMDAuBgNV\n" +
            "BAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIGA1UEAwwr0KLQ\n" +
            "tdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKYIRAXILAVZQALmz5xHPOr40\n" +
            "d6AwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMCcGCSsGAQQBgjcVCgQaMBgwCgYIKwYB\n" +
            "BQUHAwIwCgYIKwYBBQUHAwQwHQYDVR0gBBYwFDAIBgYqhQNkcQEwCAYGKoUDZHECMCsGA1UdEAQk\n" +
            "MCKADzIwMTgwNzIwMTMwMTQxWoEPMjAxOTA3MjAxMzAxNDFaMIIBNAYFKoUDZHAEggEpMIIBJQwr\n" +
            "ItCa0YDQuNC/0YLQvtCf0YDQviBDU1AiICjQstC10YDRgdC40Y8gMy45KQwsItCa0YDQuNC/0YLQ\n" +
            "vtCf0YDQviDQo9CmIiAo0LLQtdGA0YHQuNC4IDIuMCkMY9Ch0LXRgNGC0LjRhNC40LrQsNGCINGB\n" +
            "0L7QvtGC0LLQtdGC0YHRgtCy0LjRjyDQpNCh0JEg0KDQvtGB0YHQuNC4IOKEliDQodCkLzEyNC0y\n" +
            "NTM5INC+0YIgMTUuMDEuMjAxNQxj0KHQtdGA0YLQuNGE0LjQutCw0YIg0YHQvtC+0YLQstC10YLR\n" +
            "gdGC0LLQuNGPINCk0KHQkSDQoNC+0YHRgdC40Lgg4oSWINCh0KQvMTI4LTI4ODEg0L7RgiAxMi4w\n" +
            "NC4yMDE2MDYGBSqFA2RvBC0MKyLQmtGA0LjQv9GC0L7Qn9GA0L4gQ1NQIiAo0LLQtdGA0YHQuNGP\n" +
            "IDMuOSkwZQYDVR0fBF4wXDBaoFigVoZUaHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku\n" +
            "cnUvY2RwLzNlZWYxOTNmMGZiOTc5YjBmMWU2MjkyMWEzZTRiOTk1YjlhNWVlOTAuY3JsMFcGCCsG\n" +
            "AQUFBwEBBEswSTBHBggrBgEFBQcwAoY7aHR0cDovL2NlcnRlbnJvbGwudGVzdC5nb3N1c2x1Z2ku\n" +
            "cnUvY2RwL3Rlc3RfY2FfcnRsYWJzMi5jZXIwCAYGKoUDAgIDA0EAWIKbobPiDap0i63WV/XyVw9I\n" +
            "eSeOGvQAgsverXl1IdpLqXAvHX1prvCUumTiu+aYvhGJIvcxjDyLuGhb3OQjGjGCAcIwggG+AgEB\n" +
            "MIIBXTCCAUYxGDAWBgUqhQNkARINMTIzNDU2Nzg5MDEyMzEaMBgGCCqFAwOBAwEBEgwwMDEyMzQ1\n" +
            "Njc4OTAxKTAnBgNVBAkMINCh0YPRidC10LLRgdC60LjQuSDQstCw0Lsg0LQuIDI2MRcwFQYJKoZI\n" +
            "hvcNAQkBFghjYUBydC5ydTELMAkGA1UEBhMCUlUxGDAWBgNVBAgMDzc3INCc0L7RgdC60LLQsDEV\n" +
            "MBMGA1UEBwwM0JzQvtGB0LrQstCwMSQwIgYDVQQKDBvQntCQ0J4g0KDQvtGB0YLQtdC70LXQutC+\n" +
            "0LwxMDAuBgNVBAsMJ9Cj0LTQvtGB0YLQvtCy0LXRgNGP0Y7RidC40Lkg0YbQtdC90YLRgDE0MDIG\n" +
            "A1UEAwwr0KLQtdGB0YLQvtCy0YvQuSDQo9CmINCg0KLQmiAo0KDQotCb0LDQsdGBKQIRAXILAVZQ\n" +
            "ABCz6BEejDlOj3AwCgYGKoUDAgIJBQAwCgYGKoUDAgITBQAEQOVmz3Z+xdoA5pjbT2787KFKYDnn\n" +
            "DeUB3/741v8XYm2+ia78pbs1wB5wjWjd9YwOfvKLT6oJxynkOwVLGtGNFKE=</ns2:SignaturePKCS7>\t</ns2:RefAttachmentHeader></ns2:RefAttachmentHeaderList></ns:SenderProvidedRequestData>\n" +
            "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"sigID\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>GLMXyutrkQH2dH6XUoBwwAyecSR0bht7sEYNNvYRQRk=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>EoSEVGhyKFCePDWecXIZEjmp3VDOkE6xkVCDL6/z9bZsJawMNdlvN9VukGav5ObkLOITVz4UwwWOHJJMwdyiIg==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
            "      </ns2:SendRequestRequest>\n" +
            "   </S:Body>\n" +
            "</S:Envelope>";

    DependencyContainer deps = new DependencyContainer(new SignerXML(new TestSign2001(), new TestSign2001()));
    Scheduller sch = new Scheduller(deps);
    Sign signer = new Sign2018();
    public boolean supress=false;
    Injector inj = new Injector();
    String photofile = "biophoto.jpg";
    String soundfile = "biosound.wav";
    public String filename__ = "EBSMessageFUll.bin";
    public ebsTest() throws AlgorithmAlreadyRegisteredException, InvalidTransformException, IOException, SQLException, SignatureProcessorException, ClassNotFoundException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException {
        msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        buff = Files.readAllBytes(new File(filename__).toPath());

    }

    @Test
    public void sendInitialRequestRequestGis() throws Exception {

        assertNotEquals(null, deps.ebs.signer);
        assertNotEquals(null, deps.ebs.signer.getmainSign());
        assertNotEquals(null, deps.ebs.signer.getPersonalSign());
        String data = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
        "   <S:Body>\n" +
        "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" + "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>6fefa07c-5e7f-11e4-a9ff-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest Id=\"PERSONAL_SIGNATURE\" xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\" xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns2:AttachmentHeaderList>\t<ns2:AttachmentHeader>\t<ns2:contentId>attach5MB.jpg</ns2:contentId>\t<ns2:MimeType>image/jpeg</ns2:MimeType>\t<ns2:SignaturePKCS7>MIICyAYJKoZIhvcNAQcCoIICuTCCArUCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggGLMIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHsxggEEMIIBAAIBATA2MC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUCBQDBXZBUMAoGBiqFAwICCQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTQxMDI4MDg1MDE2WjAvBgkqhkiG9w0BCQQxIgQgmZUE4Hn2Dtr05pue921ZxU60Ia3toLVEQfIs24PTdT8wCgYGKoUDAgITBQAEQBwEN+RenIpvL6lZHzzsPj5H4xgqZjs330i1JjAhjcACqQcCyt3vorTEX9/gfs16s9Lt9XxNj9Y88NvvLIrfFaw=</ns2:SignaturePKCS7>\t</ns2:AttachmentHeader>\t</ns2:AttachmentHeaderList></ns:SenderProvidedRequestData>\t<AttachmentContentList>\t<AttachmentContent>\t<Id>attach5MB.jpg</Id>\t<Content><xop:Include href=\"cid:attach5MB.jpg\" xmlns:xop=\"http://www.w3.org/2004/08/xop/include\"/></Content>\t</AttachmentContent>\t</AttachmentContentList>\n" +
        "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>jTP0hndqPRXO9O+5euAtKsysn83a9+gVkvUpojGTL84=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>i3PzufK7XEmCtElirdXqtYh7/CdZCIIgowfj+TbBHPw/Mk+1YA+/LSdIM3MO2tcH8ZeFA5e04VSz0deGcnVAaA==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHs=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
        "      </ns2:SendRequestRequest>\n" +
        "   </S:Body>\n" +
        "</S:Envelope>";
        deps.ebs.setinput(data.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0)
            System.out.println("FAULT");


    }

    @Test
    public void setinput() throws Exception {
        String testData = "<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>TEST01</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>123-456-789 00</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:54:52+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>240631324</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"ef37b493-e94f-4f27-9e86-f4cd80f1057f\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>10.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>12.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>20.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>22.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>30.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"397af8d0-d456-4dc1-9353-1d6822a02200\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-2</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:50:16+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>215979546</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>FINGERPRINT</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"acf4bf60-af0f-4479-a338-d3410e532bf5\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>";
        String input = deps.inj.injectTagDirect(deps.ebs.emptySOAP, "MessagePrimaryContent", testData);
        deps.ebs.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void notNull(){

    }


    @Test
    public void sendEBSWithoutFinferprint() throws Exception {
        String testData = "<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>TEST01</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>123-456-789 00</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:54:52+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>240631324</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"ef37b493-e94f-4f27-9e86-f4cd80f1057f\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>10.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>12.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>20.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>22.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>30.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"397af8d0-d456-4dc1-9353-1d6822a02200\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-2</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:50:16+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>215979546</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>";
        String input = deps.inj.injectTagDirect(deps.ebs.emptySOAP, "MessagePrimaryContent", testData);
        deps.ebs.setinput(input.getBytes());
        assertNotEquals(null, input);
        System.out.println(input);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void getResponceRequestCompiled() throws Exception {
        deps.ebs.GetResponceRequestCompiled();
    }

    @Test
    public void printEtalon(){
        String testData = "<bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>TEST01</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>123-456-789 00</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:54:52+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>240631324</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"ef37b493-e94f-4f27-9e86-f4cd80f1057f\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>10.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>12.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>20.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>22.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>30.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"397af8d0-d456-4dc1-9353-1d6822a02200\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-2</bm:Id>\n" +
                "        <bm:Date>2017-07-31T16:50:16+03:00</bm:Date>\n" +
                "        <bm:RaId>0c2c345f-cd7b-4011-9f3b-65095ab4c186</bm:RaId>\n" +
                "        <bm:PersonId>215979546</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>ESIA</bm:IdpMnemonic>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>";
        System.out.println(testData);

        String blob ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>d8161c55-1ecc-11e9-90eb-1dec74fcc959</ns:MessageID><ns2:MessagePrimaryContent><tns:ESIAFindAccountRequest xmlns:tns=\"urn://mincomsvyaz/esia/reg_service/find_account/1.4.1\" xmlns:ns2=\"urn://mincomsvyaz/esia/commons/rg_sevices_types/1.4.1\">\n" +
                "   \t<tns:RoutingCode>TESIA</tns:RoutingCode>\n" +
                "  \t<tns:SnilsOperator>000-000-600 06</tns:SnilsOperator>\n" +
                "    <tns:ra>1000300890</tns:ra>\n" +
                "    <tns:lastName>Исмаилов</tns:lastName>\n" +
                "    <tns:firstName>Тест</tns:firstName>\n" +
                "    <tns:middleName>Банкович</tns:middleName>\n" +
                "    <tns:doc>\n" +
                "        <ns2:type>RF_PASSPORT</ns2:type>\n" +
                "        <ns2:series>0009</ns2:series>\n" +
                "        <ns2:number>123123</ns2:number>\n" +
                "    </tns:doc>\n" +
                "<tns:mobile>+7(988)0693468</tns:mobile>\n" +
                "<tns:snils>000-303-303 61</tns:snils>\n" +
                "</tns:ESIAFindAccountRequest>\n" +
                "</ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>HUVEqoOE3Rkngx6/ic6xBFtRm1C4YxRBIroEBr0+D6o=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>skAUTOLg7zoPMX/04w35xbCEFF2rok+Cs5mbnzj0075Ouy4oQ3jriElEod4n6opc/1vqgkK3CGEnbEL9ROCP/g==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>\n";
        System.out.println(blob);


    }


    @Test
    public void sendEBSSAFAR() throws Exception {
        String blob ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"><S:Body><ns2:SendRequestRequest><ns:SenderProvidedRequestData xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns:MessageID>d8161c55-1ecc-11e9-90eb-1dec74fcc959</ns:MessageID><ns2:MessagePrimaryContent><bm:RegisterBiometricDataRequest xmlns:bm=\"urn://x-artefacts-nbp-rtlabs-ru/register/1.2.0\">\n" +
                "    <bm:RegistrarMnemonic>981601_3T</bm:RegistrarMnemonic>\n" +
                "    <bm:EmployeeId>000-000-600 06</bm:EmployeeId>\n" +
                "    <bm:BiometricData>\n" +
                "        <bm:Id>ID-1</bm:Id>\n" +
                "        <bm:Date>2019-01-23T13:14:52+03:00</bm:Date>\n" +
                "        <bm:RaId>1000300890</bm:RaId>\n" +
                "        <bm:PersonId>1000368305</bm:PersonId>\n" +
                "        <bm:IdpMnemonic>TESIA</bm:IdpMnemonic>      \n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>SOUND</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"e59a11cc-1f13-11e9-a54a-af41724ba888\"/>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_start</bm:Key>\n" +
                "                <bm:Value>00.000</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_end</bm:Key>\n" +
                "                <bm:Value>1.002</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_1_desc</bm:Key>\n" +
                "                <bm:Value>digits_asc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_start</bm:Key>\n" +
                "                <bm:Value>2.601</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_end</bm:Key>\n" +
                "                <bm:Value>3.199</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_2_desc</bm:Key>\n" +
                "                <bm:Value>digits_desc</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_start</bm:Key>\n" +
                "                <bm:Value>4.001</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_end</bm:Key>\n" +
                "                <bm:Value>5.102</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "            <bm:BioMetadata>\n" +
                "                <bm:Key>voice_3_desc</bm:Key>\n" +
                "                <bm:Value>digits_random</bm:Value>\n" +
                "            </bm:BioMetadata>\n" +
                "        </bm:Data>\n" +
                "        <bm:Data>\n" +
                "            <bm:Modality>PHOTO</bm:Modality>\n" +
                "            <bm:AttachmentRef attachmentId=\"f5cb6cae-1f13-11e9-8bb3-5706b6ad71d3\"/>\n" +
                "        </bm:Data>\n" +
                "    </bm:BiometricData>\n" +
                "</bm:RegisterBiometricDataRequest>\n" +
                "</ns2:MessagePrimaryContent></ns:SenderProvidedRequestData><ns4:CallerInformationSystemSignature xmlns:ns4=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:Signature Id=\"sigID\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>HUVEqoOE3Rkngx6/ic6xBFtRm1C4YxRBIroEBr0+D6o=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>skAUTOLg7zoPMX/04w35xbCEFF2rok+Cs5mbnzj0075Ouy4oQ3jriElEod4n6opc/1vqgkK3CGEnbEL9ROCP/g==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns4:CallerInformationSystemSignature></ns2:SendRequestRequest></S:Body></S:Envelope>";


        deps.ebs.setinput(blob.getBytes());
        assertNotEquals(null, blob);
        System.out.println(blob);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
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
        System.out.println("\n@\n"+ originalid);
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
        if (originalid!=null)
            deps.gis.Ack(messageId);
    }


    @Test
    public void attachment(){
        ArrayList<String> attaches = new ArrayList();
        attaches.add("biophoto");
    }


  //  @Test
    public void findMessageID() throws Exception {

            String result = getrespreq();
            while (true){
                String id=deps.ext.extractTagValue(result, ":MessageID");
                //   System.out.println("Extract id="+ id);
                String originalid=deps.ext.extractTagValue(result, ":OriginalMessageId");
                System.out.println("Original id="+ originalid);
                if (id != null) {
                    deps.gis.Ack(id);

                }
                if (originalid.equals("22c78a30-1f14-11e9-af36-4b2d56ff4191"))
                    return;
                result = getrespreq();
                //   Thread.sleep(0);
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
        //    String prepared=deps.inj.injectAttribute(data, "Id", "SIGNED_BY_CONSUMER");
        deps.gis.setinput(prepared.getBytes());
        assertNotEquals(null, deps.gis.GetSoap());
        String response = new String(deps.gis.GetResponseRequestwoFilter());
        return response;
    };


    @Test
    public void uploadtest() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.uploadfiletoftp(msg.SoundBLOB.filename));
    }

    @Test
    public void restoreMsg() throws Exception {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        byte[] rawmsg = Files.readAllBytes(new File(filename__).toPath());
        assertNotEquals(null, msg.otherinfo);
        assertNotEquals(null, msg.PhotoBLOB);
        assertNotEquals(null, msg.SoundBLOB);

        System.out.println(msg.SoundBLOB.filename);
        byte[] BinaryXML=deps.tableProcessor.OperatorMap.get("ebs").generateUnsSOAP(rawmsg);
        assertNotEquals(null, BinaryXML);
        System.out.println(new String(BinaryXML));
    }

    @Test
    public void generateSoundBlob() throws Exception {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.generateSoundBlock(msg) );
    }

    @Test
    public void soundBioMethadata() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.SoundBioMethadata(msg) );
        System.out.println(deps.ebs.SoundBioMethadata(msg));
    }

    @Test
    public void generateSoundHeader() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.generateSoundHeader("7878778"));
        System.out.println(deps.ebs.generateSoundHeader("7878778"));
    }

    @Test
    public void photoBlock() throws IOException {
        EBSMessage msg = (EBSMessage) BinaryMessage.restored(Files.readAllBytes(new File(filename__).toPath()));
        assertNotEquals(null, deps.ebs.generatePhotoBlock(msg));
        System.out.println(deps.ebs.generatePhotoBlock(msg));
    }

    @Test
    public void processCryptoGraphy() throws Exception {
        deps.ebs.processCryptoGraphy(msg);
        assertNotEquals(null, deps.ebs.currentHashPhoto);
        assertNotEquals(null, deps.ebs.currentHashSound);
        System.out.println(deps.ebs.currentHashPhoto);
        System.out.println(deps.ebs.currentHashSound);

        assertNotEquals(null, deps.ebs.currentPKSC7Photo);
        assertNotEquals(null, deps.ebs.currentPKSC7Sound);
    }

    @Test
    public void attachSoundBlock() throws Exception {
        deps.ebs.generateSoundBlock(msg);
        deps.ebs.generatePhotoBlock(msg);

        assertNotEquals(null, deps.ebs.AttachPhotoBlock());
        assertNotEquals(null, deps.ebs.AttachSoundBlock());
        System.out.println( deps.ebs.AttachPhotoBlock());
        System.out.println( deps.ebs.AttachSoundBlock());

    }

    @Test
    public void genMessagePrimaryContent() throws Exception {
        String block = deps.ebs.genMessagePrimaryContent(msg);
        assertNotEquals(null, block);
        System.out.println(block);
    }


    @Test
    public void generateUnsSOAP() throws Exception {
        String usnigned =new String(deps.ebs.generateUnsSOAP(buff));
        assertNotEquals(null, usnigned);
        System.out.println(usnigned);
    }

    @Test
    public void restoredSignandsend() throws Exception {

        String usnigned =new String(deps.ebs.generateUnsSOAP(buff));

        deps.ebs.setinput(usnigned.getBytes());
        assertNotEquals(null, usnigned);
        System.out.println(usnigned);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
           System.out.println("FAULT");
        }
    }

    @Test
    public void printAll(){
        System.out.println(deps.gis.emptySOAP);

        System.out.println("\n\n");

        System.out.println(deps.ebs.emptySOAP);

    }

    @Test
    public void sendSA() throws Exception {
        String usn = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>868a694d-5e80-11e4-a9ff-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns2:RefAttachmentHeaderList>\t<ns2:RefAttachmentHeader>\t\t<ns2:uuid>e74b2cb9-5aa3-11e4-a9ff-d4c9eff07b77</ns2:uuid>\t\t<ns2:Hash>VpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJA=</ns2:Hash>\t\t<ns2:MimeType>image/jpeg</ns2:MimeType>\t<ns2:SignaturePKCS7>MIICyAYJKoZIhvcNAQcCoIICuTCCArUCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggGLMIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHsxggEEMIIBAAIBATA2MC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUCBQDBXZBUMAoGBiqFAwICCQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTQxMDI4MDg1ODAzWjAvBgkqhkiG9w0BCQQxIgQgVpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJAwCgYGKoUDAgITBQAEQIXxbWJSNu/oGOxFOKcDO5wE1riXWDqyffOPZnIfoUk3QNI/qvRas+0V+713qvOL9iGQ3QrKFD1PkeJVditDDP4=</ns2:SignaturePKCS7>\t</ns2:RefAttachmentHeader>\t</ns2:RefAttachmentHeaderList>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>";
        deps.ebs.setinput(usn.getBytes());
        assertNotEquals(null, usn);
        System.out.println(usn);


        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
        if (response.indexOf("fault")>0) {
            System.out.println("FAULT");
        }
    }

    @Test
    public void sendhoocked1() throws Exception {
        String hook = deps.inj.flushTagData(dumped, "ns2:CallerInformationSystemSignature");
        deps.ebs.setinput(hook.getBytes());
        System.out.println(hook);
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
    }

    @Test
    public void sendhoocked12() throws Exception {
        System.out.println(dumped);
        String injected = "<ns2:RefAttachmentHeader>\t\t<ns2:uuid>e74b2cb9-5aa3-11e4-a9ff-d4c9eff07b77</ns2:uuid>\t\t<ns2:Hash>VpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJA=</ns2:Hash>\t\t<ns2:MimeType>image/jpeg</ns2:MimeType>\t<ns2:SignaturePKCS7>MIICyAYJKoZIhvcNAQcCoIICuTCCArUCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggGLMIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHsxggEEMIIBAAIBATA2MC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUCBQDBXZBUMAoGBiqFAwICCQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTQxMDI4MDg1ODAzWjAvBgkqhkiG9w0BCQQxIgQgVpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJAwCgYGKoUDAgITBQAEQIXxbWJSNu/oGOxFOKcDO5wE1riXWDqyffOPZnIfoUk3QNI/qvRas+0V+713qvOL9iGQ3QrKFD1PkeJVditDDP4=</ns2:SignaturePKCS7>\t</ns2:RefAttachmentHeader>";
        String hook = deps.inj.flushTagData(dumped, "ns2:CallerInformationSystemSignature");
        String hook2 = deps.inj.flushTagData(hook, "ns2:RefAttachmentHeaderList");
        System.out.println(hook2);
        String written = deps.inj.injectTagDirect(hook2, "ns2:RefAttachmentHeaderList", injected);
        System.out.println(written);

        deps.ebs.setinput(written.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
    }


    @Test
    public void sendhoocked__() throws Exception {
        System.out.println(dumped);
        String hook = deps.inj.flushTagData(dumped, "ns2:CallerInformationSystemSignature");
        String hook2 = deps.inj.flushTagData(hook, "ns2:RefAttachmentHeaderList");
        System.out.println(hook2);
        String written = deps.inj.injectTagDirect(hook2, "ns2:RefAttachmentHeaderList", preparedAudioTag);
        System.out.println(written);

        deps.ebs.setinput(written.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
    }


    @Test
    public void sendhoocked__2() throws Exception {
        String flushedprepared  = new String(trans.burnTabsAndNs(prepared2Tag.getBytes()));

        System.out.println("######>>>>\n"+ flushedprepared);

        String hook = deps.inj.flushTagData(dumped, "ns2:CallerInformationSystemSignature");
        String hook2 = deps.inj.flushTagData(hook, "ns2:RefAttachmentHeaderList");

        System.out.println(hook2);


        String written = deps.inj.injectTagDirect(hook2, "ns2:RefAttachmentHeaderList", flushedprepared);
        System.out.println(written);

        deps.ebs.setinput(written.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);
    }


    @Test
    public void sendhoocked124() throws Exception {
        System.out.println(dumped);
        String injected = "<ns2:RefAttachmentHeader>\t\t<ns2:uuid>e74b2cb9-5aa3-11e4-a9ff-d4c9eff07b77</ns2:uuid>\t\t<ns2:Hash>VpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJA=</ns2:Hash>\t\t<ns2:MimeType>image/jpeg</ns2:MimeType>\t<ns2:SignaturePKCS7>MIICyAYJKoZIhvcNAQcCoIICuTCCArUCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggGLMIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHsxggEEMIIBAAIBATA2MC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUCBQDBXZBUMAoGBiqFAwICCQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTQxMDI4MDg1ODAzWjAvBgkqhkiG9w0BCQQxIgQgVpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJAwCgYGKoUDAgITBQAEQIXxbWJSNu/oGOxFOKcDO5wE1riXWDqyffOPZnIfoUk3QNI/qvRas+0V+713qvOL9iGQ3QrKFD1PkeJVditDDP4=</ns2:SignaturePKCS7>\t</ns2:RefAttachmentHeader>";
       // String injected2 = inj.injectTag(injected, "ns2:uuid", e74b2cb9-5aa3-11e4-a9ff-d4c9eff07b77)
        String hook = deps.inj.flushTagData(dumped, "ns2:CallerInformationSystemSignature");
        String hook2 = deps.inj.flushTagData(hook, "ns2:RefAttachmentHeaderList");
        System.out.println(hook2);
        String hook3 = deps.inj.injectTagDirect(hook2,"ns2:RefAttachmentHeaderList", injected );
        System.out.println("\n\n"+hook3);

        deps.ebs.setinput(hook3.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);

    }


    @Test
    public void doubleinjected() throws Exception {
        System.out.println(dumped);
        String injected = "<ns2:RefAttachmentHeader>\t\t<ns2:uuid>e74b2cb9-5aa3-11e4-a9ff-d4c9eff07b77</ns2:uuid>\t\t<ns2:Hash>VpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJA=</ns2:Hash>\t\t<ns2:MimeType>image/jpeg</ns2:MimeType>\t<ns2:SignaturePKCS7>MIICyAYJKoZIhvcNAQcCoIICuTCCArUCAQExDDAKBgYqhQMCAgkFADALBgkqhkiG9w0BBwGgggGLMIIBhzCCATagAwIBAgIFAMFdkFQwCAYGKoUDAgIDMC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUwHhcNMTQwMjIxMTMzNDMyWhcNMTUwMjIxMTMzNDMyWjAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNPVjIxCzAJBgNVBAYTAlJVMGMwHAYGKoUDAgITMBIGByqFAwICJAAGByqFAwICHgEDQwAEQLjcuMDezt3MrljIr+54Cy64Gvgy8uuGgTpjvlrDAkiGdTL/m9EDDJvMARnMjzSb1JTxovUWfTV8j2bns+KZXNyjOzA5MA4GA1UdDwEB/wQEAwID6DATBgNVHSUEDDAKBggrBgEFBQcDAjASBgNVHRMBAf8ECDAGAQH/AgEFMAgGBiqFAwICAwNBAMVRmhKGKFtRbBlGLl++KtOAvm96C5wnj+6L/wMYpw7Gd7WBM21Zqh9wu+3eZotglDsJMEYbKgiLRprSxKz+DHsxggEEMIIBAAIBATA2MC0xEDAOBgNVBAsTB1NZU1RFTTExDDAKBgNVBAoTA09WMjELMAkGA1UEBhMCUlUCBQDBXZBUMAoGBiqFAwICCQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTQxMDI4MDg1ODAzWjAvBgkqhkiG9w0BCQQxIgQgVpT3sc999CJI8TVYX35ZZfXpc/dCWO5e1MgoUg8YiJAwCgYGKoUDAgITBQAEQIXxbWJSNu/oGOxFOKcDO5wE1riXWDqyffOPZnIfoUk3QNI/qvRas+0V+713qvOL9iGQ3QrKFD1PkeJVditDDP4=</ns2:SignaturePKCS7>\t</ns2:RefAttachmentHeader>";
        String injected2 = injected+injected;
        String hook = deps.inj.flushTagData(dumped, "ns2:CallerInformationSystemSignature");
        String hook2 = deps.inj.flushTagData(hook, "ns2:RefAttachmentHeaderList");
        System.out.println(hook2);
        String hook3 = deps.inj.injectTagDirect(hook2,"ns2:RefAttachmentHeaderList", injected2 );
        System.out.println("\n\n"+hook3);

        deps.ebs.setinput(hook3.getBytes());
        assertNotEquals(null, deps.ebs.GetSoap());
        assertNotEquals(null, deps.ebs.SignedSoap());
        String response = new String(deps.ebs.SendSoapSigned());
        System.out.println(response);

    }


}
