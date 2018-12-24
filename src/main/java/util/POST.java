package util;

import com.github.kevinsawicki.http.HttpRequest;



public class POST {
    public void send(String send, String adress) {
        int response = HttpRequest.post(adress).send(send.getBytes()).code();
        System.out.println("Code:"+response);

    }

    public void send(byte[] Send, String adress) {

        int response = HttpRequest.post(adress).send(Send).code();

        System.out.println("Code:"+response);

    }
    public static void main(String[] args)  {
        POST p = new POST();
        p.send("hey", "http://80.87.98.54:10000/");
    }
}
