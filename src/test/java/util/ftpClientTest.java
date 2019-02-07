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
    String photofile = "biophoto.jpg";
    String soundfile = "biosound.wav";
    public final String photodir = "f5cb6cae-1f13-11e9-8bb3-5706b6ad71d3";
    public final String sounddir = "e59a11cc-1f13-11e9-a54a-af41724ba888";

    String smev3addr = "smev3-n0.test.gosuslugi.ru";
    timeBasedUUID gen = new timeBasedUUID();
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
        String createDir = "7878878877";
        String filetoUpload="1.html";
        String targetname = "/"+createDir+"/temp000";//+"/"+filetoUpload;
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
        String createDir = "458787878712121254";
        String filetoUpload="1.html";
        String targetname = "/"+createDir+"/"+filetoUpload;;//+"/temp";//+"/"+filetoUpload;
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
      //  assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
        assertEquals(0, ftpcl.uploadfile(filetoUpload, targetname));
        assertEquals(0, ftpcl.downloadFile(targetname, "init88.html"));
       // assertEquals(1, ftpcl.downloadFile("8787878787687/kjjkkjk.njjkj", "fake.html"));
        assertEquals((new File("1.html")).length(), (new File("init88.html")).length());

    }

    @Test
    public void createSounddir() throws IOException {
        String createDir = gen.generate();
        System.out.println("generated>>\n"+createDir);
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;
        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
        ftpcl.mkdir(createDir);
        // assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
    }

    @Test
    public void createPhotodir() throws IOException {
        String createDir = gen.generate();
        System.out.println("generated>>\n"+createDir);
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;
        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
        ftpcl.mkdir(createDir);
        // assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
    }

    @Test
    public void checkbiofiles(){
        assertTrue(new File(photofile).exists());
        assertTrue(new File(soundfile).exists());
    }

    @Test
    public void uploadphoto() throws IOException {

            String filetoUpload=photofile;
            String dirtoupload = "/"+photodir+"/"+filetoUpload;//+filetoUpload;//+"/"+filetoUpload;
            ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
            ftpcl.port = 21;

            System.out.println("port=>>"+ftpcl.port);
            assertEquals(0, ftpcl.open());

            //   assertEquals(0, ftpcl.mkdir(createDir));

        //            assertEquals(0, ftpcl.uploadfile(filetoUpload, targetname));

            assertEquals(0, ftpcl.uploadfile(filetoUpload, dirtoupload));

    }

    @Test
    public void uploadsound() throws IOException {

        String filetoUpload=soundfile;
        String dirtoupload = "/"+sounddir+"/"+filetoUpload;//+filetoUpload;//+"/"+filetoUpload;
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());

        //   assertEquals(0, ftpcl.mkdir(createDir));

        //            assertEquals(0, ftpcl.uploadfile(filetoUpload, targetname));

        assertEquals(0, ftpcl.uploadfile(filetoUpload, dirtoupload));

    }


    @Test
    public void downloadphoto() throws IOException {
        String createDir =photodir;
        String filetoUpload="download.jpg";
        String targetname = "/"+createDir+"/"+filetoUpload;;//+"/temp";//+"/"+filetoUpload;
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());

        //  assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
        assertEquals(0, ftpcl.uploadfile(photofile, targetname));
        assertEquals(0, ftpcl.downloadFile(targetname, filetoUpload));
        // assertEquals(1, ftpcl.downloadFile("8787878787687/kjjkkjk.njjkj", "fake.html"));
        assertTrue((new File(filetoUpload)).exists());

    }


    @Test
    public void downloadsound() throws IOException {
        String createDir =sounddir;
        String filetoUpload="download.wav";
        String targetname = "/"+createDir+"/"+filetoUpload;;//+"/temp";//+"/"+filetoUpload;
        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        System.out.println("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());
        //  assertEquals(0, ftpcl.mkdir(createDir));
        assertEquals(1, ftpcl.mkdir(createDir));
        assertEquals(0, ftpcl.uploadfile(soundfile, targetname));
        assertEquals(0, ftpcl.downloadFile(targetname, filetoUpload));
        // assertEquals(1, ftpcl.downloadFile("8787878787687/kjjkkjk.njjkj", "fake.html"));
        assertTrue((new File(filetoUpload)).exists());

    }

    @Test
    public void uploadanddownloadPythonStyle() throws IOException {
        String photofile = "/home/roland/IdeaProjects/sm3/photos/p1.jpg";
        String soundfile = "/home/roland/IdeaProjects/sm3/tested.wav";

        ftpClient ftpcl = new ftpClient(smev3addr, "anonymous", "smev");
        ftpcl.port = 21;

        print("port=>>"+ftpcl.port);
        assertEquals(0, ftpcl.open());

        String imageuuid = new timeBasedUUID().generate();
        String sounduuid = new timeBasedUUID().generate();

        assertEquals(0, ftpcl.mkdir(imageuuid));
        assertEquals(0, ftpcl.mkdir(sounduuid));

        String storesound = sounduuid + "/tested.wav";
        String storeimage = imageuuid + "/tested.jpg";

        assertEquals(0, ftpcl.uploadfile(soundfile, storesound));
        assertEquals(0, ftpcl.uploadfile(photofile, storeimage));

        print("\n\n\nEXECUTE >>>>>"+ storesound);
        print("EXECUTE >>>>>"+ storeimage);

        ftpcl.close();


    }


    public void print(Object input){
        System.out.println(input);
    }

/*

    from ftplib import FTP
import uuid
import os
            imagename = '/home/roland/IdeaProjects/sm3/photos/p1.jpg'
    soundname = '/home/roland/IdeaProjects/sm3/tested.wav'
    imagefile=open(imagename,'rb')
    soundfile=open(soundname,'rb')
    print( os.path.getsize(imagename))
    print( os.path.getsize(soundname))

    session = FTP("smev3-n0.test.gosuslugi.ru")
session.login()

    imageuuid = str(uuid.uuid1())
    sounduuid = str(uuid.uuid1())

            session.mkd(imageuuid)
            session.mkd(sounduuid)

    commandstoresound = 'STOR '+sounduuid+'/tested.wav'
    commandstoreimage = 'STOR '+imageuuid+'/tested.jpg'

    print("EXECUTE >>>>>", commandstoresound)
session.storbinary(commandstoresound, soundfile)

    print("EXECUTE >>>>>", commandstoreimage)
session.storbinary(commandstoreimage, imagefile)

            session.quit()


    */

}