package util;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.URL;

public class ftpClient {
    private String address;
    public  int port;
    private String user;
    private String pass;
    private FTPClient ftp;
    public ftpClient(String address, String user, String pass){
        this.address = address;
        this.user=user;
        this.pass =pass;
    }

    public int open() throws IOException {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.connect(address, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return 1;
        }
        if (ftp.login(user, pass))
            return  0;
        return 1;
    }

    public void close() throws IOException {
        ftp.disconnect();
    }

    public int mkdir(String dirname) throws IOException {
        if (ftp.makeDirectory(dirname))
            return 0;
        return 1;
    }

    public int uploadfile(String source, String targetName) throws IOException {
        ftp.enterLocalPassiveMode();

        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        File localFile = new File(source);
        InputStream inputStream = new FileInputStream(localFile);
        System.out.println("Start uploading first file");
        boolean done = ftp.storeFile(targetName, inputStream);
        inputStream.close();
        if (done)
            return 0;
        return 1;
    }

    public int downloadFile(String remoteFile1, String PathToSave) throws IOException {
        ftp.enterLocalPassiveMode();
        File downloadFile1 = new File(PathToSave);
        OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
        boolean success = ftp.retrieveFile(remoteFile1, outputStream1);
        outputStream1.close();
        if (success)
            return 0;
        return 1;
    }

}
