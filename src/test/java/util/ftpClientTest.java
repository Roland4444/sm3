package util;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ftpClientTest {
    String smev3addr = "smev3-n0.test.gosuslugi.ru";

    @Test
    public void open() throws IOException {
        mockftpServer mock =  new mockftpServer();
        ftpClient ftpcl = new ftpClient("localhost", "user", "password");
        ftpcl.port = mock.getPort();
        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
        ftpClient ftpcl2 = new ftpClient("localhost", "user", "pass0909090word");
        ftpcl2.port = mock.getPort();
        System.out.println("port=>>"+ftpcl2.port);
        assertEquals(1, ftpcl2.open());
    }

    @Test
    public void mkdir() throws IOException {
        String createDir = "test";
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;
        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
        ftpcl.mkdir(createDir);
       // assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
    }

    @Test
    public void uploadfile() throws IOException {
        String createDir = "787887887";
        String filetoUpload="1.html";
        String targetname = "/"+createDir+"/temp";//+"/"+filetoUpload;
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
        ftpcl.mkdir(createDir);
     //   assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
        assertEquals(0, ftpcl.uploadfile(filetoUpload, targetname));
    }


    @Test
    public void uploadfile2__() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(smev3addr, 21);
        ftpClient.enterLocalPassiveMode();
        ftpClient.login("anonymous", "smev");
        ftpClient.changeWorkingDirectory("test");

        Path p = Paths.get("1.html");
        byte[] arr = Files.readAllBytes(p);

        ByteArrayInputStream baos = new ByteArrayInputStream(arr);
        System.out.println(ftpClient.storeFile("test_file", baos));
        ftpClient.logout();
        ftpClient.disconnect();
    }


    @Test
    public void downloadFile() throws IOException {
        String createDir = "458787887778454";
        String filetoUpload="1.html";
        String targetname = "/"+createDir+"/temp";//+"/"+filetoUpload;
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
      //  assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
        assertEquals(0, ftpcl.uploadfile(filetoUpload, targetname));
        assertEquals(0, ftpcl.downloadFile(targetname, "init1.html"));
        assertEquals(1, ftpcl.downloadFile("8787878787687/kjjkkjk.njjkj", "fake.html"));
        assertEquals((new File("1.html")).length(), (new File("init1.html")).length());

    }
}