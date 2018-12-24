package schedulling.abstractions;

import standart.Standart;

import java.io.Serializable;

public class RequestData implements Serializable {
    public String Identifier;
    public String GennedId;
    public String Status;
    public String OriginalXML;
    public String ResponsedXML;
    public String operator;

    public static void printThat(RequestData input){
        System.out.println("\n\n\n******************\nprinting RequestData");
        System.out.println("GennedId=>"+input.GennedId);
        System.out.println("Identifier=>"+input.Identifier);
        System.out.println("Status=>"+input.Status);
        System.out.println("operator=>"+input.operator);

    }

}
